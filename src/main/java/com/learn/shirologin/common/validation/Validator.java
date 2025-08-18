package com.learn.shirologin.common.validation;

import java.util.Optional;

public interface Validator<K>{
    Optional<ValidationError> validate(K k);
}
