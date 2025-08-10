package com.learn.shirologin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum OperatorType {
    GREATER_THAN(">="),
    LESS_THAN("<="),
    EQUALS("="),
    BETWEEN("<=>");

    private final String symbol;

    public static OperatorType valueOfType(String type){
        return Stream.of(values())
                .filter(r -> r.symbol.equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }
}
