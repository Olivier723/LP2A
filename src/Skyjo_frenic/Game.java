package Skyjo_frenic;

import Skyjo_frenic.gui.CardButton;
import Skyjo_frenic.gui.SFCFrame;
import Skyjo_frenic.basics.Card;
import Skyjo_frenic.basics.GameState;
import Skyjo_frenic.basics.Player;
import Skyjo_frenic.gui.Texture;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Not sure if the game class should extend the SFCFrame class or just contain it as an object
 * TODO : remove
 */
public class Game extends SFCFrame {
    private final ArrayList<Player> players;
    private final ArrayDeque<Card> drawPile;
    private final ArrayDeque<Card> discardPile;
    private final ArrayList<CardButton> playerCards;
    private Player currentPlayer;
    private static final int MAX_CARD_AMOUNT = 108;
    public static final int MAX_CARDS_PER_HAND = 12;
    private int turn;
    private GameState state;

    public Game() {
        super("Skyjo_frenic", 800, 400);
        this.players = new ArrayList<>();
        this.state = GameState.INIT;
        this.drawPile = generateDeck();
        super.setIconImage(Texture.CARD_BACK.getImage());
        this.discardPile = new ArrayDeque<>(MAX_CARD_AMOUNT);
        this.playerCards = createPlayerCards();
        super.getOkButton().addActionListener(e -> nameInputHandler());
        super.getCancelButton().addActionListener(e -> removePlayerHandler());
        super.getNameInput().addActionListener(e -> nameInputHandler());
        this.turn = 0;
        this.currentPlayer = null;
        this.updatePlayerList();
    }

    private ArrayList<CardButton> createPlayerCards() {
        ArrayList<CardButton> playerCards = new ArrayList<>(MAX_CARDS_PER_HAND);
        for(int i = 0; i < MAX_CARDS_PER_HAND; i++) {
            CardButton cardButton = new CardButton();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = i % 4;
            gbc.gridy = i / 4;
            gbc.insets = new Insets(10, 10, 10, 10);
            super.cardPanel.add(cardButton, gbc);
            playerCards.add(cardButton);
        }
        return playerCards;
    }

    public void begin() {
        super.SFCShow();
    }

    @Override
    public String toString() {
        if(!players.isEmpty()) {
            StringBuilder s = new StringBuilder("The game has " + players.size() + " players :\n");
            for (final var player : players) {
                s.append("\t- ").append(player).append("\n");
            }
            return s.toString();
        }
        return "There currently is no players in the game !";
    }

    /**
     * Tests if the given name respects these rules :
     * Only alphanumeric characters TODO (For now)
     * @param name The name given by the player
     * @return True if the name is valid given the above rules, false otherwise
     */
    private boolean isNameValid (String name) {
        return name != null && name.matches("^[a-zA-Z0-9]+$");
    }

    /**
     * Looks for the given name in the players list
     * @param name The name that the player gave
     * @return True if the name given is already in use, false otherwise
     */
    private boolean isNameAlreadyUsed (String name) {
        for(final var player : players){
            if(player.getName().equals(name))
                return true;
        }
        return false;
    }

    /**
     * @return The complete deck with every card generated randomly
     * TODO: Change the generation method using an external file (maybe)
     */
    private ArrayDeque<Card> generateDeck() {
        ArrayDeque<Card> deck = new ArrayDeque<>(MAX_CARD_AMOUNT);
        for (int i = 0; i < MAX_CARD_AMOUNT; ++i) {
            deck.add(new Card((int) (Math.random() * MAX_CARD_AMOUNT),
                     Texture.CARD_BACK, Texture.GUIG, null));
        }
        return deck;
    }

    /**
     * Event handler for the "Start game" button
     * Sets the game's state to PLAYING to ensure that everything is going smoothly
     * Distributes the starting cards
     */
    private void gameStartHandler () {
        this.state = GameState.PLAYING;
        infoPanel.SFCHide();
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        //Distributing starting cards and show the card of the first player
        for(int i = 0; i < MAX_CARDS_PER_HAND; ++i) {
            for(final var player : players) {
                Card card = drawPile.removeLast();
                card.setAssociatedPlayer(player);
                player.addCardToHand(card);
            }
        }
        changeCurrentPlayer();
        super.cardPanel.SFCShow();
    }

    private Player getNextPlayer() {
        if(this.currentPlayer == null) {
            return players.get(0);
        }
        return players.get(this.currentPlayer.getPlayerNumber());
    }

    private void changeCurrentPlayer() {
        Player player = getNextPlayer();
        for (int i = 0; i < MAX_CARDS_PER_HAND; ++i) {
            this.playerCards.get(i).setAssociatedCard(player.getCurrentHand().get(i));
        }
        this.currentPlayer = player;
    }

    /**
     * Removes the last player added in the players list when someone clicks the "Cancel button"
     */
    private void removePlayerHandler () {
        if(!players.isEmpty()){
            this.players.remove(players.size()-1);
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
        if (!isNameValid(name)) {
            /*super.getPrompt().setFont(new Font("Arial", Font.BOLD, 14));*/
            super.prompt.setText("Invalid name. Please enter a name without special characters.");
            return;
        }

        if (isNameAlreadyUsed(name)) {
            super.prompt.setText("This name is already in use !");
            return;
        }

        if (players.size() < 8) {
            super.getNameInput().setText("");
            players.add(new Player(name, players.size()));
            updatePlayerList();
            super.prompt.setText("Please enter the next name :");

            //Try to make it so that when the game can start, the "start game" button pops up
            //I don't know how though :'(
            if (players.size() >= 2) {
                super.getLaunchButton().addActionListener(e -> gameStartHandler());
                super.gbc.gridy = InputMenuPos.LAUNCH_BUTTON.y;
                super.infoPanel.add(super.getLaunchButton(), gbc);
            }

            return;
        }

        //If all the above tests failed then it means that the game is full
        super.prompt.setText("Cannot add more players, please start the game.");
        super.infoPanel.remove(super.buttonPanel);
    }

    private void updatePlayerList () {
        playerList.setText(this.toString());
    }

    private Card drawCard() {
        if(drawPile.isEmpty()) {
            //TODO : shuffle the discard pile into the draw pile
        }
        return drawPile.removeLast();
    }
}
