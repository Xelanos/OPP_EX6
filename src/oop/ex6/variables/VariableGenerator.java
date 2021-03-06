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
    private static final int VAR_MODIFIER = 0;
    private static final int VAR_TYPE = 1;
    private static final int VAR_NAME = 2;
    private static final int VAR_VALUE = 3;
    private static final int VAR_PREV_TYPE = 4;
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
        String[] varParameters;
        Variable variable;
        String prevType = "";
        if (!RegexWorker.isBadTemplate(line)) {
            for (String command : varsCommands) {
                varParameters = getVarParameters(command, prevType, block, globalBlock);
                if(varParameters != null) {
                    if (!varParameters[VAR_MODIFIER].isEmpty()) {
                        modifier = varParameters[VAR_MODIFIER];
                    } else {
                        modifier = null;
                    }
                    type = varParameters[VAR_TYPE];
                    name = varParameters[VAR_NAME];
                    value = varParameters[VAR_VALUE];
                }
                else {
                    continue;
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
                if (isVarExists(variables, variable, block, globalBlock)) {
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
    boolean isVarExists(ArrayList<Variable> variables, Variable variable, CodeBlock block, GlobalBlock globalBlock) {
        if(globalBlock == null || block == null) {
            for (Variable var : variables) { // checking Vars from the current line
                if (var.getName().equals(variable.getName())) {
                        if (var.getName().equals(variable.getName())) {
                            return true;
                        }
                }
            }
        }
        else{
            for (Variable var : block.getVars()) { // checking Vars from the current line
                if (var.getName().equals(variable.getName())) {
                    for (Variable globalVar : globalBlock.getVars()) {
                        if (globalVar.getName().equals(variable.getName())) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
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
     * Gets a command, checks if all the conditions are good and set the var's parameters
     * @param command - the command line to check.
     * @param block - the block we make the var at.
     * @param globalBlock - the global scope
     * @return array of strings with the parameters
     * @throws CodeException in case on of the values isn't good
     */
    private String[] getVarParameters(String command, String prevType, CodeBlock block,
                                      GlobalBlock globalBlock) throws CodeException{
        String type, value, name, modifier;
        String[] varParameters = new String[4];
        command = command.trim();
        if (command.contains("final")) {    // if has modifier
            modifier = "final";
            type = RegexWorker.getSecondWord(command);
            name = RegexWorker.getNameWithEqual(command);
            value = RegexWorker.getValueAfterEqual(command);
        } else if (command.isEmpty() || command.equals("(")) { // if nothing at the command
            return null;
        } else {
            modifier = "";
            if (command.contains("=")) { // if there is an assignment in the command
                value = RegexWorker.getValueAfterEqual(command);
                name = RegexWorker.getNameWithEqual(command);

            } else { // regular var definition
                value = "";
                name = RegexWorker.getVarName(command);
                name = name.trim();
            }
            type = RegexWorker.getFirstWord(command);
            if (name.isEmpty() || name.contains(type) || type.contains(name)) {
                if (!prevType.isEmpty()) { // fixing in case of regex mess up
                    if (!type.contains(name) || name.isEmpty()) {
                        name = type;
                    }
                    type = prevType;
                }
            }
            else if (type.equals(prevType) && block != null && globalBlock != null){
                throw new CodeException("Same var type in the same row");
            }
        }
        varParameters[VAR_MODIFIER] = modifier;
        varParameters[VAR_TYPE] = type;
        varParameters[VAR_NAME] = name;
        varParameters[VAR_VALUE] = value;
        return varParameters;
    }
    /**
     * check if variable is in the method signature.
     * @param variableName name of the variable to check.
     * @param block method (preferably) to check in.
     * @return true if variable is in the method signature. false if block is not method or if not found.
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
