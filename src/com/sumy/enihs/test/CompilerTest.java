package com.sumy.enihs.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Scanner;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.compiler.Compiler;
import com.sumy.enihs.exception.EnihsException;
import com.sumy.enihs.interpreter.Interpreter;
import com.sumy.enihs.lexer.BasicLexer;
import com.sumy.enihs.object.EnihsCodeObject;
import com.sumy.enihs.parser.BasicParser;
import com.sumy.enihs.token.Token;
import com.sumy.enihs.vm.ByteCodeLoader;
import com.sumy.enihs.vm.ByteCodeObjectHelper;

public class CompilerTest {
    public static void main(String[] args) throws EnihsException {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream in = new FileInputStream("while");
            Scanner scanner = new Scanner(in);
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
                sb.append("\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
        BasicLexer lexer = new BasicLexer(sb.toString());
        BasicParser parser = new BasicParser(lexer, 100);
        Compiler comp = new Compiler();
        while (parser.LT(1) != Token.EOF) {
            AST t = parser.main();
            System.out.println(t);

            comp.compiler(t);

        }
        System.out.println(Arrays.toString(comp.dumpByteCode()));

        File file = new File("bytecode.bin");
        try {
            FileOutputStream os = new FileOutputStream(file);
            comp.dumpByteCodeToStream(os);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileInputStream in;
        try {
            in = new FileInputStream(file);
            System.out.println(file.length());
            ByteCodeLoader loader = new ByteCodeLoader();
            EnihsCodeObject obj = loader.load(in, (int) file.length());
            System.out.println(obj.names.toString());
            System.out.println(obj.consts.toString());
            System.out.println(Arrays.toString(obj.bytecode));
            System.out.println(ByteCodeObjectHelper.recomplieByteCode(obj));
            System.out.println("======================================");
            new Interpreter(obj).run();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
