package com.sumy.enihs.ast.expr;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTLeaf;
import com.sumy.enihs.ast.Postfix;
import com.sumy.enihs.compiler.Compiler;

public class Dot extends Postfix {
    public Dot(List<AST> c) {
        super(c);
    }

    public Dot(AST name) {
        addChild(name);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    @Override
    public String toString() {
        return "." + name();
    }

    @Override
    public void visit(Compiler compiler) {
        child(0).visit(compiler);
    }
}
