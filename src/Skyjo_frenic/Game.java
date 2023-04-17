package Skyjo_frenic;

import Skyjo_frenic.gui.SFCFrame;
import Skyjo_frenic.basics.Card;
import Skyjo_frenic.basics.GameState;
import Skyjo_frenic.basics.Player;

import java.util.ArrayDeque;

/**
 * Not sure if the game class should extend the SFCFrame class or just contain it as an object
 * TODO : remove
 */
public class Game extends SFCFrame {
    private final ArrayDeque<Player> players;
    private final ArrayDeque<Card> drawPile;
    private final ArrayDeque<Card> discardPile;
    private static final int MAX_CARD_AMOUNT = 108;
    private int turn;
    private GameState state;

    public Game() {
        super("Skyjo_frenic", 500, 250);
        this.players = new ArrayDeque<>();
        this.state = GameState.INIT;
        this.drawPile = generateDeck();
        System.out.println("Drawpile : " +drawPile); // TODO : remove
        this.discardPile = new ArrayDeque<>(MAX_CARD_AMOUNT);
        super.getTitleLabel().setText("Choose between 2 and 8 player names.");
        super.getPrompt().setText("Enter a nickname : ");
        super.getOkButton().addActionListener(e -> nameInputHandler());
        super.getCancelButton().addActionListener(e -> removePlayerHandler());
        super.getNameInput().addActionListener(e -> nameInputHandler());
        this.turn = 0;
    }

    public void start() {
        super.setVisible(true);
    }

    @Override
    public String toString () {
        /*
        * I don't know why my IDE told me to use StringBuilder,
        * my google research told me it's better in string concatenation.
        */
        if(!players.isEmpty()) {
            StringBuilder s = new StringBuilder("The game has " + players.size() + " players :\n");
            for (final var player : players) {
                s.append("\t- ").append(player).append("\n");
            }
            return s.toString();
        }
        return "There is currently no players in the game !";
    }

    /**
     * Tests if the given name respects these rules :
     * Only alphanumeric characters TODO (For now)
     * @param name The name given by the player
     * @return True if the name is valid given the above rules, false otherwise
     */
    private boolean isNameValid (String name) {
        return name.matches("^[a-zA-Z0-9]+$");
    }

    /**
     * Looks for the given name in the players list
     * @param name The name that the player gave
     * @return True if the name given is already in use, false otherwise
     */
    private boolean isNameAlreadyUsed(String name) {
        for(final var player : players){
            if(player.getName().equals(name))
                return true;
        }
        return false;
    }

    /**
     *
     * @return The complete deck with every card generated randomly
     * TODO: Change the generation method using an external file
     */
    private ArrayDeque<Card> generateDeck(){
        ArrayDeque<Card> deck = new ArrayDeque<>(MAX_CARD_AMOUNT);
        for (int i = 0; i < MAX_CARD_AMOUNT; ++i) {
            deck.add(new Card((int) (Math.random() * MAX_CARD_AMOUNT),
                                  "./ressources/textures/guig.png"));
        }
        return deck;
    }

    /**
     * Event handler for the "Start game" button
     * Sets the game's state to PLAYING to ensure that everything is going smoothly
     */
    private void gameStart() {
        this.state = GameState.PLAYING;
        // TODO : distribute cards
    }

    /**
     * Removes the last player added in the players list when someone clicks the "Cancel button"
     */
    private void removePlayerHandler () {
        if(!players.isEmpty()){
            this.players.removeLast();
            updatePlayerList();
        }
    }

    /**
     * The event handler for the game startup so that when you click the "OK" button,
     * it checks if the name entered is already used then if it's within the rules
     * and then if there are less than 8 players
     * TODO : Rename method
     */
    private void nameInputHandler () {
        String name = super.getNameInput().getText();
        super.getNameInput().setText("");
        if (!isNameValid(name)) {
            /*super.getPrompt().setFont(new Font("Arial", Font.BOLD, 14));*/
            super.getPrompt().setText("Invalid name. Please enter a name without special characters.");
            return;
        }

        if (isNameAlreadyUsed(name)) {
            super.getPrompt().setText("This name is already in use !");
            return;
        }

        if (players.size() < 8) {
            players.add(new Player(name));
            updatePlayerList();
            super.getPrompt().setText("Please enter the next name :");

            //Try to make it so that when the game can start, the "start game" button pops up
            //I don't know how though :'(
            /*if (players.size() >= 2) {
                super.getLaunchButton().addActionListener(e -> gameStart());
                super.getLayout().setRows(5);
                super.getContentPane().add(super.getLaunchButton());
            }*/

            return;
        }

        //If all the above tests failed then it means that the game is full
        super.getPrompt().setText("Cannot add more players, please start the game.");
    }

    private void updatePlayerList () {
        playerList.setText(this.toString());
    }
}
