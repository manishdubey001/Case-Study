package com.inin.example;

import com.inin.example.util.TicketUtil;

/**
 * Created by root on 4/1/16.
 */
public class AppManager {

    public TicketManager ticketManager= null;
    /**
     * Boot ticket crud application
     */
    public void startTicketApp() {
        int userInput = 0;
        this.ticketManager = new TicketManager();
        do{
            TicketUtil.displayTicketMenu();
            userInput = TicketUtil.acceptUserInput();
            if(userInput >=1 && userInput <=13)
                processUserInput(userInput);
        }while (userInput != 14);
    }

    /**
     * Process entered user option
     * @param option
     */
    public void processUserInput(int option){
        switch (option){
            case 1:
                ticketManager.create();
                break;
            case 2:
                ticketManager.update();
                break;
            case 3:
                ticketManager.delete();
                break;
            case 4:
                ticketManager.displaySingleTicketDetails();
                break;
            case 5:
                ticketManager.displayAllTickets();
                break;
            case 6:
                ticketManager.displayAgentTickets();
                break;
            case 7:
                ticketManager.displayTicketCountOfAgentInAsc();
                break;
            case 8:
                ticketManager.displayTicketsByTag();
                break;
            case 9:
                ticketManager.totalTicketCount();
                break;
            case 10:
                ticketManager.oldestTicket();
                break;
            case 11:
                ticketManager.ticketOlderByDate();
                break;
            case 12:
                ticketManager.displayTicketCountByTags();
                break;
            case 13:
                ticketManager.loadDummyTickets();
                break;
            default:
                System.out.println("You Entered Wrong Option...");
        }
    }
}