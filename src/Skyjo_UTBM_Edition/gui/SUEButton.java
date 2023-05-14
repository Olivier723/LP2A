package Skyjo_UTBM_Edition.gui;

import Skyjo_UTBM_Edition.basics.Game;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class SUEButton extends JButton implements EasyBackgroundImage, SUEComponent {
    private Image img;

    public SUEButton (String text) {
        super(text);
        super.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.setAlignmentY(Component.CENTER_ALIGNMENT);
        img = null;
    }

    public SUEButton (SUETexture SUETexture) {
        super();
        super.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.img = SUETexture.getImage();
    }

    public SUEButton () {
        super.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.setAlignmentY(Component.CENTER_ALIGNMENT);
        super.setBackground(Game.TRANSPARENT);
    }

    public void setBackgroundImage (SUETexture SUETexture) {
        this.img = SUETexture.getImage();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void SUEShow () {
        this.setEnabled(true);
        this.setVisible(true);
    }

    @Override
    public void SUEHide () {
        this.setEnabled(false);
        this.setVisible(false);
    }


}
