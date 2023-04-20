package Skyjo_frenic;

import javax.swing.*;

public class TestWindow {
    private JPanel panel1;
    private JTextField textField1;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JPanel infoPanel;
    private JTable table1;

    public TestWindow () {
        JFrame frame = new JFrame("TestWindow");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents () {
        // TODO: place custom component creation code here
    }
}
