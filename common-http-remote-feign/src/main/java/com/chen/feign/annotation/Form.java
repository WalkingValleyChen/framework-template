package com.chen.feign.annotation;

import com.chen.feign.convertor.GsonObjectConvertor;
import com.chen.feign.convertor.ObjectConvertor;
import feign.Param;
import feign.QueryMap;

import java.lang.annotation.Retention;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/20
 */
@Retention(RUNTIME)
@java.lang.annotation.Target(PARAMETER)
public @interface Form {


    /**
     * How to expand the value of this parameter, if {@link Param.ToStringExpander} isn't adequate.
     */
    Class<? extends Param.Expander> expander() default Param.ToStringExpander.class;

    /**
     * Specifies whether argument is already encoded
     * The value is ignored for headers (headers are never encoded)
     *
     * @see QueryMap#encoded
     */
    boolean encoded() default false;

    interface Expander {

        /**
         * Expands the value into a string. Does not accept or return null.
         */
        String expand(Object value);
    }

    final class ToStringExpander implements Param.Expander {

        @Override
        public String expand(Object value) {
            return value.toString();
        }
    }
}
