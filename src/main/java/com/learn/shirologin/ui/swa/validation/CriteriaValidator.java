package com.learn.shirologin.ui.swa.validation;

import com.learn.shirologin.common.validation.ValidationError;
import com.learn.shirologin.common.validation.ValidationSupport;
import com.learn.shirologin.common.validation.Validator;
import com.learn.shirologin.model.AlternativeDataSource;
import com.learn.shirologin.model.Criteria;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CriteriaValidator extends ValidationSupport implements Validator<Criteria> {
    @Override
    public Optional<ValidationError> validate(Criteria criteria) {
        if(isNullOrEmptyString(criteria.getName())
                || isNull(criteria.getWeight())
                || isNullOrEmptyString(criteria.getType().getName()))
            return Optional.of(new ValidationError("Data tidak boleh kosong !"));
        return Optional.empty();
    }
}
