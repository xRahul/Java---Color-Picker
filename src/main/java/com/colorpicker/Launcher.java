package com.colorpicker;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Launcher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ColorPickerFrame go = new ColorPickerFrame();
            go.setBackground(Color.WHITE);
            go.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            go.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    JOptionPane.showMessageDialog(null,
                        "Thanks for using ColorPicker.\nuserID- @rahulgr8888\nLook me up anywhere",
                        "Closing...",
                        JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            });

            go.setExtendedState(JFrame.MAXIMIZED_BOTH);

            if (isMacOSX()) {
                enableFullScreenMode(go);
            }

            go.setVisible(true);
        });
    }

    public static void enableFullScreenMode(Window window) {
        if (window instanceof JFrame) {
            ((JFrame) window).getRootPane().putClientProperty("apple.awt.fullscreenable", true);
        }
    }

    private static boolean isMacOSX() {
        String osName = System.getProperty("os.name");
        return osName != null && osName.contains("Mac OS X");
    }
}
