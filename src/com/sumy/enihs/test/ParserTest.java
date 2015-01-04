package com.sumy.enihs.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.sumy.enihs.ast.AST;
import com.sumy.enihs.lexer.BasicLexer;
import com.sumy.enihs.parser.BasicParser;
import com.sumy.enihs.token.Token;

public class ParserTest {
    public static void main(String[] args) throws Throwable {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream in = new FileInputStream("simple");
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
        while (parser.LT(1) != Token.EOF) {
            AST t = parser.program();
            System.out.println(t);
        }
    }
}
