import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements KeyListener {
    boolean gameover = false;
    int size = 25; // size of each object in the game i.e snake's head and apple
    ArrayList<Point> snakePositions = new ArrayList<Point>(); // tracks all the positions of the snake's head to tail
    Point snakeDirection = new Point(1, 0); // tracks direction of the snake's head
    Point applePosition = new Point(0, 0); // Specifies the position of the apple
    Point specialApple = new Point(0, 0); // Specifies the position of the special apple
    int score = 0;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int DELAY = 140; // Delay in milliseconds for the game loop
    private static final int SPECIAL_DELAY = 5000; // 5 seconds in milliseconds
    private Random random = new Random();
    private boolean specialAppleVisible = false;
    private Timer specialAppleTimer;
    private JButton playAgainButton;
    JButton Quit = new JButton("Quit");



    // Create two timers for special apple logic
    private Timer specialAppleSpawnTimer;
    private Timer specialAppleDisappearTimer;

    sEffects sound = new sEffects();
	
	public void playSE(int s){
		sound.setFile(s);
		sound.Play();
    }
    
    public SnakeGame() {
        setBounds(150, 0, WIDTH, HEIGHT);
        setBackground(Color.decode("#69B41E"));
        setFocusable(true);
        addKeyListener(this);
        StartGame();
        Timer timer = new Timer(DELAY, e -> gameLoop());
        timer.start();
        setLayout(null);

        // Initialize the quit button
        Quit.setFont(new Font("MV Boli", Font.BOLD, 15));
        Quit.setBounds(30, 18, 70, 30);
        Quit.setBackground(Color.decode("#94B447"));
        Quit.setForeground(Color.WHITE);
        Quit.setBorderPainted(false);
        Quit.setOpaque(false);
        add(Quit);

        // Initialize the play again button
        playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds(WIDTH / 2 - 75, HEIGHT / 2 - 15, 150, 30);
        playAgainButton.setBackground(Color.decode("#69B41E"));
        playAgainButton.setFont(new Font("MV Boli", Font.BOLD, 15));
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setBorderPainted(false);
        playAgainButton.addActionListener(e -> handlePlayAgain());

        // Add the button to the panel but initially set it as invisible
        playAgainButton.setVisible(false);
        add(playAgainButton);

        // Create the timer for spawning the special apple every 60 seconds
        specialAppleSpawnTimer = new Timer(60 * 1000, e -> SpawnSpecialApple());
        specialAppleSpawnTimer.setRepeats(true);
        specialAppleSpawnTimer.start();

        // Create the timer for making the special apple disappear after 10 seconds
        specialAppleDisappearTimer = new Timer(10 * 1000, e -> handleSpecialAppleTimer());
        specialAppleDisappearTimer.setRepeats(false);
        specialAppleDisappearTimer.start();

        specialAppleTimer = new Timer(SPECIAL_DELAY, e -> handleSpecialAppleTimer());
    }

    public void StartGame() {
        gameover = false;
        score = 0;
        snakePositions.clear();
        Point initialPosition = new Point(WIDTH / 2, HEIGHT / 2);
        snakePositions.add(initialPosition);
        randomize(applePosition, 0, (WIDTH / size - 1) * size, 0, (HEIGHT / size - 1) * size);
    }

    public void handlePlayAgain() {
        StartGame();
        gameover = false;
        playAgainButton.setVisible(false);
        requestFocus();
    }

    public void SpawnSpecialApple() {
        randomize(specialApple, 0, (WIDTH / size - 1) * size, 0, (HEIGHT / size - 1) * size);
        specialAppleVisible = true;
        specialAppleTimer.setDelay(SPECIAL_DELAY); // Reset the timer delay

        // Reset the special apple spawn timer for another 60 seconds
        specialAppleSpawnTimer.restart();

        // Start the special apple disappearance timer (it will automatically call handleSpecialAppleTimer)
        specialAppleDisappearTimer.start();
    }
    

    public void handleSpecialAppleTimer() {
        if (specialAppleVisible) {
            specialAppleVisible = false;
            specialAppleTimer.setDelay(SPECIAL_DELAY); // Reset the timer delay
            randomize(specialApple, 0, (WIDTH / size - 1) * size, 0, (HEIGHT / size - 1) * size);

            // Stop the special apple spawn timer
            specialAppleSpawnTimer.stop();
        }
    }
    

    public void randomize(Point position, int minX, int maxX, int minY, int maxY) {
        position.x = random.nextInt((maxX - minX) / size) * size + minX;
        position.y = random.nextInt((maxY - minY) / size) * size + minY;
    }

    public void endGame() {
        gameover = true;
        playAgainButton.setVisible(true);
        repaint();
    }
    

    // Inside the gameLoop method:
    public void gameLoop() {
        if (gameover) {
            return;
        }

        Point newHead = new Point(snakePositions.get(0).x + snakeDirection.x * size, snakePositions.get(0).y + snakeDirection.y * size);

        // Check if the new head position is equal to the apple position
        if (newHead.equals(applePosition)) {
            score++;
            playSE(5);
            snakePositions.add(0, newHead);
            // Generate a new random position for the apple
            randomize(applePosition, 0, (WIDTH / size - 1) * size, 0, (HEIGHT / size - 1) * size);
        } else if (newHead.equals(specialApple)) {
            score += 2;
            playSE(5);
            snakePositions.add(0, newHead);
            specialAppleVisible = false; // Special apple is consumed
            specialAppleTimer.setDelay(SPECIAL_DELAY); // Reset the timer delay
        } else {
            snakePositions.add(0, newHead);
            snakePositions.remove(snakePositions.size() - 1);
        }

        // Check for collision with the wall
        if (newHead.x < 0 || newHead.x >= WIDTH || newHead.y < 0 || newHead.y >= HEIGHT) {
            playSE(2);
            endGame();
        }

        // Check for collision with itself
        for (int i = 1; i < snakePositions.size(); i++) {
            if (newHead.equals(snakePositions.get(i))) {
                playSE(3);
                endGame();
                break;
            }
        }

        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast Graphics to Graphics2D to use setStroke
        Graphics2D g2d = (Graphics2D) g;

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        g.drawString("Score: " + score, 220, 25);

        // Draw the apple in red
        g.setColor(Color.RED);
        g.fillOval(applePosition.x, applePosition.y, size, size);

        // Draw SpecialApple in blue if score is a multiple of 5 and it's visible
        if (score % 5 == 0 && score != 0 && specialAppleVisible) {
            g.setColor(Color.BLUE);
            g.fillOval(specialApple.x, specialApple.y, size, size);
        }

        // Draw the snake's body in Black
        g.setColor(Color.BLACK);
        for (Point position : snakePositions) {
            g.fillOval(position.x, position.y, size, size);
        }

        // Set the thickness for the border lines
        Stroke sk = g2d.getStroke();
        g2d.setStroke(new BasicStroke(6.0f)); 
        // Draw border lines
        g.setColor(Color.BLACK); // set the color of the boarder lines
        g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1); // Draw a rectangle around the entire panel

        // Restore the previous stroke
        g2d.setStroke(sk);
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

}
