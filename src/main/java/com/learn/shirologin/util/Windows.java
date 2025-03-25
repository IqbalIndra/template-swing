package com.learn.shirologin.util;

import javax.swing.*;
import java.awt.*;

import static java.awt.Window.getWindows;

public class Windows {

    public static void closeAllDialogs()
    {
        Window[] windows = getWindows();

        for (Window window : windows)
        {
            if (window instanceof JDialog)
            {
                window.dispose();
            }
        }
    }
}
