package Skyjo;

public class Player {
    private int points;
    private final String name;
    public Player(String name){
        this.name = name;
        this.points = 0;
    }

    //Checking if the name given by the user is valid

    @Override
    public String toString () {
        return this.name;
    }
}
