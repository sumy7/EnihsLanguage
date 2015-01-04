package com.sumy.enihs.parser;

import com.sumy.enihs.exception.EnihsException;
import com.sumy.enihs.exception.EnihsParserException;
import com.sumy.enihs.lexer.BasicLexer;
import com.sumy.enihs.token.IdToken;
import com.sumy.enihs.token.SymbolToken;
import com.sumy.enihs.token.Token;

public class Parser {
    BasicLexer input; // 输入的词法单元
    Token[] lookahead; // 环形缓冲区
    int k; // 向前看符号的个数
    int p = 0; // 环形缓冲区中装填下一个词法单元的位置

    public Parser(BasicLexer input, int k) throws EnihsException {
        this.input = input;
        this.k = k;
        lookahead = new Token[k]; // 开辟向前看缓冲区
        for (int i = 1; i <= k; i++) {
            consume(); // 用k个向前看符号初始化缓冲区
        }
    }

    public Token LT(int i) {
        return lookahead[(p + i - 1) % k];// 环式取值
    }

    public int LA(int i) {
        Token t = LT(i);
        if (t instanceof SymbolToken) {
            return ((SymbolToken) t).getSymbolId();
        }
        return -1;
    }

    public void match(int x) throws EnihsException {
        if (LA(1) == x)
            consume();
        else
            throw new EnihsParserException("expecting "
                    + SymbolToken.getTokenName(x) + "; found " + LT(1));
    }

    public void match(String x) throws EnihsException {
        if (x.equals(LT(1).getText())) {
            consume();
        } else {
            throw new EnihsParserException("expecting " + x + "; found " + LT(1));
        }
    }

    public void matchid() throws EnihsException {
        if (LT(1) instanceof IdToken) {
            consume();
        }
    }

    public void consume() throws EnihsException {
        lookahead[p] = input.nextToken(); // 在下一个位置上放入词法单元
        p = (p + 1) % k; // 自增下标
    }
}
