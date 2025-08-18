package com.learn.shirologin.common.validation;

import org.springframework.util.ObjectUtils;

public abstract class ValidationSupport {

    protected boolean isNullOrEmptyString(String value){
        return value.isEmpty() || ObjectUtils.isEmpty(value);
    }

    protected boolean isNull(Object value){
        return ObjectUtils.isEmpty(value);
    }

    protected boolean isValueNotGreaterThanZero(Long value){
        return isNull(value) || value <= 0;
    }
}
