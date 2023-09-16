import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToePanel extends JPanel {
    private JButton[][] buttons;
    private char currentPlayer;
    private JLabel statusLabel;

    public TicTacToePanel() {
        setLayout(new BorderLayout());

        buttons = new JButton[3][3];
        currentPlayer = 'X';

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        statusLabel = new JLabel("Player " + currentPlayer + "'s turn", JLabel.CENTER);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                gamePanel.add(buttons[row][col]);
            }
        }

        add(gamePanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().equals("")) {
                buttons[row][col].setText(String.valueOf(currentPlayer));
                buttons[row][col].setEnabled(false);

                if (checkWin()) {
                    statusLabel.setText("Player " + currentPlayer + " wins!");
                    disableAllButtons();
                } else if (isBoardFull()) {
                    statusLabel.setText("It's a draw!");
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    statusLabel.setText("Player " + currentPlayer + "'s turn");
                }
            }
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true; // Check rows
            }
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                return true; // Check columns
            }
        }
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true; // Check diagonal \
        }
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true; // Check diagonal /
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableAllButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.add(new TicTacToePanel());
        frame.setVisible(true);
    }
}