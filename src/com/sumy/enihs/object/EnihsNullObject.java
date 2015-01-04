package com.sumy.enihs.object;

/**
 * Enihs的None类，只有一个实例
 * 
 * @author sumy
 *
 */
public class EnihsNullObject extends EnihsObject {
    private static EnihsObject none = null;

    private EnihsNullObject() {
    };

    public static EnihsObject getNone() {
        if (none == null) {
            none = new EnihsNullObject();
        }
        return none;
    }

    @Override
    public String toString() {
        return "None";
    }
}
