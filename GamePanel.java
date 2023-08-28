import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {

        // Left paddle
        Paddle paddle1;
        // Right paddle
        Paddle paddle2;
        Lines Horizontal_Line;
        Ball ball;
        Score score;
        final int SPEED = 3;
        Random random = new Random();

        int xVelocity = SPEED;  // Horizontal velocity of ball
        int yVelocity = SPEED;  // Vertical velocity of ball
        
        GamePanel() {
            paddle1 = new Paddle(0, 200, 13, 100, Color.red);
            paddle2 = new Paddle(775, 200, 25, 100, Color.blue);
            Horizontal_Line = new Lines(0, 250, 800, 1, Color.white);
            ball = new Ball(395, 245, 10, 10, Color.white);
            //setBackground(new Color(105,160,95));
            setBackground(Color.black);
            setSize(800, 500);
            //setPreferredSize(new Dimension(700, 400));
            setFocusable(true);
            addKeyListener(this);
        }

        // Moves the ball by changing coordinates
        public void moveBall() {
            ball.x += xVelocity;
            ball.y += yVelocity;
        }

        public void Collision() {
            // Resets the ball and paddle positions when ball goes out of bounds
            if (ball.x + 20 > getWidth() || ball.x < 0) {
                ball.x = 395;
                ball.y = random.nextInt(150, 350);
                xVelocity = SPEED;
                yVelocity = SPEED;
                /*score.P1Score++;
                score.P2Score++;*/
            }
            // Reverse direction if the circle reaches the panel's boundaries
            if (ball.y + 50 >= getHeight() || ball.y < 0) {
                yVelocity *= -1;
            }
            // Reverse direction when ball collides with paddles
            if (ball.intersects(paddle1) || ball.intersects(paddle2)) {
                xVelocity *= -1;
                if (xVelocity > 0)
                    xVelocity++;
                else 
                    xVelocity--;
            }
    
            repaint();
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
                // Move the left paddle up when the up arrow is pressed
                case KeyEvent.VK_UP:
                    if (paddle2.getY() > 0) {
                        paddle2.y -= 35; // Move up
                    }
                    break;
                case KeyEvent.VK_DOWN:
                // Move the left paddle down when the down arrow is pressed
                    if (paddle2.getY() + paddle2.height < getHeight()) {
                        paddle2.y += 35; // Move down
                    }
                    break;
                case KeyEvent.VK_W:
                // Move the right paddle up when the w button is pressed
                    if (paddle1.getY() > 0) {
                        paddle1.y -= 35; // Move up
                    }
                    break;
                case KeyEvent.VK_S:
                // Move the right paddle down when the s button is pressed
                    if (paddle1.getY() + paddle2.height < getHeight()) {
                        paddle1.y += 35; // Move down
                    }
                    break;
            }
            repaint(); // Redraw the panel after moving the rectangle
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            System.out.println(e.getKeyCode());
        }

    }