/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.main.view;

import com.learn.shirologin.ui.dashboard.view.DashboardPanel;
import com.learn.shirologin.ui.login.view.LoginPanel;
import com.learn.shirologin.util.Borders;

import java.awt.*;
import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.learn.shirologin.util.ConstantParams;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@Getter
@RequiredArgsConstructor
public class MainFrame extends JFrame{
    private JPanel cards;
    private final LoginPanel loginPanel;
    private final DashboardPanel dashboardPanel;

    @PostConstruct
    private void prepareFrame(){
        setLayoutFrame();
        setFrameUp();
    }

    private void setLayoutFrame() {
        cards = new JPanel(new CardLayout());
        cards.add(loginPanel, ConstantParams.LOGIN_PANEL);
        cards.add(dashboardPanel, ConstantParams.DASHBOARD_PANEL);
        getContentPane().add(cards);
    }

    private void setFrameUp() {
        getRootPane().setBorder(Borders.createEmptyBorder());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    public void showViewCardPanel(String cardName){
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, cardName);
    }
    
}