package oop.ex6.code;


import oop.ex6.main.CodeException;
import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

/**
 * A class representing a method.
 */
public class Method extends CodeBlock {

    /** method name */
    protected String name;
    /** method signature */
    protected String[] signature;
    /** method modifier */
    protected String modifier;
    /** method callVariables */
    protected ArrayList<Variable> callVariables;

    /**
     * constructor for the method object.
     * @param name method name.
     * @param signature method signature.
     * @param modifier method modifier.
     * @throws CodeException thrown when name or modifier are illegal or when vars in signature are not good.
     */
    public Method(String name, String signature, String modifier) throws CodeException {
        closure = new Closure();
        code = new LinkedList<>();
        blocks = new LinkedList<>();
        CodeBlock.checkIfNameValid(name);
        this.name = name;
        if (!Objects.equals(modifier, "void")){
            throw new CodeException("Problem in "+name+" :s-Java can only support void methods");
        }
        this.modifier = modifier;
        VariableGenerator generator = VariableGenerator.getInstance();
        callVariables = generator.makeVariablesFromLine(signature, null, null);
    }

    @Override
    public void blockCheck() throws CodeException {
        checkSignature();
    }

    private void checkSignature() throws CodeException {
        for (String variableDeclaration : signature){
            String[] parts = variableDeclaration.split("\\s+");
            if (parts.length != 2) throw new CodeException("Wrong variable declaration in method "+name);
            String type = parts[0];
            String name = parts[1];
            VariableGenerator generator = VariableGenerator.getInstance();
            Variable variableToAdd = generator.makeVariable(type, null ,name, null);
            closure.addVariable(variableToAdd);
            callVariables.add(variableToAdd);
        }

    }

    public String getName() {
        return name;
    }
    public ArrayList<Variable> getCallVariables(){ return callVariables;}
}
