package com.services;

import com.model.Tag;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by root on 15/1/16.
 */
public class UserConsoleInput {

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
        String inputString = null;
        inputString = scanner.nextLine();
        inputString = inputString.trim();
        if(inputString.equals("") || inputString == null){
            throw new UserInputException("Please give some proper Values");
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
                //e.printStackTrace();
                System.out.println("Please give proper subject!");
            }
        }
        return subject;
    }


    /*
    * To get name of agent*/
    public static String getAgentName(){
        boolean agentLoop = true;
        String agent_name = "";
        while (agentLoop) {
            System.out.println("Please Enter Agent Name");
            try {
                agent_name = UserConsoleInput.acceptString();
                agentLoop = false;
            } catch (UserInputException e) {
                //e.printStackTrace();
                System.out.println("Please give proper Agent name!");
            }
        }

        return agent_name;
    }


    /*
    * To get multiple tag names, separated by Comma*/
    public static Set<String> getTagNames(){
        System.out.println("Please Enter tags separated by Comma");
        String[] tag_names;
        Set<String> tagHashSet = new HashSet<>();
        try {
            tag_names = UserConsoleInput.acceptString().split(",");

            TicketOperations ticketOperations = new TicketOperations();
            for(String tag : tag_names){
                tag = tag.trim();
                if(!(tag.equals(""))){
                    ticketOperations.allTagHashSet.add(tag);
                    tagHashSet.add(tag);
                }
            }

        }catch (UserInputException e) {
            System.out.println("Please enter Proper tags" + e.getMessage());
        }

        return tagHashSet;
    }
}
