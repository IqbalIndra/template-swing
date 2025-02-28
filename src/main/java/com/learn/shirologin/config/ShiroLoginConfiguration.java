/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.config;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
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
        public org.apache.shiro.mgt.SecurityManager securityManager(){
            return new DefaultSecurityManager(realm());
        }
    
}
