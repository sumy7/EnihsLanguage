package com.sumy.enihs.token;

public class SymbolToken extends Token {
    public static final int EOL = 0;

    public static final int LBRACK = 1; // [
    public static final int RBRACK = 2; // ]
    public static final int BIG_LBRACK = 3; // {
    public static final int BIG_RBRACK = 4; // }
    public static final int LPARENT = 5; // (
    public static final int RPARENT = 6;// )
    public static final int ASSIGN = 7; // =
    public static final int PLUS = 8; // +
    public static final int MINUS = 9; // -
    public static final int MULT = 10; // *
    public static final int DEVIDE = 11; // /
    public static final int MOD = 12; // %
    public static final int DOT = 13; // .
    public static final int COMMA = 14; // ,
    public static final int EQUAL = 15; // ==
    public static final int NOT_EQUAL = 16; // !=
    public static final int LESS_THAN = 17; // <
    public static final int LESS_EQUAL_THAN = 18; // <=
    public static final int GREATER_THAN = 19;// >
    public static final int GREATER_EQUAL_THAN = 20; // >=

    public static final String[] tokenSymbol = { "EOL", "[", "]", "{", "}",
            "(", ")", "=", "+", "-", "*", "/", "%", ".", ",", "==", "!=", "<",
            "<=", ">", ">=" };
    public static final String[] tokenNames = { "<EOL>", "LBRACK", "RBRACK",
            "BIG_LBRACK", "BIG_RBRACK", "LPARENT", "RPARENT", "ASSIGN", "PLUS",
            "MINUS", "MULT", "DEVIDE", "MOD", "DOT", "COMMA", "EQUAL",
            "LESS_THAN", "LESS_EQUAL_THAN", "GREATER_THAN",
            "GREATER_EQUAL_THAN" };

    public static String getTokenName(int x) {
        return tokenNames[x];
    }

    private int symbol;

    public SymbolToken(int line, int symbol) {
        super(line);
        this.symbol = symbol;
    }

    public int getSymbolId() {
        return symbol;
    }

    public String getSymbolName() {
        return tokenNames[symbol];
    }

    public String getSymbol() {
        return tokenSymbol[symbol];
    }

    @Override
    public String toString() {
        return "< SYM , " + lineNum + " , " + getSymbol() + " >";
    }

    @Override
    public String getText() {
        return tokenSymbol[symbol];
    }

    @Override
    public boolean isSymbol() {
        return true;
    }
}
