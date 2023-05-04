package Skyjo_frenic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 *
 */
public class SFCButton extends JButton implements EasyBackgroundImage, SFCComponent {
    private Image img;

    public SFCButton (String text) {
        super(text);
        super.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.setAlignmentY(Component.CENTER_ALIGNMENT);
        img = null;
    }

    public SFCButton (SFCTexture sfcTexture) {
        super();
        super.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.img = sfcTexture.getImage();
    }

    public SFCButton (String text, SFCTexture sfcTexture) {
        super(text);
        super.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.img = sfcTexture.getImage();
    }

    public void setBackgroundImage (SFCTexture sfcTexture) {
        this.img = sfcTexture.getImage();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void SFCShow () {
        this.setEnabled(true);
        this.setVisible(true);
    }

    @Override
    public void SFCHide () {
        this.setEnabled(false);
        this.setVisible(false);
    }


}
