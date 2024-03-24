package javaproject.Entity.StillEntity;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Point;

/**
    Doctor that needs the herbs to save the king!
*/
public class Doctor extends StillEntity {
    /**
        Doctor constructor
    */
    public Doctor() {
        loadImage();
        pos = new Point(16, 10);
    }

    /**
        Method for loading the image into the doctor object
    */
    private void loadImage() {
        try {
            image = ImageIO.read(new File("javaproject/src/main/java/javaproject/images/wizard.png"));
        }
        catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }
}
