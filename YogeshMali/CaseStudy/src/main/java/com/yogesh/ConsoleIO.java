package com.yogesh;

import com.yogesh.model.Ticket;

import java.util.*;

/**
 * Created by root on 15/1/16.
 */
// Ganesh D: take care about typo
public class ConsoleIO {

    public static String getAgentNAme() {
        System.out.println("Enter Agent name ");
        return getString();
    }


    public static String getSubject() {
        System.out.println("Enter Subject ");
        return getString();
    }

    public static Set getTags() {
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Enter tags (multiple tags separate by Comma) ");
        String tags = scanIn.next();
        return new HashSet<String>(Arrays.asList(tags.split(",")));
    }

    public static int getTicketId() {
        System.out.println("Enter ticket Id ");
        //Ganesh D: Don’t create variables that you don’t use again, instead directly return it,
        // I see a lot variables been created in other getters as well

        // update :: remove Local variabless
        return getIntvalue();
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
        return scanIn.nextLine();

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
        System.out.println();
    }

    public static void showMsg(String msg) {
        System.out.println(msg);
    }


    public static void showReportingMenu() {
        System.out.println(" ***************Select Operation******************");
        System.out.println("1. showAllTicket");
        System.out.println("2. How many tickets are in the system");
        System.out.println("3. The oldest ticket in the system");
        System.out.println("4. Tickets older than a certain number of days");
        System.out.println("5. Tags in use/ No of tickets with a tag");

    }
}
