package oop.ex6.variables;

import oop.ex6.main.CodeException;

/**
 * Created by OrMiz on 12/06/2017.
 */
public class Char extends Variable {

    public Char(String value,String name, String modifier) throws CodeException{
        super(value, name, modifier);
    }
    boolean checkIfValueValid(String stringToCheck) {
        boolean result = false;
        if (stringToCheck.length() == 1){
            result = true;
        }
        return result;
    }


    void setType() {
        this.type = "char";
    }
}