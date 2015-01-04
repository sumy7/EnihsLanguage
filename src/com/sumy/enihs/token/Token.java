package com.sumy.enihs.token;

public class Token {

    public static final Token EOF = new Token(-1);// 文件结束

    protected int lineNum;

    public Token(int line) {
        lineNum = line;
    }

    @Override
    public String toString() {
        if (this == EOF) {
            return "< EOF >";
        }
        return "< Token >";
    }

    public boolean isID() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public boolean isSymbol() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public String getText() {
        return "";
    }

    public int getLineNumber() {
        return lineNum;
    }

    public int getNumber() {
        throw new Error("not a number");
    }
}
