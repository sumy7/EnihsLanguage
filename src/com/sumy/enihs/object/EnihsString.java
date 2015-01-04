package com.sumy.enihs.object;

public class EnihsString extends EnihsObject {
    private String inner;

    public EnihsString(String str) {
        inner = str;
    }

    public EnihsString(byte[] bytes, int offset, int len) {
        inner = new String(bytes, offset, len);
    }

    public byte[] getBytes() {
        return inner.getBytes();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((inner == null) ? 0 : inner.hashCode());
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
        EnihsString other = (EnihsString) obj;
        if (inner == null) {
            if (other.inner != null)
                return false;
        } else if (!inner.equals(other.inner))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return inner.toString();
    }
}
