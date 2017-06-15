package oop.ex6.code;

import oop.ex6.variables.Variable;

import java.util.HashSet;

/**
 * Created by OrMiz on 12/06/2017.
 */
public class Closure {

    private HashSet<Variable> variables;
    private HashSet<Method> methods;

    boolean addVariable(Variable variableToAdd){
        return variables.add(variableToAdd);
    }

    boolean addMethod(Method methodToAdd){
        return methods.add(methodToAdd);
    }

}
