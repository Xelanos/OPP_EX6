package oop.ex6.variables;

import oop.ex6.main.CodeException;

/**
 * A class for the char string
 */
public class Char extends Variable {

    /**
     * constructor for the variable.
     * @param value value to put in the variable.
     * @param name name of the variable.
     * @param modifier modifier for the variable.
     * @throws CodeException if name is illegal or value doesn't match type.
     */
    Char(String value,String name, String modifier) throws CodeException{
        super(value, name, modifier);
    }

    @Override
    public boolean checkIfValueValid(String stringToCheck) {
        boolean result = false;
        if (stringToCheck.length() == 3){
            if (stringToCheck.startsWith("'") && stringToCheck.endsWith("'")){
                result = true;
            }
        }
        else if(stringToCheck.isEmpty()){
            result = true;
        }
        return result;
    }


    @Override
    void setType() {
        this.type = "char";
    }
}
