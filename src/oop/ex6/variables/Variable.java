package oop.ex6.variables;

import oop.ex6.main.CodeException;

/**
 * Created by OrMiz on 12/06/2017.
 */
public abstract class Variable {

    protected String type;
    protected String name;
    protected String modifier;


    Variable(String value,String name, String modifier) throws CodeException{
        setType();
        if (checkIfValueValid(value)){
            checkIfNameValid(name);
            this.name = name;
            this.modifier = modifier;
        } else throw new VariableException(this.type, value);
    }

    abstract boolean checkIfValueValid(String stringToCheck);
    abstract void setType();

    private void checkIfNameValid(String variableName) throws VariableException {
        if (something){

        } else throw new VariableException("Bad variable name");
    }


}
