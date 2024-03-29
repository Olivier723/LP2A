package Skyjo_UTBM_Edition.basics;

import Skyjo_UTBM_Edition.gui.SUETexture;

/**
 * Represents a card in the game with a value and two textures (one for the front and one for the back)
 * A player object is associated with the card to make adding the score easier
 */
public class Card{
    private final int value;

    public int getValue () {
        return value;
    }

    private final String name;

    private Player associatedPlayer;

    public void setAssociatedPlayer (Player associatedPlayer) {
        this.associatedPlayer = associatedPlayer;
    }

    public Player getAssociatedPlayer () {
        return associatedPlayer;
    }

    private final SUETexture frontTexture;

    public SUETexture getFrontTexture () {
        return frontTexture;
    }

    private final SUETexture backTexture;

    public SUETexture getCurrentTexture () {
        return this.isRevealed() ? frontTexture : backTexture;
    }

    /**
     * Enum used to represent the state of the card
     * Could also have a boolean though
     */
    private enum CardState {
        REVEALED,
        HIDDEN
    }

    private CardState state = CardState.HIDDEN;

    public void reveal () {
        this.state = CardState.REVEALED;
    }

    public boolean isRevealed() {
        return state == CardState.REVEALED;
    }

    public boolean hasAssociatedPlayer() {
        return this.associatedPlayer != null;
    }

    public Card (int value, SUETexture backTexture, SUETexture frontTexture, String name){
        this.value = value;
        this.backTexture = backTexture;
        this.frontTexture = frontTexture;
        this.associatedPlayer = null;
        this.name = name;
    }

    /**
     * Handles the player's interaction with the card :
     * Adds the value of the card to the player's count if the card is being revealed while also revealing the card.
     *
     */
    public void flip() {
        if(!this.isRevealed() && this.hasAssociatedPlayer()) {
            this.reveal();
            this.associatedPlayer.addPoints(this.value);
        }
    }

    @Override
    public String toString () {
        return this.name;
    }
}
