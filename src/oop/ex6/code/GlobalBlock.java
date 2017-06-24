package oop.ex6.code;

import oop.ex6.main.CodeException;
import oop.ex6.variables.Variable;

import java.util.HashSet;
import java.util.LinkedList;


/**
 * Created by OrMiz on 12/06/2017.
 */
public class GlobalBlock extends CodeBlock {

    private HashSet<String> unknownVars;
    public GlobalBlock(){
        closure = new Closure();
        code = new LinkedList<>();
        blocks = new LinkedList<>();
        unknownVars = new HashSet<>();
    }

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