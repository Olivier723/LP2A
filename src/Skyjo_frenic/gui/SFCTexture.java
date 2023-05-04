package Skyjo_frenic.gui;

import javax.swing.*;
import java.awt.*;

/**
 * This is a texture repertory to help with changing the textures of the game without having to change the code everytime.
 */
public enum SFCTexture {
    CARD_BACK("resources/textures/card_back.png"),
    MAT_TEXTURE("resources/textures/mat_background.png"),
    GUIG("resources/textures/guig.png");

    private final ImageIcon image;

    SFCTexture (String path) {
        this.image = new ImageIcon(path);
    }

    public Image getImage () {
        return image.getImage();
    }
}
