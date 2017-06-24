package oop.ex6.main;


import oop.ex6.code.CodeBlock;
import oop.ex6.code.GlobalBlock;
import oop.ex6.code.Method;
import oop.ex6.variables.Variable;

import java.io.File;
import java.io.IOException;

/**
 * main class for the program.
 */

public class Sjavac {
    public static void main(String[] args) {
        try {
            File file = new File(args[0]);
            GlobalBlock globalBlock = Parser.processesLines(file);  // Create blocks and fill up codes
            globalBlock.makeBlocks();   // Put all the blocks created in one place
            for(CodeBlock codeBlock : globalBlock.getBlocks()){
                checkBlocks(codeBlock, globalBlock);    // check each block
            }
            globalBlock.blockCheck();   // check the global block
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

    /**
     * Goes over the CodeLines in the block and checks if it's a legal command.
     * @param codeBlock - the block to check.
     * @param globalBlock - the global scope with all the methods and global variables
     * @throws CodeException - in case there is an illegal command
     */
    private static void checkBlocks(CodeBlock codeBlock, GlobalBlock globalBlock) throws CodeException{
        for (CodeBlock block : codeBlock.getBlocks()){ // check all the lower scopes
            if (!(block instanceof GlobalBlock)){   // if the block we check is not the global
                for (Method method : globalBlock.getMethods()){
                    block.addMethod(method);    // get all the methods so the scope will know them
                }
            }
            for (Variable var : codeBlock.getVars()){ // get all the vars from the higher scope
                block.addVarToClosure(var);
            }
            block.blockCheck(); // check the current block
            checkBlocks(block, globalBlock);    // call again with the lower block
        }
        if(!(codeBlock instanceof GlobalBlock)){    // if the block we check is not global block
            for(Variable var : globalBlock.getVars()){
                codeBlock.addVarToClosure(var); // add all the global vars to the scope
            }
            for (Method method : globalBlock.getMethods()){
                codeBlock.addMethod(method);    // add all the methods to the scope
            }
        }
        codeBlock.blockCheck(); // check the current block
    }
}
