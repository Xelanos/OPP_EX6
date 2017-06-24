package oop.ex6.code;

import oop.ex6.main.CodeException;
import oop.ex6.variables.Variable;

import java.util.HashSet;
import java.util.LinkedList;


/**
 * A class representing global block.
 */
public class GlobalBlock extends CodeBlock {

    /** vars pending a check */
    private HashSet<String> unknownVars;

    /**
     * constructor initialize data structures.
     */
    public GlobalBlock(){
        closure = new Closure();
        code = new LinkedList<>();
        blocks = new LinkedList<>();
        unknownVars = new HashSet<>();
    }

    /**
     * adds a variable to the unknown variables array, to be check later
     * @param variableName variable name to add.
     */
    public void addToUnknown(String variableName){
        unknownVars.add(variableName);
    }

    public HashSet<String> getUnknownVars(){
        return unknownVars;
    }

    public void makeBlocks(){
        for (Method method : closure.getMethods()){
            blocks.add(method);
        }
    }

    public HashSet<Method> getMethods(){
        return this.closure.getMethods();
    }
}