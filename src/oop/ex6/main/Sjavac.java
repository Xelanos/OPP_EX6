package oop.ex6.main;

import oop.ex6.code.CodeBlock;
import oop.ex6.code.GlobalBlock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by or323 on 12/06/2017.
 */
public class Sjavac {
    public static void main(String[] args) {
        try {
            File codeFile = new File(args[0]);
            Stack<CodeBlock> codeBlockStack = new Stack<>();
        }
        catch (CodeException e){
            System.out.println("1");
            System.err.println(e.getMessage());
            return;

        }
        catch (IOException e){
            System.out.println("2");
            System.err.println(e.getMessage());
            return;

        }
        System.out.println("0");
    }
}
