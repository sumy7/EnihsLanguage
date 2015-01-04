package com.sumy.enihs.vm;

/**
 * 虚拟机指令
 * 
 * @author sumy
 *
 */
public class OpCode {

    public static final byte LOAD_CONST = 1; // 从常量表中载入对象
    public static final byte LOAD_NAME = 2;// 载入指定名字绑定的对象

    public static final byte NOP = 50; // 空指令
    public static final byte STORE_NAME = 51;// 将名字和对象绑定
    public static final byte BINARY_ADD = 52;// 加法
    public static final byte BINARY_MINUS = 53;// 减法
    public static final byte BINARY_MULT = 54; // 乘法
    public static final byte BINARY_DEVIDE = 55; // 除法
    public static final byte NEGATIVE = 56;// 取负数
    public static final byte MOD = 57; // 取余
    public static final byte PRINT_ITEM = 58;// 输出
    public static final byte PRINT_NEWLINE = 59;// 输出换行
    public static final byte JMP = 60; // 无条件跳转
    public static final byte JMP_TRUE = 61; // 为真时跳转
    public static final byte JMP_FALSE = 62;// 为假时跳转
    public static final byte LESSTHAN = 63; // 小于
    public static final byte LESSEQUAL = 64;// 小于等于
    public static final byte GREATERTHAN = 65;// 大于
    public static final byte GREATEREQUAL = 66;// 大于等于
    public static final byte EQUAL = 67;// 等于
    public static final byte NOT_EQUAL = 68; // 不等于

    public static abstract class Instruction {
        public abstract byte[] getBytecode();

        public abstract int size();

    }

    public static class LOAD_NAME extends Instruction {

        public byte position;

        public LOAD_NAME(byte position) {
            this.position = position;
        }

        @Override
        public byte[] getBytecode() {
            return new byte[] { LOAD_NAME, position };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "LOAD_NAME " + position;
        }
    }

    public static class LOAD_CONST extends Instruction {
        public byte location;

        public LOAD_CONST(byte location) {
            this.location = location;
        }

        @Override
        public byte[] getBytecode() {
            return new byte[] { LOAD_CONST, location };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "LOAD_CONST " + location;
        }
    }

    public static class NOP extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { NOP, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "NOP";
        }
    }

    public static class STORE_NAME extends Instruction {

        public byte location;

        public STORE_NAME(byte location) {
            this.location = location;
        }

        @Override
        public byte[] getBytecode() {
            return new byte[] { STORE_NAME, location };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "STORE_NAME " + location;
        }
    }

    public static class BINARY_ADD extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { BINARY_ADD, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "BINARY_ADD";
        }
    }

    public static class BINARY_MINUS extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { BINARY_MINUS, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "BINARY_MINUS";
        }
    }

    public static class BINARY_MULT extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { BINARY_MULT, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "BINARY_MULT";
        }

    }

    public static class BINARY_DEVIDE extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { BINARY_DEVIDE, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "BINARY_DEVIDE";
        }
    }

    public static class MOD extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { MOD, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "MOD";
        }
    }

    public static class PRINT_ITEM extends Instruction {
        public byte num;

        public PRINT_ITEM(byte num) {
            this.num = num;
        }

        @Override
        public byte[] getBytecode() {
            return new byte[] { PRINT_ITEM, num };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "PRINT_ITEM " + num;
        }
    }

    public static class NEGATIVE extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { NEGATIVE, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "NEGATIVE";
        }
    }

    public static class PRINT_NEWLINE extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { PRINT_NEWLINE, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "PRINT_NEWLINE";
        }
    }

    public static class JMP extends Instruction {
        private byte offset;

        public JMP(byte offset) {
            this.offset = offset;
        }

        @Override
        public byte[] getBytecode() {
            return new byte[] { JMP, offset };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "JMP";
        }
    }

    public static class JMP_FALSE extends Instruction {
        private byte offset;

        public JMP_FALSE(byte offset) {
            this.offset = offset;
        }

        @Override
        public byte[] getBytecode() {
            return new byte[] { JMP_FALSE, offset };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "JMP";
        }
    }

    public static class JMP_TRUE extends Instruction {
        private byte offset;

        public JMP_TRUE(byte offset) {
            this.offset = offset;
        }

        @Override
        public byte[] getBytecode() {
            return new byte[] { JMP_TRUE, offset };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "JMP";
        }
    }

    public static class LESSTHAN extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { LESSTHAN, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "LESSTHAN";
        }
    }

    public static class LESSEQUAL extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { LESSEQUAL, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "LESSEQUAL";
        }
    }

    public static class GREATERTHAN extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { GREATERTHAN, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "GREATERTHAN";
        }
    }

    public static class GREATEREQUAL extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { GREATEREQUAL, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "GREATEREQUAL";
        }
    }

    public static class EQUAL extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { EQUAL, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "EQUAL";
        }
    }

    public static class NOT_EQUAL extends Instruction {

        @Override
        public byte[] getBytecode() {
            return new byte[] { NOT_EQUAL, 0 };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public String toString() {
            return "NOT_EQUAL";
        }
    }
}
