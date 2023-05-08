package Skyjo_frenic.basics;

import Skyjo_frenic.gui.CardButton;
import Skyjo_frenic.gui.SFCFrame;
import Skyjo_frenic.gui.SFCTexture;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The main class of the game
 * Extends the SFCFrame class to make it easier to change the UI dynamically while also benefiting from JFrame's dispose method
 * Also contains the list of players and the card piles
 */
public class Game extends SFCFrame {
    // Global constants
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    public static final int MAX_CARDS_PER_HAND = 12;

    /**
     * A simple class to represent a pile of cards while making sure cards are never null
     * This class uses a deque because we only need to add and remove cards from the top of the pile
     * So there's no need to use a list
     */
    private static class SFCCardPile implements Iterable<Card> {
        private final ArrayDeque<Card> cardPile;

        public SFCCardPile () {
            this.cardPile = new ArrayDeque<>();
        }

        public void addCard (Card card) {
            if(card != null) {
                this.cardPile.add(card);
            }
        }

        public Card drawCard () {
            return this.cardPile.removeLast();
        }

        public boolean isEmpty () {
            return this.cardPile.isEmpty();
        }

        public Card peek () {
            return this.cardPile.peek();
        }

        @Override
        public @NotNull Iterator<Card> iterator () {
            return this.cardPile.iterator();
        }
    }

    private final ArrayList<Player> players;
    public Player getLastPlayer () {
        return this.players.get(players.size()-1);
    }
    private final SFCCardPile drawPile;
    private final SFCCardPile discardPile;
    private final ArrayList<CardButton> playerCards;
    private boolean isStartFinished = false;
    private Player currentPlayer;

    public Game() {
        super("Skyjo_frenic", 800, 400);
        this.players = new ArrayList<>();
        this.drawPile = generateDeck();
        super.setIconImage(SFCTexture.CARD_BACK.getImage());
        this.discardPile = new SFCCardPile();
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
        for(int i = 0; i < MAX_CARDS_PER_HAND; i++) {
            CardButton newCardButton = new CardButton(super.cardPanel, this);
            playerCards.add(newCardButton);
        }
        return playerCards;
    }

    /**
     *
     */
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
    private SFCCardPile generateDeck () {
        SFCCardPile deck = new SFCCardPile();

        var cardData = CSVReader.readCSV("resources/data/Cards.csv");
        for (final var card : cardData) {
            int cardAmount = Integer.parseInt(card.get(2));
            for(int i = 0; i < cardAmount; i++){
                String textureName = card.get(0).split("\\.")[0];
                System.out.println(textureName);
                SFCTexture texture =  SFCTexture.getTexture(textureName);
                int cardPoints = Integer.parseInt(card.get(1));
                deck.addCard(new Card(cardPoints, SFCTexture.CARD_BACK, texture, textureName));
            }
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
                Card card = drawPile.drawCard();
                card.setAssociatedPlayer(player);
                player.addCardToHand(card);
            }
        }

        //Now that the cards are distributed, we can associate the first player's cards to the buttons
        this.relinkCardButtons();

