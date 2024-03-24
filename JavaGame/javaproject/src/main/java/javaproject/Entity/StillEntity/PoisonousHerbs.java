package javaproject.Entity.StillEntity;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
    Punishments/traps (poisonous herbs)
*/
public class PoisonousHerbs extends Herbs {
    /**
        Poisonous Herbs constructor that sets the herb at the x and y coordinate
        @param x x-coord of the poisonous herb
        @param y y-coord of the poisonous herb
    */
    public PoisonousHerbs(int x, int y) {
        super(x, y);
        loadImage();
        value = -10;
    }

    /**
        Method for loading the image into the poisonous herb object
    */
    private void loadImage() {
        try {
            image = ImageIO.read(new File("javaproject/src/main/java/javaproject/images/001-blackleaf.png"));
        }
        catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }
}
