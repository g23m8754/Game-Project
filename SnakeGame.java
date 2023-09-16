import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements KeyListener {
    boolean gameover = false;
    int size = 20; // size of each object in the game i.e snake's head and apple
    ArrayList<Point> snakePositions = new ArrayList<Point>(); // tracks all the positions of the snake's head to tail
    Point snakeDirection = new Point(1, 0); // tracks direction of the snake's head
    Point applePosition = new Point(0, 0); // Specifies the position of the apple
    Point specialApple = new Point(0,0); // Specifies the position of the special apple
    int score = 0;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int DELAY = 140; // Delay in milliseconds for the game loop
    private static final int SPECIAL_DELAY = 5000; // 5 seconds in milliseconds
    private Random random = new Random();
    private int SPECIAL_APPLE_LIFETIME = SPECIAL_DELAY;
    //private static int SPECIAL_APPLE_LIVES = 1;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.GREEN);
        setFocusable(true);
        addKeyListener(this);
        StartGame();
        Timer timer = new Timer(DELAY, e -> gameLoop());
        timer.start();
        Timer specialAppleTimer = new Timer(SPECIAL_DELAY, e -> respawnSpecialApple());
        specialAppleTimer.start();
    }

    public void StartGame() {
        gameover = false;
        score = 0;
        snakePositions.clear();
        Point initialPosition = new Point(WIDTH / 2, HEIGHT / 2);
        snakePositions.add(initialPosition);
        randomize(applePosition, 0, (WIDTH / size - 1) * size, 0, (HEIGHT / size - 1) * size);
        randomize(specialApple, 0, (WIDTH / size - 1) * size, 0, (HEIGHT / size - 1) * size);
        
        
    }

    public void respawnSpecialApple() {
        randomize(specialApple, 0, (WIDTH / size - 1) * size, 0, (HEIGHT / size - 1) * size);
        SPECIAL_APPLE_LIFETIME = SPECIAL_DELAY;
    }

    public void randomize(Point position, int minX, int maxX, int minY, int maxY) {
        position.x = random.nextInt((maxX - minX) / size) * size + minX;
        position.y = random.nextInt((maxY - minY) / size) * size + minY;
    }

    public void endGame() {
        gameover = true;
        repaint();
    }

    public void gameLoop() {
        if (gameover) {
            return;
        }
    
        Point newHead = new Point(snakePositions.get(0).x + snakeDirection.x * size,snakePositions.get(0).y + snakeDirection.y * size);
    
        // Check if the new head position is equal to the apple position
        if (newHead.equals(applePosition) || newHead.equals(specialApple)){
            score++;
            snakePositions.add(0, newHead);
            // Generate a new random position for the apple
            randomize(applePosition, 0, (WIDTH / size - 1) * size, 0, (HEIGHT / size - 1) * size);
        } else {
            snakePositions.add(0, newHead);
            snakePositions.remove(snakePositions.size() - 1);
        }
    
        // Check for collision with the wall
        if (newHead.x < 0 || newHead.x >= WIDTH || newHead.y < 0 || newHead.y >= HEIGHT) {
            endGame();
        }
    
        // Check for collision with itself
        for (int i = 1; i < snakePositions.size(); i++) {
            if (newHead.equals(snakePositions.get(i))) {
                endGame();
                break;
            }
        }
    
        repaint();
    }
    

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw score
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);

        // Draw the apple in red
        g.setColor(Color.RED);
        g.fillOval(applePosition.x, applePosition.y, size, size);

        // Draw SpecialApple in blue if score is a multiple of 5 and its lifetime has not expired
       if (score % 5 == 0 && score!= 0 && SPECIAL_APPLE_LIFETIME > 0 ) {
            g.setColor(Color.BLUE);
            g.fillOval(specialApple.x, specialApple.y, size, size);
        }

        // Draw the snake's body in Black
        g.setColor(Color.BLACK);
        for (Point position : snakePositions) {
            g.fillOval(position.x, position.y, size, size);
        }


        if (gameover) {
            g.setColor(Color.WHITE);
            g.drawString("Game Over", WIDTH / 2 - 40, HEIGHT / 2);
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!gameover) {
            if (keyCode == KeyEvent.VK_LEFT && snakeDirection.x != 1) {
                snakeDirection.setLocation(-1, 0);
            } else if (keyCode == KeyEvent.VK_RIGHT && snakeDirection.x != -1) {
                snakeDirection.setLocation(1, 0);
            } else if (keyCode == KeyEvent.VK_UP && snakeDirection.y != 1) {
                snakeDirection.setLocation(0, -1);
            } else if (keyCode == KeyEvent.VK_DOWN && snakeDirection.y != -1) {
                snakeDirection.setLocation(0, 1);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake Game");
            frame.add(new SnakeGame());
            frame.pack();
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
