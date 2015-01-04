package com.sumy.enihs.ast.stmnt;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTList;
import com.sumy.enihs.compiler.Compiler;
import com.sumy.enihs.vm.OpCode;

public class PrintStmnt extends ASTList {
    public PrintStmnt(List<AST> c) {
        super(c);
    }

    public PrintStmnt(AST argument) {
        addChild(argument);
    }

    public AST argument() {
        return child(0);
    }

    @Override
    public String toString() {
        return "(print " + argument() + ")";
    }

    @Override
    public void visit(Compiler compiler) {
        argument().visit(compiler);
    }

    @Override
    public void compiler(Compiler compiler) {
        argument().compiler(compiler);
        compiler.addInstruction(new OpCode.PRINT_ITEM((byte) argument()
                .numChildren()));
        compiler.addInstruction(new OpCode.PRINT_NEWLINE());
    }
}
