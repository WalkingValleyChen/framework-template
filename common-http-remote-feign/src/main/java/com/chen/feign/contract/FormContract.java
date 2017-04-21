package com.chen.feign.contract;

import com.chen.feign.annotation.Form;
import com.chen.feign.convertor.GsonObjectConvertor;
import com.chen.feign.convertor.ObjectConvertor;
import feign.Contract;
import feign.MethodMetadata;
import feign.Param;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import static feign.Util.checkState;
import static feign.Util.emptyToNull;

/**
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/20
 */
public class FormContract extends Contract.Default {

    public static  String FORM_PARAM_NAME="@FORM@";

    private String formParamName;

    public FormContract() {
        this(FORM_PARAM_NAME);
    }

    public FormContract(String formParamName) {
        this.formParamName = formParamName;
    }

    @Override
    protected boolean processAnnotationsOnParameter(MethodMetadata data, Annotation[] annotations, int paramIndex) {
        boolean isHttpAnnotation = super.processAnnotationsOnParameter(data, annotations, paramIndex);
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType == Form.class) {
                Form paramAnnotation = (Form) annotation;
                String name=formParamName+paramIndex;
                nameParam(data, name, paramIndex);
                Class<? extends Param.Expander> expander = paramAnnotation.expander();
                if (expander != Param.ToStringExpander.class) {
                    data.indexToExpanderClass().put(paramIndex, expander);
                }
                data.indexToEncoded().put(paramIndex, paramAnnotation.encoded());
                isHttpAnnotation = true;
                String varName = '{' + name + '}';
                if (!data.template().url().contains(varName) &&
                        !searchMapValuesContainsSubstring(data.template().queries(), varName) &&
                        !searchMapValuesContainsSubstring(data.template().headers(), varName)) {
                    data.formParams().add(name);
                }
            }


        }
        return isHttpAnnotation;
    }

    private static <K, V> boolean searchMapValuesContainsSubstring(Map<K, Collection<String>> map,
                                                                   String search) {
        Collection<Collection<String>> values = map.values();
        if (values == null) {
            return false;
        }

        for (Collection<String> entry : values) {
            for (String value : entry) {
                if (value.contains(search)) {
                    return true;
                }
            }
        }

        return false;
    }
}
