package Skyjo;

import javax.swing.*;

public class GameWindow extends JFrame {
    private final JTextField textField;

    GameWindow(String title, int w, int h) {
        this.setTitle(title);
        this.setSize(w, h);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        textField = new JTextField(10);
        this.add(textField);
    }


}
