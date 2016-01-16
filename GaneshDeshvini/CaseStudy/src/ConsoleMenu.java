import helpers.ConsoleReader;
import service.TicketService;

import java.util.*;

/**
 * Created by root on 14/1/16.
 */
public class ConsoleMenu {
    public void start() {
        Scanner scanner = ConsoleReader.getInstance();
        boolean flag = true;
        do {
            printStartMenu();
            try {
                int n = scanner.nextInt();
                switch (n) {
                    case 1:
                        TicketService.getInstance().processCreateTicket();
                        break;

                    case 2:
                        TicketService.getInstance().processUpdateTicket();
                        break;

                    case 3:
                        TicketService.getInstance().processDeleteTicket();
                        break;

                    case 4:
                        TicketService.getInstance().processGetTicketDetail();
                        break;

                    case 5:
                        TicketService.getInstance().processGetAllTicketList();
                        break;

                    case 6:
                        TicketService.getInstance().processGetTicketsByAgentName();
                        break;

                    case 7:
                        TicketService.getInstance().processAgentWithTicketCount();
                        break;

                    case 8:
                        TicketService.getInstance().processGetAllTicketsByTag();
                        break;

                    case 9:
                        System.out.println("Quitting....");
                        flag = false;
                        break;

                    default:
                        System.out.println("Invalid input..please try again\n");
                        break;
                }

            } catch (InputMismatchException ime) {
                System.out.println("Invalid input..please try again\n");
            } catch (Exception e) {
//                e.printStackTrace();
                flag = false;
            } finally {
                scanner.nextLine();
            }
            flag = continueMenu();
        }
        while (flag);
    }

    /**
     * show continue menu
     *
     * @return
     */
    boolean continueMenu() {
        System.out.println("\n\nDo you want to continue(Y/N)");
        int i;
        for (i = 0; i <= 5; i++) {
            String s = ConsoleReader.getInstance().next();
            if (s.equalsIgnoreCase("Y")) {
                return true;
            } else if (s.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Invalid input. Please provide valid input");
            }
        }
        if (i == 6) {
            System.out.println("Exitting...Maximum retry done!!!");
        }
        return false;
    }

    /**
     * print start menu
     */
    void printStartMenu() {
        System.out.println("\n\nWelcome! Please select the option which you want to perform");
        System.out.println("1 - Create ticket");
        System.out.println("2 - Update ticket");
        System.out.println("3 - Remove/Delete ticket");
        System.out.println("4 - Select ticket by Id");
        System.out.println("5 - Select all tickets");
        System.out.println("6 - Select tickets assigned to specific agent");
        System.out.println("7 - View ticket count grouped by agent name");
        System.out.println("8 - View all tickets by tag");
        System.out.println("9 - Quit");
    }
}
