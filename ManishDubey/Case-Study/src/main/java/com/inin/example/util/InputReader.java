package com.inin.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by root on 14/1/16.
 */
public class InputReader {

    public static BufferedReader reader = null;

    /**
     * Return the reader to read data from command line
     * @return BufferedReader
     */
    public static BufferedReader getReader()
    {
        if(reader == null)
            reader = new BufferedReader(new InputStreamReader(System.in));
        return reader;
    }

    /**
     * Used for read int from console
     * @return int
     */
    public static int readInt(){
        int userInput = -1;
        try {
            BufferedReader reader = InputReader.getReader();
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
            BufferedReader reader = InputReader.getReader();
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