package oop.ex6.main;

import oop.ex6.code.CodeBlock;
import oop.ex6.code.ConditionBlock;
import oop.ex6.code.GlobalBlock;
import oop.ex6.code.Method;
import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by OrMiz on 12/06/2017.
 */
public class Parser {

    static GlobalBlock processesLines(File sJavaFile) throws CodeException, IOException{
        BufferedReader lineReader = new BufferedReader(new FileReader(sJavaFile.getPath()));
        GlobalBlock globalBlock = new GlobalBlock();
        Stack<CodeBlock> blocks = new Stack<>();
        blocks.push(globalBlock);
        VariableGenerator variableGenerator = VariableGenerator.getInstance();
        ArrayList<Variable> variables;
        String line = lineReader.readLine();
        String firstWord;
        CodeBlock block;
        try {
            while (!blocks.isEmpty()) {
                block = blocks.peek();
                if (line == null && blocks.size() == 1) {   // if has only the global block
                    GlobalBlock tempGlobal = (GlobalBlock) (blocks.pop());
                    globalBlock.combineClosure(tempGlobal);
                } else if (line == null) {     // if ended in the middle of a block
                    throw new CodeException("Wrong scope's brackets ");
                } else {
                    firstWord = RegexWorker.getFirstWord(line);
                    if (RegexWorker.isFinal(firstWord)) {    // if the line starts with final.
                        firstWord = RegexWorker.getSecondWord(line);
                    }
                    if (RegexWorker.isMethodDeclaration(line)) {    // if the line starts with void
                        Method method = makeMethod(line);
                        method.combineClosure(block);
                        blocks.push(method);
                    } else if ((RegexWorker.isConditionDeclaration(firstWord)) || // if starts with if or while
                            RegexWorker.isLoopDeclaration(firstWord)) {
                        ConditionBlock conditionBlock = makeConditionBlock(line, blocks.peek().getVars());
                        blocks.add(conditionBlock);
                    } else if ((RegexWorker.isCallingMethod(firstWord)) || RegexWorker.isCallingVar(firstWord)
                            || RegexWorker.isReturn(line)) { // if regular code line.
                        block.addLineToCode(line);
                    } else if (RegexWorker.isClosingScope(line)) {    // if is closing brackets
                        CodeBlock tempBlock = blocks.pop();
                        try {
                            Method method = ((Method) (tempBlock));
                            if (!globalBlock.addMethod(method)) {
                                throw new CodeException("Methods with same name");
                            }
                        } catch (CodeException codeException) {
                            throw codeException;
                        } catch (Exception e) {
                            blocks.peek().addBlock(tempBlock);
                        }
                    } else {
                        if (RegexWorker.isCommentOrBlank(line)) {    // if a blank line or comment
                            line = lineReader.readLine();
                            continue;
                        } else {  // var definition or ERROR.
                            variables = variableGenerator.makeVariablesFromLine(line, block, globalBlock);
                            for (Variable variable : variables) {
                                block.addVarToClosure(variable);
                            }
                        }
                    }
                    line = lineReader.readLine();
                }
            }
            checkUnknown(globalBlock);
            return globalBlock;
        }
        catch (EmptyStackException e){
            throw new CodeException("Bad Scopes");
        }
    }

    private static Method makeMethod(String line) throws CodeException {
        String modifier = null, name = null, signature = null;
        Pattern p = Pattern.compile(RegexWorker.METHOD_DECLARE);
        Matcher matcher = p.matcher(line);
        while (matcher.find()) {
            modifier = matcher.group(1);
            name = matcher.group(2);
            signature = matcher.group(3);
        }
        return new Method(name, signature, modifier);
    }

    private static ConditionBlock makeConditionBlock(String line, HashSet<Variable> knownVars) throws CodeException{
        String conditionContent = null;
        Pattern p = Pattern.compile((RegexWorker.CONDITION_DECLARE));
        Matcher matcher = p.matcher(line);
        if (matcher.find()){
            conditionContent = matcher.group(2);
        }
        if (conditionContent.equals("()")){
            throw new CodeException("Condition can't be empty");
        }
        return new ConditionBlock(conditionContent, knownVars);
    }

    private static void checkUnknown(GlobalBlock globalBlock) throws CodeException{
        for (String varName : globalBlock.getUnknownVars()){
            if(globalBlock.getVariable(varName, globalBlock) == null){
                throw new CodeException("Unknown variable: "+varName);
            }
        }
    }

}
