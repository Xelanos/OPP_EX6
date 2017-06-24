package oop.ex6.code;

import com.sun.org.apache.regexp.internal.RE;
import oop.ex6.main.CodeException;
import oop.ex6.main.RegexWorker;
import oop.ex6.variables.Variable;

import java.util.HashSet;
import java.util.LinkedList;

public abstract class CodeBlock {

    protected Closure closure;
    protected LinkedList<String> code;
    protected LinkedList<CodeBlock> blocks;

    public void blockCheck() throws CodeException{
        for (String codeLine : code){
            if (RegexWorker.isCallingMethod(codeLine)){
                String methodName = RegexWorker.getMethodName(codeLine);
                String callString = RegexWorker.getCleanCallString(codeLine);
                methoedCallCheck(methodName, callString);
            }
        }
    }

    protected void methoedCallCheck(String methodName,String callString) throws CodeException {
        Method method = closure.getMethod(methodName);
        String[] variables = callString.split(",");
        if (variables.length != method.callVariables.size()){
            throw new CodeException("Error in calling "+methodName+" with "+variables.length+" parameters");
        }
        for (int i=0; i<variables.length; i++){
            String valueToCheck = variables[i];
            Variable varToCheck = method.callVariables.get(i);
            if(this.closure.containVar(valueToCheck)){
                Variable parameterVar = this.closure.getVariable(valueToCheck, null, this);
                if (!varToCheck.checkIfValueValid(parameterVar.getValue())){
                    throw new CodeException("Error in method "+methodName+" parameter "+i+".\n"+
                            "Expected "+varToCheck.getType());
                }
            }
            else if (!varToCheck.checkIfValueValid(valueToCheck)){
                throw new CodeException("Error in method "+methodName+" parameter "+i+".\n"+
                        "Expected "+varToCheck.getType());
            }
        }
    }

    /**
     * added line to the blocks code.
     * @param line line to add.
     */
    public void addLineToCode(String line){
        code.add(line);
    }

    /**
     * adds a variable to the blocks closure.
     * @param variable variable to add.
     */
    public void addVarToClosure(Variable variable){
        closure.addVariable(variable);
    }

    /**
     * check if a certain name upholds the rules of naming in s-java.
     * @param name name to check.
     * @throws NamingException if naming is illegal.
     */
    public static void checkIfNameValid(String name) throws NamingException {
        if (Character.isDigit(name.charAt(0))) {
            throw new NamingException(name," name can't start with a number");
        }
        if (name.startsWith("_")){
            if (name.length() == 1) {
                throw new NamingException(name," name can't be only underscore");
            }
        }
        if (!name.matches(RegexWorker.IS_ALL_WORD_REGEX)){
            throw new NamingException(name,
                    " name can only contain numbers, letters and underscore and must not start with number");
        }
    }

    /**
     * added the closure of another block to this one.
     * @param block block to added closure from.
     */
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

    public boolean containVar(String varName){
        return closure.containVar(varName);
    }

    public HashSet<Variable> getVars(){
        return this.closure.getVariables();
    }

    public Variable getVariable(String variableName, GlobalBlock block) throws CodeException {
        return closure.getVariable(variableName, block, this);
    }

    public void removeVariableFromClosure(Variable variable){
        String varName = variable.getName();
        closure.deleteVar(varName);
    }

    public LinkedList<CodeBlock> getBlocks(){
        return this.blocks;
    }
}
