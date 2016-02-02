package com.services;

import com.util.UserConsoleInput;

/**
 * Created by root on 13/1/16.
 */
public class TicketMenu {

    public static void main(String[] args) {
        TicketOperations objTicktOperation = new TicketOperations();

        boolean loop = true;

        while(loop) {
            displayTicketMenu();

            int option = UserConsoleInput.acceptNumber();
            System.out.println("your option is :" + option);

            switch (option) {
                case 1:
                    System.out.println("Creating Ticket");
                    objTicktOperation.create();
                    //Lokesh:  Caller should not be given that much complex things to do. Just give a simple function from service to call, which will in turn handle all other operations.
                    /** Deepak:
                     * This is required due to sepration of user input and actual result generation functionality. */
                    //Lokesh: Why do you need to show all the Ticket when one new is created?
                    /** Deepak:
                     * This to reflect new ticket, and normally we are not showing single ticket when created.
                     * Normally we display list of tickets we have, similar to our Helpdesk ticket listing page. */
                    //* objTicktOperation.getTickets(objTicktOperation.getAllTicket());
                    break;

                case 2:
                    // Lokesh: Single Responsibility principle: Primary objective of this class seems (as in create ticket above) to be calling some service, that will do other things like taking input and do processing,
                    /** Deepak:
                     * As per single responsibility input for creation and updating a ticket input is received in that class.
                     * While searching ticket is searching mechanism which not advanced as it is just console application,
                     * thus I have taken input for searched ticket here itself */

                    System.out.println("Enter ticket Id");
                    int id1 = UserConsoleInput.acceptNumber();
                    // Lokesh: There can be two different functions in place of "getTickets", one handling single Ticket and other handling Multiple Tickets inn Collection.
                    // Lokesh: You passed here a Collection containing single Ticket, making it more of complex operation.
                    /** Deepak:
                     * This function is used to display list of ticket. List can contain single ticket, empty Or multiple tickets
                     * So function has to be covered all the conditions. */
                    objTicktOperation.getTickets(objTicktOperation.getTicketById(id1));
                    break;

                case 3:
                    System.out.println("Enter ticket Id");
                    int id2 = UserConsoleInput.acceptNumber();
                    objTicktOperation.updateTicketById(id2);
                    break;

                case 4:
                    System.out.println("Enter ticket Id");
                    int id3 = UserConsoleInput.acceptNumber();
                    if(objTicktOperation.deleteTicketById(id3))
                        System.out.println("Ticket delete successful!");
                    else
                        System.out.println("Ticket not delete!");
                    // Lokesh: what will happen if delete fails? Else part become must here.
                    break;

                case 5:
                    objTicktOperation.getTickets(objTicktOperation.searchTicketByAgent());
                    break;

                case 6:
                    objTicktOperation.getTickets(objTicktOperation.searchTicketByTag());
                    break;
                case 7:
                    System.out.println("Show All the Tickets");
                    objTicktOperation.getTickets(objTicktOperation.getAllTicket());
                    break;
                case 8:
                    System.out.println("Agents \t Counts");
                    objTicktOperation.showAgentTicketCount(objTicktOperation.calculateAgentTicketCount());
                    break;
                case 9:
                    System.out.println("Enter no of tickets you want");
                    int noOfTickets = UserConsoleInput.acceptNumber();
                    objTicktOperation.getTickets(objTicktOperation.autoLoadTickets(noOfTickets));
                    break;

                case 10:
                    boolean report = true;
                    TicketReports objTicktReports = new TicketReports();

                    while(report){
                        displayReportMenu();
                        int reportOption = UserConsoleInput.acceptNumber();
                        // Lokesh: Where are all other reports? "Number of Tickets for a given Tag", "Ticket older than given number of days" are missing.
                        /** As I told you it is completed on my local machine using unixtime stamp.
                         * To improve it with in standard way by using stream and read from file I have not pushed it on git. */
                        switch (reportOption){
                            case 1:
                                System.out.println("Total Number of Tickets : "+objTicktReports.countNoOfTicketInSystem());
                                break;

                            case 2:
                                System.out.println("Oldest ticket in system : ");
                                objTicktOperation.getTickets(objTicktReports.getOldestTicket());
                                break;

                            case 3:
                                System.out.println("Number of tags in system:");
                                objTicktReports.displayTagTicketCount(objTicktReports.getTicketCountByTag());
                                break;

                            case 4:
                                /**
                                 * Old code */
                               /* System.out.println("Please enter no of days before ticket you want!");
                                objTicktOperation.getTickets(objTicktReports.ticketOlderByDays());*/

                                objTicktOperation.getTickets(objTicktReports.getTicketOlderByDays());
                                break;

                            case 0:
                                report = false;
                                objTicktReports = null;
                                break;

                            default:
                               System.out.println("Wrong Input! please enter number between 1 to 4");
                                break;
                        }
                    }
                    break;

                // Lokesh: Order of Cases in switch-case should be in order. This one represent poor readability.
                case 0:
                    loop = false;
                    // Lokesh: Do you feel this statement is require here to exit the application?
                    break;

                default:
                    System.out.println("Wrong Input! please enter number between 1 to 10");// Lokesh: Where is 10? Self-review the code after modification.
                    break;

            }
        }
        // Lokesh: Statement not required
    }


    /*
    * Showing the operation available on Ticket System */
    public static void displayTicketMenu(){

        // Lokesh: there are better ways to avoid this long list of sout statements.
        System.out.println("########################### Ticket Menu ###########################");
        System.out.println("Select the options for particular action below:");
        System.out.println("1: Create Ticket");
        System.out.println("2: Select Ticket by id");
        System.out.println("3: Update Ticket by id");
        System.out.println("4: Delete Ticket");
        System.out.println("5: Search tickets by Agent Name");
        System.out.println("6: Search tickets by Tag name");
        System.out.println("7: Show All Tickets");
        System.out.println("8: Agent by Ticket count");
        System.out.println("9: Auto load multiple tickets");
        System.out.println("10: Ticket Reports");
        System.out.println("0: QUIT");
        System.out.println("Enter your Choice :");
    }


    public static void displayReportMenu(){
        // Lokesh: All operations are not working here.
        System.out.println("########################### Report Menu ###########################");
        System.out.println("1: Get number of Tickets in system");
        System.out.println("2: Get oldest Ticket in system");
        System.out.println("3: Get all tags used in system");
        System.out.println("4: Tickets before particular date");
        System.out.println("0: Exit");
        System.out.println("Please enter above option number :");


    }

}