package Skyjo_frenic.basics;

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

    public static final int MAX_CARDS_PER_HAND = 12;

    private final ArrayList<Card> currentHand;

    public String getName() {
        return name;
    }

    private final String name;
    public Player(String name){
        this.name = name;
        this.points = 0;
        this.currentHand = new ArrayList<>(MAX_CARDS_PER_HAND);
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
        for(var card : currentHand) {
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
