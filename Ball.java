import java.awt.Color;
import java.awt.Rectangle;

public class Ball extends Rectangle {

        Color color;

        Ball(int x, int y, int width, int height, Color color) {
            super(x, y, width, height);
            this.color = color;
        }
        
    }