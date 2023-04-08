package HalfLife3.gui;

import javax.swing.*;
import java.awt.*;

/*
 * Extends the JPanel class to allow for a background image to be set easily.
 *
 *
 */
public class HF3Panel extends JPanel{

    private Image img;

    public HF3Panel (String imgPath) {
        img = new ImageIcon(imgPath).getImage();
    }

    public HF3Panel (Image img) {
        this.img = img;
    }

    public HF3Panel () {
        this.img = null;
    }

    public void setBackgroundImage (String imgPath) {
        img = new ImageIcon(imgPath).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}
