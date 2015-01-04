package com.sumy.enihs.ast;

import com.sumy.enihs.compiler.Compiler;
import com.sumy.enihs.object.EnihsString;
import com.sumy.enihs.token.Token;
import com.sumy.enihs.vm.OpCode;

public class StringLiteral extends ASTLeaf {
    public StringLiteral(Token t) {
        super(t);
    }

    public String value() {
        return token().getText();
    }

    @Override
    public void visit(Compiler compiler) {
        compiler.addConsts(new EnihsString(value()));
    }

    @Override
    public void compiler(Compiler compiler) {
        compiler.addInstruction(new OpCode.LOAD_CONST((byte) compiler
                .addConsts(new EnihsString(value()))));
    }
}
