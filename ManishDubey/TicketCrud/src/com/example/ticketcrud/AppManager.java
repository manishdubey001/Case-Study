package com.example.ticketcrud;

import com.example.ticketcrud.service.TicketService;
import com.example.ticketcrud.util.TicketUtil;

/**
 * Created by root on 4/1/16.
 */
public class AppManager {

    /**
     * Boot ticket crud application
     */
    public static void startTicketApp() {
        int userInput = 0;
        do{
            TicketUtil.displayTicketMenu();
            userInput = TicketUtil.acceptUserInput();
            if(userInput >=1 || userInput <=8)
                processUserInput(userInput);
        }while (userInput != 9);
    }

    /**
     * Process entered user option
     * @param option
     */
    public static void processUserInput(int option){
        switch (option){
            case 1:
                TicketService.create();
                break;
            case 2:
                TicketService.update();
                break;
            case 3:
                TicketService.delete();
                break;
            case 4:
                TicketService.displaySingleTicketDetails();
                break;
            case 5:
                TicketService.displayAllTickets();
                break;
            case 6:
                TicketService.displayAgentTickets();
                break;
            case 7:
                TicketService.displayTicketCountOfAgentInAsc();
                break;
            case 8:
                TicketService.displayTicketsByTag();
                break;
            default:
                System.out.println("You Entered Wrong Option...");
        }
    }
}
