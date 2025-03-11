/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.config;

import com.learn.shirologin.service.UserSessionListener;
import java.util.Arrays;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author KBDSI-IQBAL
 */
@Configuration
public class ShiroLoginConfiguration {
    
        @Bean
        public Realm realm(){
            return new LoginRealm();
        }
        
        @Bean
        public SessionListener userSessionListener(){
            return new UserSessionListener();
        }
        
        @Bean
        public SessionManager sessionManager(){
            DefaultSessionManager defaultSessionManager = new DefaultSessionManager();
            defaultSessionManager.setGlobalSessionTimeout(60000);
            defaultSessionManager.setSessionValidationSchedulerEnabled(true);
            defaultSessionManager.setSessionListeners(Arrays.asList(userSessionListener()));
            return defaultSessionManager;
        }
        
        
        @Bean
        public org.apache.shiro.mgt.SecurityManager securityManager(){
            DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager(realm());
            defaultSecurityManager.setSessionManager(sessionManager());
            return defaultSecurityManager;
        }
    
}
