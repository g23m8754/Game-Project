import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGamePanel extends JPanel implements ActionListener {
    private final int TILE_SIZE = 20; // Size of each tile
    private final int WIDTH = 20; // Width of the game panel in tiles
    private final int HEIGHT = 15; // Height of the game panel in tiles
    private final int TOTAL_TILES = WIDTH * HEIGHT; // Total number of tiles
    private final int DELAY = 150; // Delay between timer ticks (controls the speed of the game)

    private ArrayList<Point> snake;
    private Point food;
    private int score;
    private boolean gameOver;

    private enum Direction { UP, DOWN, LEFT, RIGHT }
    private Direction direction = Direction.RIGHT;

    private Timer timer;

    public SnakeGamePanel() {
        setPreferredSize(new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });

        initGame();
    }

    private void initGame() {
        snake = new ArrayList<>();
        snake.add(new Point(5, 5)); // Initial position of the snake
        generateFood();
        score = 0;
        gameOver = false;

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void generateFood() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(HEIGHT);
        } while (snake.contains(new Point(x, y)));
        food = new Point(x, y);
    }

    private void handleKeyPress(int keyCode) {
        if (!gameOver) {
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    if (direction != Direction.DOWN)
                        direction = Direction.UP;
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != Direction.UP)
                        direction = Direction.DOWN;
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != Direction.RIGHT)
                        direction = Direction.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != Direction.LEFT)
                        direction = Direction.RIGHT;
                    break;
            }
        }
    }

    private void move() {
        Point head = snake.get(0);
        Point newHead = new Point(head.x, head.y);

        switch (direction) {
            case UP:
                newHead.y--;
                break;
            case DOWN:
                newHead.y++;
                break;
            case LEFT:
                newHead.x--;
                break;
            case RIGHT:
                newHead.x++;
                break;
        }

        // Check if the snake collides with itself or goes out of bounds
        if (newHead.equals(food) || (newHead.x >= 0 && newHead.x < WIDTH && newHead.y >= 0 && newHead.y < HEIGHT && !snake.contains(newHead))) {
            snake.add(0, newHead);

            // If the snake eats the food
            if (newHead.equals(food)) {
                score++;
                generateFood();
            } else {
                snake.remove(snake.size() - 1); // Remove the tail
            }
        } else {
            gameOver = true;
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!gameOver) {
            // Draw food
            g.setColor(Color.RED);
            g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            // Draw snake
            g.setColor(Color.GREEN);
            for (Point point : snake) {
                g.fillRect(point.x * TILE_SIZE, point.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }

            // Draw score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, 10, 20);
        } else {
            // Game over message
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", WIDTH * TILE_SIZE / 2 - 110, HEIGHT * TILE_SIZE / 2 - 20);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press 'R' to restart", WIDTH * TILE_SIZE / 2 - 90, HEIGHT * TILE_SIZE / 2 + 20);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGamePanel panel = new SnakeGamePanel();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel.requestFocusInWindow();
    }
}