package com.sumy.enihs.ast.stmnt;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTList;

public class NullStmnt extends ASTList {
    public NullStmnt(List<AST> c) {
        super(c);
    }

    public NullStmnt() {
    }

    @Override
    public String toString() {
        return "( null )";
    }
}
