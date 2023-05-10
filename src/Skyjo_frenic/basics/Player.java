package Skyjo_frenic.basics;

import java.util.ArrayList;

public class Player {

    private int points;

    public int getPoints () {
        return points;
    }

    public void addPoints (int points) {
        ++this.cardsFlippedThisTurn;
        this.points += points;
    }

    private int turn;
    private int cardsFlippedThisTurn;

    public int getTurn() {
        return this.turn;
    }

    public void addTurn() {
        this.cardsFlippedThisTurn = 0;
        this.hasDrawn = false;
        ++this.turn;
    }

    private final int playerNumber;

    public int getPlayerNumber () {
        return this.playerNumber;
    }

    private final ArrayList<Card> currentHand;

    public Card getCard(int index) {
        return this.currentHand.get(index);
    }

    private final String name;

    public String getName() {
        return name;
    }

    private Card drawnCard;

    private boolean hasDrawn = false;

    public Card getDrawnCard () {
        return drawnCard;
    }

    public void setDrawnCard (Card drawnCard) {
        this.drawnCard = drawnCard;
        if(drawnCard != null) {
            this.hasDrawn = true;
        }
    }


    public boolean hasAlreadyDrawn () {return this.hasDrawn;}

    public Player (String name, int playerNumber) {
        this.name = name;
        this.points = 0;
        this.cardsFlippedThisTurn = 0;
        this.currentHand = new ArrayList<>(Game.MAX_CARDS_PER_HAND);
        this.playerNumber = playerNumber;
    }

    public boolean canSwitchPlayer() {
        if(this.turn == 0) {
            return this.cardsFlippedThisTurn == 2;
        }
        return this.hasAlreadyDrawn();
    }

    /**
     * Determines if the player is able to flip a card
     * Only during the first turn or if he discarded the card he drew can the player flip a card without drawing another one first
     * @return True if the player can flip a card, false otherwise
     */
    public boolean canFlipCard() {
        if(this.turn == 0) {
            return this.cardsFlippedThisTurn < 2;
        }
        return this.cardsFlippedThisTurn < 1 && this.hasAlreadyDrawn();
    }

    /**
     * Tests if all cards in the player's hand are revealed for the win condition
     * @return True if all cards are revealed, false otherwise
     */
    public boolean allCardsRevealed() {
        for(Card card : this.currentHand) {
            if(!card.isRevealed()) {
                return false;
            }
        }
        return true;
    }

    public void swapCards(Card oldCard, Card newCard) {
        if(newCard == null || oldCard == null) {
            System.err.println("[ERROR] Swapping cards requires both cards to be non-null!");
            return;
        }
        int oldCardPos = this.currentHand.indexOf(oldCard);
        this.currentHand.remove(oldCardPos);
        this.currentHand.add(oldCardPos,newCard);
    }

    public void addCardToHand(Card card) {
        this.currentHand.add(card);
    }

    @Override
    public String toString () {
        return this.name;
    }

    /**
     * Tests if the given name respects these rules :
     * Only alphanumeric characters are allowed
     * @param name The name given by the player
     * @return True if the name is valid given the above rules, false otherwise
     */
    public static boolean isNameValid (String name) {
        return name != null && name.matches("^[a-zA-Z0-9]+$");
    }
}
