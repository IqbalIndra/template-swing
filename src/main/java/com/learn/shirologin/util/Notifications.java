package com.learn.shirologin.util;

import javax.swing.*;

public class Notifications {
    public static void showFormValidationAlert(JComponent parent, String title, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

}
