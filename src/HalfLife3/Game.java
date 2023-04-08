package HalfLife3;

import HalfLife3.gui.HF3Frame;
import HalfLife3.basics.Card;
import HalfLife3.basics.GameState;
import HalfLife3.basics.Player;

import java.util.ArrayList;

public class Game extends HF3Frame {
    private final ArrayList<Player> players;
    private final ArrayList<Card> drawPile;
    private final ArrayList<Card> discardPile;
    private final int MAX_CARD_AMOUNT = 108;
    private int turn;
    private GameState state;

    public Game() {
        super("HalfLife3", 500, 250);
        this.players = new ArrayList<>();
        this.state = GameState.INIT;
        this.drawPile = new ArrayList<>(108);
        this.discardPile = new ArrayList<>(108);
        super.getTitleLabel().setText("Choose between 2 and 8 player names.");
        super.getPrompt().setText("Enter a nickname : ");
        super.getButton().addActionListener(e -> eventHandler());
        super.getTextField1().addActionListener(e -> eventHandler());
        this.turn = 0;
    }

    public void start() {
        super.setVisible(true);
    }

    @Override
    public String toString () {
        StringBuilder s = new StringBuilder("The game has " + players.size() + " players :\n");
        for(final var player : players) {
            s.append("\t- ").append(player).append("\n");
        }
        return s.toString();
    }

    private boolean isNameValid (String name) {
        return name.matches("^[a-zA-Z0-9]+$");
    }

    private boolean isNameAlreadyUsed(String name) {
        for(final var player : players){
            if(player.getName().equals(name))
                return true;
        }
        return false;
    }

    private void initDrawPile() {
        for(int i = 0; i < MAX_CARD_AMOUNT; i++) {
            //TODO
        }
    }

    private void eventHandler () {
        switch (this.state) {
            case INIT -> {
                String name = super.getTextField1().getText();
                if (isNameValid(name)) {
                    if (isNameAlreadyUsed(name)) {
                        super.getPrompt().setText("This name is already used. Please enter another one.");
                    } else {
                        if(players.size() >= 8) {
                            super.getPrompt().setText("Cannot add more players, please start the game.");
                        } else {
                            players.add(new Player(name));
                            super.getPrompt().setText("Please enter the name of the next player.");
                        }
                        super.getPlayerList().setText(this.toString());
                    }
                } else {
                    super.getPrompt().setText("Invalid name. Please enter a name without special characters.");
                }
            }

            case PLAYING -> {
                for(final var player : players){

                }
                turn++;
            }
        }
    }
}
