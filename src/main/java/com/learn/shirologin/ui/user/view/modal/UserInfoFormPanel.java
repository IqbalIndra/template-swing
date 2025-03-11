/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.user.view.modal;

import com.learn.shirologin.model.UserInfo;
import com.learn.shirologin.ui.user.model.UserRoleComboBoxModel;
import com.learn.shirologin.util.Borders;
import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import lombok.RequiredArgsConstructor;
import net.miginfocom.swing.MigLayout;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@RequiredArgsConstructor
public class UserInfoFormPanel extends JPanel{
    private final UserRoleComboBoxModel userRoleComboBoxModel;
    private JLabel usernameLbl;
    private JLabel emailLbl;
    private JLabel passwordLbl;
    private JLabel roleLbl;
    private JTextField usernameTxt;
    private JTextField emailTxt;
    private JPasswordField passwordTxt;
    private JComboBox userRoleComboBox;
    
    
    @PostConstruct
    private void preparePanel(){
        setPanelUp();
        initComponents();
    }

    private void initComponents() {
        usernameLbl = new JLabel("Username:");
        emailLbl = new JLabel("Email:");
        passwordLbl = new JLabel("Password:");
        roleLbl = new JLabel("Role:");
        usernameTxt = new JTextField(20);
        emailTxt = new JTextField(50);
        passwordTxt = new JPasswordField(20);
        userRoleComboBox = new JComboBox<>(userRoleComboBoxModel);    
        
        add(usernameLbl,"left");
        add(usernameTxt,"pushx,growx");
        add(emailLbl,"left");
        add(emailTxt,"pushx,growx");
        add(passwordLbl,"left");
        add(passwordTxt,"pushx,growx");
        add(roleLbl,"left");
        add(userRoleComboBox,"pushx,growx");
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new MigLayout("wrap 2","[]10[]"));
    }
    
    public UserInfo getEntityFromForm(){
        return UserInfo.of()
                .username(usernameTxt.getText())
                .email(emailTxt.getText())
                .password(String.valueOf(passwordTxt.getPassword()))
                .role(userRoleComboBoxModel.getSelectedItem())
                .build();
    }
    
    public void clearForm(){
        usernameTxt.setText(Strings.EMPTY);
        emailTxt.setText(Strings.EMPTY);
        passwordTxt.setText(Strings.EMPTY);
        userRoleComboBox.setSelectedIndex(-1);
    }
}
