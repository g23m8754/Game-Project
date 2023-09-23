import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PingPongFrame extends JFrame implements Runnable , ActionListener {
    
    GamePanel panel = new GamePanel();
    Selection selection = new Selection();
    private Thread gameThread; // Add a Thread for the game loop

    PingPongFrame() {
        setTitle("Ping Pong");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.gray);
        setLayout(null);
        setResizable(false);

        add(selection);
        selection.button.addActionListener(this);

        add(panel);
        panel.setVisible(false);

        setVisible(true);

        // run();

    }

    // Game loop
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks =60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        //boolean running = false;
        while(panel.running) {  
            long now = System.nanoTime();
            delta += (now -lastTime)/ns;
            lastTime = now;
            if (delta >= 1) {
                //if (panel.isVisible()){
                    panel.move();
                    panel.collision();
                    //panel.pause();
                    panel.winner();
                    repaint();
                //}
                delta--;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == selection.button) {
            panel.setVisible(true);
            selection.setVisible(false);
            panel.setFocusable(true);
            panel.running = true;
            gameThread = new Thread(this::run); // Thread for the game loop
            gameThread.start(); // Start the game loop thread
        }
    }

}
