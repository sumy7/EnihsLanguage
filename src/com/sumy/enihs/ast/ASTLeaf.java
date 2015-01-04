package com.sumy.enihs.ast;

import java.util.ArrayList;
import java.util.Iterator;

import com.sumy.enihs.compiler.Compiler;
import com.sumy.enihs.exception.EnihsRuntimeException;
import com.sumy.enihs.token.Token;

public class ASTLeaf extends AST {

    private static ArrayList<AST> empty = new ArrayList<AST>();
    protected Token token;

    public ASTLeaf(Token t) {
        this.token = t;
    }

    @Override
    public AST child(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int numChildren() {
        return 0;
    }

    @Override
    public Iterator<AST> children() {
        return empty.iterator();
    }

    @Override
    public String location() {
        return "at line " + token.getLineNumber();
    }

    public Token token() {
        return token;
    }

    @Override
    public String toString() {
        return "<" + this.getClass().getSimpleName() + ">" + token.getText();
    }

    @Override
    public void visit(Compiler compiler) {
        throw new EnihsRuntimeException("visit ASTLeaf error.", this);
    }

    @Override
    public void compiler(Compiler compiler) {
        throw new EnihsRuntimeException("compiler ASTLeaf error.", this);
    }

}
