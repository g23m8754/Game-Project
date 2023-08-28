import java.awt.Color;

import javax.swing.*;

public class PingPongFrame extends JFrame {
    
    GamePanel panel = new GamePanel();

    PingPongFrame() {
        setTitle("Ping Pong");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.gray);
        setLayout(null);
        setResizable(false);
       

        // Original game loop
        /*Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.moveBall();
            }
        });
        timer.start();*/

        add(panel);

        setVisible(true);

        // Game loop
        long lastTime = System.nanoTime();
        double amountOfTicks =60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        boolean running = false;
        while(!(running)) {
            long now = System.nanoTime();
            delta += (now -lastTime)/ns;
            lastTime = now;
            if (delta >= 1) {
                panel.moveBall();
                panel.Collision();
                repaint();
                delta--;
            }
        }
    }

}
