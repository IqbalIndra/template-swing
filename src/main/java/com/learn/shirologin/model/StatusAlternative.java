package com.learn.shirologin.model;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum StatusAlternative {
    COMPLETED("Selesai"),
    ON_PROCESS("Dalam Proses");

    private final String name;

    public static StatusAlternative valueOfStatus(String status){
        return Stream.of(values())
                .filter(r -> r.name.equalsIgnoreCase(status))
                .findFirst()
                .orElse(null);
    }
}
