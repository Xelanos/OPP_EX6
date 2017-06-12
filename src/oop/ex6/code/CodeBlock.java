package oop.ex6.code;

import oop.ex6.main.CodeException;
import java.util.List;

/**
 * Created by OrMiz on 12/06/2017.
 */
public abstract class CodeBlock {

    protected Closure clousre;
    protected List<String> code;


    abstract void blockCheck() throws CodeException;

}
