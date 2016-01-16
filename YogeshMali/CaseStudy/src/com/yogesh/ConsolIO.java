package com.yogesh;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by root on 15/1/16.
 */
public class ConsolIO {

    public static String getAgentNAme() {
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Enter Agent name ");
        String agentName = getString();
        return agentName;
    }


    public static String getSubject() {
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Enter Subject ");
        String agentName = getString();
        return agentName;
    }

    public static List getTags() {
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Enter tags (multiple tags separate by Comma) ");
        String tags = scanIn.next();
        List<String> list = Arrays.asList(tags.split(","));
        return list;
    }

    public static int getTicketId() {

        System.out.println("Enter ticket Id ");
        int id = getIntvalue();
        return id;
    }


    public static int getIntvalue() {
        int intValue = 0;
        Scanner scanIn = new Scanner(System.in);
        try {
            intValue = (scanIn.nextInt());
        } catch (InputMismatchException ie) {
            System.err.println("Please enter the Number");
            scanIn.nextLine();
            MenuClass m = new MenuClass();
            m.displayList();
        }
        return intValue;
    }

    static String getString() {

        Scanner scanIn = new Scanner(System.in);
        String s = scanIn.nextLine();
        return s;
    }


    public static void showTicket(Ticket ticket) {
        System.out.println(ticket.getId() + " | " + ticket.getSubject() + "  |   " + ticket.getAgentName() + "  |   " + ticket.getTags() + "  |  " + ticket.getCreated() + "  |  " + ticket.getModified());
    }
}
