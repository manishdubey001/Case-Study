package com.inin.example.util;

import com.sun.media.sound.InvalidFormatException;

import java.io.*;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Collection;
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
        int userInput = 0;
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

    /**
     * Return the property file name
     * @param property
     * @return String
     */
    private static String getPropertyFileName(String property){
        switch (property)
        {
            case "ticketId":
                return "ticket.properties";
            default:
                return "config.properties";
        }
    }

    /**
     * Validate string by checking null and empty
     * @param str
     * @return boolean
     */
    public static boolean isValidString(String str)
    {
        return str != null && !str.isEmpty();
    }

    /**
     * Validate string by checking null and empty
     * @param collection
     * @return boolean
     */
    public static boolean isValidCollection(Collection collection)
    {
        return collection !=null && !collection.isEmpty();
    }

    /**
     * Validate date string of format dd/mm/yyyy
     * @param date
     * @param delimiter
     * @return LocalDateTime
     */
    public static boolean validDateString(String date,String delimiter){
        try {
            if (TicketUtil.isValidString(date)) {
                String[] dateArr = date.split(delimiter);
                if (dateArr.length == 3) {
                    int day  = Integer.parseInt(dateArr[0]);
                    int month  = Integer.parseInt(dateArr[1]);
                    int year  = Integer.parseInt(dateArr[2]);
                    if(day > 0 && day <= 31 && month > 1 && month <= 12 && year > 1600) {
                         return true;
                    }
                }
            }
        }catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        return false;
    }

}