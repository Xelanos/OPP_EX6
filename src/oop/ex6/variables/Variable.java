package oop.ex6.variables;

import oop.ex6.code.CodeBlock;
import oop.ex6.code.NamingException;

/**
 * Created by OrMiz on 12/06/2017.
 */
public abstract class Variable {

    protected String type;
    protected String name;
    protected String modifier;


    Variable(String value,String name, String modifier) throws VariableException, NamingException{
        setType();
        if (checkIfValueValid(value)){
            CodeBlock.checkIfNameValid(name);
            this.name = name;
            this.modifier = modifier;
        } else throw new VariableException(this.type, value);
    }

    public abstract boolean checkIfValueValid(String stringToCheck);
    abstract void setType();


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

}
