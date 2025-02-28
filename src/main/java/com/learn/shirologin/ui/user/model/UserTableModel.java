/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.user.model;

import com.learn.shirologin.model.UserInfo;
import com.learn.shirologin.ui.base.model.DefaultTableModel;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@AllArgsConstructor
public class UserTableModel extends DefaultTableModel<UserInfo>{
    
    private static final int USERNAME_INDEX = 0;
    private static final int PASSWORD_INDEX= 2;
    private static final int EMAIL_INDEX = 1;
    private static final int ROLE_INDEX = 3;

    @Override
    public String[] getColumnLabels() {
        return new String[] {
            "Username",
            "Email",
            "Password",
            "Role"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UserInfo userInfo = datas.get(rowIndex);
        switch(columnIndex) {
            case USERNAME_INDEX :
               return userInfo.getUsername();
            case PASSWORD_INDEX :
                return userInfo.getPassword();
            case EMAIL_INDEX : 
                return userInfo.getEmail();
            case ROLE_INDEX : 
                return userInfo.getRole();
            default : 
                return Strings.EMPTY;
        }
    }
    
}
