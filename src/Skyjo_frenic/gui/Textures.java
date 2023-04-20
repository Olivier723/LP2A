package Skyjo_frenic.gui;

import javax.swing.*;
import java.awt.*;

/**
 * TODO : Implement later after all the code is done.
 */
public enum Textures {
    CARD_BACK("ressources/textures/card_back.png"),
    MAT_TEXTURE("ressources/textures/mat_background.png");

    private final Image texture;

    /*public static final Image[] CARD_TEXTURES = new Image[CARD_BACK];*/

    Textures(String path) {
        this.texture = new ImageIcon(path).getImage();
    }

    public Image getTexture() {
        return texture;
    }
}
