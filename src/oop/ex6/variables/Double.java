package oop.ex6.variables;

import oop.ex6.main.CodeException;

/**
 * Created by OrMiz on 12/06/2017.
 */
class Double extends Variable {

    Double(String value,String name, String modifier) throws CodeException{
        super(value, name, modifier);
    }
    public boolean checkIfValueValid(String stringToCheck) {
        boolean result = true;
        try{
            java.lang.Double.parseDouble(stringToCheck);
        }
        catch (NumberFormatException badNum){
            result = false;
        }
        return result;
    }

    void setType() {
        this.type = "double";
    }
}
