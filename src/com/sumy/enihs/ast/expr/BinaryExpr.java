package com.sumy.enihs.ast.expr;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTList;
import com.sumy.enihs.ast.Name;
import com.sumy.enihs.ast.OP;
import com.sumy.enihs.compiler.Compiler;
import com.sumy.enihs.exception.EnihsRuntimeException;
import com.sumy.enihs.vm.OpCode;

public class BinaryExpr extends ASTList {
    public BinaryExpr(List<AST> c) {
        super(c);
    }

    public BinaryExpr(AST left, AST op, AST right) {
        addChild(left);
        addChild(op);
        addChild(right);
    }

    public AST left() {
        return child(0);
    }

    public String operator() {
        return ((OP) child(1)).token().getText();
    }

    public AST right() {
        return child(2);
    }

    @Override
    public void visit(Compiler compiler) {
        left().visit(compiler);
        right().visit(compiler);
    }

    @Override
    public void compiler(Compiler compiler) {
        if ("=".equals(operator())) {
            right().compiler(compiler);
            if (left() instanceof Name) {
                Name name = (Name) left();
                compiler.addInstruction(new OpCode.STORE_NAME((byte) compiler
                        .addNames(name.name())));
                return;
            } else {
                throw new EnihsRuntimeException(
                        "Compiler, left must be a name ", this);
            }

        }
        left().compiler(compiler);
        right().compiler(compiler);
        if ("+".equals(operator())) {
            compiler.addInstruction(new OpCode.BINARY_ADD());
        } else if ("-".equals(operator())) {
            compiler.addInstruction(new OpCode.BINARY_MINUS());
        } else if ("*".equals(operator())) {
            compiler.addInstruction(new OpCode.BINARY_MULT());
        } else if ("/".equals(operator())) {
            compiler.addInstruction(new OpCode.BINARY_DEVIDE());
        } else if ("%".equals(operator())) {
            compiler.addInstruction(new OpCode.MOD());
        } else if (">".equals(operator())) {
            compiler.addInstruction(new OpCode.GREATERTHAN());
        } else if ("<".equals(operator())) {
            compiler.addInstruction(new OpCode.LESSTHAN());
        } else if (">=".equals(operator())) {
            compiler.addInstruction(new OpCode.GREATEREQUAL());
        } else if ("<=".equals(operator())) {
            compiler.addInstruction(new OpCode.LESSEQUAL());
        } else if ("==".equals(operator())) {
            compiler.addInstruction(new OpCode.EQUAL());
        } else if ("!=".equals(operator())) {
            compiler.addInstruction(new OpCode.NOT_EQUAL());
        } else {
            throw new EnihsRuntimeException("bad compiler operator "
                    + operator(), this);
        }
    }
}
