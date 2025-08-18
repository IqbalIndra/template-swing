package com.learn.shirologin.ui.swa.validation;

import com.learn.shirologin.common.validation.ValidationError;
import com.learn.shirologin.common.validation.ValidationSupport;
import com.learn.shirologin.common.validation.Validator;
import com.learn.shirologin.model.AlternativeDataSource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AlternativeDataSourceValidator extends ValidationSupport implements Validator<AlternativeDataSource> {
    @Override
    public Optional<ValidationError> validate(AlternativeDataSource alternativeDataSource) {
        if(isNullOrEmptyString(alternativeDataSource.getCode())
        || isNull(alternativeDataSource.getFileSource())
        || isNull(alternativeDataSource.getAlternativeCriteria()))
            return Optional.of(new ValidationError("Data tidak boleh kosong !"));
        return Optional.empty();
    }
}
