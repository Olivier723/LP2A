package HalfLife3.gui;

import javax.swing.*;
import java.awt.*;

public class HF3Frame extends JFrame {

    ImageIcon background;

    public JLabel getTitleLabel () {
        return titleLabel;
    }

    private JLabel titleLabel;

    public JLabel getPrompt () {
        return prompt;
    }

    public JTextField getTextField1 () {
        return textField1;
    }

    public JButton getButton () {
        return button;
    }

    public JTextPane getPlayerList () {
        return playerList;
    }

    private JLabel prompt;
    private JTextField textField1;
    private JButton button;
    private JTextPane playerList;

    protected HF3Frame (String title, int w, int h) {
        this.setLNF();
        this.setTitle(title);
        this.setSize(w, h);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocation((1920-this.getWidth())/2, (1080-this.getHeight())/2);
        /*this.setResizable(false);*/
        this.background = new ImageIcon("./ressources/textures/guig.png");
        this.createUIComponents();
    }

    private void createUIComponents () {
        var contentPane = this.getContentPane();
        HF3Panel generalPanel = new HF3Panel(background.getImage());
        contentPane.add(generalPanel);
        GridLayout layout = new GridLayout(4, 1);
        layout.setVgap(10);
        layout.setHgap(10);
        generalPanel.setLayout(layout);
        generalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD , 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        generalPanel.add(titleLabel);
        HF3Panel inputPanel = new HF3Panel(background.getImage());
        inputPanel.setLayout(new GridLayout(1, 2));
        textField1 = new JTextField(10);
        prompt = new JLabel();
        prompt.setBackground(Color.WHITE);
        prompt.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(prompt);
        inputPanel.add(textField1);
        generalPanel.add(inputPanel);
        button = new JButton("OK");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(100, 50));
        button.setAlignmentY(Component.CENTER_ALIGNMENT);
        generalPanel.add(button);
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
