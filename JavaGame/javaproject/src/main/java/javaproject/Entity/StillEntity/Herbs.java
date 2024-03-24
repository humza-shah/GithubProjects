package javaproject.Entity.StillEntity;

/**
    Herbs for regular reward (blue herbs), bonus reward (gold herb) and punishments (poisonous herbs)
*/
public class Herbs extends StillEntity {
    public int value; //Value of the herb
    /**
        Herbs constructor that positions the herb at the x and y coordinate passed
        @param x x-coord of the herb
        @param y y-coord of the herb
    */
    public Herbs(int x, int y) {
        super(x, y);
    }

    /**
        get the value of the herb
        @return Returns the value of the herb
    */
    public int getValue() {
        return value;
    }
}
