package com.inin.example.util;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.Properties;

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

    /**
     * Return the configure ticket serialization file name
     * @return
     * @param property
     */
    public static String getProperty(String property)
    {
        Properties prop = new Properties();
        String propFileName = getPropertyFileName(property);
        String propertyValue="";
        File file  = createFile(propFileName);
        try(FileInputStream fis =  new FileInputStream(file)) {
            if (fis != null)
                prop.load(fis);
            else
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            propertyValue = prop.getProperty(property);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return propertyValue;
    }
    /**
     * Return the configure ticket serialization file name
     * @return
     * @param property
     */
    public static void setProperty(String property, String value)
    {
        Properties prop = new Properties();
        String propFileName = getPropertyFileName(property);
        File file  = createFile(propFileName);
        try(FileInputStream fis =  new FileInputStream(file);FileOutputStream fos=new FileOutputStream(file)) {
            if (fis != null)
                prop.load(fis);
            else
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            prop.setProperty(property,value);
            prop.store(fos, "Properties");

    }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Create file if not present and return File object
     * @return File
     * @throws IOException
     */
    public static File createFile(String fileName) {
        File file = null;
        try {
            file = new File("src/resources/"+fileName);
            file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }

    private static String getPropertyFileName(String property){
        switch (property)
        {
            case "ticketId":
                return "ticket.properties";
            default:
                return "config.properties";
        }
    }

}