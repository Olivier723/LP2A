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

    public String getName() {
        return name;
    }

    private final String name;

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
        if(this.cardsFlippedThisTurn == 1){
            return true;
        }
        return false;

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
}
