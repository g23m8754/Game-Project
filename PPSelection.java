import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;

public class PPSelection extends JPanel {

    JButton SButton = new JButton("Single Player");
    JButton MButton = new JButton("Multiplayer");
    JButton Back = new JButton("Back");
    JTextField rounds = new JTextField("");
    JLabel numOfRounds = new JLabel("First to get to:");

    PPSelection() {
        setBounds(0, 0, 800, 525);
        setBackground(new Color(0x123456));
        setLayout(null);

        // Button to return to main menu
        Back.setBounds(30, 30, 70, 27);
        Back.setFont(new Font("MV Boli", Font.PLAIN,15));
        Back.setForeground(Color.WHITE);
        Back.setBackground(new Color(0x123456));
        Back.setOpaque(false);
        Back.setBorderPainted(false);
        add(Back);

        // Text box to enter the number of rounds
        rounds.setBounds(getWidth()/2 + 50, getHeight()/2 - 77, 30, 27);
        rounds.setBackground(new Color(0x123456));
        rounds.setForeground(Color.WHITE);
        rounds.setFont(new Font("MV Boli", Font.PLAIN,15));
        add(rounds);

        // Button to play single player
        SButton.setBounds(getWidth()/2 - 60, getHeight()/2 - 30, 150, 27);
        SButton.setFont(new Font("MV Boli", Font.PLAIN,15));
        SButton.setForeground(new Color(0x123456));
        SButton.setBackground(Color.WHITE);
        add(SButton);

        // Button to play multiplayer
        MButton.setBounds(getWidth()/2 - 60, getHeight()/2 + 30, 150, 27);
        MButton.setFont(new Font("MV Boli", Font.PLAIN,15));
        MButton.setForeground(new Color(0x123456));
        MButton.setBackground(Color.WHITE);
        add(MButton);

        numOfRounds.setBounds(getWidth()/2 - 70, getHeight()/2 - 77, 150, 27);
        numOfRounds.setFont(new Font("MV Boli", Font.PLAIN,15));
        numOfRounds.setForeground(Color.WHITE);
        add(numOfRounds);
    }
    
}
