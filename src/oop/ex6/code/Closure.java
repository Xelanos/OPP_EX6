package oop.ex6.code;

import oop.ex6.main.CodeException;
import oop.ex6.variables.Variable;

import java.util.HashSet;

/**
 * Created by OrMiz on 12/06/2017.
 */
public class Closure {

    /** variables of the closure */
    private HashSet<Variable> variables;

    /** methods of the closure  */
    private HashSet<Method> methods;

    /**
     * constructor initialize sets.
     */
    Closure(){
        variables = new HashSet<>();
        methods = new HashSet<>();
    }

    /**
     * add a variable to the closure.
     * @return true if managed to successfully add the variable, false if not.
     */
    boolean addVariable(Variable variableToAdd){
        return variables.add(variableToAdd);
    }

    /**
     * add a method to the closure.
     * @return true if managed to successfully add the variable, false if not.
     */
    boolean addMethod(Method methodToAdd){
        return methods.add(methodToAdd);
    }

    /**
     * @param methodName name of the method to get
     * @return the method object in the closure.
     * @throws CodeException if method is not in closure.
     */
    Method getMethod(String methodName) throws CodeException {
        for (Method method : methods){
            if (method.getName().equals(methodName)){
                return method;
            }
        }
        throw new CodeException("Unknown method: "+methodName);
    }

    /**
     * @param methodName name to check.
     * @return if method is in closure.
     */
    boolean containsMethod(String methodName){
        for (Method method : methods){
            if (method.getName().equals(methodName)){
                return true;
            }
        }
        return false;
    }

    /**
     * gets a variable object from the closure.
     * @param variableName variable to get.
     * @param block block to get from.
     * @param codeBlock current global of the program.
     * @return the variable object required.
     * @throws CodeException if the variable is unknown.
     */
    Variable getVariable(String variableName, GlobalBlock block, CodeBlock codeBlock) throws CodeException {
        for (Variable variable : variables){
            if (variable.getName().equals(variableName)){
                return variable;
            }
        }
        if (codeBlock instanceof Method) {
            Method method = (Method)(codeBlock);
            for (Variable variable : method.callVariables){
                if (variable.getName().equals(variableName)){
                    return variable;
                }
            }
            block.addToUnknown(variableName);
            return null;
        }
        else{
            throw new CodeException("Unknown variable: "+ variableName);
        }
    }

    /**
     * @param varName variable name to check.
     * @return if the closure has the variable.
     */
    boolean containVar(String varName){
        for (Variable variable : variables){
            if (variable.getName().equals(varName)){
                return true;
            }
        }
        return false;
    }

    public HashSet<Variable> getVariables(){
        return variables;
    }

    public HashSet<Method> getMethods(){
        return methods;
    }

    void deleteVar(String varName){
        for (Variable variable : variables){
            if (variable.getName().equals(varName)){
                variables.remove(variable);
            }
        }
    }

}
