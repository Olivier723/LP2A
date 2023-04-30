package Skyjo_frenic.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SFCFrame extends JFrame implements SFCComponent {

    // Get the user's screen size to make the UI reactive (hopefully)
    public final Dimension screenSize;

    private final Texture background;

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

    private JTextField nameInput;

    public JTextField getNameInput () {
        return nameInput;
    }

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

    protected SFCPanel mainPanel;

    protected SFCPanel cardPanel;

    protected SFCPanel buttonPanel;

    protected GridBagConstraints gbc;

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
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int x = (int) ((screenSize.getWidth() - w) / 2);
        int y = (int) ((screenSize.getHeight() - h) / 2);
        this.setLocation(x, y);
        this.background = Texture.MAT_TEXTURE;
        this.createUIComponents();
    }

    /**
     * TODO : Change how the game handles the player initiation menu.
     * Refer to gui_proto.png for more information.
     */
    private void createUIComponents () {
        mainPanel = new SFCPanel(background);
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel);

        SFCButton testButton = new SFCButton("Test");
        testButton.addActionListener(e -> {
            System.out.println("Test");
        });
        mainPanel.add(testButton, BorderLayout.NORTH);

        cardPanel = new SFCPanel(background);
        GridBagLayout cardLayout = new GridBagLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        SFCPanel panel = new SFCPanel(background);
        panel.setLayout(new GridBagLayout());
        mainPanel.add(panel, BorderLayout.CENTER);
        panel.add(createInfoPanel());
        this.setMenuBar(createMenuBar());
    }

    /**
     * Creates the player input menu and also win menu. (Hopefully)
     */
    private SFCPanel createInfoPanel() {

        infoPanel = new SFCPanel(Texture.GUIG);
        infoPanel.setPreferredSize(maxInfoPanelSize);

        GridBagLayout infoLayout = new GridBagLayout();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        infoPanel.setLayout(infoLayout);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Choose between 2 and 8 player names.");
        titleLabel.setFont(new Font("Arial", Font.BOLD , 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = InputMenuPos.TITLE.y;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(titleLabel, gbc);

        SFCPanel inputPanel = new SFCPanel(background);
        inputPanel.setLayout(new GridLayout(1, 2));
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        inputPanel.setPreferredSize(new Dimension(450, 25));
        gbc.gridy = InputMenuPos.NAME_INPUT.y;
        infoPanel.add(inputPanel, gbc);

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
        gbc.gridy = InputMenuPos.BUTTON_MENU.y;
        infoPanel.add(buttonPanel, gbc);

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
        gbc.gridy = InputMenuPos.PLAYER_LIST.y;
        infoPanel.add(playerList, gbc);
        return infoPanel;
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
        exitItem.addActionListener(e -> this.dispose());
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
}
