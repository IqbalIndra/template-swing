/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.config;

import com.learn.shirologin.model.UserInfo;
import com.learn.shirologin.repository.UserInfoRepository;
import java.util.Collections;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author KBDSI-IQBAL
 */
public class LoginRealm extends AuthorizingRealm{
    
    @Autowired  private UserInfoRepository userInfoRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo user =  (UserInfo) pc.getPrimaryPrincipal();
        authorizationInfo.setRoles(Collections.singleton(user.getRole().name()));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String password = new String((char[]) upToken.getPassword());
        
        UserInfo userInfo = userInfoRepository.findByUsername(upToken.getUsername())
                .orElseThrow(()-> new UnknownAccountException("Invalid username or password"));
        
        boolean passwordMatches = password.equalsIgnoreCase(userInfo.getPassword());

        if (!passwordMatches) {
            throw new IncorrectCredentialsException("Invalid username or password");
        }

        SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(userInfo,
                password, getName());
    
        return authInfo;
    }
    
}
