package oop.ex6.code;

import oop.ex6.main.CodeException;
import oop.ex6.main.RegexWorker;
import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableException;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public abstract class CodeBlock {

    protected Closure closure;
    protected LinkedList<String> code;
    protected LinkedList<CodeBlock> blocks;

    abstract void blockCheck() throws CodeException;

    protected void methoedCallCheck(String methodName,String callString) throws CodeException {
        Method method = closure.getMethod(methodName);
        String[] variables = callString.split(",");
        if (variables.length != method.callVariables.size()){
            throw new CodeException("Error in calling "+methodName+" with "+variables.length+" parameters");
        }
        for (int i=0; i<variables.length; i++){
            String valueToCheck = variables[i];
            Variable varToCheck = method.callVariables.get(i);
            if (!varToCheck.checkIfValueValid(valueToCheck)){
                throw new CodeException("Error in method "+methodName+" parameter "+i+".\n"+
                        "Expected"+varToCheck.getType());
            }
        }
    }

    public void addLineToCode(String line){
        code.add(line);
    }

    public void addVarToClosure(Variable variable){
        closure.addVariable(variable);
    }

    public static void checkIfNameValid(String name) throws NamingException {
        String finalSubString = name;
        if (Character.isDigit(name.charAt(0))) {
            throw new NamingException(name," name can't start with a number");
        }
        if (name.startsWith("_")){
            finalSubString = name.substring(1);
            if (finalSubString.length() == 0) {
                throw new NamingException(name," name can't be underscore");
            }
        }
        if (!finalSubString.matches(RegexWorker.IS_ALL_WORD_REGEX)){
            throw new NamingException(name,
                    " name can only contain numbers, letters and underscore");
        }
    }

    public void combineClosure(CodeBlock block){
        HashSet<Variable> oldVars = block.closure.getVariables();
        HashSet<Method> oldMethods = block.closure.getMethods();
        for (Variable var : oldVars){
            this.closure.addVariable(var);
        }
        for (Method method : oldMethods){
            this.closure.addMethod(method);
        }
    }

    public void addBlock(CodeBlock block){
        blocks.add(block);
    }

    public boolean addMethod(Method method){
        if(closure.containsMethod(method.getName()))
        {
            return false;
        }
        else {
            closure.addMethod(method);
            return true;
        }
    }

}
