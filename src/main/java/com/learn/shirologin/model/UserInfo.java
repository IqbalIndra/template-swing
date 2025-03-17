/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author KBDSI-IQBAL
 */
@Data
@Builder(builderMethodName = "of")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 2804537642267854538L;

    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private boolean deleted;
}
