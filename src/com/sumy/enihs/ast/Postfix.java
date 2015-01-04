package com.sumy.enihs.ast;

import java.util.List;

import com.sumy.enihs.compiler.Compiler;

public abstract class Postfix extends ASTList {

    public Postfix(List<AST> c) {
        super(c);
    }

    public Postfix() {

    }

    @Override
    public abstract void visit(Compiler compiler);
}
