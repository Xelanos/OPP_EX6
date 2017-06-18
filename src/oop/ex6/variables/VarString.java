package oop.ex6.variables;

import oop.ex6.main.CodeException;

/**
 * Created by OrMiz on 12/06/2017.
 */
class VarString extends Variable {

    VarString(String value,String name, String modifier) throws CodeException {
        super(value, name, modifier);
    }
    public boolean checkIfValueValid(String stringToCheck) {
        boolean result = false;
        if (stringToCheck.startsWith("\"") && stringToCheck.endsWith("\"")){
            result = true;
        }
        return result;
    }

    void setType() {
        this.type = "string";
    }
}
