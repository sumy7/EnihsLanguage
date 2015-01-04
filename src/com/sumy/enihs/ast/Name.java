package com.sumy.enihs.ast;

import com.sumy.enihs.compiler.Compiler;
import com.sumy.enihs.token.Token;
import com.sumy.enihs.vm.OpCode;

public class Name extends ASTLeaf {
    public static final int UNKNOWN = -1;
    public int nest, index;

    public Name(Token t) {
        super(t);
    }

    public String name() {
        return token().getText();
    }

    @Override
    public void visit(Compiler compiler) {
        compiler.addNames(name());
    }

    @Override
    public void compiler(Compiler compiler) {
        compiler.addInstruction(new OpCode.LOAD_NAME((byte) compiler
                .addNames(name())));
    }
}
