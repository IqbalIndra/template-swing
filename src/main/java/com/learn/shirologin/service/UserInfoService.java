/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.service;

import com.learn.shirologin.model.UserInfo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author KBDSI-IQBAL
 */
public interface UserInfoService {
   void login(String username, String password);
    List<UserInfo> getAllUserInfo();
    Page<UserInfo> getAllUserInfo(Pageable pageable);
    UserInfo save(UserInfo info);
    UserInfo update(UserInfo info);
    void delete(UserInfo info);
}
