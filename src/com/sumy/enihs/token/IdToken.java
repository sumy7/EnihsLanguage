package com.sumy.enihs.token;

public class IdToken extends Token {

    private String id;

    public IdToken(int line, String id) {
        super(line);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "<  ID , " + lineNum + " , " + getId() + " >";
    }

    @Override
    public String getText() {
        return id;
    }

    @Override
    public boolean isID() {
        return true;
    }
}
