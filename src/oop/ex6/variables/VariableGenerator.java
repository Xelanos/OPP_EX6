package oop.ex6.variables;


import oop.ex6.code.CodeBlock;
import oop.ex6.code.GlobalBlock;
import oop.ex6.code.Method;
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

            default: throw new CodeException("Unsupported variable type : " +varType);
        }
    }

    public ArrayList<Variable> makeVariablesFromLine(String line, CodeBlock block, GlobalBlock globalBlock) throws CodeException {
        ArrayList<String> varsCommands = RegexWorker.getVarsCommands(RegexWorker.parametersInBrackets(line));
        ArrayList<Variable> variables = new ArrayList<>();
        String type, value, name, modifier;
        Variable variable;
        String prevType = "";
        if (!RegexWorker.isBadTemplate(line)) {
            for (String command : varsCommands) {
                command = command.trim();
                if (command.contains("final")) {
                    modifier = "final";
                    type = RegexWorker.getSecondWord(command);
                    name = RegexWorker.getNameWithEqual(command);
                    value = RegexWorker.getValueAfterEqual(command);
                } else if (command.isEmpty() || command.equals("(")) {
                    continue;
                } else {
                    modifier = null;
                    if (command.contains("=")) {
                        value = RegexWorker.getValueAfterEqual(command);
                        name = RegexWorker.getNameWithEqual(command);

                    } else {
                        value = "";
                        name = RegexWorker.getVarName(command);
                    }
                    type = RegexWorker.getFirstWord(command);
                    if (name.isEmpty() || name.contains(type) || type.contains(name)) {
                        if (!prevType.isEmpty()) {
                            if (!type.contains(name) || name.isEmpty()) {
                                name = type;
                            }
                            type = prevType;
                        }
                    }
                    else if (type.equals(prevType)){
                        throw new CodeException("Same var type in the same row");
                    }
                }
                if (block != null) {
                    if (RegexWorker.isVariableName(value)) {
                        Variable variableInClosure = block.getVariable(value, globalBlock);
                        if (variableInClosure != null) {
                            if (type.equals(variableInClosure.getType())) {
                                String variableInClosureValue = variableInClosure.getValue();
                                if (variableInClosureValue.equals("") && (!isInMethodSignature(variableInClosure.getName(), block))) {
                                    throw new VariableException("Referencing variable " + variableInClosure.getName() +
                                            " without assignment");
                                }
                                value = variableInClosureValue;
                            } else throw new VariableException(name, variableInClosure.getName());
                        }
                    }
                }
                variable = makeVariable(type, value, name, modifier);
                if(block instanceof Method){
                    for (Variable tempVar : ((Method) block).getCallVariables()){
                        variables.add(tempVar);
                    }
                }
                if (isVarExists(variables, variable)) {
                    throw new CodeException("variable " + variable.getName() + " is already exists in the scope");
                } else {
                    variables.add(variable);
                }
                prevType = type;
            }
            return variables;
        }
        else {
            throw new CodeException("Can't assign method to variable.");
        }
    }

    boolean isVarExists(ArrayList<Variable> variables, Variable variable){
        for (Variable var : variables){
            if (var.getName().equals(variable.getName())){
                return true;
            }
        }
        return false;
    }

    boolean isInMethodSignature(String variableName, CodeBlock block){
        if (block instanceof Method) {
            Method method = (Method)(block);
            for (Variable variable : method.getCallVariables()) {
                if (variable.getName().equals(variableName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
