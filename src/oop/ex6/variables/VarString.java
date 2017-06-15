package oop.ex6.variables;

import oop.ex6.main.CodeException;

/**
 * Created by OrMiz on 12/06/2017.
 */
public class VarString extends Variable {

    public VarString(String value,String name, String modifier) throws CodeException {
        super(value, name, modifier);
    }
    boolean checkIfValueValid(String stringToCheck) {
        return true;
    }

    void setType() {
        this.type = "string";
    }
}
