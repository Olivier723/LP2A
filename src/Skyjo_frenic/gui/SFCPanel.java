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

    public SFCPanel (Texture texture) {
        this.img = texture.getImage();
    }

    public void setBackgroundImage (Texture texture) {
        this.img = texture.getImage();
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
