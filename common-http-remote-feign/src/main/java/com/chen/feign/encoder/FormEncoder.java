package com.chen.feign.encoder;

import com.chen.feign.convertor.ConvertException;
import com.chen.feign.convertor.GsonObjectConvertor;
import com.chen.feign.convertor.ObjectConvertor;
import com.chen.utils.LoggerUtil;
import feign.RequestTemplate;
import feign.codec.Encoder;
import feign.form.FormDataProcessor;
import feign.form.FormEncodedDataProcessor;
import feign.form.MultipartEncodedDataProcessor;
import lombok.val;

import java.lang.reflect.Type;
import java.util.*;

/**
 *
 * 对fegin-form的一点封装，
 * feign-form需要指定参数名 post(@Param("email") String email, @Param("password") String password);
 * 本类会将注解了@FormBean的参数转为同等效果，不需指定@Param
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/19
 */
public class FormEncoder implements Encoder {

    private final Encoder deligate;

    private final Map<String, FormDataProcessor> processors;

    private ObjectConvertor objectConvertor;

    public static  String FORM_PARAM_NAME="@FORM@";

    private String formParamName;


    /**
     * Default {@code FormEncoder} constructor.
     * <p>
     * Sets {@link feign.codec.Encoder.Default} instance as delegate encoder.
     */
    public FormEncoder() {
        this(new Encoder.Default());
        objectConvertor=GsonObjectConvertor.getInstance();
    }

    public FormEncoder(Encoder delegate){
        this(delegate,GsonObjectConvertor.getInstance(),FORM_PARAM_NAME);
    }
    public FormEncoder(Encoder delegate,ObjectConvertor objectConvertor,String formParamName) {
        this.formParamName = formParamName;
        this.deligate = delegate;
        this.objectConvertor=objectConvertor;
        processors = new HashMap<String, FormDataProcessor>(2, 1.F);

        FormEncodedDataProcessor formEncodedDataProcessor = new FormEncodedDataProcessor();
        processors.put(formEncodedDataProcessor.getSupportetContentType().toLowerCase(),
                formEncodedDataProcessor);

        MultipartEncodedDataProcessor multipartEncodedDataProcessor = new MultipartEncodedDataProcessor();
        processors.put(multipartEncodedDataProcessor.getSupportetContentType().toLowerCase(),
                multipartEncodedDataProcessor);
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {


        String formType = "";
        for (Map.Entry<String, Collection<String>> entry : template.headers().entrySet()) {
            if (!entry.getKey().equalsIgnoreCase("Content-Type")) {
                continue;
            }
            for (String contentType : entry.getValue()) {
                if (contentType != null && processors.containsKey(contentType.toLowerCase())) {
                    formType = contentType;

                    //form表单提交，非map对象直接用gson转为map对象
                    if (!MAP_STRING_WILDCARD.equals(bodyType)&&objectConvertor!=null) {
                        try {
                            object=objectConvertor.toMap(object);
                            bodyType = MAP_STRING_WILDCARD;
                        } catch (ConvertException e) {
                            LoggerUtil.log("cannot convert this object");
                        }
                    }

                    break;
                }
            }
            if (!formType.isEmpty()) {
                break;
            }
        }

        if (formType.isEmpty()) {
            deligate.encode(object, bodyType, template);
            return;
        }

        if (!MAP_STRING_WILDCARD.equals(bodyType)) {
            deligate.encode(object, bodyType, template);
            return;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) object;
        formObjectExpand(data);
        processors.get(formType).process(data, template);
    }

    private void formObjectExpand(Map<String, Object> data) {
        List<String> readyRemove=new ArrayList<>();
        Map<String,Object> insert=new HashMap<>();
        for(String key:data.keySet()){
            if(key.startsWith(FORM_PARAM_NAME)){
                Object value = data.get(key);
                readyRemove.add(key);
                try {
                    insert.putAll(objectConvertor.toMap(value));
                } catch (ConvertException e) {
                    LoggerUtil.logException(e);
                }
            }
        }
        //ConcurrentModificationException
        data.putAll(insert);
        for (String key : readyRemove) {
            data.remove(key);
        }
    }


}

