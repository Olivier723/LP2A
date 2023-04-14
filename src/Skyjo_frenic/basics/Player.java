package Skyjo_frenic.basics;

import java.util.ArrayList;

public class Player {
    private Integer points;

    private static final int MAX_CARDS_PER_HAND = 12;

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


    public boolean areAllCardsRevealed() {
        for(var card : currentHand) {
            if (!card.isRevealed()) {
                return false;
            }
        }
        return true;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    @Override
    public String toString () {
        return this.name;
    }
}