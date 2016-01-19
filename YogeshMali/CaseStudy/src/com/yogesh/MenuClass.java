package com.yogesh;

import com.yogesh.service.TicketService;

import java.util.List;

/**
 * Created by root on 15/1/16.
 */
public class MenuClass {


    public TicketService ticketService = new TicketService();

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
                createTicket();
                break;
            case 2:
                updateTicket();
                break;
            case 3:
                showAllTicket();
                break;
            case 4:
                showSingleTicket();
                break;
            case 5:
                searchTicketsUsingtag();
                break;
            case 6:
                showTicketcountAgent();
                break;
            case 7:
                removeTicket();
                break;
            case 8:
                searchTicketsUsingAgentname();
                break;
            case 9:
                exit();
                break;
            default:
                ;
                break;
        }
    }


    private void createTicket() {

        int id = ConsolIO.getTicketId();
        String subject = ConsolIO.getSubject();
        String agentName = ConsolIO.getAgentNAme();
        List<String> list = ConsolIO.getTags();

        ticketService.createTicketService(id, subject, agentName, list);

    }

    private void updateTicket() {
        int id = ConsolIO.getTicketId();
        if (ticketService.isTicketIdExit(id)) {
            ConsolIO.showMsg("Press a to update Agent name  ");
            String updatedId = ConsolIO.getString();
            if (updatedId.equals("a")) {
                String newAgentName = ConsolIO.getAgentNAme();
                ticketService.updateAgentName(id, newAgentName);
            }
            ConsolIO.showMsg("Press t to update Tags  ");
            String updatedTag = ConsolIO.getString();
            if (updatedTag.equals("t")) {
                List<String> newlist = ConsolIO.getTags();
                ticketService.updateTags(id, newlist);
            }
            ConsolIO.showMsg(" Agent name has been updated ");
        } else {
            ConsolIO.showMsg("Ticket is not Exists");
        }
    }

    private void showAllTicket() {
        ticketService.showAllTicketService();
    }

    private void showSingleTicket() {
        int id = ConsolIO.getTicketId();
        ticketService.showSingleTicketService(id);
    }

    private void searchTicketsUsingtag() {
        ConsolIO.showMsg("Enter the tag want to search");
        String tag = ConsolIO.getString();
        ticketService.searchTicketsUsingtagService(tag);
        msg();
    }

    private void showTicketcountAgent() {
        ticketService.showTicketcountAgentService();
    }


    private void removeTicket() {
        ticketService.removeTicketService(ConsolIO.getTicketId());
    }

    private void searchTicketsUsingAgentname() {
        ticketService.searchTicketsUsingAgentnameService(ConsolIO.getAgentNAme());
    }

    private void exit() {
        ticketService.exitService();
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
