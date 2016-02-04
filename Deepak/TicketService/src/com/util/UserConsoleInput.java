package com.util;

import com.customexceptions.UserInputException;
import com.services.TicketOperations;

import java.awt.*;
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
    public static String acceptString(String message){
        String inputString = null;
        boolean loop = true;
        while (loop){
            System.out.println(message);
            inputString = scanner.nextLine();
            inputString = inputString.trim();
            if(inputString == null || inputString.equals("")){
                try {
                    throw new UserInputException("Please give some proper value");
                } catch (UserInputException e) {
                    System.out.println(e.getMessage());
                }
            }else{
                loop = false;
            }
        }
        return inputString;
    }


    /*
    * to get the subject */

    /*public static String getSubject(){
        boolean subjectLoop = true;
        String subject = "";
        while (subjectLoop) {
            System.out.println("Please Enter Subject");
            try {
                subject = UserConsoleInput.acceptString();
                subjectLoop = false;
            } catch (UserInputException e) {
                System.out.println(e.getMessage()+" for subject!");
            }
        }
        return subject;
    }*/


    /*
    * To get name of agent*/
    /*public static String getAgentName(){
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
    }*/


    /*
    * To get multiple tag names, separated by Comma*/
    public static Set<String> getTagNames(){
        Set<String> tagHashSet = new HashSet<>();
        System.out.println("Enter tag names separated by comma(,)");
        String[] tagNames = acceptString("Enter Tags").split(",");

            for(String tag : tagNames){
                tag = tag.trim();
                if(!(tag.equals(""))){
                    tagHashSet.add(tag);
                }
            }
        return tagHashSet;
    }

// Lokesh: File create/check in UserConsoleInput?
    /*public static File createFile(){
        File file = null;
        try {
            file = new File("src/com/resources/tickets.ser");
            if(!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        return file;
    }*/

    public static String getPermission(String str1, String str2){
        boolean permission = true;
        String option = "";
        while(permission){
            option = acceptString("Enter "+str1+"/"+str2);
            if(option.toLowerCase().equals(str1) || option.toLowerCase().equals(str2))
                permission = false;
        }

        return option.toLowerCase();
    }

}
