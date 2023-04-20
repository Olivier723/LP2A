package Skyjo_frenic.basics;

import Skyjo_frenic.gui.SFCButton;
import Skyjo_frenic.gui.Textures;

import javax.swing.*;
import java.awt.*;

/**
 * Extends SFCButton so that it's easier to implement for the GUI,
 * However it makes it kinda annoying to make compatible w/ the CLI
 * since the button part is not really useful for the CLI
 */
public class Card{
    private final int value;

    public int getValue() {
        return value;
    }

    private Player associatedPlayer;

    private Textures texture;

    public Image getTexture() {
        return texture.getTexture();
    }

    public void setTexture(Textures texture) {
        this.texture = texture;
    }

    public Player getAssociatedPlayer () {
        return associatedPlayer;
    }

    public void setAssociatedPlayer (Player associatedPlayer) {
        this.associatedPlayer = associatedPlayer;
    }

    private enum cardState {
        REVEALED,
        HIDDEN,
    }



    private cardState state = cardState.HIDDEN;

    //TODO : Make it so that when the card is revealed, it's texture changes automatically
    public void reveal () {
        this.state = cardState.REVEALED;
    }

    public boolean isRevealed() {
        return state == cardState.REVEALED;
    }

    public boolean hasAssociatedPlayer() {
        return this.associatedPlayer != null;
    }

    public Card (int value, Textures texture, Player associatedPlayer){
        this.value = value;
        this.texture = texture;
        this.associatedPlayer = associatedPlayer;
    }


    /**
     * Handles the click on a card :
     *  - Adds the value of the card to the player's count if the card is being revealed
     *  -
     */
    public void onClick() {
        if(!isRevealed() && hasAssociatedPlayer()) {
            reveal();
            associatedPlayer.addPoints(this.value);
            return;
        }
    }

    @Override
    public String toString () {
        return String.valueOf(this.value);
    }
}
