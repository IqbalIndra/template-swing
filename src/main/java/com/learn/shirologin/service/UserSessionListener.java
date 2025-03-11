/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

/**
 *
 * @author KBDSI-IQBAL
 */
@Slf4j
public class UserSessionListener extends SessionListenerAdapter{

    @Override
    public void onExpiration(Session session) {
        log.info("Session Expired . {}",session.getId());
    }

    @Override
    public void onStart(Session session) {
        log.info("Session Started . {}",session.getId());
    }

    @Override
    public void onStop(Session session) {
        log.info("Session stop . {}",session.getId());
    }
    
}
