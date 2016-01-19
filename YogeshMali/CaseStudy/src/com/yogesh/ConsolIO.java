package com.yogesh;

import com.yogesh.model.Ticket;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by root on 15/1/16.
 */
public class ConsolIO {

    public static String getAgentNAme() {
        System.out.println("Enter Agent name ");
        String agentName = getString();
        return agentName;
    }


    public static String getSubject() {
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

    /**
     * Display Menu List
     */
    public static void displayList() {

        System.out.println(" ***************Select Operation******************");
        System.out.println("1. Create Ticket");
        System.out.println("2. Update Ticket");
        System.out.println("3. Show All Tickets");
        System.out.println("4. Show Single Ticket");
        System.out.println("5. Search all tickets by specific tag");
        System.out.println("6. Ticket count grouped by agent name(order by agent name)");
        System.out.println("7. Delete/Remove Ticket by id");
        System.out.println("8. Select tickets assigned to specific agent");
        System.out.println("9. exit");

    }

    public static void ticketListHeader() {
        System.out.println("Id  |  Subject  | Agent Name |  Tags  |      Created      |   Modified    ");
    }

    public static void showMsg(String msg) {
        System.out.println(msg);
    }

}
