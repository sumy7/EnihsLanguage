package com.sumy.enihs.ast;

import java.util.List;

public class ParameterList extends ASTList {

    public int[] offsets = null;

    public ParameterList(List<AST> c) {
        super(c);
    }

    public String name(int i) {
        return ((ASTLeaf) child(i)).token().getText();
    }

    public int size() {
        return numChildren();
    }

}
