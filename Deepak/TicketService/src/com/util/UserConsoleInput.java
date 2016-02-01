package com.util;

import com.customexceptions.UserInputException;
import com.services.TicketOperations;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by root on 15/1/16.
 */
public class UserConsoleInput {
// Lokesh: There can be single function for All string value read from Console. I don't see any difference in function for reading agent name, subject and even tag(other than converting to HashSet).
    public static Scanner scanner = new Scanner(System.in);

    /*
    * To get the number from user from console */
    public static int acceptNumber(){
        boolean loop = true;
        int inputNumber = 0;
        String inputString = "";
        do {
            try {
                inputString = scanner.nextLine();
                inputNumber = Integer.parseInt(inputString);
                loop = false;
            } catch (NumberFormatException nfe) {
                System.out.println(inputString+ " This is not valid input! Please enter number only");
            }
        }while(loop);

        return inputNumber;
    }


    /*
    * To get the String from user from console */
    public static String acceptString() throws UserInputException {
        String inputString = scanner.nextLine();
        inputString = inputString.trim();
        if(inputString == null || inputString.equals("")){
            throw new UserInputException("Please give some proper input");
        }

        return inputString;
    }


    /*
    * to get the subject */

    public static String getSubject(){
        boolean subjectLoop1 = true;
        String subject = "";
        while (subjectLoop1) {
            System.out.println("Please Enter Subject");
            try {
                subject = UserConsoleInput.acceptString();
                subjectLoop1 = false;
            } catch (UserInputException e) {
                System.out.println(e.getMessage()+" for subject!");
            }
        }
        return subject;
    }


    /*
    * To get name of agent*/
    public static String getAgentName(){
        boolean agentLoop = true;
        String agentName = "";
        while (agentLoop) {
            System.out.println("Please Enter Agent Name");
            try {
                agentName = UserConsoleInput.acceptString();
                agentLoop = false;
            } catch (UserInputException e) {
                //e.printStackTrace();
                System.out.println(e.getMessage()+" for Agent Name!");
            }
        }

        return agentName;
    }


    /*
    * To get multiple tag names, separated by Comma*/
    public static Set<String> getTagNames(){
        System.out.println("Please Enter tags separated by Comma");
        Set<String> tagHashSet = new HashSet<>();
        try {
            String[] tagNames = UserConsoleInput.acceptString().split(",");

            TicketOperations ticketOperations = new TicketOperations();
            for(String tag : tagNames){
                tag = tag.trim();
                if(!(tag.equals(""))){
                    ticketOperations.allTagHashSet.add(tag);
                    tagHashSet.add(tag);
                }
            }

        }catch (UserInputException e) {
            System.out.println("Okay you don't want any tag, it's fine!");
        }

        return tagHashSet;
    }
// Lokesh: File create/check in UserConsoleInput?
    public static File createFile(){
        File file = null;
        try {
            file = new File("src/com/resources/tickets.ser");
            if(!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
