package Skyjo_UTBM_Edition.gui;

import javax.swing.*;
import java.awt.*;
import Skyjo_UTBM_Edition.basics.Game;

/*
 * Extends the JPanel class to allow for a background image to be set easily.
 *
 */
public class SUEPanel extends JPanel implements EasyBackgroundImage, SUEComponent {

    private Image img;

    public SUEPanel (SUETexture SUETexture) {
        this.img = SUETexture.getImage();
    }

    public SUEPanel () {
        this.img = null;
        this.setBackground(Game.TRANSPARENT);
    }

    public void setBackgroundImage (SUETexture SUETexture) {
        this.img = SUETexture.getImage();
    }

    public void deleteBackgroundImage () {
        this.img = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    public void SUEShow () {
        this.setEnabled(true);
        this.setVisible(true);
    }

    public void SUEHide () {
        this.setEnabled(false);
        this.setVisible(false);
    }
}
