package com.learn.shirologin.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder(builderMethodName = "of")
public class Criteria {
    private Long id;
    private String name;
    private CriteriaType type;
    private Double weight;
    private boolean deleted;

    @Override
    public String toString() {
        return this.name;
    }
}
