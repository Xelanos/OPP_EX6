package oop.ex6.variables;

/**
 * Created by or323 on 18/06/2017.
 */
public class VariableNamingExeption extends VariableException {

    VariableNamingExeption(String varName, String message) {
        super("Error in "+varName+": "+message);
    }
}
