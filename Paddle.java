import java.awt.Color;
import java.awt.Rectangle;

public class Paddle extends Rectangle {

        Color color;

        Paddle(int x, int y, int width, int height, Color color) {
            super(x, y, width, height);
            this.color = color;
        }
        
    }