package com.sumy.enihs.exception;

import com.sumy.enihs.ast.AST;

public class EnihsRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 4878019125666249348L;

    public EnihsRuntimeException(String msg) {
        super(msg);
    }

    public EnihsRuntimeException(String msg, AST ast) {
        super(msg + " " + ast.location());
    }

}
