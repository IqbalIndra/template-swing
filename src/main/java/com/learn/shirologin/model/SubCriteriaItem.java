package com.learn.shirologin.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(builderMethodName = "of")
public class SubCriteriaItem {
    private OperatorType operator;
    private Double weight;
    private Double minValue;
    private Double maxValue;
}
