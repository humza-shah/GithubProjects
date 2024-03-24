package javaproject.Entity.StillEntity;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
    Bonus reward (gold herb)
*/
public class GoldHerbs extends Herbs {

    /**
        Gold herb constructor that sets the herb at the x and y coordinate
        @param x x-coord of gold herb
        @param y y-coord of gold herb
    */
    public GoldHerbs(int x, int y) {
        super(x, y);
        loadImage();
        value = 50;
    }

    /**
        Method for loading the image into the gold herb object
    */
    private void loadImage() {
        try {
            image = ImageIO.read(new File("javaproject/src/main/java/javaproject/images/001-goldleaf.png"));
        }
        catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }
}
