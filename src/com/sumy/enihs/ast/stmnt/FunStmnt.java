package com.sumy.enihs.ast.stmnt;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTList;
import com.sumy.enihs.ast.ParameterList;

public class FunStmnt extends ASTList {
    public int size = -1;

    public FunStmnt(List<AST> c) {
        super(c);
    }

    public FunStmnt(AST param, AST body) {
        addChild(param);
        addChild(body);
    }

    public ParameterList parameters() {
        return (ParameterList) child(0);
    }

    public BlockStmnt body() {
        return (BlockStmnt) child(1);
    }

    @Override
    public String toString() {
        return "(fun " + parameters() + " " + body() + ")";
    }

}
