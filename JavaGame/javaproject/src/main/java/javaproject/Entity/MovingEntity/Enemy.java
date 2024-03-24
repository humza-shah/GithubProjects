package javaproject.Entity.MovingEntity;
import java.awt.Point;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
    Moving enemy that chases the player
*/
public class Enemy extends MovingEntity {
    /**
        Enemy constructor
    */
    public Enemy(int x, int y) {
        loadImage();
        pos = new Point(x, y);
    }

    /**
        Method for loading the image into the enemy object
    */
    private void loadImage() {
        try {
            image = ImageIO.read(new File("javaproject/src/main/java/javaproject/images/001-onibi.png"));
        }
        catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }
}