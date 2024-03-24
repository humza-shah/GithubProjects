package javaproject.Entity;

import javaproject.Entity.Entity;
import javaproject.Control.Board;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;

/**
    Entity class. Any entity that exists on the board
*/
public class Entity {
    public BufferedImage image;
    public Point pos;

    /**
        Entity constructor
    */
    public Entity() {
        //Does nothing
    }

    /**
        Entity constructor with x and y coordinate
        @param x x-coord of entity
        @param y y-coord of entity
    */
    public Entity(int x, int y) {
        pos = new Point(x, y);
    }

    /**
        Draw the entity on the board
        @param g The graphics
        @param observer The entity
    */
    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but 
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        g.drawImage(image, pos.x * Board.TILE_SIZE, pos.y * Board.TILE_SIZE, observer);
    }

    /**
        Gets the position of the entity
        @return Returns the position of the entity
    */
    public Point getPos() {
        return pos;
    }
}