        super.cardPanel.SFCShow();
        this.updateInfoLabel();
    }

    /**
     * @return The next player in the list and cycles back to the first player if the last player was reached
     */
    private Player getNextPlayer() {
        return players.get(this.currentPlayer.getPlayerNumber() % this.players.size());
    }

    /**
     * When the "next player" button is pressed, this function changes
     * each card associated to each button for each card in the new current player
     */
    private void relinkCardButtons () {
        for(int i = 0; i < MAX_CARDS_PER_HAND; ++i) {
            CardButton temp = this.playerCards.get(i);
            temp.setAssociatedCard(this.currentPlayer.getCard(i));
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
            if (!this.isStartFinished && this.getLastPlayer().getTurn() == 1) {
                this.isStartFinished = true;
                this.setStartingPlayer();
                super.announce("The game has started !\n" + this.currentPlayer.getName() + " will start the game !");
                super.drawButton.addActionListener(e -> drawPileHandler());
                super.discardButton.addActionListener(e -> discardPileHandler());

            }
            //Otherwise, we just switch players normally
            else {
                this.currentPlayer = this.getNextPlayer();
            }

            this.relinkCardButtons();
            
            this.updateInfoLabel();
            if(this.isStartFinished) {
                this.updateGeneralInfoLabel();
            }
            return;
        }
        if (this.currentPlayer.getTurn() == 0) {
            super.announce("You must flip two cards before switching player !");
            return;
        }
        super.announce("You must discard or draw a card before switching player !");
    }

    /**
     * Removes the last player added in the players list when someone clicks the "Cancel" button
     */
    private void removePlayerHandler () {
        if(!this.players.isEmpty()){
            this.players.remove(players.size()-1);
            this.updatePlayerList();
            if(this.players.size() < 2) {
                super.getLaunchButton().SFCHide();
            }
        }
    }

    /**
     * The event handler for the game startup so that when you click the "OK" button,
     * it checks if the name entered is already used then if it's within the rules
     * If there are more than 2 players, the "Start game" button is shown
     * Furthermore, if a player is trying to join when there already is 8 players, this function will open a popup warning the player
     */
    private void nameInputHandler () {
        String name = super.nameInput.getText();
        if (!Player.isNameValid(name)) {
            super.announce("Only alphanumeric characters are allowed !");
            return;
        }

        if (this.isNameAlreadyUsed(name)) {
            super.announce("This name is already in use !");
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
                return;
            }

            if (this.players.size() == 2) {
                super.getLaunchButton().addActionListener(e -> gameStartHandler());
                super.infoPanelGBC.gridy = InputMenuPos.LAUNCH_BUTTON.y;
                super.popupPanel.add(super.getLaunchButton(), infoPanelGBC);
            }
            return;
        }

        //If all the above tests failed then it means that the game is full
        super.announce("Cannot add more players, please start the game.");
        super.popupPanel.remove(super.buttonPanel);
    }

    /**
     * Updates the player list on the info screen
     */
    private void updatePlayerList () {
        this.playerList.setText(this.toString());
        super.repaint();
    }

    /**
     * <h4>This method is used exclusively by this class only if the player wants to discard the card he just drew</h4>
     * Discards a player's selected card if he has one
     * and then sets his selected card to null while also de-referencing the card's associated player
     * If a player has no selected card, does nothing
     */
    private void discardCard() {
        if(this.currentPlayer == null){
            System.err.println("[ERROR] Current player is null !");
            return;
        }
        if(this.currentPlayer.getDrawnCard() == null) {
            return;
        }
        this.currentPlayer.getDrawnCard().setAssociatedPlayer(null);
        super.announce("You have discarded a " + this.currentPlayer.getDrawnCard());
        this.discardPile.addCard(this.currentPlayer.getDrawnCard());
        this.currentPlayer.setDrawnCard(null);
        this.updateGeneralInfoLabel();
    }

    /**
     * <h4>This method is used exclusively by the CardButton class to discard the card associated with it before it is overwritten by the associated player's new card</h4>
     * Simply adds the card to the discard pile if the card is not null and <em>does not handle anything else</em>
     * @param card the card to discard
     */
    public void discardCard(Card card) {
        if(card == null) {
            System.err.println("[ERROR] Card is null !");
            return;
        }
        super.announce("You have discarded a " + card);
        this.discardPile.addCard(card);
    }

    /**
     * Handles what happens when the player clicks on the discard pile
     * Determines if the player wants to discard a card or draw a card
     */
    private void discardPileHandler() {
        if(this.currentPlayer.hasAlreadyDrawn()) {
            this.discardCard();
            return;
        }
        this.drawFrom(this.discardPile);
    }

    /**
     * Handles what happens when the player clicks on the draw pile
     * And creates a popup that warns the player if he has already drawn a card this turn
     */
    private void drawPileHandler() {
        if(this.currentPlayer.hasAlreadyDrawn()) {
            super.announce("You have already drawn a card this turn");
            return;
        }
        this.drawFrom(this.drawPile);
    }

    /**
     * Draws a card from the specified pile or warns the player that the pile is empty
     * @param cardPile the pile from which the card will be drawn
     */
    private void drawFrom (SFCCardPile cardPile) {
        if(cardPile == null){
            return;
        }
        if(cardPile.isEmpty()){
            super.announce("The pile you tried to draw from is empty");
            return;
        }
        if (cardPile.peek() == null) {
            return;
        }
        super.announce("You have drawn a " + cardPile.peek());
        cardPile.peek().setAssociatedPlayer(this.currentPlayer);
        this.currentPlayer.setDrawnCard(cardPile.drawCard());
        this.updateGeneralInfoLabel();
    }

    private void updateGeneralInfoLabel() {
        String message;
        if(this.currentPlayer.getDrawnCard() == null) {
            message = "You have no card in hand";
        } else {
            message = "You have a " + this.currentPlayer.getDrawnCard() + " in hand";
        }
        super.updateLabel(super.generalInfoLabel, message );
    }

    private void updateInfoLabel() {
        super.updateLabel(super.currentPlayerLabel, "Current player : " + this.currentPlayer.getName());
    }
}