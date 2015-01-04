package com.sumy.enihs.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTList;
import com.sumy.enihs.ast.ArrayLiteral;
import com.sumy.enihs.ast.Name;
import com.sumy.enihs.ast.NumberLiteral;
import com.sumy.enihs.ast.OP;
import com.sumy.enihs.ast.ParameterList;
import com.sumy.enihs.ast.StringLiteral;
import com.sumy.enihs.ast.expr.ArrayRef;
import com.sumy.enihs.ast.expr.BinaryExpr;
import com.sumy.enihs.ast.expr.Dot;
import com.sumy.enihs.ast.expr.NegativeExpr;
import com.sumy.enihs.ast.expr.PrimaryExpr;
import com.sumy.enihs.ast.stmnt.Arguments;
import com.sumy.enihs.ast.stmnt.BlockStmnt;
import com.sumy.enihs.ast.stmnt.ClassBody;
import com.sumy.enihs.ast.stmnt.ClassStmnt;
import com.sumy.enihs.ast.stmnt.DefStmnt;
import com.sumy.enihs.ast.stmnt.FunStmnt;
import com.sumy.enihs.ast.stmnt.IfStmnt;
import com.sumy.enihs.ast.stmnt.NullStmnt;
import com.sumy.enihs.ast.stmnt.PrintStmnt;
import com.sumy.enihs.ast.stmnt.ProgramStmnt;
import com.sumy.enihs.ast.stmnt.WhileStmnt;
import com.sumy.enihs.exception.EnihsException;
import com.sumy.enihs.exception.EnihsParserException;
import com.sumy.enihs.lexer.BasicLexer;
import com.sumy.enihs.token.SymbolToken;
import com.sumy.enihs.token.Token;

public class BasicParser extends Parser {

    protected HashMap<String, Precedence> operators;

    public static class Precedence {
        int value;
        boolean leftAssoc;

        public Precedence(int v, boolean a) {
            value = v;
            leftAssoc = a;
        }
    }

    public BasicParser(BasicLexer input, int k) throws EnihsException {
        super(input, k);

        operators = new HashMap<String, BasicParser.Precedence>();
        operators.put("=", new Precedence(1, false));
        operators.put("==", new Precedence(2, true));
        operators.put("!=", new Precedence(2, true));
        operators.put("<", new Precedence(2, true));
        operators.put("<=", new Precedence(2, true));
        operators.put(">", new Precedence(2, true));
        operators.put(">=", new Precedence(2, true));
        operators.put("+", new Precedence(3, true));
        operators.put("-", new Precedence(3, true));
        operators.put("*", new Precedence(4, true));
        operators.put("/", new Precedence(4, true));
        operators.put("%", new Precedence(4, true));
    }

    /* factor : "-" primary | primary */
    public AST factor() throws EnihsException {
        AST ast;
        if (LA(1) == SymbolToken.MINUS) {
            match(SymbolToken.MINUS);
            ast = new NegativeExpr(primary());
        } else {
            ast = primary();
        }
        return ast;
    }

    /* expr : factor { OP factor } */
    public AST expr() throws EnihsException {
        AST right = factor();
        Precedence next;
        while ((next = nextOperator()) != null) {
            right = doShift(right, next.value);
        }
        return right;
    }

    private AST doShift(AST left, int prec) throws EnihsException {
        OP op = new OP(LT(1));
        consume();
        AST right = factor();
        Precedence next;
        while ((next = nextOperator()) != null && rightIsExpr(prec, next)) {
            right = doShift(right, next.value);
        }
        return new BinaryExpr(Arrays.asList(left, op, right));
    }

    private Precedence nextOperator() throws EnihsException {
        Token t = LT(1);
        if (t instanceof SymbolToken) {
            return operators.get(((SymbolToken) t).getSymbol());
        } else {
            return null;
        }
    }

    private static boolean rightIsExpr(int prec, Precedence nextPrec) {
        if (nextPrec.leftAssoc) {
            return prec < nextPrec.value;
        } else {
            return prec <= nextPrec.value;
        }
    }

