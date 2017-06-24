package oop.ex6.main;


import oop.ex6.code.CodeBlock;
import oop.ex6.code.GlobalBlock;
import oop.ex6.code.Method;

import java.io.File;
import java.io.IOException;



public class Sjavac {
    public static void main(String[] args) {
        try {
            File file = new File(args[0]);
            GlobalBlock globalBlock = Parser.processesLines(file);
            globalBlock.makeBlocks();
            for(CodeBlock codeBlock : globalBlock.getBlocks()){
                checkBlocks(codeBlock, globalBlock);
            }
            globalBlock.blockCheck();
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

       }System.out.println("0");
    }

    private static void checkBlocks(CodeBlock codeBlock, GlobalBlock globalBlock) throws CodeException{
        for (CodeBlock block : codeBlock.getBlocks()){
            if (!(block instanceof GlobalBlock)){
                for (Method method : globalBlock.getMethods()){
                    block.addMethod(method);
                }
            }
            block.blockCheck();
            checkBlocks(block, globalBlock);
        }
    }
}
