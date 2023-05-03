package Skyjo_frenic.basics;

import Skyjo_frenic.Game;

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

    public ArrayList<Card> getCurrentHand() {
        return currentHand;
    }

    private final String name;

    public String getName() {
        return name;
    }

    private boolean hasDrawn = false;

    public void hasDrawn () {
        this.hasDrawn = true;
    }

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
        return this.hasDrawn;

    }

    public boolean canFlipCard() {
        if(this.turn == 0) {
            return this.cardsFlippedThisTurn < 2;
        }
        return this.cardsFlippedThisTurn < 1;
    }

    public boolean allCardsRevealed() {
        for(Card card : this.currentHand) {
            if(!card.isRevealed()) {
                return false;
            }
        }
        return true;
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
     * Only alphanumeric characters TODO (For now)
     * @param name The name given by the player
     * @return True if the name is valid given the above rules, false otherwise
     */
    public static boolean isNameValid (String name) {
        return name != null && name.matches("^[a-zA-Z0-9]+$");
    }
}
