public class TicketApplication {
    private TicketOperations ticketOperation = null;

    public void startApplication(){
        ticketOperation = new TicketOperations();
        int userInput;
        do {
            displayMenuList();
            userInput = InputDataReader.readInteger();
            performTicketAction(userInput);
        }while (userInput != 10);
    }

    public void displayMenuList() {
        String[] menuArray = {"1. Create Ticket.", "2. Update Ticket By Id.", "3. Delete Ticket By Id.", "4. Select Single ticket by Id.", "5. Select all Tickets.",
                "6. Select Tickets assigned to specific agent.", "7. Ticket count grouped by agent name.", "8. Search all tickets by specific tag.",
                "9. Total Number of tickets in the system", "10. Oldest ticket", "11.Tickets older than a certain number of days", "12.Tags in use/# of tickets with a tag",
                "13. Exit", "What do you want to perform? Please enter your choice :: "};

        for (String menu : menuArray){ System.out.println(menu); }
    }

    public void performTicketAction(int input){
        switch(input){
            case 1 : ticketOperation.createTicket();
                break;
            case 2 : ticketOperation.updateTicket();
                break;
            case 3 : ticketOperation.deleteTicketById();
                break;
            case 4 : ticketOperation.showTicketById();
                break;
            case 5 : ticketOperation.showAllTickets();
                break;
            case 6 : ticketOperation.showTicketsByAgentName();
                break;
            case 7 : ticketOperation.ticketCountsByAgentName();
                break;
            case 8 : ticketOperation.searchTicketsByTag();
                break;
            case 9 : ticketOperation.totalTicketCount();
                break;
            case 10: ticketOperation.oldestTicket();
                break;
            case 11: ticketOperation.ticketOlderByDays();
                break;
            case 12: ticketOperation.showTicketCountByTags();
                break;
            case 13 :
                System.exit(0);
                System.out.println("End of operation");
                break;
            default:
                System.out.println("Wrong Choice..Please enter valid choice!!!");
        }
    }

    public static void main(String[] args) {
        TicketApplication obj =  new TicketApplication();
        obj.startApplication();
    }
}
