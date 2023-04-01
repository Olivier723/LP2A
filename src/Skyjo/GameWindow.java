package Skyjo;

import javax.swing.*;

public class GameWindow extends JFrame {

    private final JFrame window;

    GameWindow(String title, int w, int h) {
        window = new JFrame(title);
        window.setSize(w, h);
        window.setVisible(true);
    }


}
