package Skyjo;

import java.util.ArrayList;

public class Game extends GameFrame{
    private final ArrayList<Player> players;
    private final ArrayList<Card> drawPile;
    private final ArrayList<Card> discardPile;
    private GameState state;

    public Game() {
        super("Skyjo", 500, 250);
        this.players = new ArrayList<>();
        this.state = GameState.INIT;
        this.drawPile = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        super.getTitleLabel().setText("Choose between 2 and 8 player names.");
        super.getPrompt().setText("Enter a nickname : ");
        super.getButton().addActionListener(e -> eventHandler());
        super.getTextField1().addActionListener(e -> eventHandler());
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
                        super.getCurrentPlayers().setText(this.toString());
                    }
                } else {
                    super.getPrompt().setText("Invalid name. Please enter a name without special characters.");
                }
            }
        }
    }
}
