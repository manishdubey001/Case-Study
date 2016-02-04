public class TicketApplication {
    // Lokesh: Shouldn't it be private?   --- done
    private TicketOperations ticketOperation = null;

    private void startApplication(){
        ticketOperation = new TicketOperations();
        int userInput;
        do {
            displayMenuList();
            userInput = InputDataReader.readInteger();
            performTicketAction(userInput);
        }while (userInput != 9);
    }
    // Lokesh: Avoid to put any kind of Logic in Constructor. Understand the purpose of Cons   --- done
    /*public TicketApplication(){

    }*/

    //will add this in util
    private void displayMenuList() {
        String[] menuArray = {"1. Create Ticket.", "2. Update Ticket By Id.", "3. Delete Ticket By Id.", "4. Select Single ticket by Id.", "5. Select all Tickets.",
                "6. Select Tickets assigned to specific agent.", "7. Ticket count grouped by agent name(order by agent name).", "8. Search all tickets by specific tag.",
                "9. Exit", "What do you want to perform? Please enter your choice :: "};

        for (String menu : menuArray){ System.out.println(menu); }
    }

    private void performTicketAction(int input){
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
            case 9 :
                System.out.println("End of operation");
            default:
                System.out.println("Wrong Choice..Please enter valid choice!!!");
        }
    }

    public static void main(String[] args) {
        TicketApplication obj = new TicketApplication();
        obj.startApplication();
    }
}
