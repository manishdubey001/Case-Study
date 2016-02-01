package com.yogesh;

import com.yogesh.model.Ticket;
import com.yogesh.service.TicketService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by root on 15/1/16.
 */
public class MenuClass {



    TicketManager ticketManager = new TicketManager();
    public MenuClass() {
        displayList();
    }

    /**
     * Select Operation
     *
     * @param menuId
     */
    private void operation(int menuId) {

        switch (menuId) {
            case 1:
                ticketManager.createTicket();
                break;
            case 2:
                ticketManager.updateTicket();
                break;
            case 3:
                ticketManager.showAllTicket();
                break;
            case 4:
                ticketManager.showSingleTicket();
                break;
            case 5:
                ticketManager.searchTicketsUsingtag();
                break;
            case 6:
                ticketManager.showTicketcountAgent();
                break;
            case 7:
                ticketManager.removeTicket();
                break;
            case 8:
                ticketManager.searchTicketsUsingAgentname();
                break;
            case 9:
                ticketManager.exit();
                break;
            default:
                ;
                break;
        }
    }



    /**
     * this method handle continue process or exit
     */
    public void msg() {

        ConsolIO.showMsg("Please Press 1 : Continue   ");
        int msgId = ConsolIO.getIntvalue();
        if (msgId == 1) {
            displayList();

        } else {
            ConsolIO.showMsg("thank you");
        }
    }

    /**
     * Display Menu List
     */
    public void displayList() {

        ConsolIO.displayList();
        ConsolIO.showMsg("Select Operation ");
        int menuId = ConsolIO.getIntvalue();
        this.operation(menuId);
        msg();
    }

}
