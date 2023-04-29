package Skyjo_frenic.gui;

import javax.swing.*;
import java.awt.*;

/**
 * TODO : Implement later after all the code is done.
 */
public enum Texture {
    CARD_BACK("ressources/textures/card_back.png"),
    MAT_TEXTURE("ressources/textures/mat_background.png"),
    GUIG("ressources/textures/guig.png");

    private final Image image;

    Texture (String path) {
        this.image = new ImageIcon(path).getImage();
    }

    public Image getImage () {
        return image;
    }
}
