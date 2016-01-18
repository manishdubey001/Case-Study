package com.yogesh;

/**
 * Created by root on 15/1/16.
 */
public class Helper {

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
