package oop.ex6.variables;

import com.sun.org.apache.bcel.internal.classfile.Code;
import oop.ex6.main.CodeException;
import oop.ex6.main.RegexWorker;

import java.util.ArrayList;

/**
 * A class for generating variables
 */
public class VariableGenerator {
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

    /**
     * @param varType variable desired type.
     * @param varValue variable desired value.
     * @param varName variable name.
     * @param varModifier variable modifier.
     * @return the desired variable.
     * @throws CodeException thrown when type is not supported or value doesn't matches type.
     */
    public Variable makeVariable(String varType, String varValue, String varName, String varModifier)
            throws CodeException {
        switch (varType){
            case "int": return new Int(varValue,varName,varModifier);

            case "boolean": return new Boolean(varValue,varName,varModifier);

            case "char": return new Char(varValue,varName,varModifier);

            case "double": return new Double(varValue,varName,varModifier);

            case "String": return new VarString(varValue,varName,varModifier);

            default: throw new CodeException("Unsupported variable type");
        }
    }

    public ArrayList<Variable> makeVariablesFromLine(String line) throws CodeException{
        ArrayList<String> varsCommands = RegexWorker.getVarsCommands(RegexWorker.parametersInBrackets(line));
        ArrayList<Variable> variables = new ArrayList<>();
        String type, value, name, modifier;
        Variable variable;
        for (String command : varsCommands){
            value = null;
            name = null;
            type = RegexWorker.getFirstWord(command);

            name = RegexWorker.getVarName(command);

        }
        return variables;
    }
}
