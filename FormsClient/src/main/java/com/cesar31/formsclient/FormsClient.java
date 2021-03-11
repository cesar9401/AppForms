package com.cesar31.formsclient;

import com.cesar31.formsclient.ui.MainView;

/**
 *
 * @author cesar31
 */
public class FormsClient {

    public static void main(String[] args) {
        // Write your code here
        java.awt.EventQueue.invokeLater(() -> {
            MainView view = new MainView();
            view.setLocationRelativeTo(null);
            view.setVisible(true);
        });
    }
}
