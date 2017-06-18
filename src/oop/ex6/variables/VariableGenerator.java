package oop.ex6.variables;

import oop.ex6.main.CodeException;

/**
 * A class for generating variables
 */
public class VariableGenerator {
    private enum VariableType {INT, }
    private static VariableGenerator instance = null;

    private VariableGenerator(){} // private const. to restrict new instances

    /**
     * @return the generator only instance.
     */
    public static VariableGenerator getInstance() {
        if (instance == null){
            instance = new VariableGenerator();
        }
        return instance;
    }

    public Variable makeVariable(String varType, String varValue, String varName, String varModifiyer)
            throws CodeException {
        switch (varType){
            case "int": return new Int(varValue,varName,varModifiyer);

            case "boolean": return new Boolean(varValue,varName,varModifiyer);

            case "char": return new Char(varValue,varType,varName);

            case "double": return new Double(varValue, varType,varName);

            case "String": return new VarString(varValue, varType, varName);

            default: throw new CodeException("Bad variable type");
        }

    }
}
