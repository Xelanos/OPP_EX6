package oop.ex6.variables;


import oop.ex6.code.ConditionBlock;
import oop.ex6.main.CodeException;

/**
 * A class for boolean variable.
 */
class Boolean extends Variable {

    /**
     * constructor for the variable.
     * @param value value to put in the variable.
     * @param name name of the variable.
     * @param modifier modifier for the variable.
     * @throws CodeException if name is illegal or value doesn't match type.
     */
    Boolean(String value,String name, String modifier) throws CodeException{
            super(value, name, modifier);
    }

    @Override
    public boolean checkIfValueValid(String stringToCheck) {
        boolean result = false;
        if ((stringToCheck.equals("false")) || (stringToCheck.equals("true"))) {
            result = true;
        } else
            result = ConditionBlock.isNum(stringToCheck) || stringToCheck.isEmpty();
        return result;
    }

    @Override
    void setType() {
        this.type = "boolean";
    }
}

