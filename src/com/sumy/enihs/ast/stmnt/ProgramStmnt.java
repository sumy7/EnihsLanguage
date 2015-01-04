package com.sumy.enihs.ast.stmnt;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTList;
import com.sumy.enihs.compiler.Compiler;

public class ProgramStmnt extends ASTList {
    public ProgramStmnt(List<AST> c) {
        super(c);
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (AST ast : this) {
            buf.append(ast);
            buf.append("\n");
        }
        return buf.toString();
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
