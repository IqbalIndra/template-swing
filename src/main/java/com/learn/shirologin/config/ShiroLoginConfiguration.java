/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.config;

import com.learn.shirologin.service.UserSessionListener;
import java.util.Arrays;

import com.learn.shirologin.session.UserSessionRepository;
import com.learn.shirologin.session.UserSessionValidationScheduler;
import com.learn.shirologin.ui.login.controller.LoginController;
import com.learn.shirologin.ui.main.view.MainFrame;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;

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
        public SessionDAO sessionDAO(@Autowired JdbcTemplate jdbcTemplate){
            return new UserSessionRepository(jdbcTemplate);
        }

        @Bean
        public UserSessionValidationScheduler userSessionValidationScheduler(@Autowired JdbcTemplate jdbcTemplate,
                                                                             @Autowired DefaultSessionManager sessionManager,
                                                                             @Autowired LoginController loginController,
                                                                             @Autowired MainFrame mainFrame){
            UserSessionValidationScheduler userSessionValidationScheduler = new UserSessionValidationScheduler();
            userSessionValidationScheduler.setJdbcTemplate(jdbcTemplate);
            userSessionValidationScheduler.setDefaultSessionManager(sessionManager);
            userSessionValidationScheduler.setLoginController(loginController);
            userSessionValidationScheduler.setFrame(mainFrame);
            userSessionValidationScheduler.setInterval(10000);
            return userSessionValidationScheduler;
        }
        
        @Bean
        public SessionListener userSessionListener(){
            return new UserSessionListener();
        }
        
        @Bean
        public DefaultSessionManager sessionManager(@Autowired SessionDAO sessionDAO,
                                                    @Lazy @Autowired UserSessionValidationScheduler userSessionValidationScheduler){
            DefaultSessionManager defaultSessionManager = new DefaultSessionManager();
            defaultSessionManager.setGlobalSessionTimeout(180000);
            defaultSessionManager.setSessionValidationSchedulerEnabled(true);
            defaultSessionManager.setSessionListeners(Arrays.asList(userSessionListener()));
            defaultSessionManager.setSessionDAO(sessionDAO);
            defaultSessionManager.setDeleteInvalidSessions(true);
            defaultSessionManager.setSessionValidationScheduler(userSessionValidationScheduler);

            return defaultSessionManager;
        }
        
        
        @Bean
        public org.apache.shiro.mgt.SecurityManager securityManager(@Autowired DefaultSessionManager sessionManager){
            DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager(realm());
            defaultSecurityManager.setSessionManager(sessionManager);
            return defaultSecurityManager;
        }
    
}
