package com.sumy.enihs.object;

public class EnihsBoolean extends EnihsInteger {

    public static EnihsBoolean True = new EnihsBoolean(1);
    public static EnihsBoolean False = new EnihsBoolean(0);

    private EnihsBoolean(int integer) {
        super(integer);
    }

}
