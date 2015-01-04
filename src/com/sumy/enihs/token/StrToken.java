package com.sumy.enihs.token;

public class StrToken extends Token {

    private String text;

    public StrToken(int line, String str) {
        super(line);
        text = str;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "< STR , " + lineNum + " , " + getText() + " >";
    }

    @Override
    public boolean isString() {
        return true;
    }
}
