package javaproject.Entity.StillEntity;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
    Mountains/barriers that are scattered around the board
*/
public class Mountains extends StillEntity {
    /**
        Mountains constructor that sets the mountain at the x and y coordinate
        @param x x-coord of the mountain
        @param y y-coord of the mountain
    */
    public Mountains(int x, int y) {
        super(x, y);
        loadImage();
    }

    /**
        Method for loading the image into the mountain object
    */
    private void loadImage() {
        try {
            image = ImageIO.read(new File("javaproject/src/main/java/javaproject/images/001-mountain.png"));
        }
        catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }
}
