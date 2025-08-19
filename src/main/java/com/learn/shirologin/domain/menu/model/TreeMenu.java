/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.domain.menu.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 *
 * @author KBDSI-IQBAL
 */
@Data
@AllArgsConstructor
public class TreeMenu {
    private String code;
    private String name;
    private String parentCode;
    private String path;
    
    @Override
    public String toString(){
        return name;
    }
    
}
