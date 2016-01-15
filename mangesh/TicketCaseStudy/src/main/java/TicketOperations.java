import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by root on 15/1/16.
 */
public class TicketOperations {

    ArrayList<Ticket> masterTicketData = new ArrayList();
    HashMap<String, Integer> agentTicketsTotalCounts = new HashMap<String, Integer>();
    HashMap<String, HashSet> tagsWithTicketIds = new HashMap<String, HashSet>();

    Scanner console;
    public boolean loop;
    static int ticketId;

    BufferedReader br;

    Date date = new Date();
    public long timeStamp = date.getTime();

    public TicketOperations(){
        ticketId = 100;
        console = new Scanner(System.in);
        br = new BufferedReader(new InputStreamReader(System.in));
        this.menuList();
    }

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

    public int performTicketAction(int input){
        switch(input){
            case 1 : this.createTicket();
                     break;
            case 2 : this.updateTicket();
                     break;
            case 3 : this.deleteOrShowTicket(1);
                     break;
            case 4 : this.deleteOrShowTicket(2);
                     break;
            case 5 : this.showAllTickets();
                     break;
            case 6 : this.showTicketByAgentName();
                     break;
            case 7 : this.ticketCountsByAgentName();
                     break;
            case 8 : this.searchTicketsByTagName();
                     break;
            case 9 :
                System.out.println("End of operation");
                this.loop = false;
        }
        return input;
    }

    public void createTicket(){
        boolean subjLoop = true;
        System.out.println("Enter Ticket Subject : ");
        String sub = null;
        while (subjLoop){
            sub = console.nextLine();
            sub.trim();
            if(!sub.isEmpty()){
                subjLoop = false;
            }
        }

        boolean agentLoop = true;
        String agent = null;
        System.out.println("Enter Agent Name : ");
        while (agentLoop){
            agent = console.nextLine();
            agent.trim();
            if(!agent.isEmpty()){
                agentLoop = false;
                System.out.println("");
            }
        }

        System.out.println("Enter Multiple tags separated by comma(,) : ");
        String tags = null;
        try {
            tags = br.readLine();


        }catch (IOException io){

        }

        List<String> tagList = Arrays.asList(tags.split(","));

        if (!tagList.isEmpty()) {
            this.updateTags(tagList, ticketId);
        }

        Date date = new Date();

        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setSubject(sub);
        ticket.setAgent_name(agent);
        ticket.setTempTags(tags);
        ticket.setTags(tagList);
        ticket.setCreated(date);
        ticket.setModified(date);

        this.masterTicketData.add(ticket);
        this.updateAgentsTicketCount(agent, 1);
        ticketId++;

        System.out.println("Ticket has been added successfully");
        this.menuList();
    }

    public void updateAgentsTicketCount(String agent, int action){
        int agentCount = 0 ;
        if(agentTicketsTotalCounts.containsKey(agent)){
            agentCount = agentTicketsTotalCounts.get(agent);
            if(action == 1) // add
                agentCount = agentCount + 1;
            else if(action == 0) // remove
                agentCount = agentCount - 1;
        }
        else if(action == 1)
            agentCount = 1;

        agentTicketsTotalCounts.put(agent, agentCount);
    }

    public static void main(String[] args) {
        //TicketOperations ticketObj = new TicketOperations();
        new TicketOperations();
    }

    public void showAllTickets() {
        // need to do sort on modified

        Collections.sort(this.masterTicketData);

        if(this.masterTicketData.isEmpty()){
            System.out.println("No Tickets Found!!!");
        }
        else
        {
            System.out.println("Id | Subject | Agent Name | Tags | Created | Modified");
            for (Ticket ticket : this.masterTicketData){
                System.out.println(ticket.getId() + " | " + ticket.getSubject() + " | " + ticket.getAgent_name() + " | " + ticket.getTempTags() + " | " +
                        ticket.getCreated() + " | " + ticket.getModified());
            }
            System.out.println("tags :: " + tagsWithTicketIds);
            this.menuList();
        }
    }

    public void updateTicket(){
        System.out.println("Enter Ticket Id :: ");
        boolean updateLoop = true;
        int id = 0;
        while (updateLoop){
            try {
                id = console.nextInt();
                if(this.isTicketExist(id)) {
                    System.out.println("What do you want to update?");
                    System.out.println("1. Agent Name");
                    System.out.println("2. Tags");
                    int updateTicketId;
                    try {
                        Date date = new Date();
                        updateTicketId = Integer.valueOf(br.readLine());
                        if(updateTicketId == 1) {
                            for(Ticket ticket : masterTicketData) {
                                if(ticket.getId() == id) {
                                    System.out.println("Old Agent Name : " + ticket.getAgent_name());
                                    System.out.println("Enter New Agent Name :: ");
                                    String agentName = br.readLine();
                                    ticket.setAgent_name(agentName);
                                    ticket.setModified(date);
                                    System.out.println("Agent Name is updated Successfully!!!");
                                    this.menuList();
                                }
                            }
                        }else if(updateTicketId == 2) {
                            for(Ticket ticket : masterTicketData){
                                if(ticket.getId() == id ){
                                    System.out.println("Old tags :: " + ticket.getTempTags());
                                    System.out.println("Enter New tags seperated by comma (,) :: ");
                                    String newTags = br.readLine();
                                   // if (!newTags.isEmpty()) {

                                        // update tags
                                        List<String> newTagsList = Arrays.asList(newTags);
                                        if(!newTagsList.isEmpty()){
                                            this.updateTags(newTagsList, id);
                                        }

                                        // remove
                                        List<String> oldTicketTags = ticket.getTags();
                                        this.removeTicketTags(oldTicketTags, id);

                                        ticket.setTempTags(newTags);
                                        ticket.setModified(date);
                                        System.out.println("Ticket Tags are Updated Successfully!!!");



                                        this.menuList();
                                   // }
                                }
                            }

                        }
                    }
                    catch(IOException ie){

                    }
                }
                else
                {
                    System.out.println("Entered ticket " + id + " is not present in the system.");
                    updateLoop = false;
                    this.menuList();
                }
                updateLoop = false;
            }
            catch (InputMismatchException ie){
                System.out.println("Invalid Input !!! Please enter only Number :: ");
                updateLoop = false;

            }
        }
    }

