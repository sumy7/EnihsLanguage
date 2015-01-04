package com.sumy.enihs.vm;

import java.util.Arrays;

import com.sumy.enihs.object.EnihsCodeObject;

/**
 * 将字节码对象转变为可阅读形式
 * 
 * @author sumy
 *
 */
public class ByteCodeObjectHelper {
    /**
     * 转换名称列表
     * 
     * @param obj
     *            字节码对象
     * @return 列表字符串
     */
    public static String dumpNameList(EnihsCodeObject obj) {
        return obj.names.toString();
    }

    /**
     * 转换常量列表
     * 
     * @param obj
     *            字节码对象
     * @return 列表字符串
     */
    public static String dumpConstList(EnihsCodeObject obj) {
        return obj.consts.toString();
    }

    /**
     * 转换字节码
     * 
     * @param obj
     *            字节码对象
     * @return 字节码字符串
     */
    public static String dumpByteCode(EnihsCodeObject obj) {
        return Arrays.toString(obj.bytecode);
    }

    /**
     * 转换字节码为指令形式
     * 
     * @param obj
     *            字节码对象
     * @return 字节码序列字符串，以<i><换行符></i>分割
     */
    public static String recomplieByteCode(EnihsCodeObject obj) {
        int instructcnt = Type.intFromByteArray(obj.bytecode, 0);
        int pc = 4;
        int cnt = 0;
        StringBuffer buf = new StringBuffer();
        while (cnt < instructcnt) {
            buf.append(getOpCodeName(obj.bytecode[pc]) + " "
                    + obj.bytecode[pc + 1] + "\n");
            pc += 2;
            cnt++;
        }
        return buf.toString();
    }

    /**
     * 获取字节码的名称
     * 
     * @param opcode
     *            字节码序号
     * @return 字节码名称
     */
    public static String getOpCodeName(byte opcode) {
        switch (opcode) {
        case OpCode.LOAD_CONST:
            return "LOAD_CONST";
        case OpCode.LOAD_NAME:
            return "LOAD_NAME";
        case OpCode.NOP:
            return "NOP";
        case OpCode.STORE_NAME:
            return "STORE_NAME";
        case OpCode.BINARY_ADD:
            return "BINARY_ADD";
        case OpCode.BINARY_MINUS:
            return "BINARY_MINUS";
        case OpCode.BINARY_MULT:
            return "BINARY_MULT";
        case OpCode.BINARY_DEVIDE:
            return "BINARY_DEVIDE";
        case OpCode.NEGATIVE:
            return "NEGATIVE";
        case OpCode.MOD:
            return "MOD";
        case OpCode.PRINT_ITEM:
            return "PRINT_ITEM";
        case OpCode.PRINT_NEWLINE:
            return "PRINT_NEWLINE";
        case OpCode.JMP:
            return "JMP";
        case OpCode.JMP_TRUE:
            return "JMP_TRUE";
        case OpCode.JMP_FALSE:
            return "JMP_FALSE";
        case OpCode.GREATERTHAN:
            return "GREATERTHAN";
        case OpCode.GREATEREQUAL:
            return "GREATEREQUAL";
        case OpCode.LESSTHAN:
            return "LESSTHAN";
        case OpCode.LESSEQUAL:
            return "LESSEQUAL";
        case OpCode.EQUAL:
            return "EUQAL";
        case OpCode.NOT_EQUAL:
            return "NOT_EQUAL";
        default:
            return "undefined";
        }
    }

}
