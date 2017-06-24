package oop.ex6.variables;

import oop.ex6.main.CodeException;

/**
 * A class for the double variable.
 */
class Double extends Variable {

    /**
     * constructor for the variable.
     * @param value value to put in the variable.
     * @param name name of the variable.
     * @param modifier modifier for the variable.
     * @throws CodeException if name is illegal or value doesn't match type.
     */
    Double(String value,String name, String modifier) throws CodeException{
        super(value, name, modifier);
    }

    @Override
    public boolean checkIfValueValid(String stringToCheck) {
        boolean result = true;
        try{
            java.lang.Double.parseDouble(stringToCheck);
        }
        catch (NumberFormatException badNum){
            result = stringToCheck.isEmpty();
        }
        return result;
    }

    @Override
    void setType() {
        this.type = "double";
    }
}
