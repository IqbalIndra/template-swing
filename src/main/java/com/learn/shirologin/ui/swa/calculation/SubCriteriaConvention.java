package com.learn.shirologin.ui.swa.calculation;

import com.learn.shirologin.model.OperatorType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Component
public class SubCriteriaConvention implements Convention{

    @Override
    public boolean check(String operator, Double variable, Double minValue, Double maxValue) {
        if(operator.equals(OperatorType.GREATER_THAN.getSymbol()))
            return variable >= maxValue;
        else if(operator.equals(OperatorType.LESS_THAN.getSymbol()))
            return variable <= minValue;
        else if(operator.equals(OperatorType.EQUALS.getSymbol()))
            return Objects.equals(variable, maxValue);
        else
            return variable >= minValue && variable <= maxValue;
    }
}
