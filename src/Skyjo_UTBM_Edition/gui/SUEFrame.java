package Skyjo_UTBM_Edition.gui;

import Skyjo_UTBM_Edition.basics.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Hides some of the ugly code of the GUI behind a simpler interface.
 */
public class SUEFrame extends JFrame implements SUEComponent {
    public final Dimension screenSize;
    private final SUETexture background;
    public enum InputMenuPos {
        TITLE,
        NAME_INPUT,
        PLAYER_LIST,
        BUTTON_MENU,
        LAUNCH_BUTTON;
        public final int y;
        InputMenuPos() {
            this.y = this.ordinal();
        }
    }
    private final static Dimension maxInfoPanelSize = new Dimension(700, 500);
    private final static Dimension buttonSize = new Dimension(100, 25);
    private static final Dimension drawButtonSize = new Dimension(150, 220);

    private final static Font defaultFont = new Font("Arial", Font.PLAIN, 20);
    private final static Font titleFont = new Font("Arial", Font.BOLD, 40);
    private final static Font smallFont = new Font("Arial", Font.BOLD, 14);

    /**
     * The panel containing the whole game
     */
    protected SUEPanel mainPanel;

    /**
     * Container of the {@link #popupPanel} to center it on the screen
     */
    protected SUEPanel popupPanelContainer;

    /**
     * The panel that asks the player for their name
     * (Originally thought to be used like a popup instead of the {@link #announce} method , but could not be used due to time constraints)
     */
    protected SUEPanel popupPanel;

    /**
     * This label is what prompts the player to enter their name
     */
    protected JLabel nameInputPrompt;

    /**
     * This is where the player enters their name
     */
    protected JTextField nameInput;

    /**
     * The panel containing the ok and cancel buttons
     */
    protected SUEPanel buttonPanel;
    private SUEButton okButton;
    public SUEButton getOkButton () {
        return okButton;
    }

    private SUEButton cancelButton;
    public SUEButton getCancelButton () {
        return cancelButton;
    }

    private SUEButton launchButton;
    public SUEButton getLaunchButton () {
        return launchButton;
    }

    /**
     * Used during the players registrations,
     * Lists the players in the game
     */
    protected JTextPane playerList;


    /**
     * The label situated on the left that displays the current player's name and the card they're holding
     */
    protected SUEPanel infoPanel;

    /**
     * Shows the current player's points count
     */
    protected JLabel totalPointsLabel;

    /**
     * Contains the info about the card that the player is holding
     * i.e. it's name and texture
     */
    protected SUEPanel heldCardPanel;

    /**
     * Shows the card that the current player is holding
     */
    protected JLabel heldCardName;

    /**
     * The texture associated with the card name in {@link #heldCardName}
     */
    protected SUEPanel heldCardTexture;

    /**
     * Shows the name of the current player
     */
    protected JLabel currentPlayerLabel;


    /**
     * The panel in the center of the screen containing the {@link CardButton}s
     */
    protected SUEPanel cardPanel;


    /**
     * The panel on the right containing the draw, discard and actions panels
      */
    protected SUEPanel actionsPanel;

    protected SUEPanel drawPanel;

    protected SUEButton drawButton;

    /**
     * The button that allows the player to end their turn
     */
    protected SUEButton nextPlayerButton;

    /**
     * The panel containing the discard button
     */
    protected SUEPanel discardPanel;

    protected SUEButton discardButton;


    // Constraints used to place the components in the panels
    protected GridBagConstraints infoPanelGBC;
    protected GridBagConstraints actionsPanelGBC;

