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

    public abstract boolean checkIfValueValid(String stringToCheck);
    abstract void setType();

    private void checkIfNameValid(String variableName) throws VariableException {
        String finalSubString = variableName;
        String isAllWordRegex = "^\\w+";
        if (Character.isDigit(variableName.charAt(0))) {
            throw new VariableNamingExeption(variableName,"Variable name can't start with a number");
        }
        if (variableName.startsWith("_")){
            finalSubString = variableName.substring(1);
            if (finalSubString.length() == 0) {
                throw new VariableNamingExeption(variableName,"Variable name can't be underscore");
            }
        }
        if (!finalSubString.matches(isAllWordRegex)){
            throw new VariableNamingExeption(variableName,
                    "Variable name can only contain numbers, letters and underscore");
        }
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

}
