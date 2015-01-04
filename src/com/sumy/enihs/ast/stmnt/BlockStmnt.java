package com.sumy.enihs.ast.stmnt;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTList;
import com.sumy.enihs.compiler.Compiler;

public class BlockStmnt extends ASTList {
    public BlockStmnt(List<AST> c) {
        super(c);
    }

    @Override
    public void visit(Compiler compiler) {
        for (AST t : this) {
            t.visit(compiler);
        }
    }

    @Override
    public void compiler(Compiler compiler) {
        for (AST t : this) {
            t.compiler(compiler);
        }
    }
}
