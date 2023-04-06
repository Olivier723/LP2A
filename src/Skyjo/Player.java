package Skyjo;

import java.util.ArrayList;

public class Player {
    private int points;
    private ArrayList<Card> currentHand;
    public String getName () {
        return name;
    }

    private final String name;
    public Player(String name){
        this.name = name;
        this.points = 0;
        this.currentHand = new ArrayList<>(12);
    }

    public void revealCard(int x, int y) {
        // TODO
    }

    public boolean areAllCardsReveal() {
        // TODO
        return false;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    @Override
    public String toString () {
        return this.name;
    }
}
