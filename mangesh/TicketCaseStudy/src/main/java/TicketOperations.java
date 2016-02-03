import model.Ticket;
import service.TicketReportsService;
import service.TicketService;
import java.util.*;

public class TicketOperations {
    private TicketService ticketService = null;
    private TicketReportsService ticketReportsService = null;

    public TicketOperations(){
        ticketService = new TicketService();
        ticketReportsService = new TicketReportsService();
    }

    public void createTicket(){
        System.out.println("Create ticket");

        System.out.println("Enter Ticket Subject : ");
        String subject = InputDataReader.readString();
        System.out.println("Enter Agent Name : ");
        String agentName = InputDataReader.readString();
        System.out.println("Enter Tags (separated by comma(,) : ");
        String tags = InputDataReader.readString();

        HashSet<String> tagSet = new HashSet<>(Arrays.asList(tags.toLowerCase().split(",")));

        if(subject != null && !subject.isEmpty() && agentName != null && !agentName.isEmpty()) {
            Ticket tickets = ticketService.createTicket(subject,agentName,tagSet);
            if(tickets != null)
                System.out.println("Ticket Has been created successfully");
            else
                System.out.println("There is error while creating a ticket.");
        }
        else
            System.out.println("Ticket Subject & Agent Name is compulsory.");
    }

     public void showAllTickets(){
         Map<Integer, Ticket> tickets = ticketService.getAllTickets();
         if(tickets.size() >0 ){
            showHeader();
            tickets.values().stream().sorted((Ticket t1, Ticket t2) -> -t1.getModified().compareTo(t2.getModified())).forEach(ticket -> System.out.println(ticket.getId()
                    + "  |  " + ticket.getSubject() + "  |  " + ticket.getAgentName() + "  |  " + ticket.getTags() + "  |  " + ticket.getCreated() + "  |  " + ticket.getModified()));
         }
         else
         {
             System.out.println("No tickets found!!!");
         }
     }

    public void deleteTicketById(){
        System.out.println("Enter ticket Id for deletion : ");
        int id = InputDataReader.readInteger();
        if(ticketService.deleteTicket(id)) {
            System.out.println("Ticket id " + id + " is deleted successfully.");
        }
        else
            System.out.println("Entered ticket id " + id + " is not present in the system." );
    }

    public void showTicketById(){
        System.out.println("Enter ticket Id for showing details : ");
        int id = InputDataReader.readInteger();
        Ticket ticket = ticketService.getTicketDetails(id);
        if(ticket != null){
            System.out.println("Ticket details of ticket id : " + id);
            showHeader();
            System.out.println(ticket.getId() +"  |  " + ticket.getSubject() +"  |  " + ticket.getAgentName() +"  |  " + ticket.getTags() +"  |  " +
                    ticket.getCreated() +"  |  " + ticket.getModified());
        }
        else
            System.out.println("Entered ticket id " + id + " is not present in the system." );
    }

    public  void showTicketsByAgentName() {
        System.out.println("Please enter Agent Name : ");
        String agentName = InputDataReader.readString();
        if(agentName.isEmpty()){
            System.out.println("Invalid agent Name. Agent Name should not be empty!!!");
        }
        else
        {
            List<Ticket> agentTicketList = ticketService.getTicketsByAgentName(agentName);
            if(agentTicketList.size()>0){
                showHeader();
                agentTicketList.forEach(ticket -> System.out.println(ticket.getId() + "  |  " + ticket.getSubject() + "  |  " + ticket.getAgentName() +
                        "  |  " + ticket.getTags() + "  |  " + ticket.getCreated() + "  |  " + ticket.getModified()));
            }
            else
                System.out.println("No records found for entered agent " + agentName);
        }
    }

    public void searchTicketsByTag(){
        System.out.println("Enter tag to search ticket(s) : ");
        String tag = InputDataReader.readString();
        if(tag.isEmpty()){
            System.out.println("Invalid Tag!!!. Tag Name should not be empty!!!");
        }
        else
        {
            List<Ticket> ticketList = ticketService.getTicketsByTag(tag);
            if(ticketList.size()>0)
            {
                showHeader();
                ticketList.forEach(ticket -> System.out.println(ticket.getId() + "  |  " + ticket.getSubject() + "  |  " + ticket.getAgentName() +
                        "  |  " + ticket.getTags() + "  |  " + ticket.getCreated() + "  |  " + ticket.getModified()));
            }
            else
                System.out.println("No tickets are found for entered tag " + tag);
        }
    }

    public void ticketCountsByAgentName(){
        Map<String, List<Ticket>> ticketCountList = ticketService.getTicketCounts();
        if(ticketCountList.size()>0){
            ticketCountList.forEach((String agentName, List<Ticket> ticketList) -> System.out.println(agentName + " :  " +ticketList.size()));
        }
        else
            System.out.println("No Records Found!!!");
    }

    public void updateTicket(){
        System.out.println("Enter ticket Id to update : ");
        int id = InputDataReader.readInteger();
        if(ticketService.isTicketExist(id)){
            System.out.println("Enter Agent Name : ");
            String agentName = InputDataReader.readString();
            System.out.println("Enter A-adding new tags / R-remove existing tag / N-no");
            String action = InputDataReader.readString();

            HashSet<String> tags = null;
            if(!action.isEmpty() && (action.equals("A") || action.equals("R"))){
                System.out.println("Enter tags seperated by comma(,) : ");
                String tag = InputDataReader.readString();
                tags = new HashSet<>(Arrays.asList(tag.split(",")));
            }

            Ticket ticket = ticketService.update(id, agentName, tags, action);

           if(ticket != null){
               System.out.println("Ticket id " + id + " is updated successfully");
               showHeader();
               System.out.println(ticket.getId() + "  |  " + ticket.getSubject() + "  |  " + ticket.getAgentName() + "  |  " + ticket.getTags() + "  |  " +
                                    ticket.getCreated() + "  |  " + ticket.getModified());
           }
        }
        else
            System.out.println("Entered Ticket id " + id + " is not present in the system.");
    }

    public void showHeader(){
        System.out.println("Id  |  Subject  |  Agent  |  tags  |  created  |  Modified");
    }

    public void totalTicketCount(){
        System.out.println("Total number of tickets present in the system : " + ticketReportsService.getTotalTicketCounts());
    }

    public void oldestTicket(){
        Ticket ticket = ticketReportsService.getOldestTicket();
        if(ticket != null)
            System.out.println("Oldest ticket in the system is : \n" + ticket.getId() +"  |  " + ticket.getSubject() +"  |  " + ticket.getAgentName() +"  |  " + ticket.getTags()
                    +"  |  " + ticket.getCreated()  +"  |  " +  ticket.getModified());
        else
            System.out.println("No Ticket Found in the system");
    }

    public void ticketOlderByDays(){

    }

    public void showTicketCountByTags(){
        System.out.println("Ticket count grouped by tags (ascending order). ");
        System.out.println("tags   |   Ticket Count");
        Map<String,List<Ticket>> ticketsGroupByTag = ticketReportsService.getTicketCountByTags();
        if(ticketsGroupByTag.size() > 0)
            ticketsGroupByTag.forEach((String tag,List<Ticket> ticketsList)->System.out.println(tag +"  |  "+ ticketsList.size()));
        else
            System.out.println("No ticket founds");
    }
}
