package Skyjo_frenic.basics;

import Skyjo_frenic.Game;

import java.util.ArrayList;

public class Player {

    private int points;

    public int getPoints () {
        return points;
    }

    public void setPoints (int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    private int turn;

    public int getTurn() {
        return this.turn;
    }

    public void addTurn() {
        ++this.turn;
    }

    private final int playerNumber;

    public int getPlayerNumber() {
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
    public Player(String name, int playerNumber) {
        this.name = name;
        this.points = 0;
        this.currentHand = new ArrayList<>(Game.MAX_CARDS_PER_HAND);
        this.playerNumber = playerNumber;
    }

    // Might be useless
    public void revealCard(int x, int y) {
        // TODO
    }

    /*public void displayCurrentHand () {
        for(var card : currentHand) {
            card.show();
        }
    }

    public void hideCurrentHand () {
        for(var card : currentHand) {
            card.hide();
        }
    }*/

    public boolean areAllCardsRevealed() {
        for(final var card : currentHand) {
            if (!card.isRevealed()) {
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
