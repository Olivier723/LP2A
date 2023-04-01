package Skyjo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    private final ArrayList<Player> players;
    private final GameWindow window;
    public Game(){
        //Create the list of players
        Scanner sc = new Scanner(System.in);
        System.out.println("How many players are there ? (Must be between 2 an 8 included)");
        int size = getSize(sc);
        this.players = new ArrayList<>(size);


        //Create each player's instance
        for(int i = 0; i < size; i++) {
            System.out.printf("Veuillez enter un nom pour le joueur %d: \n", i + 1);
            String answer = sc.nextLine();
            while (isNameValid(answer)) {
                System.out.println("The name you entered is not valid, please enter a valid name !");
                answer = sc.nextLine();
            }
            players.add(new Player(answer));
        }

        this.window = new GameWindow("Test", 640, 480);
    }

    @Override
    public String toString () {
        String s = "The game has "+ players.size() +" players :\n";
        for(final var player : players){
            s += "\t- " + player + "\n";
        }
        return s;
    }

    private boolean isNameValid(String name){
        return name.matches(".*[^a-zA-Z0-9 ].*");
    }


    private int getSize(Scanner sc){
        int value = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                value = sc.nextInt();
                if(value >= 2 && value <= 8) {
                    validInput = true;
                }
                else {
                    System.out.println("Please enter a number between 2 and 8 !");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer value.");
                sc.nextLine(); // consume the invalid input
            }

        }

        //Empty the buffer
        sc.nextLine();
        return value;
    }
}
