package com.sumy.enihs.ast.stmnt;

import java.util.List;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.ast.ASTList;
import com.sumy.enihs.compiler.Compiler;
import com.sumy.enihs.vm.OpCode;

public class IfStmnt extends ASTList {
    public IfStmnt(List<AST> c) {
        super(c);
    }

    public IfStmnt(AST condition, AST thenBlock, AST elseBlock) {
        addChild(condition);
        addChild(thenBlock);
        addChild(elseBlock);
    }

    public IfStmnt(AST condition, AST thenBlock) {
        addChild(condition);
        addChild(thenBlock);
    }

    public AST condition() {
        return child(0);
    }

    public AST thenBlock() {
        return child(1);
    }

    public AST elseBlock() {
        return numChildren() > 2 ? child(2) : null;
    }

    @Override
    public String toString() {
        return "(if " + condition() + " " + thenBlock() + " else" + elseBlock()
                + ")";
    }

    @Override
    public void visit(Compiler compiler) {
        condition().visit(compiler);
        thenBlock().visit(compiler);
        if (elseBlock() != null) {
            elseBlock().visit(compiler);
        }
    }

    @Override
    public void compiler(Compiler compiler) {
        int then_jmp_end = -1;
        condition().compiler(compiler);
        int jmp_end = compiler.addInstruction(new OpCode.NOP());// 预留位置，跳转到最后
        thenBlock().compiler(compiler);
        if (elseBlock() != null) {
            then_jmp_end = compiler.addInstruction(new OpCode.NOP()); // 预留位置，Then之后跳转到最后
            elseBlock().compiler(compiler);
        }
        int end = compiler.addInstruction(new OpCode.NOP());// 获取最后位置

        if (then_jmp_end != -1) {
            compiler.replaceInstruction(new OpCode.JMP_FALSE(
                    (byte) ((then_jmp_end - jmp_end + 1) * 2)), jmp_end);
            compiler.replaceInstruction(new OpCode.JMP(
                    (byte) ((end - then_jmp_end) * 2)), then_jmp_end);
        } else {
            compiler.replaceInstruction(new OpCode.JMP_FALSE(
                    (byte) ((end - jmp_end) * 2)), jmp_end);
        }
    }

}
