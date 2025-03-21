package com.learn.shirologin.session;

import com.learn.shirologin.ui.login.controller.LoginController;
import com.learn.shirologin.ui.main.view.MainFrame;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ReflectionUtils;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Slf4j
public class UserSessionValidationScheduler implements SessionValidationScheduler,Runnable {

    private JdbcTemplate jdbcTemplate;
    private DefaultSessionManager defaultSessionManager;
    private LoginController loginController;
    private ScheduledExecutorService scheduledExecutorService;

    @Setter
    private long interval = DefaultSessionManager.DEFAULT_SESSION_VALIDATION_INTERVAL;

    @Setter
    private boolean enabled = false;

    public UserSessionValidationScheduler(){

    }

    @Autowired
    public void setDefaultSessionManager(DefaultSessionManager defaultSessionManager) {
        this.defaultSessionManager = defaultSessionManager;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setLoginController(LoginController loginController){
        this.loginController = loginController;
    }

    @Override
    public void run() {
        if(log.isDebugEnabled()){
            log.debug("Executing session validation...");
        }
        long startTime = System.currentTimeMillis();


        String sql = "select session from sessions limit ?,?";
        int start = 0;
        int size = 20;
        List<String> sessionList = jdbcTemplate.queryForList(sql, String.class, start, size);
        while(sessionList.size() > 0) {
            for(String sessionStr : sessionList) {
                try {
                    Session session = SessionSerializable.deserialize(sessionStr);
                    Method validateMethod = ReflectionUtils.findMethod(AbstractValidatingSessionManager.class, "validate", Session.class, SessionKey.class);
                    validateMethod.setAccessible(true);
                    ReflectionUtils.invokeMethod(validateMethod, defaultSessionManager, session, new DefaultSessionKey(session.getId()));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Login Expired. Please login !", "Session",JOptionPane.ERROR_MESSAGE);
                    Subject subject = SecurityUtils.getSubject();
                    if(subject.isAuthenticated())
                        subject.logout();

                    loginController.viewToLoginPanel();
                }
            }
            start = start + size;
            sessionList = jdbcTemplate.queryForList(sql, String.class, start, size);
        }

        long stopTime = System.currentTimeMillis();

        if(log.isDebugEnabled()){
            log.debug("Session validation completed successfully in " + (stopTime - startTime) + " milliseconds.");
        }
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void enableSessionValidation() {
        if (this.interval > 0L) {
            this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                }
            });
            this.scheduledExecutorService.scheduleAtFixedRate(this, interval, interval, TimeUnit.MILLISECONDS);
            this.enabled = true;
        }
    }

    @Override
    public void disableSessionValidation() {
        this.scheduledExecutorService.shutdownNow();
        this.enabled = false;
    }
}
