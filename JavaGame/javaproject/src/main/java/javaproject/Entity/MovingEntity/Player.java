package javaproject.Entity.MovingEntity;

import javaproject.Control.Board;
import java.awt.event.KeyEvent;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
    Player that is the main protagonist of the game
*/
public class Player extends MovingEntity {
    private int score;
    private int key;

    /**
        Player constructor
    */
    public Player() {
        loadImage();
        pos = new Point(1, 1);
        score = 0;
    }

    /**
        Method for loading the image into the player object
    */
    private void loadImage() {
        try {
            image = ImageIO.read(new File("javaproject/src/main/java/javaproject/images/001-knight.png"));
        }
        catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }
    
    /**
        Method for moving the player
        @param e Key press
    */
    public void keyPressed(KeyEvent e) {
        // every keyboard get has a certain code. get the value of that code from the
        // keyboard event so that we can compare it to KeyEvent constants
        key = e.getKeyCode();
        // depending on which arrow key was pressed, we're going to move the player by
        // one whole tile for this input
        if (key == KeyEvent.VK_UP) {
            pos.translate(0, -1);
        }
        if (key == KeyEvent.VK_RIGHT) {
            pos.translate(1, 0);
        }
        if (key == KeyEvent.VK_DOWN) {
            pos.translate(0, 1);
        }
        if (key == KeyEvent.VK_LEFT) {
            pos.translate(-1, 0);
        }
    }

    /**
        Method for making sure the player can not go outside of the edges
    */
    public void tick() {
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.

        // prevent the player from moving off the edge of the board sideways
        if (pos.x < 1) {
            pos.x = 1;
        }
        else if (pos.x >= Board.COLUMNS-1) {
            pos.x = Board.COLUMNS - 2;
        }
        // prevent the player from moving off the edge of the board vertically
        if (pos.y < 1) {
            pos.y = 1;
        }
        else if (pos.y >= Board.ROWS-1) {
            pos.y = Board.ROWS - 2;
        }
    }

    /**
        Method that returns the score as a string so it can be printed to the board
        @return Returns the score as a string
    */
    public String getScore() {
        return String.valueOf(score);
    }

    /**
        Method that returns the score as it's integer value
        @return
     */
    public int getScoreValue() {
        return score;
    }

    /**
        Method that adds a given score to the player based on the collected herb
        @param amount Amount of score added or reduced
    */
    public void addScore(int amount) {
        score += amount;
    }

    /**
        Method that gets the key press that the player took (if the player tries moving onto a mountain, we move them back)
        @return Returns the key that was pressed by the player
    */
    public int getKey() {
        return key;
    }
}