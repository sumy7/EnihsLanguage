package com.sumy.enihs.ast;

import java.util.List;

public class ArrayLiteral extends ASTList {

    public ArrayLiteral(List<AST> list) {
        super(list);
    }

    public int size() {
        return numChildren();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        String sep = "";
        buf.append("[");
        for (AST t : this) {
            buf.append(sep);
            buf.append(t);
            sep = " ";
        }
        buf.append("]");
        return buf.toString();
    }
}
