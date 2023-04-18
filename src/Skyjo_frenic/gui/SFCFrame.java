package Skyjo_frenic.gui;

import javax.swing.*;
import java.awt.*;

public class SFCFrame extends JFrame implements SFCComponent {

    ImageIcon background;

    public JLabel getTitleLabel () {
        return titleLabel;
    }

    private JLabel titleLabel;

    public JLabel getPrompt () {
        return prompt;
    }

    public JTextField getNameInput () {
        return nameInput;
    }

    public SFCButton getOkButton () {
        return okButton;
    }

    public JTextPane getPlayerList () {
        return playerList;
    }

    private JLabel prompt;

    private JTextField nameInput;

    private SFCButton okButton;

    private SFCButton cancelButton;

    public SFCButton getCancelButton () {
        return cancelButton;
    }

    private SFCButton launchButton;

    public SFCButton getLaunchButton () {
        return launchButton;
    }
    protected JTextPane playerList;

    private GridLayout layout;

/*
    public void hide () {
        this.setVisible(false);
    }


    public void show () {
        this.setVisible(true);
    }*/

    protected SFCFrame (String title, int w, int h) {
        this.setLNF();
        this.setTitle(title);
        this.setSize(w, h);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocation((1920-this.getWidth())/2, (1080-this.getHeight())/2);
        /*this.setResizable(false);*/
        this.background = new ImageIcon("./ressources/textures/guig.png");
        this.createUIComponents();
    }

    /**
     * TODO : Change how the game handles the player initiation menu.
     * Refer to <a href="./gui_proto.png"> this image<//> for more information.
     */
    private void createUIComponents () {
        var contentPane = this.getContentPane();
        SFCPanel generalPanel = new SFCPanel(background.getImage());
        contentPane.add(generalPanel);

        layout = new GridLayout(4, 1);
        layout.setVgap(10);
        layout.setHgap(10);
        generalPanel.setLayout(layout);

        generalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD , 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        generalPanel.add(titleLabel);

        SFCPanel inputPanel = new SFCPanel(background.getImage());

        inputPanel.setLayout(new GridLayout(1, 2));
        generalPanel.add(inputPanel);

        nameInput = new JTextField(10);
        inputPanel.add(nameInput);

        prompt = new JLabel();
        prompt.setBackground(Color.WHITE);
        prompt.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(prompt);

        SFCPanel buttonPanel = new SFCPanel(background.getImage());
        generalPanel.add(buttonPanel);

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

        playerList = new JTextPane();
        playerList.setFont(new Font("Arial", Font.PLAIN, 14));
        playerList.setEditable(false);
        generalPanel.add(playerList);
    }

    private void setLNF() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (UnsupportedLookAndFeelException e){
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }catch (Exception e1){
                e1.printStackTrace();
                System.err.println("Unsupported Look and Feel");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("Unsupported Look and Feel");
        }
    }
}
