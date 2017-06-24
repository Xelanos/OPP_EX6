package oop.ex6.main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexWorker {

    private static final String COMMENT = "[\\/]{2}";
    private static final String IS_BAD_COMMENT = "[\\/]";
    private static final String BLANK_ROW = "^\\s*$";
    private static final String END_WITH_OPEN_BARKETS = "(.*?)(?<=\\))";
    private static final String END_WITH_EQUAL = "=$";
    private static final String FIRST_WORD =
            "(\\w*)+[(]|^\\s*(\\w*)+[(]|(\\w*)\\s[=]|^\\s*(\\w*)\\s[=]|^\\s*(\\w*)|(\\w*)";
    private static final String SECOND_WORD = "(?:\\W+\\w+){1}(\\S+)";
    public static final String IS_ALL_WORD_REGEX = "^\\w+";
    private static final String SCOPE_CLOSING = "\\w*}|\\s*\\w*}";
    private static final String RETURN = "([\\s\\t]return;)|(return;)";
    private static final String PARAMETERS_IN_BRACKETS = "(?=\\()(.*?)(?=\\))";
    private static final String EXPRESSION_IN_BRACKETS = "[a-zA-Z0-9_=\\-\\s.\"\'\\%]+";
    private static final String VALUE_AFTER_EQUAL = "[^=]+$";
    private static final String CLEAN_ENDING = ".*(?=;)|.*(?=\\()";
    private static final String CLEAN_SPACE =
            "\\w+\\s+\\w+\\s+=+\\s+.|\\w+\\s+\\w+\\s+\\w+\\s+=+\\s+.|\\w+\\s+\\w+\\s+\\w|\\w+\\s+\\w";
    private static final String NAME_WITH_EQUAL = "(\\w+)[\\s\\t]+?(?=[=])[\\s\\t]?|(\\w+)(?=[=])[\\s\\t]?";
    private static final String VAR_NAME = "(?:\\W+\\w+[\\!@#$%^&*]?)+";
    public static final  String METHOD_DECLARE =
            "[\\s\\t]*(\\w+)[\\s\\t]*(\\w+[\\s!@#$%^&*]?[\\w\\%]+)(.*?)(?<=\\))[\\s\\t]*[${][\\s\\t]*";
    public static final String CONDITION_DECLARE = "[\\s\\t]*(\\w+)[\\s\\t]?+(.*?)(?<=\\))";
    public static final String CONDITION_CONTENT = "([\\-]\\d+[\\.]+\\d+)|(\\w+)";
    public static final String VARIABLE_NAME = "^[a-zA-Z_][a-zA-Z_$0-9]*$";
    private static final String BAD_TEMPLATE = "\\w+\\s\\w\\s\\=\\s\\w+\\((.?)*?\\)";

    public static String getFirstWord(String line){
        Pattern firstWordPattern = Pattern.compile(FIRST_WORD);
        Matcher result = firstWordPattern.matcher(line);
        if (result.find()){
            return cleanSpace(cleanWord(result.group(0)));
        }
        else{
            return "Maybe Blank";
        }
    }

    public static String getVarName(String varDeclaration){
        Pattern firstWordPattern = Pattern.compile(VAR_NAME);
        Matcher result = firstWordPattern.matcher(varDeclaration);
        String varName = " ";
        if (result.find()){
            varName = cleanWord(result.group());
            if (result.find()){
                varName = cleanWord(result.group());
            }
        }
        return varName;
    }

    public static String cleanWord(String word){
        String cleanWord;
        Pattern cleanPattern = Pattern.compile(CLEAN_SPACE);
        Matcher result = cleanPattern.matcher(word);
        if (result.find()){
            cleanWord = result.group();
        }
        else{
            cleanWord = word;
        }
         cleanPattern = Pattern.compile(CLEAN_ENDING);
        result = cleanPattern.matcher(cleanWord);
        if(result.find()) {
            return result.group();
        }
        else{
            return cleanWord;
        }

    }

    static boolean isMethodDeclaration(String line){
        return isGoodByRegex(line, METHOD_DECLARE);
    }

    static boolean isConditionDeclaration(String startingWord){
        String cleanWord = cleanWord(startingWord);
        return cleanWord.equals("if");
    }

    static boolean isLoopDeclaration(String startingWord){
        String cleanWord = cleanWord(startingWord);
        return cleanWord.equals("while");
    }

    public static boolean isCallingMethod(String startingWord){
        return isGoodByRegex(startingWord, END_WITH_OPEN_BARKETS);
    }


    public static boolean isCallingVar(String startingWord){
        return isGoodByRegex(startingWord, END_WITH_EQUAL);
    }

    static boolean isFinal(String startingWord){
        String cleanWord = cleanWord(startingWord);
        return cleanWord.equals("final");
    }

    static boolean isCommentOrBlank(String line) throws CodeException{
        boolean result;
            if(isGoodByRegex(line, IS_BAD_COMMENT)){
                if(isGoodByRegex(line, COMMENT)){
                    result = true;
                }
                else{
                    throw new CodeException("Bad Comment Line");
                }
            }
        else{
                result = isGoodByRegex(line, BLANK_ROW);
        }
        return result;
    }


    static boolean isClosingScope(String line){
       return isGoodByRegex(line, SCOPE_CLOSING);
    }

    public static boolean isReturn(String line){
        return isGoodByRegex(line, RETURN);
    }

    public static String getSecondWord(String line) throws CodeException {
        Pattern firstWordPattern = Pattern.compile(SECOND_WORD);
        Matcher result = firstWordPattern.matcher(line);
        if (result.find()){
            return cleanSpace(cleanWord(result.group(0)));
        }
        else{
            throw new CodeException("After Final comes var type");
        }
    }

    public static String parametersInBrackets(String line){
        Pattern firstWordPattern = Pattern.compile(PARAMETERS_IN_BRACKETS);
        Matcher result = firstWordPattern.matcher(line);
        if (result.find()){
            return result.group(0);
        }
        else{
            return line;
        }
    }

    public static ArrayList<String> getVarsCommands(String line){
        ArrayList<String> varsCommand = new ArrayList<>();
        Pattern pattern = Pattern.compile(EXPRESSION_IN_BRACKETS);
        Matcher result = pattern.matcher(line);
        while (result.find()){
            varsCommand.add(result.group());
        }
        return varsCommand;
    }

    public static String getValueAfterEqual(String line) throws CodeException{
        Pattern firstWordPattern = Pattern.compile(VALUE_AFTER_EQUAL);
        Matcher result = firstWordPattern.matcher(line);
        if (result.find()){
            return cleanWord(result.group(0).trim());
        }
        else{
            throw new CodeException("NO Value after Equal");
        }
    }

    public static String getNameWithEqual(String line) throws CodeException{
        Pattern firstWordPattern = Pattern.compile(NAME_WITH_EQUAL);
        Matcher result = firstWordPattern.matcher(line);
        if (result.find()){
            return cleanSpace(cleanWord(result.group(0)));
        }
        else{
            throw new CodeException("no value when have final");
        }
    }

    private static String cleanSpace(String word){
        String result = word.replace(" ","");
        return result.trim();
    }

    public static boolean isVariableName(String value){
       return value.matches(VARIABLE_NAME) && !value.equals("true") && !value.equals("false");
    }

    public static boolean isBadTemplate(String line){
        return isGoodByRegex(line, BAD_TEMPLATE);
    }

    static boolean isGoodByRegex(String line, String regex){
        boolean result = false;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()){
            result = true;
        }
        return result;
    }
}
