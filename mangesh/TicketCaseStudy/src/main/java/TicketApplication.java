import util.Util;

public class TicketApplication {
    private TicketOperations ticketOperation = null;

    public void startApplication(){
        ticketOperation = new TicketOperations();
        int userInput;
        do {
            Util.displayMenuList();
            userInput = InputDataReader.readInteger();
            performTicketAction(userInput);
        }while (userInput!= 13);
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
