package com.sumy.enihs.object;

import java.util.ArrayList;

/**
 * 保存名称、常量、指令字节码
 * 
 * @author sumy
 *
 */
public class EnihsCodeObject extends EnihsObject {
    public ArrayList<String> names;
    public ArrayList<EnihsObject> consts;
    public byte[] bytecode;

    public EnihsCodeObject(ArrayList<String> namelist,
            ArrayList<EnihsObject> constlist, byte[] bytecode) {
        this.names = namelist;
        this.consts = constlist;
        this.bytecode = bytecode;

        // this.names = new ArrayList<String>();
        // Collections.copy(names, namelist);
        // this.consts = new ArrayList<EnihsObject>();
        // Collections.copy(consts, constlist);
        // this.bytecode = Arrays.copyOf(bytecode, bytecode.length);
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<EnihsObject> getConsts() {
        return consts;
    }

    public byte[] getBytecode() {
        return bytecode;
    }

}
