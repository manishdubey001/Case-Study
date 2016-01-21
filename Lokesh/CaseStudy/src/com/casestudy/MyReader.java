package com.casestudy;

/**
 * Created by lokesh on 12/1/16.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class MyReader {

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
        InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(ir);
        String str;
        try{
            str =  br.readLine();
            if(str == null || str.length() == 0)
            {
                // Sentinel values like this can be convenient, but are you sure no one would ever want to use
                // "0" as a subject or tag? If you can, you should try to choose an impossible value (for example
                // a control character might be a better choice here).

                // Update: I prefer to use Sentinel values specifically in the kind of application based on Console. I would have a different approach in other cases.
                // Update: I do agree to use a impossible value rather than a common char/string. Changed to a control key
                str = "\u0002";
            }
        }
        catch (Exception e){
            str = "\u0002";
        }
        return str;
    }
}
