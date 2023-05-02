package Skyjo_frenic.basics;

import Skyjo_frenic.gui.SFCTexture;

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

    public Card (int value, SFCTexture backTexture, SFCTexture frontTexture, Player associatedPlayer){
        this.value = value;
        this.backTexture = backTexture;
        this.frontTexture = frontTexture;
        this.associatedPlayer = associatedPlayer;
    }

    /**
     * Handles the click on a card :
     *  - Adds the value of the card to the player's count if the card is being revealed
     *  -
     */
    public void flip() {
        if(!this.isRevealed() && this.hasAssociatedPlayer()) {
            this.reveal();
            this.associatedPlayer.addPoints(this.value);
        }
    }

    @Override
    public String toString () {
        return String.valueOf(this.value);
    }
}
