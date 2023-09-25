import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class Selection extends JPanel {

    JButton button = new JButton("Start");

    Selection() {
        setBounds(0, 0, 800, 500);
        setBackground(Color.red);
        setLayout(new GridBagLayout()); // Use GridBagLayout for panel1

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(button, gbc); // Add button1 to panel1 with GridBagConstraints

        // add(button);
        setFocusable(true);
    }
    
}
