package com.sumy.enihs.ast;

import java.util.Iterator;

import com.sumy.enihs.compiler.Compiler;

public abstract class AST implements Iterable<AST> {
    public abstract AST child(int i);

    public abstract int numChildren();

    public abstract Iterator<AST> children();

    public abstract String location();

    public Iterator<AST> iterator() {
        return children();
    }

    public abstract void visit(Compiler compiler);

    public abstract void compiler(Compiler compiler);

}
