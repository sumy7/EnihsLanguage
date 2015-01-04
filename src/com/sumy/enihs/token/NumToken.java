package com.sumy.enihs.token;

public class NumToken extends Token {

    private String number;

    public NumToken(int line, String num) {
        super(line);
        number = num;
    }

    @Override
    public String getText() {
        return number;
    }

    @Override
    public int getNumber() {
        return Integer.parseInt(number);
    }

    @Override
    public String toString() {
        return "< NUM , " + lineNum + " , " + getText() + " >";
    }

    @Override
    public boolean isNumber() {
        return true;
    }

}
