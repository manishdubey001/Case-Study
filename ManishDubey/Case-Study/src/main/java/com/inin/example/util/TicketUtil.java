package com.inin.example.util;

import java.security.InvalidParameterException;

/**
 * Created by root on 30/12/15.
 */
public class TicketUtil {

    /**
     * Display Ticket menu on console
     */
    public static void displayTicketMenu(){
        System.out.println("\nPlease enter appropriate option from below menu list");
        System.out.println("=================================================================");
        System.out.println("1. \tCreate Ticket");
        System.out.println("2. \tUpdate Ticket by id");
        System.out.println("3. \tDelete/Remove Ticket by id");
        System.out.println("4. \tSelect single Ticket by id");
        System.out.println("5. \tSelect all Tickets");
        System.out.println("6. \tSelect Tickets assigned to specific agent");
        System.out.println("7. \tTicket count grouped by agent name(order by agent name)");
        System.out.println("8. \tSearch all Tickets by specific tag");
        System.out.println("9. \tTotal Ticket count");
        System.out.println("10.\tOldest Ticket in system");
        System.out.println("11.\tTickets older than by date");
        System.out.println("12.\tTags in use /# of tickets with a tag");
        System.out.println("13.\tLoad dummy Tickets");
        System.out.println("14.\tQuit");
        System.out.println("=================================================================\n");
        System.out.print("Enter your option: ");
    }

    /**
     * Accept the user entered option and validate it. And User input 10 then quit.
     * @return int
     */
    public static int acceptUserInput(){
        int userInput  = 0;
        try {
            userInput = InputReader.readInt();
            if (userInput <= 0 || userInput > 14)
                throw new InvalidParameterException();
            if(userInput == 14){
                System.out.print("You are sure want to quit(y/n)?: ");
                String ch = InputReader.readString();
                if(ch.toLowerCase().equals("y")) {
                    userInput = 14;
                    InputReader.closeReader();
                }
                else if(ch.toLowerCase().equals("n"))
                    userInput = 0;
                else
                    throw new InvalidParameterException();
            }
        } catch (InvalidParameterException e) {
            System.out.println("Invalid Parameter...");
        }
        return userInput;
    }
}