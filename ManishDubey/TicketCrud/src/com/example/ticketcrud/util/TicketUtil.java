package com.example.ticketcrud.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;

/**
 * Created by root on 30/12/15.
 */
public class TicketUtil {
// cjm - I like splitting these kind of methods out into a separate class
    public static BufferedReader reader = null;
    private static int currentTicketId = 0;

    /**
     * Display Ticket menu on console
     */
    public static void displayTicketMenu(){
        System.out.println("\nPlease enter appropriate option from below menu list");
        System.out.println("=================================================================");
        System.out.println("1.\tCreate Ticket");
        System.out.println("2.\tUpdate Ticket by id");
        System.out.println("3.\tDelete/Remove Ticket by id");
        System.out.println("4.\tSelect single Ticket by id");
        System.out.println("5.\tSelect all Tickets");
        System.out.println("6.\tSelect tickets assigned to specific agent");
        System.out.println("7.\tTicket count grouped by agent name(order by agent name)");
        System.out.println("8.\tSearch all tickets by specific tag");
        System.out.println("9.\tQuit");
        System.out.println("=================================================================\n");
        System.out.print("Enter your option: ");
    }

    /**
     * Accept the user entered option and validate it
     * @return int
     */
    public static int acceptUserInput(){
        BufferedReader reader = TicketUtil.getReader();
        int userInput  = 0;
        try {
            userInput = Integer.parseInt(reader.readLine());
            if (userInput <= 0 || userInput > 9)
                throw new InvalidParameterException();
            if(userInput == 9){
                System.out.print("You are sure want to quit(y/n)?: ");
                String ch = reader.readLine();
                if(ch.toLowerCase().equals("y")) {
                    userInput = 9;
                   reader.close();
                }
                else if(ch.toLowerCase().equals("n"))
                    userInput = 0;
                else
                    System.out.println("Invalid option.....");
            }
        } catch (NumberFormatException e) {
            System.out.println("Only integer value is accepted...");
        } catch (InvalidParameterException e) {
            System.out.println("Only 1 to 9 option is allowed ...");
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

        return userInput;
    }

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
     * Return the next ticket Id
     * @return int
     */
    public static int getNextTicketId()
    {
        return ++currentTicketId;
    }
}
