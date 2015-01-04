package com.sumy.enihs.ast.stmnt;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.Postfix;
import com.sumy.enihs.compiler.Compiler;

public class Arguments extends Postfix {

    public Arguments(List<AST> c) {
        super(c);
    }

    public int size() {
        return numChildren();
    }

    @Override
    public void visit(Compiler compiler) {
        for (AST ast : this) {
            ast.visit(compiler);
        }
    }

    @Override
    public void compiler(Compiler compiler) {
        for (AST ast : this) {
            ast.compiler(compiler);
        }
    }
}
