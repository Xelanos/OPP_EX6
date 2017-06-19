package oop.ex6.main;

import java.io.*;

/**
 * Created by OrMiz on 12/06/2017.
 */
public class Parser {

    void processesLines(File sJavaFile) throws CodeException{
        try(BufferedReader lineReader = new BufferedReader(new FileReader(sJavaFile.getPath()))) {

        }
        catch (IOException badFile){
            throw new CodeException("Bad File");
        }
    }

}
