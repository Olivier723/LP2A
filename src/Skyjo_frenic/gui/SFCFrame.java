package Skyjo_frenic.gui;

import javax.swing.*;
import java.awt.*;

public class SFCFrame extends JFrame implements SFCComponent {

    // Get the user's screen size to make the UI reactive (hopefully)
    Dimension screenSize;
    Texture background;

    private JLabel titleLabel;

    public JLabel getTitleLabel () {
        return titleLabel;
    }

    private JLabel prompt;

    public JLabel getPrompt () {
        return prompt;
    }

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

    public JTextPane getPlayerList () {
        return playerList;
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
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int x = (int) ((screenSize.getWidth() - w) / 2);
        int y = (int) ((screenSize.getHeight() - h) / 2);
        this.setLocation(x, y);
        /*this.setResizable(false);*/
        this.background = Texture.MAT_TEXTURE;
        this.createUIComponents();
    }

    /**
     * TODO : Change how the game handles the player initiation menu.
     * Refer to gui_proto.png for more information.
     */
    private void createUIComponents () {
        var contentPane = this.getContentPane();
        SFCPanel mainPanel = new SFCPanel(background.getImage());
        contentPane.add(mainPanel);

        SFCPanel infoPanel = new SFCPanel(Texture.GUIG.getImage());
        infoPanel.setMaximumSize(new Dimension(500, 500));
        infoPanel.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()/2));
        infoPanel.setMinimumSize(new Dimension(200, 200));
        mainPanel.add(infoPanel);

        GridLayout infoLayout = new GridLayout(4, 1);
        infoLayout.setVgap(10);
        infoLayout.setHgap(10);
        infoPanel.setLayout(infoLayout);

        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD , 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(titleLabel);

        SFCPanel inputPanel = new SFCPanel(background.getImage());

        inputPanel.setLayout(new GridLayout(1, 2));
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        infoPanel.add(inputPanel);

        prompt = new JLabel();
        prompt.setBackground(Color.WHITE);
        prompt.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(prompt);

        nameInput = new JTextField();
        nameInput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        inputPanel.add(nameInput);

        SFCPanel buttonPanel = new SFCPanel(background.getImage());
        infoPanel.add(buttonPanel);

        GridLayout buttonLayout = new GridLayout(1, 2);
        buttonLayout.setHgap(10);
        buttonPanel.setLayout(buttonLayout);

        okButton = new SFCButton("OK");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.setMaximumSize(new Dimension(100, 50));
        okButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.add(okButton);

        cancelButton = new SFCButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setMaximumSize(new Dimension(100, 50));
        cancelButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.add(cancelButton);

        launchButton = new SFCButton("Start Game");
        launchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        launchButton.setMaximumSize(new Dimension(100, 50));
        launchButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        playerList = new JTextPane();
        playerList.setFont(new Font("Arial", Font.PLAIN, 14));
        playerList.setEditable(false);
        infoPanel.add(playerList);

        this.setMenuBar(createMenuBar());
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
        menuBar.setFont(new Font("Arial", Font.BOLD, 14));
        Menu menu = new Menu("Menu");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> this.dispose());
        menu.add(exitItem);
        menuBar.add(menu);
        return menuBar;
    }
}
