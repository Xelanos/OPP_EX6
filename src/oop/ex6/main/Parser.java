package oop.ex6.main;

import oop.ex6.code.CodeBlock;
import oop.ex6.code.GlobalBlock;
import oop.ex6.variables.VariableGenerator;

import java.io.*;
import java.util.Stack;

/**
 * Created by OrMiz on 12/06/2017.
 */
public class Parser {

    GlobalBlock processesLines(File sJavaFile) throws CodeException{
        try(BufferedReader lineReader = new BufferedReader(new FileReader(sJavaFile.getPath()))) {
            GlobalBlock globalBlock = new GlobalBlock();
            Stack<CodeBlock> blocks = new Stack<>();
            blocks.push(globalBlock);
            VariableGenerator variableGenerator = VariableGenerator.getInstance();
            String line = lineReader.readLine();
            String firstWord;
            CodeBlock block;
            while (!blocks.isEmpty()){
                block = blocks.peek();
                firstWord = RegexWorker.getFirstWord(line);
                // TODO: Add condition if the word in the line is } so we will know to do pop from the stack.
                // TODO: Once we did pop, add the block that pop into the next block closure.
                // TODO: If Line is null and the Stack is not empty - Throw Exception.
                // TODO: return the last block (Global Block).
                if (isMethodDeclaration(firstWord)){
                    // TODO: Create new Method Block with the properties
                    // TODO: Combine the Method Block Closure with the block's closure.
                    // TODO: Add the Method Block into the blocks Stack.
                }
                else if ((isConditionDeclaration(firstWord)) || isLoopDeclaration(firstWord)){
                    // TODO: Create new Condition Block with the properties.
                    // TODO: Add the Condition Block into the blocks Stack.
                }
                else if (isCallingMethod(firstWord)){
                    // TODO: Add the line into code in block.
                }
                else if (isCallingVar(firstWord)){
                    // TODO: Add the line into code in block.
                }
                else{
                    // TODO: Define a Var using the Generator.
                    // TODO: Add the var into the closure (variables) of the block.
                }
            }
        }
        catch (IOException badFile){
            throw new CodeException("Bad File");
        }
    }

    private boolean isMethodDeclaration(String startingWord){
        String cleanWord = RegexWorker.cleanWord(startingWord);
        return cleanWord.equals("void");
    }

    private boolean isConditionDeclaration(String startingWord){
        String cleanWord = RegexWorker.cleanWord(startingWord);
        return cleanWord.equals("if");
    }

    private boolean isLoopDeclaration(String startingWord){
        String cleanWord = RegexWorker.cleanWord(startingWord);
        return cleanWord.equals("while");
    }

    private boolean isCallingMethod(String startingWord){
        String cleanWord = RegexWorker.cleanWord(startingWord);
        return cleanWord.matches(RegexWorker.END_WITH_OPEN_BARKETS);
    }

    private boolean isCallingVar(String startingWord){
        String cleanWord = RegexWorker.cleanWord(startingWord);
        return cleanWord.matches(RegexWorker.END_WITH_EQUAL);
    }



}
