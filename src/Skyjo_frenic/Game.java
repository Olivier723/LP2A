package Skyjo_frenic;

import Skyjo_frenic.gui.CardButton;
import Skyjo_frenic.gui.SFCFrame;
import Skyjo_frenic.basics.Card;
import Skyjo_frenic.basics.Player;
import Skyjo_frenic.gui.SFCTexture;

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

    public Game() {
        super("Skyjo_frenic", 800, 400);
        this.players = new ArrayList<>();
        this.drawPile = generateDeck();
        super.setIconImage(SFCTexture.CARD_BACK.getImage());
        this.discardPile = new ArrayDeque<>(MAX_CARD_AMOUNT);
        this.playerCards = createPlayerCards();
        super.getOkButton().addActionListener(e -> nameInputHandler());
        super.getCancelButton().addActionListener(e -> removePlayerHandler());
        super.nameInput.addActionListener(e -> nameInputHandler());
        this.currentPlayer = null;
        this.updatePlayerList();
    }

    /**
     * Creates the buttons associated to the current player's hand
     * The buttons are created and never change after that, it's only the card that they reference that changes
     * @return The list of buttons
     */
    private ArrayList<CardButton> createPlayerCards() {
        ArrayList<CardButton> playerCards = new ArrayList<>(MAX_CARDS_PER_HAND);
        GridBagConstraints cardgbc = new GridBagConstraints();
        Insets insets = new Insets(10, 10, 10, 10);
        for(int i = 0; i < MAX_CARDS_PER_HAND; i++) {
            playerCards.add(new CardButton(i%4, i/4, cardPanel, cardgbc, insets));
        }
        return playerCards;
    }

    public void begin() {
        super.SFCShow();
    }

    /**
     * This function is what displays the number of players and their names in the list on the info panel
     */
    @Override
    public String toString() {
        if(!this.players.isEmpty()) {
            StringBuilder s = new StringBuilder("The game has " + this.players.size() + " players :\n");
            for (final var player : this.players) {
                s.append("\t- ").append(player.getName()).append("\n");
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
                              SFCTexture.CARD_BACK, SFCTexture.GUIG, null));
        }
        return deck;
    }

    /**
     * Changes the UI to show what's necessary to play the game and hide the info panel
     */
    private void setPlayingUI() {
        super.popupPanel.SFCHide();
        super.infoPanelGBC.gridy = 0;
        super.infoPanelGBC.gridx = 0;
        super.popupPanelContainer.add(cardPanel, super.infoPanelGBC);
        super.nextPlayerButton.addActionListener(e -> changeCurrentPlayer());
        actionsPanelGBC.gridy = 1;
        super.actionsPanel.add(super.nextPlayerButton, actionsPanelGBC);
    }

    /**
     * Event handler for the "Start game" button
     * Distributes the starting card and shows the cards of the first player
     * and hides the info panel
     */
    private void gameStartHandler () {
        this.setPlayingUI();

        //Distributing starting cards and show the cards of the first player
        for(int i = 0; i < MAX_CARDS_PER_HAND; ++i) {
            for(final var player : players) {
                Card card = drawPile.removeLast();
                card.setAssociatedPlayer(player);
                player.addCardToHand(card);
            }
        }

        //Now that the cards are distributed, we can associate the first player's cards to the buttons
        this.associateCurrentPlayerToCardButtons();
        this.updateInfoLabel();

        super.cardPanel.SFCShow();
    }

    /**
     *
     * @return The next player in the list and cycles back to the first player if the last player was reached
     */
    private Player getNextPlayer() {
        return players.get(this.currentPlayer.getPlayerNumber() % this.players.size());
    }

    private void associateCurrentPlayerToCardButtons() {
        for(int i = 0; i < MAX_CARDS_PER_HAND; ++i) {
            CardButton temp = this.playerCards.get(i);
            temp.setAssociatedCard(this.currentPlayer.getCurrentHand().get(i));
            temp.updateTexture();
        }
    }

    /**
     * Sets the current player to the player with the highest score in the list
     * Used to determine which player starts the game
     */
    private void setStartingPlayer() {
        int highestScore = 0;
        for(final var player : players) {
            if(player.getPoints() > highestScore) {
                highestScore = player.getPoints();
                this.currentPlayer = player;
            }
        }
    }

    /**
     * Changes the current player and associates the card buttons on screen to the current player's hand
     * Also makes sure that the player can switch player before going to the next player
     */
    private void changeCurrentPlayer() {
        if(currentPlayer.canSwitchPlayer()) {
            this.currentPlayer.addTurn();
            this.currentPlayer = this.getNextPlayer();
            this.updateInfoLabel();
            System.out.println("Current player : " + currentPlayer + " , score : " + currentPlayer.getPoints());
            this.associateCurrentPlayerToCardButtons();
            return;
        }
        if(currentPlayer.getTurn() == 0){
            System.out.println("You must flip two cards before switching player !");
        }
    }

    /**
     * Removes the last player added in the players list when someone clicks the "Cancel" button
     */
    private void removePlayerHandler () {
        if(!this.players.isEmpty()){
            this.players.remove(players.size()-1);
            this.updatePlayerList();
        }
    }



    /**
     * The event handler for the game startup so that when you click the "OK" button,
     * it checks if the name entered is already used then if it's within the rules
     * and then if there are less than 8 players
     * TODO : Rename method
     */
    private void nameInputHandler () {
        String name = super.nameInput.getText();
        if (!isNameValid(name)) {
            super.prompt.setText("Only alphanumeric characters are allowed !");
            return;
        }

        if (isNameAlreadyUsed(name)) {
            super.prompt.setText("This name is already in use !");
            return;
        }

        if (players.size() < 8) {
            super.nameInput.setText("");
            this.players.add(new Player(name, players.size()+1));
            this.updatePlayerList();
            super.prompt.setText("Please enter the next name :");

            //Set the first player who joined as the player who will play first
            if(this.players.size() == 1) {
                this.currentPlayer = this.players.get(0);
            }

            if (this.players.size() == 2) {
                super.getLaunchButton().addActionListener(e -> gameStartHandler());
                super.infoPanelGBC.gridy = InputMenuPos.LAUNCH_BUTTON.y;
                super.popupPanel.add(super.getLaunchButton(), infoPanelGBC);
            }

            return;
        }

        //If all the above tests failed then it means that the game is full
        super.prompt.setText("Cannot add more players, please start the game.");
        super.popupPanel.remove(super.buttonPanel);
    }

    /**
     * Updates the player list on the info screen
     */
    private void updatePlayerList () {
        playerList.setText(this.toString());
        super.repaint();
    }


    private Card drawCard() {
        if(drawPile.isEmpty()) {
            //TODO : shuffle the discard pile into the draw pile
        }
        return drawPile.removeLast();
    }

    private void updateInfoLabel() {
        super.infoLabel.setText("Current player : " + this.currentPlayer.getName());
        super.repaint();
    }
}
