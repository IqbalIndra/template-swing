package com.learn.shirologin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum CriteriaType {
    BENEFIT("Benefit"),
    COST("Cost");

    private final String name;

    public static CriteriaType valueOfType(String status){
        return Stream.of(values())
                .filter(r -> r.name.equalsIgnoreCase(status))
                .findFirst()
                .orElse(null);
    }
}
