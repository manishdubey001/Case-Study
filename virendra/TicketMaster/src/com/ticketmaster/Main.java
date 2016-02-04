package com.ticketmaster;

import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.utils.AppUtil;

import java.io.IOException;

/**
 * Main class
 * Used to run the new instance of application
 * This application contains the latest modified code with
 * different set of collection classes (HashMap,LinkedHashMap,TreeMap)
 */
public class Main {

    public static int collectionChoice = 1;
    public static int fileChoice = 2;

    public static void main(String[] args) throws ClassNotFoundException,TicketNotFoundException, IOException {

        System.out.println("Initializing program...");
        long s = AppUtil.logTime("start",true, 0);
        //initialization of program
        AppUtil.initializeApp();

        AppUtil.logTime("end",false, s);

        try{
            AppRunner.app().start();
        }catch (TicketNotFoundException | IOException   e){
            System.out.println("Exception occurred in application.");
            System.out.println(e.getMessage());
            System.out.println("Closing Application. Thank you.");
        }

    }
}