package com.sumy.enihs.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sumy.enihs.compiler.Compiler;
import com.sumy.enihs.exception.EnihsRuntimeException;

public class ASTList extends AST {

    protected List<AST> children;

    public ASTList() {
        children = null;
    }

    public ASTList(List<AST> list) {
        this.children = list;
    }

    public void addChild(AST t) {
        if (children == null) {
            children = new ArrayList<AST>();
        }
        children.add(t);
    }

    @Override
    public AST child(int i) {
        return children.get(i);
    }

    @Override
    public int numChildren() {
        return children.size();
    }

    @Override
    public Iterator<AST> children() {
        return children.iterator();
    }

    @Override
    public String location() {
        for (AST t : children) {
            String s = t.location();
            if (s != null) {
                return s;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(<" + this.getClass().getSimpleName().toString() + "> ");
        String sep = "";
        for (AST t : children) {
            builder.append(sep);
            sep = " ";
            builder.append(t.toString());
        }
        return builder.append(')').toString();
    }

    @Override
    public void visit(Compiler compiler) {
        throw new EnihsRuntimeException("visit ASTList error.", this);
    }

    @Override
    public void compiler(Compiler compiler) {
        throw new EnihsRuntimeException("compiler ASTList error.", this);

    }

}
