import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
/*import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;*/
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
//import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {

        // Left paddle
        final Paddle paddle1;
        // Right paddle
        final Paddle paddle2;

        final Lines Horizontal_Line;
        final Ball ball;
        int Velocity = 3;
        Random random = new Random();

        JLabel Score1 = new JLabel("0  "); // Display score for player 1
        JLabel Score2 = new JLabel("0"); // Display score for player 2

        int p1Score = 0; // Score for player 1
        int p2Score = 0; // Score for player 2

        int xVelocity = Velocity;  // Horizontal velocity of ball
        int yVelocity = Velocity;  // Vertical velocity of ball
        
        boolean running = false;

        JLabel WinnerMessage = new JLabel();

        sEffects sound = new sEffects();
		
		public void playMusic(int i){
			sound.setFile(i);
			sound.Play();
			sound.Loop();
		}
		
		public void stopMusic(int i){
			sound.stop();
		}
		
		public void playSE(int i){
			sound.setFile(i);
			sound.Play();
		}

        GamePanel() {
            paddle1 = new Paddle(0, 200, 13, 100, Color.red);
            paddle2 = new Paddle(775, 200, 25, 100, Color.blue);
            Horizontal_Line = new Lines(0, 250, 800, 1, Color.white);
            ball = new Ball(395, 245, 10, 10, Color.white);

            WinnerMessage.setVisible(false);
            WinnerMessage.setForeground(new Color(57, 255, 20));
            WinnerMessage.setFont(new Font("Calibri", Font.BOLD, 50));
            add(WinnerMessage);

            setBackground(Color.black);
            setSize(800, 500);
            Score1.setBounds(0,0,50,50);
            Score1.setForeground(new Color(255, 49, 49));
            Score1.setFont(new Font("Calibri", Font.BOLD, 30));

            Score2.setBounds(780, 0, 50, 50);
            Score2.setForeground(new Color(80, 200, 230));
            Score2.setFont(new Font("Calibri", Font.BOLD, 30));

            add(Score1);
            add(Score2);
            setFocusable(true);
            addKeyListener(this);
        }

        // Moves the ball by changing coordinates and calls the move method from Paddle
        public void move() {
            ball.x += xVelocity;
            ball.y += yVelocity;
            paddle1.Move();
            paddle2.Move();
        }

        public void collision() {
            // Check if paddle goes off screen
            if (paddle1.y <= 0)
                paddle1.y = 0;
            if (paddle1.y + paddle1.height >= getHeight())
                paddle1.y = getHeight() - paddle1.height;
            if (paddle2.y <= 0)
                paddle2.y = 0;
            if (paddle2.y + paddle2.height >= getHeight())
                paddle2.y = getHeight() - paddle2.height;

            // Resets the ball and paddle positions when ball goes out of bounds
            if (ball.x + 10 > getWidth() || ball.x < 0) {
                scoreCount();
                ball.x = 395;
                ball.y = random.nextInt(150, 350);
                Velocity *= -1;
                xVelocity = Velocity;
                yVelocity = Velocity;
                paddle1.y = 200;
                paddle2.y = 200;
            }

            // Reverse direction if the circle reaches the panel's boundaries
            if (ball.y + 50 >= getHeight() || ball.y < 0) {
                yVelocity *= -1;
                playSE(2); //sound effect
            }

            // Reverse direction when ball collides with paddles
            if (ball.intersects(paddle1) || ball.intersects(paddle2)) {
                xVelocity *= -1;
                if (xVelocity > 0) {
                    if (xVelocity <= 11) {
                        xVelocity++;
                    }
                } else {
                    if (xVelocity >= -11) { 
                        xVelocity--;
                    }
                }
                playSE(2); //sound effect
            }
    
            repaint();
        }

        public void winner() {
            if (p1Score == 11) {
                WinnerMessage.setVisible(true);
                WinnerMessage.setText("Player 1 wins!");
                running = true;
            } else if (p2Score == 11) {
                WinnerMessage.setVisible(true);
                WinnerMessage.setText("Player 2 wins!");
                running = true;
            }
        }

        public void scoreCount() {
            if (ball.x <= 0) {
                p2Score++;
                Score2.setText("" + p2Score + "  ");
                playSE(3); //sound effect
            }
            if (ball.x + 10 >= 800) {
                p1Score++;
                Score1.setText("" + p1Score);
                playSE(3); //sound effect
            }
        }

        // Paint the components onto the game screen
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g.setColor(Horizontal_Line.color);
            g.drawLine(Horizontal_Line.x, Horizontal_Line.y, Horizontal_Line.x + Horizontal_Line.width, Horizontal_Line.y + Horizontal_Line.height);

            g2d.setColor(paddle1.color);
            g2d.fillRect(paddle1.x, paddle1.y, paddle1.width, paddle1.height);

            g2d.setColor(paddle2.color);
            g2d.fillRect(paddle2.x, paddle2.y, paddle2.width, paddle2.height);

            g2d.setColor(ball.color);
            g2d.fillOval(ball.x, ball.y, ball.width, ball.height);

        }   


        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    // Move the left paddle up when the up arrow is pressed
                    paddle2.yDirection(-10);
                    paddle2.Move(); // Move up
                    break;
                case KeyEvent.VK_DOWN:
                    // Move the left paddle down when the down arrow is pressed
                    paddle2.yDirection(10);
                    paddle2.Move(); // Move down
                    break;
                case KeyEvent.VK_W:
                    // Move the right paddle up when the w button is pressed
                    paddle1.yDirection(-10);
                    paddle1.Move(); // Move up
                    break;
                case KeyEvent.VK_S:
                    // Move the right paddle down when the s button is pressed
                    paddle1.yDirection(10);
                    paddle1.Move(); // Move down
                    break;
                case KeyEvent.VK_ESCAPE:
                    // Escape button to stop the game loop
                    running = true;
                    setVisible(false);
                    // setVisible(false);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            System.out.println(e.getKeyCode());
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    // Right paddle stops moving when the up arrow is released
                    paddle2.yDirection(0);
                    paddle2.Move();
                    break;
                case KeyEvent.VK_DOWN:
                    // Left paddle stops moving when the down arrow is released
                    paddle2.yDirection(0);
                    paddle2.Move();
                    break;
                case KeyEvent.VK_W:
                    // Right paddle stops moving when the w button is released
                    paddle1.yDirection(0);
                    paddle1.Move();
                    break;
                case KeyEvent.VK_S:
                    // Left paddle stops moving when the s button is released
                    paddle1.yDirection(0);
                    paddle1.Move();
                    break;
            }
        }

    }