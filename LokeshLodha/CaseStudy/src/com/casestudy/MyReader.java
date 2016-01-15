package com.casestudy;

/**
 * Created by lokesh on 12/1/16.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class MyReader {

    public static int readChoice(){
        try {
            return Integer.parseInt(readInput());
        }
        catch (Exception e){
            return 0;
        }
    }

    public static String readInput(){
        InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(ir);
        String str;
        try{
            str =  br.readLine();
            if(str == null)
                str = "0";
        }
        catch (Exception e){
            str = "0";
        }
        return str;
    }
}
