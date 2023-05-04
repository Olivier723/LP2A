package Skyjo_frenic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

/**
 * Hides some of the ugly code of the GUI behind a simpler interface.
 */
public class SFCFrame extends JFrame implements SFCComponent {
    public final Dimension screenSize;
    private final SFCTexture background;
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

    protected JLabel prompt;

    protected JTextField nameInput;

    private SFCButton okButton;

    public SFCButton getOkButton () {
        return okButton;
    }

    private SFCButton cancelButton;

    public SFCButton getCancelButton () {
        return cancelButton;
    }

    private SFCButton launchButton;

    public SFCButton getLaunchButton () {
        return launchButton;
    }

    protected JTextPane playerList;

    protected SFCPanel infoPanel;

    protected JLabel currentPlayerLabel;

    protected JLabel generalInfoLabel;

    protected SFCPanel popupPanel;

    protected SFCPanel popupPanelContainer;

    protected SFCPanel mainPanel;

    protected SFCPanel cardPanel;

    protected SFCPanel buttonPanel;

    protected SFCButton nextPlayerButton;

    protected SFCPanel actionsPanel;

    protected SFCButton drawButton;

    protected SFCButton discardButton;

    protected GridBagConstraints infoPanelGBC;
    protected GridBagConstraints actionsPanelGBC;

