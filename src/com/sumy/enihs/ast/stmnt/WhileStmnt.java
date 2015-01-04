package com.sumy.enihs.ast.stmnt;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTList;
import com.sumy.enihs.compiler.Compiler;
import com.sumy.enihs.vm.OpCode;

public class WhileStmnt extends ASTList {
    public WhileStmnt(List<AST> c) {
        super(c);
    }

    public WhileStmnt(AST condition, AST body) {
        addChild(condition);
        addChild(body);
    }

    public AST condition() {
        return child(0);
    }

    public AST body() {
        return child(1);
    }

    @Override
    public String toString() {
        return "(while " + condition() + " " + body() + ")";
    }

    @Override
    public void visit(Compiler compiler) {
        condition().visit(compiler);
        body().visit(compiler);
    }

    @Override
    public void compiler(Compiler compiler) {
        int begin = compiler.addInstruction(new OpCode.NOP()); // while开始位置
        condition().compiler(compiler);
        int jmp_end = compiler.addInstruction(new OpCode.NOP());// 预留位置，跳转到结束
        body().compiler(compiler);
        int jmp_begin = compiler.addInstruction(new OpCode.NOP());// 预留位置，跳转到开始
        System.out.println(begin + " " + jmp_end + " " + jmp_begin);
        compiler.replaceInstruction(new OpCode.JMP_FALSE((byte) ((jmp_begin
                - jmp_end + 1) * 2)), jmp_end);
        compiler.replaceInstruction(new OpCode.JMP(
                (byte) ((begin - jmp_begin) * 2)), jmp_begin);
    }

}
