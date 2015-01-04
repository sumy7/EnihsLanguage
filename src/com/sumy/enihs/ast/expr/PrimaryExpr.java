package com.sumy.enihs.ast.expr;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTList;
import com.sumy.enihs.ast.Postfix;

public class PrimaryExpr extends ASTList {
    public PrimaryExpr(List<AST> c) {
        super(c);
    }

    public static AST create(List<AST> c) {
        return c.size() == 1 ? c.get(0) : new PrimaryExpr(c);
    }

    public AST operand() {
        return child(0);
    }

    public Postfix postfix(int nest) {
        return (Postfix) child(numChildren() - nest - 1);
    }

    public boolean hasPostfix(int nest) {
        return numChildren() - nest > 1;
    }

}
