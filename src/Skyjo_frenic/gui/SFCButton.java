package Skyjo_frenic.gui;

import javax.swing.*;
import java.awt.*;

public class SFCButton extends JButton implements EasyBackgroundImage, SFCComponent {
    private Image img;

    public SFCButton (String text) {
        super.setText(text);
        img = null;
    }

    public SFCButton (String text, String imgPath) {
        super.setText(text);
        img = new ImageIcon(imgPath).getImage();
    }

    public SFCButton (String text, Image img) {
        super.setText(text);
        this.img = img;
    }

    public SFCButton () {
        this.img = null;
    }

    public void setBackgroundImage (String imgPath) {
        img = new ImageIcon(imgPath).getImage();
    }

    public void setBackgroundImage (Image img) {
        this.img = img;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    public void reveal () {
        this.setEnabled(true);
        this.setVisible(true);
    }

    public void disappear () {
        this.setEnabled(false);
        this.setVisible(false);
    }
}
