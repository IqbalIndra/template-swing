/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.service;

import com.learn.shirologin.model.UserInfo;
import java.util.List;

/**
 *
 * @author KBDSI-IQBAL
 */
public interface UserInfoService {
   void login(String username, String password);
    List<UserInfo> getAllUserInfo();
}
