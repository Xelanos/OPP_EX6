package oop.ex6.main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexWorker {

    private static final String COMMENT = "^[/]+[/]";
    private static final String BLANK_ROW = "^\\s*$";
    private static final String END_WITH_OPEN_BARKETS = "[(]$";
    private static final String END_WITH_EQUAL = "=$";
    private static final String FIRST_WORD =
            "(\\w*)+[(]|^\\s*(\\w*)+[(]|(\\w*)\\s[=]|^\\s*(\\w*)\\s[=]|^\\s*(\\w*)|(\\w*)";
    private static final String SECOND_WORD = "(?:\\W+\\w+){1}(\\S+)";
    public static final String IS_ALL_WORD_REGEX = "^\\w+";
    private static final String SCOPE_CLOSING = "\\w*}|\\s*\\w*}";
    private static final String RETURN = "(return;)";
    private static final String PARAMETERS_IN_BRACKETS = "(?=\\()(.*?)(?=\\))";
    private static final String EXPRESSION_IN_BRACKETS = "\\w[^,]+";
    private static final String VALUE_AFTER_EQUAL = "[^=\\s]+$";


    public static String getFirstWord(String line){
        Pattern firstWordPattern = Pattern.compile(FIRST_WORD);
        Matcher result = firstWordPattern.matcher(line);
        if (result.find()){
            return result.group(1);
        }
        else{
            return "Maybe Blank";
        }
    }

    public static String getVarName(String varDeclaration){
        Pattern firstWordPattern = Pattern.compile(IS_ALL_WORD_REGEX);
        Matcher result = firstWordPattern.matcher(varDeclaration);
        String varName = " ";
        if (result.find()){
            varName = result.group(1);
        }
        return varName;
    }

    private static String cleanWord(String word){
        Pattern cleanPattern = Pattern.compile("[\\w]+");
        Matcher result = cleanPattern.matcher(word);
        return result.group(1);
    }

    static boolean isMethodDeclaration(String startingWord){
        String cleanWord = cleanWord(startingWord);
        return cleanWord.equals("void");
    }

    static boolean isConditionDeclaration(String startingWord){
        String cleanWord = cleanWord(startingWord);
        return cleanWord.equals("if");
    }

    static boolean isLoopDeclaration(String startingWord){
        String cleanWord = cleanWord(startingWord);
        return cleanWord.equals("while");
    }

    static boolean isCallingMethod(String startingWord){
        String cleanWord = cleanWord(startingWord);
        return cleanWord.matches(END_WITH_OPEN_BARKETS);
    }

    static boolean isCallingVar(String startingWord){
        String cleanWord = cleanWord(startingWord);
        return cleanWord.matches(END_WITH_EQUAL);
    }
    static boolean isFinal(String startingWord){
        String cleanWord = cleanWord(startingWord);
        return cleanWord.equals("final");
    }
    static boolean isCommentOrBlank(String line){
        return ((line.matches(COMMENT) || line.matches(BLANK_ROW)));
    }

    static boolean isClosingScope(String line){
        return ((line.matches(SCOPE_CLOSING)));
    }

    static boolean isReturn(String line){
        return line.matches(RETURN);
    }

    static String getSecondWord(String line) throws CodeException {
        Pattern firstWordPattern = Pattern.compile(SECOND_WORD);
        Matcher result = firstWordPattern.matcher(line);
        if (result.find()){
            return result.group(1);
        }
        else{
            throw new CodeException("After Final comes var type");
        }
    }

    public static String parametersInBrackets(String line){
        Pattern firstWordPattern = Pattern.compile(PARAMETERS_IN_BRACKETS);
        Matcher result = firstWordPattern.matcher(line);
        if (result.find()){
            return result.group(1);
        }
        return " ";
    }

    public static ArrayList<String> getVarsCommands(String line){
        ArrayList<String> varsCommand = new ArrayList<>();
        Pattern firstWordPattern = Pattern.compile(EXPRESSION_IN_BRACKETS);
        Matcher result = firstWordPattern.matcher(line);
        if (result.find()){
            for (int i = 1; i < result.groupCount(); i++){
                varsCommand.add(result.group(i));
            }
        }
        return varsCommand;
    }

    public static String getValueAfterEqual(String line) throws CodeException{
        Pattern firstWordPattern = Pattern.compile(VALUE_AFTER_EQUAL);
        Matcher result = firstWordPattern.matcher(line);
        if (result.find()){
            return result.group(1);
        }
        else{
            throw new CodeException("NO Value after Equal");
        }
    }
}
