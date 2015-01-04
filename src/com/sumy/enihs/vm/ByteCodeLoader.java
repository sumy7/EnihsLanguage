package com.sumy.enihs.vm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.sumy.enihs.exception.EnihsRuntimeException;
import com.sumy.enihs.object.EnihsCodeObject;
import com.sumy.enihs.object.EnihsInteger;
import com.sumy.enihs.object.EnihsNullObject;
import com.sumy.enihs.object.EnihsObject;
import com.sumy.enihs.object.EnihsString;

/**
 * 将文件中的字节码转换成字节码对象
 * 
 * @author sumy
 *
 */
public class ByteCodeLoader {
    private ArrayList<String> names;
    private ArrayList<EnihsObject> consts;

    public ByteCodeLoader() {

    }

    /**
     * 将文件保存为字节码对象
     * 
     * @param stream
     *            输入流
     * @param size
     *            文件大小
     * @return 字节码对象
     */
    public EnihsCodeObject load(InputStream stream, int size) {
        names = new ArrayList<String>();
        consts = new ArrayList<EnihsObject>();
        byte[] bytes = readBytesfromStream(stream, size);

        int index = 0;
        int namecnt = Type.intFromByteArray(bytes, 0);
        index += Type.LEN_INT;
        for (int i = 0; i < namecnt; i++) {
            int strlen = Type.intFromByteArray(bytes, index);
            index += Type.LEN_INT;
            String name = new String(bytes, index, strlen);
            index += strlen;
            names.add(name);
        }
        int constcnt = Type.intFromByteArray(bytes, index);
        index += Type.LEN_INT;
        for (int i = 0; i < constcnt; i++) {
            byte type = bytes[index];
            index += Type.LEN_BYTE;
            int constlen = Type.intFromByteArray(bytes, index);
            index += Type.LEN_INT;
            EnihsObject _const;
            switch (type) {
            case Type.TYPE_NONE:
                consts.add(EnihsNullObject.getNone());
                break;
            case Type.TYPE_STRING:
                _const = new EnihsString(bytes, index, constlen);
                consts.add(_const);
                index += constlen;
                break;
            case Type.TYPE_INTEGER:
                _const = new EnihsInteger(Type.intFromByteArray(bytes, index));
                consts.add(_const);
                index += Type.LEN_INT;
                break;
            default:
                throw new EnihsRuntimeException("no bytecode");
            }
        }
        int instructioncnt = Type.intFromByteArray(bytes, index);
        byte[] bytecode = new byte[instructioncnt * 2 + Type.LEN_INT];
        System.arraycopy(bytes, index, bytecode, 0, bytecode.length);
        return new EnihsCodeObject(names, consts, bytecode);
    }

    /**
     * 将文件读入byte数组
     * 
     * @param stream
     *            输入流
     * @param size
     *            文件大小
     * @return byte数组
     */
    private byte[] readBytesfromStream(InputStream stream, int size) {
        byte[] bytes = new byte[size];

        int offset = 0;
        int readBytes = Integer.MAX_VALUE;
        try {
            while (readBytes > 0) {
                readBytes = stream.read(bytes, offset, size - offset);
                offset += readBytes;
            }
        } catch (IOException e) {
            throw new EnihsRuntimeException("load code object error.");
        }
        return bytes;
    }

}
