package com.example.ticketcrud.util;

import java.io.BufferedReader;
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
}
