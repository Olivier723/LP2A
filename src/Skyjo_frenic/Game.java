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

    public Player getLastPlayer () {
        return this.players.get(players.size()-1);
    }

    private final ArrayDeque<Card> drawPile;
    private final ArrayDeque<Card> discardPile;
    private final ArrayList<CardButton> playerCards;
    private boolean isStartFinished = false;
    private Player currentPlayer;
    private Card selectedCard;
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
        this.selectedCard = null;
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
                              SFCTexture.CARD_BACK, SFCTexture.GUIG));
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
        super.popupPanelContainer.add(super.cardPanel, super.infoPanelGBC);
        super.nextPlayerButton.addActionListener(e -> this.changeCurrentPlayer());
        super.actionsPanelGBC.gridy = 1;
        super.actionsPanel.add(super.nextPlayerButton, super.actionsPanelGBC);
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
        this.linkCurrentPlayerToCardButtons();
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

    private void linkCurrentPlayerToCardButtons () {
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
     * This function is the logic behind the player switching system
     * It checks if the player has done everything necessary
     * and then set the currentPlayer to the next player in the list
     */
    private void changeCurrentPlayer() {
        // We first check if the player is able to finish his turn
        if(currentPlayer.canSwitchPlayer()) {
            this.currentPlayer.addTurn();

            // If the last player has finished the first turn, we can set the starting player
            if (!this.isStartFinished && getLastPlayer().getTurn() == 1) {
                this.isStartFinished = true;
                this.setStartingPlayer();
                super.announce("The game has started !\n" + this.currentPlayer.getName() + " will start the game !");
                super.drawButton.addActionListener(e -> drawFrom(this.drawPile));
                super.discardButton.addActionListener(e -> discardCard());

            }
            //Otherwise, we just switch players normally
            else {
                this.currentPlayer = this.getNextPlayer();
            }

            this.updateInfoLabel();
            this.linkCurrentPlayerToCardButtons();
            return;
        }
        super.announce("You must flip two cards before switching player !");
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
        if (!Player.isNameValid(name)) {
            super.prompt.setText("Only alphanumeric characters are allowed !");
            return;
        }

        if (this.isNameAlreadyUsed(name)) {
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

    private void discardCard() {
        this.discardPile.add(this.selectedCard);
    }


    private void drawFrom(ArrayDeque<Card> pile) throws NullPointerException {
        if(pile == null){
            throw new NullPointerException("The given pile is null");
        }
        if(pile.isEmpty()){
            super.announce("The pile you tried to draw from is empty");
        }
        this.currentPlayer.hasDrawn();
        this.selectedCard = pile.removeLast();
    }

    private void updateInfoLabel() {
        super.infoLabel.setText("Current player : " + this.currentPlayer.getName());
        super.repaint();
    }
}
