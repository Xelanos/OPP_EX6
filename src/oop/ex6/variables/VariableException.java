package oop.ex6.variables;

import oop.ex6.main.CodeException;

/**
 * A class for exceptions made from creating variables.
 */
class VariableException extends CodeException {

    /**
     * an error message for when type doesn't matches value.
     * @param varType type of the current variable.
     * @param varValue value tried to assign.
     */
    VariableException(String varType, String varValue) {
        super("Can't create a variable of type "+varType+" with the value: "+varValue);
    }

    VariableException(String message){
        super(message);
    }
}
