package oop.ex6.code;


import oop.ex6.main.CodeException;
import oop.ex6.variables.VariableGenerator;

/**
 * Created by OrMiz on 12/06/2017.
 */
public class Method extends CodeBlock {

    protected String name;
    protected String[] signaiture;
    protected String modifier;


    @Override
    void blockCheck() throws CodeException {
        checkSignature();
    }

    private void checkSignature() throws CodeException {
        for (String variableDeclaration : signaiture){
            String[] parts = variableDeclaration.split("\\s+");
            if (parts.length != 2) throw new CodeException("Wrong variable declaration in method "+name);
            String type = parts[0];
            String name = parts[1];
            VariableGenerator generator = VariableGenerator.getInstance();
            clousre.addVariable(generator.makeVariable(type, null ,name, null));
        }

    }
}
