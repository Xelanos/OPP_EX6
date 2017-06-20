package oop.ex6.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yanir on 19/06/2017.
 */
public class RegexWorker {

    public static final String COMMENT = "^[/]+[/]";
    public static final String BLANK_ROW = "^\\s*$";
    public static final String END_WITH_OPEN_BARKETS = "[(]$";
    public static final String END_WITH_EQUAL = "=$";
    public static final String FIRST_WORD =
            "(\\w*)+[(]|^\\s*(\\w*)+[(]|(\\w*)\\s[=]|^\\s*(\\w*)\\s[=]|^\\s*(\\w*)|(\\w*)";
    public static final String IS_ALL_WORD_REGEX = "^\\w+";
    public static final String SCOPE_CLOSING = "\\w*}|\\s*\\w*}";

    static String getFirstWord(String line) throws CodeException{
        Pattern firstWordPattern = Pattern.compile(FIRST_WORD);
        Matcher result = firstWordPattern.matcher(line);
        if (result.find()){
            return result.group(1);
        }
        else{
            return "Maybe Blank";
        }
    }

    static String cleanWord(String word){
        Pattern cleanPattern = Pattern.compile("[\\w]+");
        Matcher result = cleanPattern.matcher(word);
        return result.group(1);
    }

    static boolean isMethodDeclaration(String startingWord){
        String cleanWord = RegexWorker.cleanWord(startingWord);
        return cleanWord.equals("void");
    }

    static boolean isConditionDeclaration(String startingWord){
        String cleanWord = RegexWorker.cleanWord(startingWord);
        return cleanWord.equals("if");
    }

    static boolean isLoopDeclaration(String startingWord){
        String cleanWord = RegexWorker.cleanWord(startingWord);
        return cleanWord.equals("while");
    }

    static boolean isCallingMethod(String startingWord){
        String cleanWord = RegexWorker.cleanWord(startingWord);
        return cleanWord.matches(RegexWorker.END_WITH_OPEN_BARKETS);
    }

    static boolean isCallingVar(String startingWord){
        String cleanWord = RegexWorker.cleanWord(startingWord);
        return cleanWord.matches(RegexWorker.END_WITH_EQUAL);
    }

    static boolean isCommentOrBlank(String line){
        return ((line.matches(RegexWorker.COMMENT) || line.matches(RegexWorker.BLANK_ROW)));
    }

    static boolean isClosingScope(String line){
        return ((line.matches(RegexWorker.SCOPE_CLOSING)));
    }
}