    /* block : "{" [statement] { EOL [ statement ] } "}" */
    public AST block() throws EnihsException {
        ArrayList<AST> list = new ArrayList<AST>();

        match(SymbolToken.BIG_LBRACK);
        shipEOL();
        while (LA(1) != SymbolToken.BIG_RBRACK) {
            list.add(statement());
        }
        match(SymbolToken.BIG_RBRACK);

        AST ast = new BlockStmnt(list);

        return ast;
    }

    /* simple : expr [ args ] */
    public AST simple() throws EnihsException {
        ArrayList<AST> list = new ArrayList<AST>();
        list.add(expr());
        if (LA(1) != SymbolToken.EOL && LA(1) != SymbolToken.BIG_RBRACK) {
            list.add(args());
        }
        if (list.size() == 1)
            return list.get(0);
        AST ast = new PrimaryExpr(list);
        return ast;
    }

    /*
     * statement : "if" expr block [ "else" block ] | "while" expr block |
     * "print" args | simple
     */
    public AST statement() throws EnihsException {
        AST ast;
        if ("if".equals(LT(1).getText())) {
            match("if");
            AST expr = expr();
            shipEOL();
            AST t_b = block();
            shipEOL();
            if ("else".equals(LT(1).getText())) {
                match("else");
                shipEOL();
                AST f_b = block();
                shipEOL();
                ast = new IfStmnt(expr, t_b, f_b);
            } else {
                ast = new IfStmnt(expr, t_b);
            }
        } else if ("while".equals(LT(1).getText())) {
            match("while");
            AST expr = expr();
            shipEOL();
            AST b = block();
            shipEOL();
            ast = new WhileStmnt(expr, b);
        } else if ("print".equals(LT(1).getText())) {
            match("print");
            AST args = args();
            shipEOL();
            ast = new PrintStmnt(args);
        } else {
            ast = simple();
            shipEOL();
        }
        return ast;
    }

    /* param : IDENTIFIER */
    public AST param() throws EnihsException {
        AST ast = new Name(LT(1));
        matchid();
        return ast;
    }

    /* params : param { "," param } */
    public ArrayList<AST> params() throws EnihsException {
        ArrayList<AST> list = new ArrayList<AST>();
        list.add(param());
        while (LA(1) == SymbolToken.COMMA) {
            match(SymbolToken.COMMA);
            list.add(param());
        }
        return list;
    }

    /* param_list : "(" [ params ] ")" */
    public ParameterList param_list() throws EnihsException {
        ParameterList ast;
        match(SymbolToken.LPARENT);
        ast = new ParameterList(params());
        match(SymbolToken.RPARENT);
        return ast;
    }

    /* def : "def" IDENTIFIER param_list block */
    public AST def() throws EnihsException {
        match("def");
        AST id = new Name(LT(1));
        matchid();
        AST p = param_list();
        AST b = block();
        return new DefStmnt(id, p, b);

    }

    /* args : expr { "," expr } */
    public AST args() throws EnihsException {
        ArrayList<AST> list = new ArrayList<AST>();
        if (LA(1) != SymbolToken.RPARENT)
            list.add(expr());
        while (",".equals(LT(1).getText())) {
            match(SymbolToken.COMMA);
            list.add(expr());
        }
        AST ast = new Arguments(list);
        return ast;
    }

    /* member : def | simple */
    public AST member() throws EnihsException {
        if ("def".equals(LT(1).getText())) {
            return def();
        } else {
            return simple();
        }
    }

    /* class_body : "{" [ member ] { EOL [ member ]} "}" */
    public AST class_body() throws EnihsException {
        ArrayList<AST> list = new ArrayList<AST>();
        match(SymbolToken.BIG_LBRACK);
        shipEOL();
        while (LA(1) != SymbolToken.BIG_RBRACK) {
            list.add(member());
            shipEOL();
        }
        match(SymbolToken.BIG_RBRACK);
        shipEOL();
        return new ClassBody(list);
    }

