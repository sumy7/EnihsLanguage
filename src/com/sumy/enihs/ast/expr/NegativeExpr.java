package com.sumy.enihs.ast.expr;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTList;
import com.sumy.enihs.compiler.Compiler;
import com.sumy.enihs.vm.OpCode;

public class NegativeExpr extends ASTList {
    public NegativeExpr(List<AST> c) {
        super(c);
    }

    public NegativeExpr(AST operand) {
        addChild(operand);
    }

    public AST operand() {
        return child(0);
    }

    @Override
    public String toString() {
        return "-" + operand();
    }

    @Override
    public void visit(Compiler compiler) {
        operand().visit(compiler);
    }

    @Override
    public void compiler(Compiler compiler) {
        operand().compiler(compiler);
        compiler.addInstruction(new OpCode.NEGATIVE());
    }
}
