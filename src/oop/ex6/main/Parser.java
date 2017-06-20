package oop.ex6.main;

import oop.ex6.code.CodeBlock;
import oop.ex6.code.GlobalBlock;
import oop.ex6.code.Method;
import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by OrMiz on 12/06/2017.
 */
public class Parser {

    GlobalBlock processesLines(File sJavaFile) throws CodeException, IOException{
        BufferedReader lineReader = new BufferedReader(new FileReader(sJavaFile.getPath()));
        GlobalBlock globalBlock = new GlobalBlock();
        Stack<CodeBlock> blocks = new Stack<>();
        blocks.push(globalBlock);
        VariableGenerator variableGenerator = VariableGenerator.getInstance();
        ArrayList<Variable> variables;
        String line = lineReader.readLine();
        String firstWord;
        CodeBlock block;
        boolean hasModifier;
        while (!blocks.isEmpty()){
            block = blocks.peek();
            hasModifier = false;
            firstWord = RegexWorker.getFirstWord(line);
            if (RegexWorker.isFinal(firstWord)){    // if the line starts with final.
                firstWord = RegexWorker.getSecondWord(line);
                hasModifier = true;
            }
            if (RegexWorker.isMethodDeclaration(firstWord)){    // if the line starts with void
                // TODO: Create new Method Block with the properties
                // TODO: Combine the Method Block Closure with the block's closure.
                // TODO: Add the Method Block into the blocks Stack.
            }
            else if ((RegexWorker.isConditionDeclaration(firstWord)) || // if starts with if or while
                    RegexWorker.isLoopDeclaration(firstWord)){
                // TODO: Create new Condition Block with the properties.
                // TODO: Add the Condition Block into the blocks Stack.
            }
            else if (RegexWorker.isCallingMethod(firstWord) || RegexWorker.isCallingVar(firstWord)
                    || RegexWorker.isReturn(line)) { // if regular code line.
                block.addLineToCode(line);
            }
            else if (RegexWorker.isClosingScope(line)) {    // if is closing brackets
                CodeBlock tempBlock = blocks.pop();
                // TODO: Add Block into the new top block closure

            }
            else if (line == null && blocks.size() == 1){   // if has only the global block
                globalBlock = (GlobalBlock)(blocks.pop());
            }
            else if (line == null){     // if ended in the middle of a block
                throw new CodeException("Wrong scope's brackets ");
            }
            else{
                if (RegexWorker.isCommentOrBlank(line)){    // if a blank line or comment
                    continue;
                }
                else {  // var definition or ERROR.
                    variables = variableGenerator.makeVariablesFromLine(line);
                    for(Variable variable : variables){
                        block.addVarToClosure(variable);
                    }
                }
            }
            line = lineReader.readLine();
        }
        return globalBlock;
    }




}
