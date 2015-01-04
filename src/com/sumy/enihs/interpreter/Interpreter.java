package com.sumy.enihs.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sumy.enihs.exception.EnihsRuntimeException;
import com.sumy.enihs.object.EnihsBoolean;
import com.sumy.enihs.object.EnihsCodeObject;
import com.sumy.enihs.object.EnihsInteger;
import com.sumy.enihs.object.EnihsObject;
import com.sumy.enihs.object.EnihsString;
import com.sumy.enihs.vm.ByteCodeObjectHelper;
import com.sumy.enihs.vm.OpCode;

/**
 * 解释器
 * 
 * @author sumy
 *
 */
public class Interpreter {
    public ArrayList<String> names;
    public ArrayList<EnihsObject> consts;
    public byte[] bytecode;

    public Interpreter(EnihsCodeObject code) {
        this.names = code.names;
        this.consts = code.consts;
        this.bytecode = code.bytecode;
    }

    public Interpreter(ArrayList<String> names, ArrayList<EnihsObject> consts,
            byte[] bytecode) {
        this.names = names;
        this.consts = consts;
        this.bytecode = bytecode;
    }

    /**
     * 开始执行字节码
     */
    public void run() {
        Map<String, EnihsObject> global = new HashMap<String, EnihsObject>();
        Stack stack = new Stack(1000);

        Object op1;
        Object op2;

        int pc = 4;// 跳过指令长度
        while (pc < bytecode.length) {
            switch (bytecode[pc]) {
            case OpCode.LOAD_CONST:
                stack.push(consts.get(bytecode[pc + 1]));
                break;

            case OpCode.LOAD_NAME:
                op1 = names.get(bytecode[pc + 1]);
                op2 = global.get(op1);
                if (op2 == null) {
                    throw new EnihsRuntimeException("name " + op1
                            + " not assign before use.");
                }
                stack.push(op2);
                break;

            case OpCode.STORE_NAME:
                global.put(names.get(bytecode[pc + 1]),
                        (EnihsObject) stack.pop());
                break;
            case OpCode.BINARY_MINUS:
                op2 = stack.pop();
                op1 = stack.pop();
                if (op1 instanceof EnihsInteger && op2 instanceof EnihsInteger) {
                    int res = ((EnihsInteger) op1).nativeValue()
                            - ((EnihsInteger) op2).nativeValue();
                    stack.push(new EnihsInteger(res));
                } else {
                    throw new EnihsRuntimeException("Binary_minus:op1 "
                            + op1.getClass() + " op2 " + op2.getClass());
                }
                break;

            case OpCode.BINARY_ADD:
                op2 = stack.pop();
                op1 = stack.pop();
                if (op1 instanceof EnihsInteger && op2 instanceof EnihsInteger) {
                    int res = ((EnihsInteger) op1).nativeValue()
                            + ((EnihsInteger) op2).nativeValue();
                    stack.push(new EnihsInteger(res));
                } else if (op1 instanceof EnihsString
                        || op2 instanceof EnihsString) {
                    stack.push(new EnihsString(op1.toString() + op2.toString()));
                } else {
                    throw new EnihsRuntimeException("Binary_add:op1 "
                            + op1.getClass() + " op2 " + op2.getClass());
                }
                break;

            case OpCode.BINARY_DEVIDE:
                op2 = stack.pop();
                op1 = stack.pop();
                if (op1 instanceof EnihsInteger && op2 instanceof EnihsInteger) {
                    int res = ((EnihsInteger) op1).nativeValue()
                            / ((EnihsInteger) op2).nativeValue();
                    stack.push(new EnihsInteger(res));
                } else {
                    throw new EnihsRuntimeException("Binary_devide:op1 "
                            + op1.getClass() + " op2 " + op2.getClass());
                }
                break;
            case OpCode.BINARY_MULT:
                op2 = stack.pop();
                op1 = stack.pop();
                if (op1 instanceof EnihsInteger && op2 instanceof EnihsInteger) {
                    int res = ((EnihsInteger) op1).nativeValue()
                            * ((EnihsInteger) op2).nativeValue();
                    stack.push(new EnihsInteger(res));
                } else {
                    throw new EnihsRuntimeException("Binary_mult:op1 "
                            + op1.getClass() + " op2 " + op2.getClass());
                }
                break;
            case OpCode.MOD:
                op2 = stack.pop();
                op1 = stack.pop();
                if (op1 instanceof EnihsInteger && op2 instanceof EnihsInteger) {
                    int res = ((EnihsInteger) op1).nativeValue()
                            % ((EnihsInteger) op2).nativeValue();
                    stack.push(new EnihsInteger(res));
                } else {
                    throw new EnihsRuntimeException("Mod:op1 " + op1.getClass()
                            + " op2 " + op2.getClass());
                }
                break;
            case OpCode.NEGATIVE:
                op1 = stack.pop();
                if (op1 instanceof EnihsInteger) {
                    int res = -((EnihsInteger) op1).nativeValue();
                    stack.push(new EnihsInteger(res));
                } else {
                    throw new EnihsRuntimeException("negative:op1 "
                            + op1.getClass());
                }
            case OpCode.PRINT_ITEM:
                int itemcnt = bytecode[pc + 1];
                Stack printstack = new Stack(itemcnt);
                for (int i = 0; i < itemcnt; i++) {
                    printstack.push(stack.pop());
                }
                for (int i = 0; i < itemcnt; i++) {
                    System.out.print(printstack.pop());
                }
                break;
            case OpCode.PRINT_NEWLINE:
                System.out.println();
                break;
            case OpCode.NOP:
                break;
            case OpCode.LESSTHAN:
                op2 = stack.pop();
                op1 = stack.pop();
                if (op1 instanceof EnihsInteger && op2 instanceof EnihsInteger) {
                    if (((EnihsInteger) op1).nativeValue() < ((EnihsInteger) op2)
                            .nativeValue()) {
                        stack.push(EnihsBoolean.True);
                    } else {
                        stack.push(EnihsBoolean.False);
                    }
                } else {
                    throw new EnihsRuntimeException("LessThan:op1 "
                            + op1.getClass() + " op2 " + op2.getClass());
                }
                break;
            case OpCode.LESSEQUAL:
                op2 = stack.pop();
                op1 = stack.pop();
                if (op1 instanceof EnihsInteger && op2 instanceof EnihsInteger) {
                    if (((EnihsInteger) op1).nativeValue() <= ((EnihsInteger) op2)
                            .nativeValue()) {
                        stack.push(EnihsBoolean.True);
                    } else {
                        stack.push(EnihsBoolean.False);
                    }
                } else {
                    throw new EnihsRuntimeException("LessEqual:op1 "
                            + op1.getClass() + " op2 " + op2.getClass());
                }
                break;
            case OpCode.GREATERTHAN:
                op2 = stack.pop();
                op1 = stack.pop();
                if (op1 instanceof EnihsInteger && op2 instanceof EnihsInteger) {
                    if (((EnihsInteger) op1).nativeValue() > ((EnihsInteger) op2)
                            .nativeValue()) {
                        stack.push(EnihsBoolean.True);
                    } else {
                        stack.push(EnihsBoolean.False);
                    }
                } else {
                    throw new EnihsRuntimeException("GreaterThan:op1 "
                            + op1.getClass() + " op2 " + op2.getClass());
                }
                break;
            case OpCode.GREATEREQUAL:
                op2 = stack.pop();
                op1 = stack.pop();
                if (op1 instanceof EnihsInteger && op2 instanceof EnihsInteger) {
                    if (((EnihsInteger) op1).nativeValue() >= ((EnihsInteger) op2)
                            .nativeValue()) {
                        stack.push(EnihsBoolean.True);
                    } else {
                        stack.push(EnihsBoolean.False);
                    }
                } else {
                    throw new EnihsRuntimeException("GreaterEqual:op1 "
                            + op1.getClass() + " op2 " + op2.getClass());
                }
                break;
            case OpCode.EQUAL:
                op2 = stack.pop();
                op1 = stack.pop();
                if (op1 instanceof EnihsInteger && op2 instanceof EnihsInteger) {
                    if (((EnihsInteger) op1).nativeValue() == ((EnihsInteger) op2)
                            .nativeValue()) {
                        stack.push(EnihsBoolean.True);
                    } else {
                        stack.push(EnihsBoolean.False);
                    }
                } else {
                    throw new EnihsRuntimeException("Equal:op1 "
                            + op1.getClass() + " op2 " + op2.getClass());
                }
                break;
            case OpCode.NOT_EQUAL:
                op2 = stack.pop();
                op1 = stack.pop();
                if (op1 instanceof EnihsInteger && op2 instanceof EnihsInteger) {
                    if (((EnihsInteger) op1).nativeValue() != ((EnihsInteger) op2)
                            .nativeValue()) {
                        stack.push(EnihsBoolean.True);
                    } else {
                        stack.push(EnihsBoolean.False);
                    }
                } else {
                    throw new EnihsRuntimeException("NOT_Equal:op1 "
                            + op1.getClass() + " op2 " + op2.getClass());
                }
                break;
            case OpCode.JMP:
                pc += bytecode[pc + 1];
                pc -= 2;
                break;
            case OpCode.JMP_FALSE:
                op1 = stack.pop();
                if (op1 instanceof EnihsBoolean) {
                    if (op1.equals(EnihsBoolean.False)) {
                        pc += bytecode[pc + 1];
                        pc -= 2;
                    }
                } else {
                    throw new EnihsRuntimeException(
                            "JMP need boolean type, but found " + op1);
                }
                break;
            case OpCode.JMP_TRUE:
                op1 = stack.pop();
                if (op1 instanceof EnihsBoolean) {
                    if (op1.equals(EnihsBoolean.True)) {
                        pc += bytecode[pc + 1];
                        pc -= 2;
                    }
                } else {
                    throw new EnihsRuntimeException(
                            "JMP need boolean type, but found " + op1);
                }
                break;
            default:
                throw new EnihsRuntimeException("unimplement instruction "
                        + ByteCodeObjectHelper.getOpCodeName(bytecode[pc]));
            }
            pc += 2;
        }
    }
}
