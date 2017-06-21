package oop.ex6.code;

import oop.ex6.main.CodeException;
import oop.ex6.variables.Variable;

import java.util.HashSet;

/**
 * Created by OrMiz on 12/06/2017.
 */
public class Closure {

    private HashSet<Variable> variables;
    private HashSet<Method> methods;

    public Closure(){
        variables = new HashSet<>();
        methods = new HashSet<>();
    }

    boolean addVariable(Variable variableToAdd){
        return variables.add(variableToAdd);
    }

    boolean addMethod(Method methodToAdd){
        return methods.add(methodToAdd);
    }

    Method getMethod(String methodName) throws CodeException {
        for (Method method : methods){
            if (method.getName().equals(methodName)){
                return method;
            }
        }
        throw new CodeException("Unknown method: "+methodName);
    }

    Variable getVariable(String variableName) throws CodeException {
        for (Variable variable : variables){
            if (variable.getName().equals(variableName)){
                return variable;
            }
        }
        throw new CodeException("Unknown variable: "+variableName);
    }

    public HashSet<Variable> getVariables(){
        return variables;
    }

    public HashSet<Method> getMethods(){
        return methods;
    }

}
