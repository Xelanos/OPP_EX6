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

    static String getFirstWord(String line) throws CodeException{
        Pattern firstWordPattern = Pattern.compile(FIRST_WORD);
        Matcher result = firstWordPattern.matcher(line);
        if (result.find()){
            return result.group(1);
        }
        else{
            throw new CodeException("Bad Line Starter");
        }
    }

    static String cleanWord(String word){
        Pattern cleanPattern = Pattern.compile("[\\w]+");
        Matcher result = cleanPattern.matcher(word);
        return result.group(1);
    }
}
