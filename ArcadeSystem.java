import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArcadeSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    public ArcadeSystem() {
        setTitle("Arcade System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        addMenu();
        addSnakeGame();
        addTicTacToeGame();
        
        cardLayout.show(cardPanel, "Menu");
        
        add(cardPanel);
    }
    
    private void addMenu() {
        JPanel menuPanel = new JPanel(new GridLayout(2, 1));
        
        JButton snakeButton = new JButton("Play Snake");
        snakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Snake");
            }
        });
        
        JButton ticTacToeButton = new JButton("Play Tic-Tac-Toe");
        ticTacToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "TicTacToe");
            }
        });
        
        menuPanel.add(snakeButton);
        menuPanel.add(ticTacToeButton);
        
        cardPanel.add(menuPanel, "Menu");
    }
    
    private void addSnakeGame() {
        SnakeGamePanel snakeGamePanel = new SnakeGamePanel();
        cardPanel.add(snakeGamePanel, "Snake");
    }
    
    private void addTicTacToeGame() {
        TicTacToePanel ticTacToePanel = new TicTacToePanel();
        cardPanel.add(ticTacToePanel, "TicTacToe");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ArcadeSystem arcadeSystem = new ArcadeSystem();
                arcadeSystem.setVisible(true);
            }
        });
    }
}