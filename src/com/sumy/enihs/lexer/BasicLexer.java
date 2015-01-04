package com.sumy.enihs.lexer;

import java.util.Stack;

import com.sumy.enihs.exception.EnihsException;
import com.sumy.enihs.exception.EnihsLexerException;
import com.sumy.enihs.token.IdToken;
import com.sumy.enihs.token.NumToken;
import com.sumy.enihs.token.StrToken;
import com.sumy.enihs.token.SymbolToken;
import com.sumy.enihs.token.Token;

public class BasicLexer extends Lexer {

    // 标记当前处理的内容
    private static final int NORMAL = 0; // 正常处理
    private static final int LINE_COMMENT = 1; // 当前正在处理单行注释
    private static final int MUTI_COMMENT = 2; // 当前正在处理多行注释
    private static final int STRING = 3; // 当前正在处理字符串

    private Stack<Integer> lexerFlag;// 内容处理栈

    public BasicLexer(String input) {
        super(input);
        lexerFlag = new Stack<Integer>();
        lexerFlag.push(NORMAL);
    }

    @Override
    public Token nextToken() throws EnihsException {
        while (c != EOF) {
            switch (c) {
            case ' ':
            case '\t':
            case '\r':
                WS();
                continue;
            case '\n':
                consume();
                return new SymbolToken(lineNum++, SymbolToken.EOL);
            case '{':
                consume();
                return new SymbolToken(lineNum, SymbolToken.BIG_LBRACK);
            case '}':
                consume();
                return new SymbolToken(lineNum, SymbolToken.BIG_RBRACK);
            case '[':
                consume();
                return new SymbolToken(lineNum, SymbolToken.LBRACK);
            case ']':
                consume();
                return new SymbolToken(lineNum, SymbolToken.RBRACK);
            case '!':
                consume();
                if (c == '=') {
                    consume();
                    return new SymbolToken(lineNum, SymbolToken.NOT_EQUAL);
                }
                throw new EnihsLexerException("invalid character: " + c);
            case '=':
                consume();
                if (c == '=') {
                    consume();
                    return new SymbolToken(lineNum, SymbolToken.EQUAL);
                }
                return new SymbolToken(lineNum, SymbolToken.ASSIGN);
            case '+':
                consume();
                return new SymbolToken(lineNum, SymbolToken.PLUS);
            case '-':
                consume();
                return new SymbolToken(lineNum, SymbolToken.MINUS);
            case '*':
                consume();
                return new SymbolToken(lineNum, SymbolToken.MULT);
            case '%':
                consume();
                return new SymbolToken(lineNum, SymbolToken.MOD);
            case '>':
                consume();
                if (c == '=') {
                    consume();
                    return new SymbolToken(lineNum,
                            SymbolToken.GREATER_EQUAL_THAN);
                }
                return new SymbolToken(lineNum, SymbolToken.GREATER_THAN);
            case '<':
                consume();
                if (c == '=') {
                    consume();
                    return new SymbolToken(lineNum, SymbolToken.LESS_EQUAL_THAN);
                }
                return new SymbolToken(lineNum, SymbolToken.LESS_THAN);
            case '/':
                consume();
                if (c == '/') {
                    lexerFlag.push(LINE_COMMENT);
                    consume();
                    process_LINECOMMENT();
                    continue;
                }
                if (c == '*') {
                    lexerFlag.push(MUTI_COMMENT);
                    consume();
                    process_MUTLCOMMENT();
                    continue;
                }
                return new SymbolToken(lineNum, SymbolToken.DEVIDE);
            case ',':
                consume();
                return new SymbolToken(lineNum, SymbolToken.COMMA);
            case '"':
                consume();
                lexerFlag.push(STRING);
                return process_STRING();
            case '(':
                consume();
                return new SymbolToken(lineNum, SymbolToken.LPARENT);
            case ')':
                consume();
                return new SymbolToken(lineNum, SymbolToken.RPARENT);
            default:
                if (isLETTER() || isUnderline()) {
                    return IDENTIFIER();
                }
                if (isNUMBER()) {
                    return NUMBER();
                }
                throw new EnihsLexerException("invalid character: " + c);
            }
        }
        return Token.EOF;
    }

    Token process_STRING() {
        StringBuilder buf = new StringBuilder();
        while (true) {
            if (c == '\\') {
                consume();
                if (c == 'n')
                    buf.append('\n');
                if (c == 't')
                    buf.append('\t');
                if (c == '"')
                    buf.append("\"");
                if (c == '\\')
                    buf.append('\\');
            } else {
                buf.append(c);
            }
            consume();
            if (c == '\"')
                break;
        }
        consume();
        return new StrToken(lineNum, buf.toString());
    }

    /**
     * 多行注释，允许多行注释
     */
    void process_MUTLCOMMENT() {
        while (true) {
            if (c == '*') {
                consume();
                if (c == '/') {
                    lexerFlag.pop();
                }
            } else if (c == '/') {
                consume();
                if (c == '*') {
                    lexerFlag.push(MUTI_COMMENT);
                }
            }
            consume();
            if (lexerFlag.peek() == NORMAL)
                return;
        }
    }

    /**
     * 处理单行注释
     */
    void process_LINECOMMENT() {
        while (c != '\n')
            consume();
        lineNum++;
        lexerFlag.pop();
    }

    /**
     * 忽略空白字符
     */
    void WS() {
        while (c == ' ' || c == '\t' || c == '\r')
            consume();
    }

    /**
     * 标识符，由一个或多个字符、下划线、数字组成，首位不能为数字
     */
    Token IDENTIFIER() {
        StringBuilder buf = new StringBuilder();
        do {
            buf.append(c);
            consume();
        } while (isLETTER() || isUnderline() || isNUMBER());
        return new IdToken(lineNum, buf.toString());
    }

    /**
     * 数字，由多个数字组成
     */
    Token NUMBER() {
        StringBuilder buf = new StringBuilder();
        do {
            buf.append(c);
            consume();
        } while (isNUMBER());
        return new NumToken(lineNum, buf.toString());
    }

    boolean isLETTER() {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'A';
    }

    boolean isUnderline() {
        return c == '_';
    }

    boolean isNUMBER() {
        return c >= '0' && c <= '9';
    }

}
