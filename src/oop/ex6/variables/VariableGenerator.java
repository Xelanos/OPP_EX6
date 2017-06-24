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

    /**
     * private const. to restrict new instances
     */
    private VariableGenerator(){}

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

    /**
     * makes variables from a variables related line.
     * works with every line that has variables such as : declaration , assignment, method declaration.
     * @param line line to analyze variables from
     * @param block if trying to add variables in a specific block, can be null if not.
     * @param globalBlock global block of the current program.
     * @return an array of variables to be added.
     * @throws CodeException thrown when making a variable from the line is illegal.
     */
    public ArrayList<Variable> makeVariablesFromLine(String line, CodeBlock block, GlobalBlock globalBlock)
            throws CodeException {
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
                        name = name.trim();
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
                                if (variableInClosureValue.equals("") &&
                                        (!isInMethodSignature(variableInClosure.getName(), block))) {
                                    throw new VariableException("Referencing variable " +
                                            variableInClosure.getName() + " without assignment");
                                }
                                value = variableInClosureValue;
                            } else throw new VariableException(name, variableInClosure.getName());
                        }
                    }
                }
                variable = makeVariable(type, value, name, modifier);
                if (isVarExists(variables, variable, block)) {
                    throw new CodeException("variable " + variable.getName() +
                            " is already exists in the scope");
                }
                else if(isGlobalVar(variable, globalBlock)) {
                    if (!line.contains("=")){
                        variables.add(variable);
                    }
                }
                 else {
                    if(line.contains("=")){
                        if(globalBlock.containVar(variable.getValue())){
                            Variable temp = globalBlock.getVariable(variable.getValue(), globalBlock);
                            if(!temp.getValue().isEmpty()){
                                variables.add(variable);
                            }
                            else{
                                throw new CodeException("Global var has no value");
                            }
                        }
                        else {
                            variables.add(variable);
                        }
                    }
                    else {
                        variables.add(variable);
                    }
                }
                prevType = type;
            }
            return variables;
        }
        else {
            throw new CodeException("Can't assign method to variable.");
        }
    }

    /**
     * check if variable already exists in the current scope
     * @param variables an array of variables of the current line.
     * @param variable variable to check if found.
     * @param block block in which the variable is added. can be null if block is not opened yet.
     * @return true if the variable is in the block, false if not.
     */
    boolean isVarExists(ArrayList<Variable> variables, Variable variable, CodeBlock block) {
        for (Variable var : variables) { // checking Vars from the current line
            if (var.getName().equals(variable.getName())) {
                return true;
            }
        }
        if (block instanceof Method) { // in case the block is method, checking the signature parameters
            for (Variable var : ((Method) block).getCallVariables()) {
                if (var.getName().equals(variable.getName())) {
                    return true;
                }
            }
        }
        // checking local scope
        return block != null && block.containVar(variable.getName());
    }

    /**
     * check if the variable is in the global block
     * @param variable variable to check for
     * @param globalBlock global block of the program.
     * @return true if the variable is in global, false if not.
     */
    boolean isGlobalVar(Variable variable, GlobalBlock globalBlock) {
        return globalBlock != null && globalBlock.containVar(variable.getName());
    }


    /**
     * check if variable is in the method signature.
     * @param variableName name of the variable to check.
     * @param block method (preferably) to check in.
     * @return true if variable is in the method signature.
     */
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
