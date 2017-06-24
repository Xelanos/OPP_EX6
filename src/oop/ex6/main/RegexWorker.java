package oop.ex6.main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to handle regex working.
 */

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
    private static final String PARAMETERS_IN_BRACKETS = "(?:\\()(.*?)(?=\\)?)";
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
    private static final String METHOD_NAME = "[\\s\\t]*\\w*[\\s\\t]?+\\((?<=\\()";
    public static final String CALL_STRING = "\\(([^)]+)\\)";
    public static final String METHOD_NAME_CHECK = "^[a-zA-Z]\\w*";


    /**
     * @param line line to read from.
     * @return the first word in the line.
     */
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

    /**
     * @param varDeclaration variable declaration line.
     * @return the variable name withing the declaration.
     */
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

    /**
     * gets rid of redundant spaces in the word.
     * @param word word to clean.
     * @return the clean word.
     */
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

    /**
     * @param line line to check.
     * @return if the line is a method declaration or not.
     */
    static boolean isMethodDeclaration(String line){
        return isGoodByRegex(line, METHOD_DECLARE);
    }

    /**
     * @param startingWord first word of the line.
     * @return if the line is condition declaration or not.
     */
    static boolean isConditionDeclaration(String startingWord){
        String cleanWord = cleanWord(startingWord);
        return cleanWord.equals("if");
    }

    /**
     * @param startingWord first word of the line.
     * @return if the line is a loop declaration or not.
     */
    static boolean isLoopDeclaration(String startingWord){
        String cleanWord = cleanWord(startingWord);
        return cleanWord.equals("while");
    }

    /**
     * @param startingWord first word of the line.
     * @return if the line is a method call or not.
     */
    public static boolean isCallingMethod(String startingWord){
        return isGoodByRegex(startingWord, END_WITH_OPEN_BARKETS);
    }


    /**
     * @param startingWord first word of the line.
     * @return if the line is calling a variable.
     */
    public static boolean isCallingVar(String startingWord){
        return isGoodByRegex(startingWord, END_WITH_EQUAL);
    }



    /**
     * @param startingWord first word of the line.
     * @return if the final modifier exists.
     */
    static boolean isFinal(String startingWord){
        String cleanWord = cleanWord(startingWord);
        return cleanWord.equals("final");
    }



    /**
     * check if the line is a blank or a comment line
     * @param line line to check
     * @return true if the line is a blank or a comment line, false if not.
     * @throws CodeException if the line is a comment line and starts not with //.
     */
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

    public static String getMethodName(String line){
        Pattern pattern = Pattern.compile(METHOD_NAME);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()){
            return cleanWord(matcher.group().trim());
        }
        return "";
    }

    /**
     * @param line line to read.
     * @return if the line is closing a scope or not.
     */
    static boolean isClosingScope(String line){
       return isGoodByRegex(line, SCOPE_CLOSING);
    }

    /**
     * @param line line to read.
     * @return if the line is a return or not.
     */
    public static boolean isReturn(String line){
        return isGoodByRegex(line, RETURN);
    }

    /**
     * @param line line to read
     * @return the second word of the line.
     * @throws CodeException if not declared a var type after final
     */
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

    /**
     * @param line line to read.
     * @return the parameters inside the bracket if found, the line itself if not found.
     */
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

    /**
     * @param line line to read
     * @return an array of the vars commend of the current line.
     */
    public static ArrayList<String> getVarsCommands(String line){
        ArrayList<String> varsCommand = new ArrayList<>();
        Pattern pattern = Pattern.compile(EXPRESSION_IN_BRACKETS);
        Matcher result = pattern.matcher(line);
        while (result.find()){
            varsCommand.add(result.group());
        }
        return varsCommand;
    }

    /**
     * @param line line to read.
     * @return the value which appears after the equal.
     * @throws CodeException if not value appears after the '=' .
     */
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

    /**
     * @param word words to remove spaces from.
     * @return the word without spaces.
     */
    private static String cleanSpace(String word){
        String result = word.replace(" ","");
        return result.trim();
    }

    /**
     * @param value value string to check.
     * @return if the value is a variable name or not.
     */
    public static boolean isVariableName(String value){
       return value.matches(VARIABLE_NAME) && !value.equals("true") && !value.equals("false");
    }

    /**
     * @param line line to check.
     * @return if the line is in a bad template.
     */
    public static boolean isBadTemplate(String line){
        return isGoodByRegex(line, BAD_TEMPLATE);
    }

    public static String getCleanCallString(String line){
        Pattern firstWordPattern = Pattern.compile(CALL_STRING);
        Matcher result = firstWordPattern.matcher(line);
        if(result.find()){
            return result.group(1);
        }
        return "";
    }

    /**
     * general check by regex.
     * @param line line to check.
     * @param regex regex to check line according too
     * @return true if the line matches the regex, false if not.
     */
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
