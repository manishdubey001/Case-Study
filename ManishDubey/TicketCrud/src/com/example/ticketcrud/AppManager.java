package com.example.ticketcrud;

import com.example.ticketcrud.service.TicketService;
import com.example.ticketcrud.util.TicketUtil;

/**
 * Created by root on 4/1/16.
 */
public class AppManager {

    public TicketService ticketService = new TicketService();
    /**
     * Boot ticket crud application
     */
    public void startTicketApp() {
        int userInput = 0;

        do{
            TicketUtil.displayTicketMenu();
            userInput = TicketUtil.acceptUserInput();
            if(userInput >=1 || userInput <=9)
                processUserInput(userInput);
        }while (userInput != 10);
    }

    /**
     * Process entered user option
     * @param option
     */
    public void processUserInput(int option){
        switch (option){
            case 1:
                ticketService.create();
                break;
            case 2:
                ticketService.update();
                break;
            case 3:
                ticketService.delete();
                break;
            case 4:
                ticketService.displaySingleTicketDetails();
                break;
            case 5:
                ticketService.displayAllTickets();
                break;
            case 6:
                ticketService.displayAgentTickets();
                break;
            case 7:
                ticketService.displayTicketCountOfAgentInAsc();
                break;
            case 8:
                ticketService.displayTicketsByTag();
                break;
            case 9:
                ticketService.loadDummyTickets();
                break;
            default:
                System.out.println("You Entered Wrong Option...");
        }
    }
}
