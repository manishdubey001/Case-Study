package com.inin.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by root on 14/1/16.
 */
public class InputReader {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Used for read int from console
     * @return int
     */
    public static int readInt(){
        int userInput = -1;
        try {
            userInput = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Only integer value is allowed");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return userInput;
    }

    /**
     * Used to read String from console
     * @return String
     */
    public static String readString(){
        String userInput = null;
        try {
            userInput = reader.readLine();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return userInput;
    }

    public static void closeReader(){
        try {
            if(reader != null) {
                reader.close();
                reader = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}