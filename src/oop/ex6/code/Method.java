package oop.ex6.code;


import oop.ex6.main.CodeException;
import oop.ex6.main.RegexWorker;
import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
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
        closure = new Closure();
        code = new LinkedList<>();
        blocks = new LinkedList<>();
        checkName(name);
        this.name = name;
        if (!Objects.equals(modifier, "void")){
            throw new CodeException("Problem in "+name+" :s-Java can only support void methods");
        }
        this.modifier = modifier;
        VariableGenerator generator = VariableGenerator.getInstance();
        callVariables = generator.makeVariablesFromLine(RegexWorker.getCleanCallString(signature), null, null);
    }

    @Override
    public void blockCheck() throws CodeException {
        checkSignature();
        checkIfHasReturn();
    }

    /**
     * checks if the method has a return line.
     * @throws CodeException if method has no return.
     */
    private void checkIfHasReturn() throws CodeException {
        boolean hasReturn = false;
        String lastLine = code.getLast();
        if (lastLine.equals("return;")) hasReturn = true;
        if (!hasReturn) throw new CodeException("Method "+getName()+" has no return at the end");
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

    /**
     * check if the name of the method is valid
     * @param name method name
     * @throws NamingException if the name is invalid.
     */
    void checkName(String name) throws NamingException{
        if (!name.matches(RegexWorker.METHOD_NAME_CHECK)){
            throw new NamingException(name, "Illegal method name");
        }
    }
}
