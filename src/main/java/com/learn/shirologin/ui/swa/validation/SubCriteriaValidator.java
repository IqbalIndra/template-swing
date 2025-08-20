package com.learn.shirologin.ui.swa.validation;

import com.learn.shirologin.common.validation.ValidationError;
import com.learn.shirologin.common.validation.ValidationSupport;
import com.learn.shirologin.common.validation.Validator;
import com.learn.shirologin.model.Criteria;
import com.learn.shirologin.model.SubCriteria;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubCriteriaValidator extends ValidationSupport implements Validator<SubCriteria> {

    @Override
    public Optional<ValidationError> validate(SubCriteria subCriteria) {
        if(isNullOrEmptyString(subCriteria.getName())
                || isNullOrEmptyString(subCriteria.getName())
                || isNullOrEmptyString(subCriteria.getOperator().getSymbol())
                || isNull(subCriteria.getWeight())
                || isNull(subCriteria.getMinValue())
                || isNull(subCriteria.getMaxValue()))
            return Optional.of(new ValidationError("Data tidak boleh kosong !"));
        return Optional.empty();
    }
}
