/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware{
    
    private static ApplicationContext applicationContext;
    
    public static <T> T getBean(Class<T> type) {
      return applicationContext.getBean(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        ApplicationContextHolder.applicationContext = ac;
    }
    
}
