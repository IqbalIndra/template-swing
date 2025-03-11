/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.ui.main.view;

import com.learn.shirologin.util.Borders;
import java.awt.Dimension;
import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 *
 * @author KBDSI-IQBAL
 */
@Component
@Getter
public class MainFrame extends JFrame{
    
    @Setter
    private boolean unsaved = false; 
    
    @PostConstruct
    private void prepareFrame(){
        setFrameUp();
    }

    private void setFrameUp() {
        getRootPane().setBorder(Borders.createEmptyBorder());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    public void showView(String title, JPanel jPanel, Dimension dimension){
        setTitle(title);
        
        if(dimension != null){
            setPreferredSize(dimension);
        }else{
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        
        getContentPane().removeAll();
        add(jPanel);   
    }
    
}