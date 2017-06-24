package oop.ex6.code;

import oop.ex6.main.CodeException;

/**
 * A class for exceptions from bad names.
 */
public class NamingException extends CodeException {

    NamingException(String name, String message) {
        super("Error in "+name+": "+message);
    }
}
