package Skyjo_UTBM_Edition.gui;

import Skyjo_UTBM_Edition.basics.Game;
import Skyjo_UTBM_Edition.basics.Card;
import Skyjo_UTBM_Edition.basics.Player;

import java.awt.*;

/**
 * This class is just an SFCButton with a card associated with it
 * As such it does handle the click event and the necessary verifications.
 * Also handles how the card is rendered and resized
 */
public class CardButton extends SUEButton {
    private Card associatedCard;

    /**
     * The game instance that created this button
     */
    private final Game parentGame;

    // Constants related to the size of the button
    private static final double GOLDEN_RATIO = 1.61803399F;
    private static final double CARD_TO_SCREEN_RATIO = 0.15F;
    public static final Dimension minimumSize = new Dimension(100, (int) (100 * GOLDEN_RATIO));
    public static final Dimension maximumSize = new Dimension(400, (int) (400 * GOLDEN_RATIO));


    /**
     * Sets the associated card and adds an action listener to the button
     * removing any previous action listeners if there were any
     * @param associatedCard the card to associate with this button
     */
    public void setAssociatedCard (Card associatedCard) {
        this.associatedCard = associatedCard;
        if(super.getActionListeners().length > 0) {
            super.removeActionListener(super.getActionListeners()[0]);
        }
        super.addActionListener(e -> onClick());
    }

    /**
     * @param cardPanel the panel representing the grid of cards
     * @param game the game instance that created this button
     */
    public CardButton (SUEPanel cardPanel, Game game) {
        super(SUETexture.CARD_BACK);
        this.associatedCard = null;
        this.parentGame = game;
        Dimension screenSize = SUEFrame.getScreenSize();
        Dimension cardSize = new Dimension((int) (screenSize.width * CARD_TO_SCREEN_RATIO),
                                           (int) (screenSize.width * CARD_TO_SCREEN_RATIO * GOLDEN_RATIO));
        this.associatedCard = null;
        super.setPreferredSize(cardSize);
        cardPanel.add(this);
    }

    /**
     * Handles what should happen when a card is clicked
     * If either the associated card or the associated player of this card is null does nothing but print an error
     * Otherwise, if the player drew from the draw pile and then clicks a button, the card is swapped with the drawn card,
     * the card that was clicked is discarded and adds the value of the card that was drawn to the associatedPlayer's score
     */
    private void onClick() {
        if(associatedCard == null) {
            System.err.println("[ERROR] Uh oh, this button refers to nothing");
            return;
        }
        if(!associatedCard.hasAssociatedPlayer()) {
            System.err.println("[ERROR] How can the card not have an associated player ?");
            return;
        }

        Player associatedPlayer = associatedCard.getAssociatedPlayer();

        //If the player has already drawn a card and has a card selected then set the card clicked to be the selected card and discard the clicked card
        if(associatedPlayer.hasAlreadyDrawn() && associatedPlayer.getDrawnCard() != null) {
            //First discard the card that was clicked
            parentGame.discardCard(associatedCard);

            // If the card is revealed, remove the points from the player's score before swapping the cards
            if(associatedCard.isRevealed()){
                associatedPlayer.addPoints(-associatedCard.getValue());
            }
            associatedPlayer.swapCards(associatedCard, associatedPlayer.getDrawnCard());

            // Then set this button's associated card to be the drawn card and reveal it
            this.associatedCard = associatedPlayer.getDrawnCard();
            this.associatedCard.reveal();

            //Then get rid of the drawn card in the player "inventory
            this.associatedCard.getAssociatedPlayer().setDrawnCard(null);
            parentGame.heldCardTexture.deleteBackgroundImage();

            associatedPlayer.addPoints(this.associatedCard.getValue());
            parentGame.updateGeneralInfoLabel();
            parentGame.updateTotalPointsLabel();
            this.setBackgroundImage(associatedCard.getCurrentTexture());
            return;
        }


        if(associatedPlayer.canFlipCard()){
            this.associatedCard.flip();
            super.setBackgroundImage(associatedCard.getCurrentTexture());
            parentGame.updateTotalPointsLabel();
        }
    }

    /**
     * Refreshes the texture of the card to match the current state of the card when the associated card changes
     */
    public void updateTexture() {
        if(associatedCard != null) {
            super.setBackgroundImage(associatedCard.getCurrentTexture());
            super.repaint();
        }
    }
}
