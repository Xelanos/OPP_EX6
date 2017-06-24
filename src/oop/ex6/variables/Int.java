package oop.ex6.variables;

import oop.ex6.main.CodeException;
import oop.ex6.main.RegexWorker;

/**
 * A class for int variable
 */
class Int extends Variable {

    /**
     * constructor for the variable.
     * @param value value to put in the variable.
     * @param name name of the variable.
     * @param modifier modifier for the variable.
     * @throws CodeException if name is illegal or value doesn't match type.
     */
    Int(String value,String name, String modifier) throws CodeException {
        super(value, name, modifier);
    }

    @Override
    public boolean checkIfValueValid(String stringToCheck) {
        boolean result = true;
        try{
            java.lang.Integer.parseInt(stringToCheck);
        }
        catch (NumberFormatException badNum) {
            result = stringToCheck.isEmpty() || RegexWorker.isVariableName(stringToCheck);
        }
        return result;
    }

    @Override
    void setType() {
        this.type = "int";
    }
}

