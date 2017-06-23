package oop.ex6.code;

import oop.ex6.main.CodeException;
import oop.ex6.main.RegexWorker;
import oop.ex6.variables.Variable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConditionBlock extends CodeBlock {

    public ConditionBlock(String Condition, HashSet<Variable> oldVars) throws CodeException{
        closure = new Closure();
        code = new LinkedList<>();
        blocks = new LinkedList<>();
        for(Variable var : oldVars){
            this.closure.addVariable(var);
        }
        Pattern pattern = Pattern.compile(RegexWorker.CONDITION_CONTENT);
        Matcher matcher = pattern.matcher(Condition);
        while (matcher.find()){
            if (isNum(matcher.group())){
            }
            else if((matcher.group().equals("true") || matcher.group().equals("false")
                    || RegexWorker.isVariableName(matcher.group()))){
                if(RegexWorker.isVariableName(matcher.group())){
                    if(!containVar(matcher.group())){
                        throw new CodeException("Illegal condition ");
                    }
                }
            }
            else {
                if (this.containVar(matcher.group())){
                    throw new CodeException("Var not Declared");
                }
            }
        }
    }

    @Override
    void blockCheck() throws CodeException {

    }

    public static boolean isNum(String line){
        boolean result = true;
        try{
            Integer.parseInt(line);
        }
        catch (NumberFormatException badNum){
            try{
                Double.parseDouble(line);
            }
            catch (NumberFormatException bad){
                result = false;
            }
        }
        return result;
    }
}
