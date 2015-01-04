package com.sumy.enihs.ast.stmnt;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTLeaf;
import com.sumy.enihs.ast.ASTList;
import com.sumy.enihs.ast.Name;

public class ClassStmnt extends ASTList {
    public ClassStmnt(List<AST> c) {
        super(c);
    }

    public ClassStmnt(Name name, AST body, Name superClass) {
        addChild(name);
        if (superClass != null)
            addChild(superClass);
        addChild(body);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    public String superClass() {
        if (numChildren() < 3) {
            return null;
        } else {
            return ((ASTLeaf) child(1)).token().getText();
        }
    }

    public ClassBody body() {
        return (ClassBody) child(numChildren() - 1);
    }

    @Override
    public String toString() {
        String parent = superClass();
        if (parent == null) {
            parent = "*";
        }
        return "(class " + name() + " " + parent + " " + body() + ")";
    }

}
