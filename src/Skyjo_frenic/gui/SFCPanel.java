package Skyjo_frenic.gui;

import javax.swing.*;
import java.awt.*;

/*
 * Extends the JPanel class to allow for a background image to be set easily.
 *
 *
 */
public class SFCPanel extends JPanel implements EasyBackgroundImage, SFCComponent {

    private Image img;

    public SFCPanel (String imgPath) {
        img = new ImageIcon(imgPath).getImage();
    }

    public SFCPanel (Image img) {
        this.img = img;
    }

    public SFCPanel () {
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

    public void SFCShow () {
        this.setEnabled(true);
        this.setVisible(true);
    }

    public void SFCHide () {
        this.setEnabled(false);
        this.setVisible(false);
    }
}