    /* defclass : "class" IDENTIFIER [ "extends" IDENTIFIER] class_body */
    public AST defclass() throws EnihsException {
        match("class");
        Name id = new Name(LT(1));
        Name par = null;
        matchid();
        if ("extends".equals(LT(1).getText())) {
            match("extends");
            par = new Name(LT(1));
            matchid();
        }
        shipEOL();
        AST classbody = class_body();
        return new ClassStmnt(id, classbody, par);
    }

    /* program : [ defcalss | def | statement ] EOL */
    public AST program() throws EnihsException {
        AST ast;
        if ("class".equals(LT(1).getText())) {
            ast = defclass();
        } else if ("def".equals(LT(1).getText())) {
            ast = def();
        } else if (LA(1) == SymbolToken.EOL) {
            match(SymbolToken.EOL);
            ast = new NullStmnt();
        } else {
            ast = statement();
        }
        return ast;
    }

    public AST main() throws EnihsException {
        ArrayList<AST> list = new ArrayList<AST>();
        while (LT(1) != Token.EOF) {
            AST t = program();
            if (!(t instanceof NullStmnt)) {
                list.add(t);
            }
        }
        return new ProgramStmnt(list);
    }

    /* elements : expr { "," expr } */
    public AST elements() throws EnihsException {
        ArrayList<AST> list = new ArrayList<AST>();
        list.add(expr());
        while (LA(1) == SymbolToken.COMMA) {
            match(SymbolToken.COMMA);
            list.add(expr());
        }
        return new ArrayLiteral(list);
    }

    /* postfix : "." IDENTIFIER | "(" [ args ] ")" | "[" expr "]" */
    public AST postfix() throws EnihsException {
        AST ast;
        if (LA(1) == SymbolToken.DOT) {
            match(SymbolToken.DOT);
            ast = new Dot(new Name(LT(1)));
            matchid();
        } else if (LA(1) == SymbolToken.LPARENT) {
            match(SymbolToken.LPARENT);
            ast = args();
            match(SymbolToken.RPARENT);
        } else if (LA(1) == SymbolToken.LBRACK) {
            match(SymbolToken.LBRACK);
            ast = new ArrayRef(expr());
            match(SymbolToken.RBRACK);
        } else {
            ast = null;
        }
        return ast;
    }

    /*
     * primary : "fun" param_list block | ( "[" [ elements ] "]" | "(" expr ")"
     * | NUMBER | IDENTIFIER | STRING ) { postfix }
     */

    public AST primary() throws EnihsException {
        ASTList ast;
        if ("fun".equals(LT(1).getText())) {
            match("fun");
            ParameterList param = param_list();
            AST blo = block();
            ast = new FunStmnt(param, blo);
        } else {
            ast = new PrimaryExpr(null);
            if (LA(1) == SymbolToken.LBRACK) {
                match(SymbolToken.LBRACK);
                if (LA(1) != SymbolToken.RBRACK)
                    ast.addChild(elements());
                match(SymbolToken.RBRACK);
            } else if (LA(1) == SymbolToken.LPARENT) {
                match(SymbolToken.LPARENT);
                ast.addChild(expr());
                match(SymbolToken.RPARENT);
            } else {
                if (LT(1).isNumber()) {
                    ast.addChild(new NumberLiteral(LT(1)));
                } else if (LT(1).isString()) {
                    ast.addChild(new StringLiteral(LT(1)));
                } else if (LT(1).isID()) {
                    ast.addChild(new Name(LT(1)));
                } else {
                    throw new EnihsParserException("unknow token type "
                            + LT(1).toString());
                }
                consume();
            }
            while (LA(1) == SymbolToken.DOT || LA(1) == SymbolToken.LPARENT
                    || LA(1) == SymbolToken.LBRACK) {
                ast.addChild(postfix());
            }
            if (ast.numChildren() == 1) {
                return ast.child(0);
            }
        }
        return ast;
    }

    /**
     * 消除多余的换行符
     */
    private void shipEOL() throws EnihsException {
        while (LA(1) == SymbolToken.EOL) {
            consume();
        }
    }
}
