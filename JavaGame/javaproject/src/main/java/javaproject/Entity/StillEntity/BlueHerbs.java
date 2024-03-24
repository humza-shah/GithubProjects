package javaproject.Entity.StillEntity;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
    Regular rewards (blue herbs)
*/
public class BlueHerbs extends Herbs {
    /**
        Blue herb constructor that sets the herb at the x and y coordinate
        @param x x-coord of blue herb
        @param y y-coord of blue herb
    */
    public BlueHerbs(int x, int y) {
        super(x, y);
        loadImage();
        value = 10; //Gives value of 10
    }

    /**
        Method for loading the image into the blue herb object
    */
    private void loadImage() {
        try {
            image = ImageIO.read(new File("javaproject/src/main/java/javaproject/images/001-leaf.png"));
        }
        catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }
}
