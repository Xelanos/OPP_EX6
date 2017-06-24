package oop.ex6.variables;

import oop.ex6.code.CodeBlock;
import oop.ex6.code.NamingException;

/**
 * A class representing a variable in s-Java.
 */
public abstract class Variable {

    /** type of this variable */
    protected String type;
    /** name of this variable */
    protected String name;
    /** modifier of this variable */
    protected String modifier;
    /** value of this variable */
    protected String value;


    /**
     * constructor for the variable.
     * @param value value to put in the variable.
     * @param name name of the variable.
     * @param modifier modifier for the variable.
     * @throws VariableException if cannot make variable type with the value.
     * @throws NamingException if name is illegal.
     */
    Variable(String value,String name, String modifier) throws VariableException, NamingException{
        setType();
        if (checkIfValueValid(value)){
            CodeBlock.checkIfNameValid(name);
            this.name = name;
            this.modifier = modifier;
            this.value = value;
        } else throw new VariableException(this.type, value);
    }

    /**
     * checks if value supplied is a valid value to this variable.
     * @param stringToCheck value to check.
     * @return true if value is valid, false if invalid.
     */
    public abstract boolean checkIfValueValid(String stringToCheck);

    /**
     * sets the type of this variable.
     */
    abstract void setType();


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