    public boolean isTicketExist(int ticketId) {
        //System.out.println("ic check :: "  + masterTicketData.get(ticketId));

        for (Ticket ticket : masterTicketData){
            if(ticket.getId() == ticketId)
                return true;
        }
        return false;
    }

    public void deleteOrShowTicket(int choice){
        System.out.println("Enter Ticket Id :: ");
        boolean loop = true;
        int id = 0;
        while (loop) {
            try {
                id = console.nextInt();
                if(this.isTicketExist(id)) {
                    for(Ticket ticket : masterTicketData) {
                        if (ticket.getId() == id) {

                            if(choice == 1) { // delete
                                masterTicketData.remove(ticket);
                                System.out.println("Ticket Has been deleted Successfully");
                                this.updateAgentsTicketCount(ticket.getAgent_name(), 0);
                            }
                            else if(choice == 2) { // display single ticket data
                                System.out.println("Id | Subject | Agent Name | Tags | Created | Modified");
                                System.out.println(ticket.getId() + " | " + ticket.getSubject() + " | " + ticket.getAgent_name() + " | " + ticket.getTempTags() + " | " +
                                            ticket.getCreated() + " | " + ticket.getModified());
                            }
                            loop = false;
                            this.menuList();
                            break;
                        }
                    }
                }
                else{
                    System.out.println("Entered ticket " + id + " is not present in the system.");
                    loop = false;
                    this.menuList();
                }

                loop = false;
            }
            catch (InputMismatchException ie){
                System.out.println("Invalid Input !!! Please enter only Number :: ");
                loop = false;
            }
        }
    }

    public void showTicketByAgentName() {
        System.out.println("Enter Agent Name :: ");
        String agentName = console.nextLine();
        boolean isTicket = false;
        agentName.trim();
        System.out.println("Id | Subject | Agent Name | Tags | Created | Modified");
        for (Ticket ticket : masterTicketData) {
            if(ticket.getAgent_name().equals(agentName)){
                System.out.println(ticket.getId() + " | " + ticket.getSubject() + " | " + ticket.getAgent_name() + " | " + ticket.getTempTags() + " | " +
                        ticket.getCreated() + " | " + ticket.getModified());
                isTicket = true;
            }
        }

        if(isTicket == false) {
            System.out.println("No Ticket(s) assigned to Agent " + agentName);
        }
        this.menuList();
    }

    public void ticketCountsByAgentName(){
        System.out.println("Agent Count  |   Total Count");

        Set setOfKeys = agentTicketsTotalCounts.keySet();
        Iterator it = setOfKeys.iterator();

        while (it.hasNext()){
            String agentName = (String) it.next();
            Integer count = (Integer) agentTicketsTotalCounts.get(agentName);
            System.out.println(agentName + "     |     " + count);
        }
        this.menuList();
    }

    public void searchTicketsByTagName() {
        System.out.println("Enter Tag Name :: ");
        String tagName = console.nextLine();
        HashSet ids = new HashSet();
        if(this.tagsWithTicketIds.containsKey(tagName)) {
            ids = tagsWithTicketIds.get(tagName);
        }

        if(masterTicketData.isEmpty()){
            System.out.println("No ticket(s) found in the system");
        }
        else{
            System.out.println("Id | Subject | Agent Name | Tags | Created | Modified");
            for (Ticket ticket : masterTicketData){
                if(ids.contains(ticket.getId())){
                    System.out.println(ticket.getId() + " | " + ticket.getSubject() + " | " + ticket.getAgent_name() + " | " + ticket.getTempTags() + " | " +
                            ticket.getCreated() + " | " + ticket.getModified());
                }
            }
            this.menuList();
        }

    }

    public void updateTags(List<String> tagsList, int ticketId){
        for (String tag : tagsList) {
            if (this.tagsWithTicketIds.containsKey(tag)) {
                HashSet<Integer> idSet = new HashSet<Integer>();
                idSet = tagsWithTicketIds.get(tag);
                idSet.add(ticketId);
                this.tagsWithTicketIds.put(tag, idSet);
            } else {
                HashSet<Integer> idSet = new HashSet<Integer>();
                idSet.add(ticketId);
                this.tagsWithTicketIds.put(tag, idSet);
            }
        }
    }

    public void removeTicketTags(List<String> oldTicketTags, int ticketId){
        for (String tag : oldTicketTags){
            if(oldTicketTags.contains(tag)) {
                HashSet allTags = this.tagsWithTicketIds.get(tag);
                allTags.remove(ticketId);
            }
        }
    }
}
