package Skyjo_frenic.basics;

import Skyjo_frenic.gui.SFCTexture;

/**
 * Represents a card in the game with a value and two textures (one for the front and one for the back)
 * A player object is associated with the card to make adding the score easier
 */
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

    private final SFCTexture frontTexture;
    private final SFCTexture backTexture;

    public SFCTexture getCurrentTexture () {
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

    public void hide () {
        this.state = CardState.HIDDEN;
    }

    public boolean isRevealed() {
        return state == CardState.REVEALED;
    }

    public boolean hasAssociatedPlayer() {
        return this.associatedPlayer != null;
    }

    public Card (int value, SFCTexture backTexture, SFCTexture frontTexture){
        this.value = value;
        this.backTexture = backTexture;
        this.frontTexture = frontTexture;
        this.associatedPlayer = null;
    }

    /**
     * Handles the player's interaction with the card :
     *  - Adds the value of the card to the player's count if the card is being revealed while also revealing the card
     *  -
     */
    public void flip() {
        if(!this.isRevealed() && this.hasAssociatedPlayer()) {
            this.reveal();
            this.associatedPlayer.addPoints(this.value);
            return;
        }
        if(this.isRevealed()){
            this.hide();
        }
    }

    @Override
    public String toString () {
        return String.valueOf(this.value);
    }
}
