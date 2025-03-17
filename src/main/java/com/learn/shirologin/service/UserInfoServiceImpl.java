/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.service;

import com.learn.shirologin.model.UserInfo;
import com.learn.shirologin.repository.UserInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author KBDSI-IQBAL
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService{
    
    private final UserInfoRepository userInfoRepository;

    @Override
    public void login(String username, String password) throws AuthenticationException{
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // get the Subject instance for the current thread
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser == null)
                return;
            currentUser.logout();

            Session session = currentUser.getSession(false);
            if (session != null)
                session.stop();
        // attempt to authenticate the user
            currentUser.login(token);
    }

    @Override
    public List<UserInfo> getAllUserInfo() {
        return userInfoRepository.findAll();
    }

    @Override
    public Page<UserInfo> getAllUserInfo(Pageable pageable) {
        return userInfoRepository.findByPagination(pageable);
    }

    @Override
    public UserInfo save(UserInfo info) {
        return userInfoRepository.save(info);
    }

    @Override
    public UserInfo update(UserInfo info) {
        return userInfoRepository.update(info);
    }

    @Override
    public void delete(UserInfo info) {
        userInfoRepository.findById(
                info.getId()
        ).orElseThrow(() -> new RuntimeException("User not found"));
        userInfoRepository.delete(info.getId());
    }

}
