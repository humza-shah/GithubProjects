package javaproject.Entity.StillEntity;

import javaproject.Entity.Entity;

/**
    Still entities such as herbs, mountains and the doctor
*/
public class StillEntity extends Entity {

    /**
        StillEntity constructor
    */
    public StillEntity() {
        //Does nothing
    }
    
    /**
        StillEntity constructor with x and y position
        @param x x-coord of the still entity
        @param y y-coord of the still entity
    */
    public StillEntity(int x, int y) {
        super(x, y);
    }
}