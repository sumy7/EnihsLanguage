package com.sumy.enihs.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.sumy.enihs.lexer.BasicLexer;
import com.sumy.enihs.token.Token;

public class LexerTest {
    public static void main(String[] args) throws Throwable {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream in = new FileInputStream("example1");
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
        Token t = lexer.nextToken();
        while (t != Token.EOF) {
            System.out.println(t);
            t = lexer.nextToken();
        }
        System.out.println(t);// EOF
    }
}
