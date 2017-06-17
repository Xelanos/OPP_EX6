package oop.ex6.variables;

import oop.ex6.main.CodeException;

/**
 * An exception thrown when can't create variable
 */
public class VarExeption extends CodeException {

    public VarExeption(String varType, String varValue) {
        super("Could not create a variable of type "+varType+" with the value"+varValue);
    }
}
