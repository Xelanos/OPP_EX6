package oop.ex6.code;


import oop.ex6.main.CodeException;
import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableGenerator;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by OrMiz on 12/06/2017.
 */
public class Method extends CodeBlock {

    protected String name;
    protected String[] signature;
    protected String modifier;
    protected ArrayList<Variable> callVariables;

    public Method(String name, String signature, String modifier) throws CodeException {
        CodeBlock.checkIfNameValid(name);
        this.name = name;
        if (!Objects.equals(modifier, "void")){
            throw new CodeException(name+" :s-Java can only support void methods");
        }
        this.modifier = modifier;
        VariableGenerator generator = VariableGenerator.getInstance();
        callVariables = generator.makeVariablesFromLine(signature);

    }

    @Override
    void blockCheck() throws CodeException {
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
}
