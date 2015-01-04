package com.sumy.enihs.object;

public class EnihsInteger extends EnihsObject {
    private int inner;

    public EnihsInteger(int integer) {
        inner = integer;
    }

    public int nativeValue() {
        return inner;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + inner;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EnihsInteger other = (EnihsInteger) obj;
        if (inner != other.inner)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "" + inner;
    }
}
