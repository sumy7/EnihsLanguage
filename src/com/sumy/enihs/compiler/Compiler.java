package com.sumy.enihs.compiler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.exception.EnihsRuntimeException;
import com.sumy.enihs.object.EnihsCodeObject;
import com.sumy.enihs.object.EnihsInteger;
import com.sumy.enihs.object.EnihsNullObject;
import com.sumy.enihs.object.EnihsObject;
import com.sumy.enihs.object.EnihsString;
import com.sumy.enihs.vm.Type;
import com.sumy.enihs.vm.OpCode.Instruction;

/**
 * 将AST转换为中间代码
 * 
 * @author sumy
 *
 */
public class Compiler {
    public ArrayList<Instruction> instructions; // 指令序列
    public ArrayList<String> names; // 变量名
    public ArrayList<EnihsObject> consts; // 常量

    public Compiler() {
        instructions = new ArrayList<Instruction>();
        names = new ArrayList<String>();
        consts = new ArrayList<EnihsObject>();
        consts.add(EnihsNullObject.getNone());
    }

    /**
     * 将ast转换为中间代码，分两次遍历，第一次获取名称引用，第二次转换
     * 
     * @param tree
     *            需要转换的ast
     */
    public void compiler(AST tree) {
        tree.visit(this);
        tree.compiler(this);
    }

    /**
     * 添加一条指令到中间代码集
     * 
     * @param instruction
     *            需要添加的指令
     * @return 指令位置
     */
    public int addInstruction(Instruction instruction) {
        instructions.add(instruction);
        return instructions.size() - 1;
    }

    /**
     * 替换一条指令
     * 
     * @param instruction
     *            被用来替换的指令
     * @param position
     *            替换位置
     */
    public void replaceInstruction(Instruction instruction, int position) {
        instructions.remove(position);
        instructions.add(position, instruction);
    }

    /**
     * 向变量名表添加一个变量名
     * 
     * @param name
     *            添加的变量名
     * @return 返回变量名添加的位置，若已存在，返回存在的位置
     */
    public int addNames(String name) {
        int index = names.indexOf(name);
        if (index == -1) {
            names.add(name);
            return names.size() - 1;
        }
        return index;
    }

    /**
     * 添加一个常量
     * 
     * @param _const
     *            被添加的常量
     * @return 返回常量添加的位置，若已存在，返回存在的位置
     */
    public int addConsts(EnihsObject _const) {
        int index = consts.indexOf(_const);
        if (index == -1) {
            consts.add(_const);
            return consts.size() - 1;
        }
        return index;
    }

    /**
     * 将指令序列转换为CodeObject存储
     * 
     * @return CodeObject对象
     */
    public EnihsCodeObject getCodeObject() {
        byte[] instructionbytecode = new byte[Type.LEN_INT
                + instructions.size() * 2];
        int pos = 0;
        Type.intToByteArray(instructionbytecode, pos, instructions.size());
        pos += Type.LEN_INT;
        for (Instruction instruction : instructions) {
            byte[] tmp = instruction.getBytecode();
            instructionbytecode[pos] = tmp[0];
            instructionbytecode[pos + 1] = tmp[1];
            pos += instruction.size();
        }
        return new EnihsCodeObject(names, consts, instructionbytecode);
    }

    /**
     * 将指令序列生成字节码
     * 
     * @return 字节码
     */
    public byte[] dumpByteCode() {
        // -----------------计算总长度----------------

        // 计算方法：
        // 名字列表的长度 - INT
        // --名字的长度 - INT
        // --名字的值 - 变长
        // 常量列表的长度 - INT
        // --常量类型 - BYTE
        // --常量的长度 - INT
        // --常量的值 - 变长
        // 指令列表的长度 - INT
        // --指令 - 2*BYTE

        int totalSize = 0;
        byte[][] namesbyte = new byte[names.size()][];
        totalSize += Type.LEN_INT; // 名称列表长度值
        for (int i = 0; i < names.size(); i++) {
            namesbyte[i] = names.get(i).getBytes();
            totalSize += Type.LEN_INT; // 字符串长度值
            totalSize += namesbyte[i].length; // 字符串长度
        }

        byte[] consttypebyte = new byte[consts.size()];
        byte[][] constbyte = new byte[consts.size()][];
        totalSize += Type.LEN_INT;// 常量列表长度值
        for (int i = 0; i < consts.size(); i++) {
            EnihsObject _const = consts.get(i);
            if (_const instanceof EnihsNullObject) {
                consttypebyte[i] = Type.TYPE_NONE;
                totalSize += Type.LEN_BYTE; // 空类型的类型值
                totalSize += Type.LEN_INT;// 空类型的长度值 = 0
                constbyte[i] = new byte[0];
            } else if (_const instanceof EnihsInteger) {
                consttypebyte[i] = Type.TYPE_INTEGER;
                totalSize += Type.LEN_BYTE; // 数值的类型值
                totalSize += Type.LEN_INT;// 数值的长度值
                constbyte[i] = new byte[Type.LEN_INT];
                Type.intToByteArray(constbyte[i], 0,
                        ((EnihsInteger) _const).nativeValue());
                totalSize += constbyte[i].length;// 数值的长度
            } else if (_const instanceof EnihsString) {
                consttypebyte[i] = Type.TYPE_STRING;
                totalSize += Type.LEN_BYTE;// 字符串的类型值
                totalSize += Type.LEN_INT; // 字符串的长度值
                constbyte[i] = ((EnihsString) _const).getBytes();
                totalSize += constbyte[i].length;// 字符串的长度
            } else {
                throw new EnihsRuntimeException(
                        "Not implement bytecode gens for " + _const);
            }
        }
        totalSize += Type.LEN_INT;// 指令的长度值
        totalSize += instructions.size() * 2;// **！现阶段！**每条指令的长度都是2

        // ---------------开始转换-----------------
        byte[] bytecode = new byte[totalSize];
        int pos = 0;
        Type.intToByteArray(bytecode, pos, names.size());
        pos += Type.LEN_INT;
        for (int i = 0; i < names.size(); i++) {
            Type.intToByteArray(bytecode, pos, namesbyte[i].length);
            pos += Type.LEN_INT;
            for (int j = 0; j < namesbyte[i].length; j++) {
                bytecode[pos + j] = namesbyte[i][j];
            }
            pos += namesbyte[i].length;
        }
        Type.intToByteArray(bytecode, pos, consts.size());
        pos += Type.LEN_INT;
        for (int i = 0; i < consts.size(); i++) {
            bytecode[pos] = consttypebyte[i];
            pos += Type.LEN_BYTE;
            Type.intToByteArray(bytecode, pos, constbyte[i].length);
            pos += Type.LEN_INT;
            for (int j = 0; j < constbyte[i].length; j++) {
                bytecode[pos + j] = constbyte[i][j];
            }
            pos += constbyte[i].length;
        }
        Type.intToByteArray(bytecode, pos, instructions.size());
        pos += Type.LEN_INT;
        for (Instruction instruction : instructions) {
            byte[] tmp = instruction.getBytecode();
            bytecode[pos] = tmp[0];
            bytecode[pos + 1] = tmp[1];
            pos += instruction.size();
        }
        return bytecode;
    }

    /**
     * 将字节码输出到输出流流中
     * 
     * @param stream
     *            输出流
     * @throws IOException
     *             读写异常
     */
    public void dumpByteCodeToStream(OutputStream stream) throws IOException {
        byte[] bytecode = dumpByteCode();
        stream.write(bytecode);
    }
}
