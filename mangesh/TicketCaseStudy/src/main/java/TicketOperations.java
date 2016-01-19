import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by root on 15/1/16.
 */
public class TicketOperations {

    ArrayList<Ticket> masterTicketData = new ArrayList();
    HashMap<String, Integer> agentTicketsTotalCounts = new HashMap<String, Integer>(); // Storing ticket counts against agents name
    HashMap<String, HashSet> tagsWithTicketIds = new HashMap<String, HashSet>();  // storing ticket ids against tag name

    TicketService ticketService = new TicketService();
    InputData inputData = new InputData();

    Scanner console;
    public boolean loop;
    static int ticketId;

    BufferedReader br;

    Date date = new Date();
    public long timeStamp = date.getTime();

    public TicketOperations(int id){
        ticketId = id;
        console = new Scanner(System.in);
        br = new BufferedReader(new InputStreamReader(System.in));
        this.menuList();
    }

    /**
     * Displaying ticket operation menu
     */
    public void menuList(){
        System.out.println("");
        System.out.println("==================== Ticket Operations ======================");
        System.out.println("'");

        System.out.println("1. Create Ticket.");
        System.out.println("2. Update Ticket By Id.");
        System.out.println("3. Delete Ticket By Id.");
        System.out.println("4. Select Single ticket by Id.");
        System.out.println("5. Select all Tickets.");
        System.out.println("6. Select Tickets assigned to specific agent.");
        System.out.println("7. Ticket count grouped by agent name(order by agent name).");
        System.out.println("8. Search all tickets by specific tag.");
        System.out.println("9. Exit");
        System.out.println("");
        System.out.println("What do you want to perform? Please enter your choice :: ");
        System.out.println("");

        this.selectTicketOperation();
    }

    /**
     * Taking input from user
     */
    public void selectTicketOperation() {

        this.loop = true;
        int input;
        while(loop) {
            try{
                input = Integer.parseInt(console.nextLine());
                this.performTicketAction(input);
            }
            catch (NumberFormatException n) {
                System.out.println("Invalid input !! Please enter valid input between 1 - 9 :: ");
            }
        }
    }

    /**
     * Performing user selected operation
     * @param input
     * @return
     */
    public int performTicketAction(int input){
        switch(input){
            case 1 : this.createTicket();
                     break;
            case 2 : this.updateTicket();
                     break;
            case 3 : this.deleteTicketById();
                     break;
            case 4 : this.showTicketById();
                     break;
            case 5 : this.showAllTickets();
                     break;
            case 6 : this.showTicketByAgentName();
                     break;
            case 7 : this.ticketCountsByAgentName();
                     break;
            case 8 : this.searchTicketsByTagName();  // In-progress
                     break;
            case 9 :
                System.out.println("End of operation");
                this.loop = false;
        }
        return input;
    }

    /**
     * Creating a new ticket
     * Adding tags
     */
    public void createTicket(){
        String subject = inputData.getString();
        String agentName = inputData.getAgentName();
        List<String> tagList = inputData.getTagsList();

        int returnValue = ticketService.createTicket(ticketId, subject, agentName, tagList);

        ticketId++;

        if(returnValue == 1)
            System.out.println("Ticket has been added successfully");
        else
            System.out.println("ERROR : Creating ticket false");

        this.menuList();
    }

    /**
     * showing all ticket details
     */
    public void showAllTickets() {
        ticketService.showAllTickets();
        this.menuList();
    }

    /**
     * Update ticket details.
     */
    public void updateTicket(){
  // get ticket id from user
        int ticketId = inputData.getTicketIdFromUser();

        if(ticketService.isTicketExist(ticketId)) {
            //user choice
            int userChoice = inputData.getUserChoiceForUpdate();

            if(userChoice == 1){
                String agentName = inputData.getAgentName();
                ticketService.updateTicketAgent(ticketId, agentName);  // chk return value then print msg
                System.out.println("Agent Name is updated Successfully!!!");
            }
            else if(userChoice == 2){
                List<String> tags = inputData.getTagsList();
                ticketService.updateTicketTags(ticketId, tags); // chk return value then print msg
                System.out.println("Ticket Tags are Updated Successfully!!!");
            }
        }
        else
        {
            System.out.println("Entered ticket " + ticketId + " is not present in the system.");
            this.menuList();
        }
    }

    public void deleteTicketById(){
        // get ticket id from user
        int ticketId = inputData.getTicketIdFromUser();

        if(ticketService.isTicketExist(ticketId)) {
            if(ticketService.deleteTicket(ticketId)){
                System.out.println("Ticket Has been deleted Successfully");
            }
            else {
                System.out.println("ERROR - Ticket Not deleted");
            }
        }
        else
        {
            System.out.println("Entered ticket " + ticketId + " is not present in the system.");
        }
        this.menuList();
    }

    public void showTicketById() {
        // get ticket id from user
        int ticketId = inputData.getTicketIdFromUser();

        if(ticketService.isTicketExist(ticketId)) {
            HashMap TicketDetails = ticketService.getTicketDetailsById(ticketId);
            System.out.println("Id | Subject | Agent Name | Tags | Created | Modified");
            System.out.println(TicketDetails.get("id") + " | " + TicketDetails.get("subject") + " | " + TicketDetails.get("agentName") + " | " + TicketDetails.get("tags") + " | " +
                    TicketDetails.get("created") + " | " + TicketDetails.get("modified"));
        }
        else
        {
            System.out.println("Entered ticket " + ticketId + " is not present in the system.");
        }
        this.menuList();
    }

    /**
     * Showing ticket by agent name
     */
    public void showTicketByAgentName() {
        //user agent name
        String agentName = inputData.getAgentName();
        ticketService.showTicketByAgentName(agentName);
        this.menuList();
    }

    /**
     * Showing Ticket count(s) grouped by agent name
     */
    public void ticketCountsByAgentName(){
        ticketService.ticketCountsByAgentName();
        this.menuList();
    }

    /**
     * Searching ticket by using tag name
     */
    public void searchTicketsByTagName() {
        String tag = inputData.getString();
        ticketService.showTicketsByTagName(tag);
        this.menuList();
    }

   public static void main(String[] args) {
        //TicketOperations ticketObj = new TicketOperations();
        new TicketOperations(100);
    }
}
