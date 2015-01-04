package com.sumy.enihs.ast.expr;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.Postfix;
import com.sumy.enihs.compiler.Compiler;

public class ArrayRef extends Postfix {

    public ArrayRef(List<AST> c) {
        super(c);
    }

    public ArrayRef(AST index) {
        addChild(index);
    }

    public AST index() {
        return child(0);
    }

    @Override
    public String toString() {
        return "[" + index() + "]";
    }

    @Override
    public void visit(Compiler compiler) {
        for (AST ast : this) {
            ast.visit(compiler);
        }
    }
}
