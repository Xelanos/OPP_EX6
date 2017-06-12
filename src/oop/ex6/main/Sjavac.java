package oop.ex6.main;

import java.io.File;
import java.io.IOException;

/**
 * Created by or323 on 12/06/2017.
 */
public class Sjavac {
    public static void main(String[] args) {
        try {
            File codeFile = new File(args[0]);
        }
        catch (CodeException e){
            System.out.println("1");
            System.err.println(e.getMessage());
            return;

        }
        catch (IOException e){
            System.out.println("2");
            System.err.println(e.getMessage());
            return;

        }
        System.out.println("0");
    }
}