    public static Dimension getScreenSize () {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    @Override
    public void SFCHide () {
        this.setVisible(false);
    }

    @Override
    public void SFCShow () {
        this.setVisible(true);
    }

    protected SFCFrame (String title, int w, int h) {
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLNF();
        this.setTitle(title);
        this.setSize(w, h);
        this.setMinimumSize(new Dimension(800, 600));

        // Makes it so that the program asks if the user wants to quit before closing.
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing (WindowEvent e) {
                quit();
            }
        });

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        int x = (int) ((screenSize.getWidth() - w) / 2);
        int y = (int) ((screenSize.getHeight() - h) / 2);
        this.setLocation(x, y);
        this.background = SFCTexture.MAT_TEXTURE;
        this.createUIComponents();
    }

    /**
     */
    private void createUIComponents () {
        mainPanel = new SFCPanel(background);
        /*mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized (ComponentEvent e) {
                super.componentResized(e);
                Dimension size = mainPanel.getSize();
                Dimension newSize = new Dimension(size.width - 20, size.height - 20);
                infoPanel.setPreferredSize(newSize);
            }
        });*/
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel);

        nextPlayerButton = new SFCButton("Next Player");
        nextPlayerButton.setPreferredSize(buttonSize);

        cardPanel = new SFCPanel(SFCTexture.GUIG);
        /*GridBagLayout cardLayout = new GridBagLayout();*/
        GridLayout cardLayout = new GridLayout(3, 4);
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.setLayout(cardLayout);
        cardPanel.setMaximumSize(new Dimension(700,700 ));

        cardPanel.setMinimumSize(new Dimension(700, 700));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        actionsPanel = new SFCPanel();
        actionsPanel.setLayout(new GridBagLayout());
        actionsPanelGBC = new GridBagConstraints();
        mainPanel.add(actionsPanel, BorderLayout.EAST);

        drawButton = new SFCButton("Draw");
        actionsPanelGBC.gridx = 0;
        actionsPanelGBC.gridy = 0;
        drawButton.setMaximumSize(CardButton.maximumSize);
        drawButton.setMinimumSize(CardButton.minimumSize);
        actionsPanel.add(drawButton, actionsPanelGBC);

        discardButton = new SFCButton("Discard");
        actionsPanelGBC.gridy = 2;
        discardButton.setMaximumSize(CardButton.maximumSize);
        discardButton.setMinimumSize(CardButton.minimumSize);
        actionsPanel.add(discardButton, actionsPanelGBC);

        /*SFCPanel padding = new SFCPanel(background);
        padding.setPreferredSize(CardButton.cardSize);
        mainPanel.add(padding, BorderLayout.WEST);*/

        infoPanel = new SFCPanel();
        infoPanel.setLayout(new GridLayout(2, 1));

        currentPlayerLabel = new JLabel();
        currentPlayerLabel.setForeground(Color.WHITE);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        infoPanel.add(currentPlayerLabel);
        mainPanel.add(infoPanel, BorderLayout.WEST);

        generalInfoLabel = new JLabel();
        generalInfoLabel.setForeground(Color.WHITE);
        generalInfoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        infoPanel.add(generalInfoLabel);



        mainPanel.add(createPopupPanel(), BorderLayout.CENTER);
        this.setMenuBar(createMenuBar());
    }

    /**
     * Creates the player input menu and also win menu.
     */
    private SFCPanel createPopupPanel () {

        popupPanelContainer = new SFCPanel(SFCTexture.CARD_BACK);
        popupPanelContainer.setLayout(new GridBagLayout());

        popupPanel = new SFCPanel(SFCTexture.GUIG);
        popupPanel.setPreferredSize(maxInfoPanelSize);
        popupPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
        infoPanelGBC = new GridBagConstraints();
        infoPanelGBC.weightx = 1;
        infoPanelGBC.weighty = 1;

        popupPanelContainer.add(popupPanel, infoPanelGBC);

        GridBagLayout infoLayout = new GridBagLayout();
        infoPanelGBC.gridx = 0;
        popupPanel.setLayout(infoLayout);
        popupPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Choose between 2 and 8 player names.");
        titleLabel.setFont(new Font("Arial", Font.BOLD , 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanelGBC.gridy = InputMenuPos.TITLE.y;
        popupPanel.add(titleLabel, infoPanelGBC);

        SFCPanel inputPanel = new SFCPanel(background);
        inputPanel.setLayout(new GridLayout(1, 2));
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        inputPanel.setPreferredSize(new Dimension(450, 25));
        infoPanelGBC.gridy = InputMenuPos.NAME_INPUT.y;
        popupPanel.add(inputPanel, infoPanelGBC);

        prompt = new JLabel("Enter a nickname : ");
        prompt.setBackground(Color.WHITE);
        prompt.setFont(new Font("Arial", Font.BOLD, 14));
        prompt.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        inputPanel.add(prompt);

        nameInput = new JTextField();
        nameInput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        inputPanel.add(nameInput);

        buttonPanel = new SFCPanel(background);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanelGBC.gridy = InputMenuPos.BUTTON_MENU.y;
        popupPanel.add(buttonPanel, infoPanelGBC);

        GridLayout buttonLayout = new GridLayout(1, 2);
        buttonLayout.setHgap(10);
        buttonPanel.setLayout(buttonLayout);

        okButton = new SFCButton("OK");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.setPreferredSize(buttonSize);
        okButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.add(okButton);

        cancelButton = new SFCButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setPreferredSize(buttonSize);
        cancelButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.add(cancelButton);

        launchButton = new SFCButton("Start Game");
        launchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        launchButton.setMaximumSize(buttonSize);
        launchButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        playerList = new JTextPane();
        playerList.setFont(new Font("Arial", Font.PLAIN, 14));
        playerList.setEditable(false);
        playerList.setBackground(new Color(0, 0, 0, 0));
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
        menuBar.setFont(new Font("Arial", Font.BOLD , 14));

        Menu menu = new Menu("Menu");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> this.quit());
        menu.add(exitItem);
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.addActionListener(l -> {
            try {
                Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=dQw4w9WgXcQ").toURI());
            } catch (Exception e) {
                System.out.println("Bruh");
            }
        });
        menu.add(aboutItem);
        menuBar.add(menu);
        return menuBar;
    }

    /**
     * Creates a dialog box to confirm quitting the game.
     */
    public void quit() {
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * @param message The message to display
     */
    public void announce (String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void updateLabel(JLabel label, String message) {
        label.setText(message);
        this.repaint();
    }
}
