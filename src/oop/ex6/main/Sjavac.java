package oop.ex6.main;


import oop.ex6.code.GlobalBlock;

import java.io.File;
import java.io.IOException;



public class Sjavac {
    public static void main(String[] args) {
        try {
            File file = new File(args[0]);
            GlobalBlock globalBlock = Parser.processesLines(file);
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

       }System.out.println("0");
    }
}
