/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.util;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author KBDSI-IQBAL
 */
public class Borders {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 20;

    public static Border createEmptyBorder() {
        return BorderFactory.createEmptyBorder(HEIGHT, WIDTH, HEIGHT, WIDTH);
    }
}
