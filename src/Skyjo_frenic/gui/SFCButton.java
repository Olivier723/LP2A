package Skyjo_frenic.gui;

import javax.swing.*;
import java.awt.*;

public class SFCButton extends JButton implements EasyBackgroundImage, SFCComponent {
    private Image img;

    public SFCButton (String text) {
        super(text);
        super.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.setAlignmentY(Component.CENTER_ALIGNMENT);
        img = null;
    }

    public SFCButton (String text, Texture texture) {
        super(text);
        super.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.img = texture.getImage();
    }

    public SFCButton () {
        this.img = null;
    }

    public void setBackgroundImage (Texture texture) {
        this.img = texture.getImage();
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
