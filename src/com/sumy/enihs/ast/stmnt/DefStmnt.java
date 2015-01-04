package com.sumy.enihs.ast.stmnt;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTLeaf;
import com.sumy.enihs.ast.ASTList;
import com.sumy.enihs.ast.ParameterList;

public class DefStmnt extends ASTList {

    public int index, size;

    public DefStmnt(List<AST> c) {
        super(c);
    }

    public DefStmnt(AST name, AST param, AST body) {
        addChild(name);
        addChild(param);
        addChild(body);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    public ParameterList parameters() {
        return (ParameterList) child(1);
    }

    public BlockStmnt body() {
        return (BlockStmnt) child(2);
    }

    @Override
    public String toString() {
        return "(def " + name() + " " + parameters() + " " + body() + ")";
    }

    public int locals() {
        return size;
    }

}