    public static Dimension getScreenSize () {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    @Override
    public void SUEHide () {
        this.setVisible(false);
    }

    @Override
    public void SUEShow () {
        this.setVisible(true);
    }

    protected SUEFrame (String title, int w, int h) {
        this.screenSize = SUEFrame.getScreenSize();
        if(w > 0 && h > 0 && w < screenSize.width && h < screenSize.height) {
            this.setSize(w, h);
        } else {
            this.setSize(screenSize);
        }

        this.setLNF();
        this.setTitle(title);
        this.setSize(w, h);
        this.setMinimumSize(new Dimension(1200, 800));

        // Makes it so that the program asks if the user wants to quit before closing.
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing (WindowEvent e) {
                quit();
            }
        });

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        int middleScreenX = (int) ((screenSize.getWidth() - w) / 2);
        int middleScreenY = (int) ((screenSize.getHeight() - h) / 2);
        this.setLocation(middleScreenX, middleScreenY);
        this.background = SUETexture.MAT_TEXTURE;
        this.createUIComponents();
    }

    /**
     * Creates the UI components for the action panel
     */
    private void createActionPanel(){
        actionsPanel = new SUEPanel();
        actionsPanel.setLayout(new GridBagLayout());
        actionsPanelGBC = new GridBagConstraints();
        mainPanel.add(actionsPanel, BorderLayout.EAST);

        actionsPanelGBC.gridx = 0;
        actionsPanelGBC.gridy = 0;
        actionsPanelGBC.insets = new Insets(0, 0, 20, 0);

        GridBagConstraints drawPanelGBC = new GridBagConstraints();
        drawPanelGBC.gridx = 0;
        drawPanelGBC.gridy = 0;
        drawPanelGBC.weightx = 1;
        drawPanelGBC.weighty = 1;
        drawPanelGBC.insets = new Insets(0, 0, 20, 0);
        drawPanelGBC.anchor = GridBagConstraints.CENTER;

        drawPanel = new SUEPanel();
        drawPanel.setLayout(new GridBagLayout());
        drawPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        drawPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel drawLabel = new JLabel("Draw pile :");
        drawLabel.setForeground(Color.BLACK);
        drawLabel.setFont(defaultFont);
        drawPanel.add(drawLabel, drawPanelGBC);
        drawPanelGBC.gridy = 1;
        drawButton = new SUEButton();
        drawButton.setPreferredSize(drawButtonSize);
        drawButton.setBackgroundImage(SUETexture.CARD_BACK);
        drawPanel.add(drawButton, drawPanelGBC);

        discardPanel = new SUEPanel();
        discardPanel.setLayout(new GridBagLayout());
        JLabel discardLabel = new JLabel("Discard pile :");
        discardLabel.setForeground(Color.BLACK);
        discardLabel.setBackground(new Color(255, 255, 255, 25));
        drawPanelGBC.gridy = 0;
        discardLabel.setFont(defaultFont);
        discardPanel.add(discardLabel, drawPanelGBC);
        drawPanelGBC.gridy = 1;
        discardButton = new SUEButton();
        discardButton.setPreferredSize(drawButtonSize);
        discardPanel.add(discardButton, drawPanelGBC);
    }

    private void createInfoPanel() {
        infoPanel = new SUEPanel();
        infoPanel.setMinimumSize(CardButton.minimumSize);
        infoPanel.setMaximumSize(CardButton.maximumSize);
        infoPanel.setLayout(new GridLayout(2, 1));
        mainPanel.add(infoPanel, BorderLayout.WEST);

        currentPlayerLabel = new JLabel();
        currentPlayerLabel.setForeground(Color.WHITE);
        currentPlayerLabel.setFont(titleFont);
        currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(currentPlayerLabel, BorderLayout.NORTH);

        totalPointsLabel = new JLabel();
        totalPointsLabel.setForeground(Color.BLACK);
        totalPointsLabel.setFont(defaultFont);
        infoPanel.add(totalPointsLabel);

        heldCardPanel = new SUEPanel();
        heldCardPanel.setLayout(new GridBagLayout());
        GridBagConstraints currentCardGBC = new GridBagConstraints();
        currentCardGBC.gridx = 0;
        currentCardGBC.gridy = 0;
        currentCardGBC.weightx = 1;

        heldCardName = new JLabel();
        heldCardName.setForeground(Color.BLACK);
        heldCardName.setFont(defaultFont);
        heldCardPanel.add(heldCardName, currentCardGBC);

        currentCardGBC.gridy = 1;
        heldCardTexture = new SUEPanel();
        heldCardTexture.setPreferredSize(drawButtonSize);
        heldCardPanel.add(heldCardTexture, currentCardGBC);

        infoPanel.add(heldCardPanel);
    }

    /**
     * Creates all the components of the game then it is up to the subclasses to add them to the frame whenever they're needed
     */
    private void createUIComponents () {
        mainPanel = new SUEPanel(background);
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel);

        nextPlayerButton = new SUEButton("Next Player");

        cardPanel = new SUEPanel();
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.setMaximumSize(new Dimension(700,700 ));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cardPanel.setMinimumSize(new Dimension(800, 900));
        GridLayout cardLayout = new GridLayout(3, 4);
        cardLayout.setVgap(5);
        cardLayout.setHgap(5);
        cardPanel.setLayout(cardLayout);

        this.createActionPanel();
        this.createInfoPanel();

        mainPanel.add(createPopupPanel(), BorderLayout.CENTER);
        this.setMenuBar(createMenuBar());
    }

    /**
     * Creates the player input menu
     */
    private SUEPanel createPopupPanel () {

        popupPanelContainer = new SUEPanel();
        popupPanelContainer.setLayout(new GridBagLayout());

        popupPanel = new SUEPanel();
        popupPanel.setBackground(new Color(0x55FFFFFF, true));
        popupPanel.setPreferredSize(maxInfoPanelSize);
        infoPanelGBC = new GridBagConstraints();
        infoPanelGBC.weightx = 1;
        infoPanelGBC.weighty = 1;

        popupPanelContainer.add(popupPanel, infoPanelGBC);

        GridBagLayout infoLayout = new GridBagLayout();
        infoPanelGBC.gridx = 0;
        popupPanel.setLayout(infoLayout);
        popupPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Choose between 2 and 8 player names.");
        titleLabel.setFont(defaultFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);
        infoPanelGBC.gridy = InputMenuPos.TITLE.y;
        popupPanel.add(titleLabel, infoPanelGBC);

        SUEPanel inputPanel = new SUEPanel();
        inputPanel.setLayout(new GridLayout(1, 2));
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        inputPanel.setPreferredSize(new Dimension(450, 25));
        infoPanelGBC.gridy = InputMenuPos.NAME_INPUT.y;
        popupPanel.add(inputPanel, infoPanelGBC);

        nameInputPrompt = new JLabel("Enter a nickname : ");
        nameInputPrompt.setForeground(Color.BLACK);
        nameInputPrompt.setFont(smallFont);
        nameInputPrompt.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        inputPanel.add(nameInputPrompt);

        nameInput = new JTextField();
        nameInput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        inputPanel.add(nameInput);

        buttonPanel = new SUEPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanelGBC.gridy = InputMenuPos.BUTTON_MENU.y;
        popupPanel.add(buttonPanel, infoPanelGBC);

        GridLayout buttonLayout = new GridLayout(1, 2);
        buttonLayout.setHgap(10);
        buttonPanel.setLayout(buttonLayout);

        okButton = new SUEButton("OK");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.setPreferredSize(buttonSize);
        okButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.add(okButton);

        cancelButton = new SUEButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setPreferredSize(buttonSize);
        cancelButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.add(cancelButton);

        launchButton = new SUEButton("Start Game");
        launchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        launchButton.setMaximumSize(buttonSize);
        launchButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        infoPanelGBC.gridy = InputMenuPos.LAUNCH_BUTTON.y;
        popupPanel.add(launchButton, infoPanelGBC);
        launchButton.SUEHide();

        playerList = new JTextPane();
        playerList.setFont(smallFont);
        playerList.setEditable(false);
        playerList.setForeground(Color.BLACK);
        playerList.setBackground(Game.TRANSPARENT);
        infoPanelGBC.gridy = InputMenuPos.PLAYER_LIST.y;
        popupPanel.add(playerList, infoPanelGBC);
        return popupPanelContainer;
    }

    private void setLNF() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (UnsupportedLookAndFeelException e){
            System.err.println("[WARNING] Defaulting to Cross Platform Look and Feel");
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }catch (Exception e1){
                e1.printStackTrace();
                System.err.println("[ERROR] Unsupported Look and Feel");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("[ERROR] Unsupported Look and Feel");
        }
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setFont(smallFont);

        Menu menu = new Menu("Menu");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> this.quit());
        menu.add(exitItem);
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.addActionListener(l -> {
            try {
                Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=dQw4w9WgXcQ").toURI());
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
        menu.add(aboutItem);
        menuBar.add(menu);
        return menuBar;
    }

    /**
     * Creates a dialog box to confirm quitting the game.
     * Then quits if yes is selected or does nothing if no is selected.
     */
    public void quit() {
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Creates a simple popup dialog box with the given message.
     * Usually used to display game events or user input errors.
     * @param message The message to display
     */
    public void announce (String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Updates the given label by setting the text to the given message.
     * @param label The label to update
     * @param message The message to set the label to
     */
    public void updateLabel(JLabel label, String message) {
        label.setText(message);
        this.repaint();
    }
}
