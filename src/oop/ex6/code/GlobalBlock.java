package oop.ex6.code;

import oop.ex6.main.CodeException;

import java.util.LinkedList;


/**
 * Created by OrMiz on 12/06/2017.
 */
public class GlobalBlock extends CodeBlock {

    public GlobalBlock(){
        closure = new Closure();
        code = new LinkedList<>();
        blocks = new LinkedList<>();
    }
    @Override
    void blockCheck() throws CodeException {

    }
}