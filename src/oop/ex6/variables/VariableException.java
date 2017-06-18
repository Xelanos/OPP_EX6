package oop.ex6.variables;

import oop.ex6.main.CodeException;

/**
 * Created by or323 on 18/06/2017.
 */
public class VariableException extends CodeException {

    public VariableException(String varType, String varValue) {
        super("Can't create a variable of type "+varType+" with the value: "+varValue);
    }

    VariableException(String message){
        super(message);
    }
}
