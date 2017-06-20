package oop.ex6.code;

import oop.ex6.main.CodeException;
import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableException;

import java.util.List;

/**
 * Created by OrMiz on 12/06/2017.
 */
public abstract class CodeBlock {

    protected Closure closure;
    protected List<String> code;


    abstract void blockCheck() throws CodeException;

    protected void methoedCallCheck(String methodName,String callString) throws CodeException {
        Method method = closure.getMethod(methodName);
        String[] variables = callString.split(",");
        if (variables.length != method.callVariables.size()){
            throw new CodeException("Error in calling "+methodName+" with "+variables.length+" parameters");
        }
        for (int i=0; i<variables.length; i++){
            String valueToCheck = variables[i];
            Variable varToCheck = method.callVariables.get(i);
            if (!varToCheck.checkIfValueValid(valueToCheck)){
                throw new CodeException("Error in method "+methodName+" parameter "+i+".\n"+
                        "Expected"+varToCheck.getType());
            }
        }
    }

    public void addLineToCode(String line){
        code.add(line);
    }

}
