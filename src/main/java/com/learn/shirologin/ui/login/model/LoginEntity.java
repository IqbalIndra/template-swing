/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.login.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author KBDSI-IQBAL
 */
@Data
@ToString
@Builder(builderMethodName = "of")
public class LoginEntity {
    private String username;
    private String password;
}
