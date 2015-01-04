package com.sumy.enihs.ast;

import com.sumy.enihs.compiler.Compiler;
import com.sumy.enihs.token.Token;

public class OP extends ASTLeaf {

    public OP(Token t) {
        super(t);
    }

    public String op() {
        return token().getText();
    }

    @Override
    public void visit(Compiler compiler) {
        return;
    }
}
