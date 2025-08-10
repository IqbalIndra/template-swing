package com.learn.shirologin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(builderMethodName = "of")
public class SubCriteria {
    private Long id;
    private String name;
    private OperatorType operator;
    private Double weight;
    private Double minValue;
    private Double maxValue;
    private boolean deleted;
}
