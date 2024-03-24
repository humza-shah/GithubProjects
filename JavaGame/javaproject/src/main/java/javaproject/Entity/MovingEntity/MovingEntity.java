package javaproject.Entity.MovingEntity;

import javaproject.Entity.Entity;

/**
    Moving entity class (player and enemies)
*/
public class MovingEntity extends Entity {
    /**
        Moving Entity constructor
    */
    public MovingEntity() {
        //Does nothing
    }

    /**
        Moving Entity constructor that takes in a x and y coordinate
        @param x x coordinate of the board
        @param y y coordinate of the board
    */
    public MovingEntity(int x, int y) {
        super(x, y);
    }

    /**
        Gets the x coordinate
        @return Returns x-coord
    */
    public int getX() {
        return pos.x;
    }

    /**
        Gets the y coordinate
        @return Returns y-coord
    */
    public int getY() {
        return pos.y;
    }

    /**
        Sets the position of the player/enemy by a translation of x and y
        @param x x-coord to translate
        @param y y-coord to translate
     */
    public void translate(int x, int y) {
        pos.translate(x, y);
    }
}