/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.model;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;

/**
 *
 * @author KBDSI-IQBAL
 */

@AllArgsConstructor
public enum Role {
    ADMIN("Admin"),
    USER("User");
    
    private final String name;
    
    public static Role valueOfRole(String role){
        return Stream.of(values())
                .filter(r -> r.name.equalsIgnoreCase(role))
                .findFirst()
                .orElse(null);
                
    }
}
