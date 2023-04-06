package Skyjo;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

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

    public JTextPane getCurrentPlayers () {
        return currentPlayers;
    }

    private JLabel prompt;
    private JTextField textField1;
    private JButton button;
    private JTextPane currentPlayers;

    GameFrame (String title, int w, int h) {
        this.setTitle(title);
        this.setSize(w, h);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Color.WHITE);
        this.setLocation((1920-this.getWidth())/2, (1080-this.getHeight())/2);
        this.setResizable(false);
        this.createUIComponents();
    }

    private void createUIComponents () {
        var contentPane = this.getContentPane();
        JPanel generalPanel = new JPanel();
        contentPane.add(generalPanel);
        GridLayout layout = new GridLayout(4, 1);
        layout.setVgap(10);
        layout.setHgap(10);
        generalPanel.setLayout(layout);
        generalPanel.setBackground(Color.WHITE);
        generalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD , 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        generalPanel.add(titleLabel);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2));
        inputPanel.setBackground(Color.WHITE);
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
        currentPlayers = new JTextPane();
        currentPlayers.setFont(new Font("Arial", Font.PLAIN, 14));
        currentPlayers.setBackground(Color.WHITE);
        generalPanel.add(currentPlayers);
    }
}
