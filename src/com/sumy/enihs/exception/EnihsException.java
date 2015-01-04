package com.sumy.enihs.exception;

import com.sumy.enihs.ast.AST;

public class EnihsException extends Exception {

    private static final long serialVersionUID = -1063485906244126335L;

    public EnihsException(String msg) {
        super(msg);
    }

    public EnihsException(String msg, AST ast) {
        super(msg + " " + ast.location());
    }
}
