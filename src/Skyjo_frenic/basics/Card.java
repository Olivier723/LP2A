package Skyjo_frenic.basics;

import Skyjo_frenic.gui.SFCButton;

import java.awt.*;

/**
 * Extends SFCButton so that it's easier to implement for the GUI,
 * However it makes it kinda annoying to make compatible w/ the CLI
 * since the button part is not really useful for the CLI
 */
public class Card extends SFCButton {
    private final int value;

    public int getValue() {
        return value;
    }

    private enum cardState {
        REVEALED,
        HIDDEN,
    }

    private cardState state = cardState.HIDDEN;

    public void reveal() {
        this.state = cardState.REVEALED;
    }

    public boolean isRevealed() {
        return state == cardState.REVEALED;
    }

    public Card (int value, Image img){
        this.value = value;
        super.setBackgroundImage(img);
    }

    public Card (int value, String imgPath){
        this.value = value;
        super.setBackgroundImage(imgPath);
    }

    // Faut le mettre ici ? ou dans le game ? TODO : a voir
    public void onClick(Integer points) {
        points++;
    }

    @Override
    public String toString () {
        return String.valueOf(this.value);
    }
}
