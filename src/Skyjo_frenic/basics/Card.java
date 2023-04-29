package Skyjo_frenic.basics;

import Skyjo_frenic.gui.Texture;

import java.awt.*;


public class Card{
    private final int value;

    public int getValue() {
        return value;
    }

    private Player associatedPlayer;

    public void setAssociatedPlayer (Player associatedPlayer) {
        this.associatedPlayer = associatedPlayer;
    }

    public Player getAssociatedPlayer () {
        return associatedPlayer;
    }

    private Texture texture;

    public Texture getTexture() {
        return texture;
    }

    public void setTexture (Texture texture) {
        this.texture = texture;
    }

    private enum CardState {
        REVEALED,
        HIDDEN;
    }

    private CardState state = CardState.HIDDEN;

    //TODO : Make it so that when the card is revealed, it's texture changes automatically
    public void reveal () {
        this.state = CardState.REVEALED;
    }

    public boolean isRevealed() {
        return state == CardState.REVEALED;
    }

    public boolean hasAssociatedPlayer() {
        return this.associatedPlayer != null;
    }

    public Card (int value, Texture texture, Player associatedPlayer){
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
