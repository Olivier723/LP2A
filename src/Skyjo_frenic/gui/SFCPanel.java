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

    public SFCPanel (SFCTexture SFCTexture) {
        this.img = SFCTexture.getImage();
    }

    public SFCPanel () {
        this.img = null;
        this.setBackground(new Color(0, 0, 0, 0));
    }

    public void setBackgroundImage (SFCTexture SFCTexture) {
        this.img = SFCTexture.getImage();
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
