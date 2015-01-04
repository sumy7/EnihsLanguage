package com.sumy.enihs.ast;

import com.sumy.enihs.compiler.Compiler;
import com.sumy.enihs.object.EnihsInteger;
import com.sumy.enihs.token.Token;
import com.sumy.enihs.vm.OpCode;

public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(Token t) {
        super(t);
    }

    public int value() {
        return token().getNumber();
    }

    @Override
    public void visit(Compiler compiler) {
        compiler.addConsts(new EnihsInteger(value()));
    }

    @Override
    public void compiler(Compiler compiler) {
        compiler.addInstruction(new OpCode.LOAD_CONST((byte) compiler
                .addConsts(new EnihsInteger(value()))));
    }
}
