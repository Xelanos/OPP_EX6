package oop.ex6.code;

import oop.ex6.main.CodeException;

/**
 * Created by or323 on 18/06/2017.
 */
public class NamingException extends CodeException {

    NamingException(String name, String message) {
        super("Error in "+name+": "+message);
    }
}
