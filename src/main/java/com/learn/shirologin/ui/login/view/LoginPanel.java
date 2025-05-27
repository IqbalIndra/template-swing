/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.login.view;

import com.learn.shirologin.ui.login.model.LoginEntity;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@Getter
public class LoginPanel extends JPanel{
    private JLabel labelUsername;
    private JLabel labelPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton buttonLogin;
    private JButton buttonCancel;
    
    
    @PostConstruct
    private void preparePanel(){
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setLayout(new MigLayout("align 50% 50%"));
    }

    private void initComponents() {
      labelUsername = new JLabel("Username");
      labelPassword = new JLabel("Password");
      txtUsername = new JTextField(20);
      txtPassword = new JPasswordField(20);
      buttonLogin = new JButton("Login");
      buttonCancel = new JButton("Cancel");
      
      add(labelUsername);
      add(txtUsername, "wrap");
      add(labelPassword);
      add(txtPassword,"wrap");
      add(buttonLogin);
      add(buttonCancel);
    }
    
    
    public void clearForm(){
        txtUsername.setText("");
        txtPassword.setText("");
    }
    
    public LoginEntity getRequestFromForm(){
        return LoginEntity
                .of()
                .username(txtUsername.getText())
                .password(new String(txtPassword.getPassword()))
                .build();
    }
    
    
}
