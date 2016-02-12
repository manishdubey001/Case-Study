package com.casestudy;

/**
 * Created by lokesh on 12/1/16.
 * This is Reader class providing function to read String and integer values from Console
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class InputReader {

    public static int readChoice(String str){
        try {
            return Integer.parseInt(readInput(str));
        }
        catch (Exception e){
            return 0;
        }
    }

    public static String readInput(String string){
        System.out.println(string);
        String str;

        try{
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            str =  bufferedReader.readLine();
            if(str == null || str.length() == 0)
            {
                str = "\u0002";
                bufferedReader.close();
                inputStreamReader.close();
            }
        }
        catch (Exception e){
            str = "\u0002";
        }
        return str;
    }
}