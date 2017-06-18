package oop.ex6.variables;


import oop.ex6.main.CodeException;

/**
 * Created by OrMiz on 12/06/2017.
 */
class Boolean extends Variable {

    Boolean(String value,String name, String modifier) throws CodeException{
            super(value, name, modifier);
    }

    public boolean checkIfValueValid(String stringToCheck) {
        boolean result = false;
        if ((stringToCheck.equals("false")) || (stringToCheck.equals("true"))) {
            result = true;
        }
        return result;
    }

    void setType() {
        this.type = "boolean";
    }
}

