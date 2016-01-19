package com.yogesh;


import java.util.*;

/**
 * Created by root on 31/12/15.
 */
public class CaseStudy {
    // cjm - think about creating at least a couple of classes from this one. One to manage the menu, and another to manage the ticket list

    ArrayList<Ticket> arrTicketList = new ArrayList();
    HashMap<String, HashSet> hmticketTags = new HashMap<>();
    TreeMap<String, Integer> tmCount = new TreeMap<>();
    // cjm - It can be complicated to maintain parallel collections like this; consider just searching/sorting the tickets array instead.
    Scanner scanIn;

    public CaseStudy() {
        scanIn = new Scanner(System.in);
        this.displayList();
    }

    /**
     * Select Operation
     *
     * @param menuId
     */
    private void operation(int menuId) {
        switch (menuId) {
            case 1:
                this.createTicket();
                break;
            case 2:
                this.updateTicket();
                break;
            case 3:
                this.showAllTicket();
                break;
            case 4:
                this.showSingleTicket();
                break;
            case 5:
                this.searchTicketsUsingtag();
                break;
            case 6:
                this.showTicketcountAgent();
                break;
            case 7:
                this.removeTicket();
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

    private void exit() {
        scanIn = null;
        arrTicketList = null;
        hmticketTags = null;
        tmCount = null;
        System.exit(0);
    }

    /**
     * this method for search ticket based on Agent name
     */
    private void searchTicketsUsingAgentname() {
        boolean ticketFlag = false;
        System.out.println("Enter Agent name ");
        String agentName = scanIn.next();
        System.out.println("Id  |  Subject  | Agent Name |  Tags  |      Created      |   Modified    ");
        System.out.println();
        for (Ticket ticket : this.arrTicketList) {
            if (ticket.getAgentName().equals(agentName)) {
                System.out.println(ticket.getId() + " | " + ticket.getSubject() + "  |   " + ticket.getAgentName() + "  |   " + ticket.getTags() + "  |  " + ticket.getCreated() + "  |  " + ticket.getModified());
                ticketFlag = true;
            }
        }

        if (ticketFlag == false) {
            System.out.println("Ticket Not found");
        }
        this.msg();

    }

    /**
     * this method for remove specific Ticket by id
     */
    private void removeTicket() {

        System.out.println("Enter Ticket Id  ");
        int id = 0;
        try {
            id = (scanIn.nextInt());
        } catch (InputMismatchException ie) {
            System.err.println("Please enter the Number");
            scanIn.nextLine();
            this.displayList();
        }

        if (isTicketIdExit(id)) {
            for (Ticket ticket : this.arrTicketList) {
                if (ticket.getId() == id) {
                    this.updateCount(ticket.getAgentName());
                    List<String> oldTagsList = ticket.getTags();
                    this.removeHmTicketTags(oldTagsList, id);
                    this.arrTicketList.remove(ticket);
                    System.out.println(" Ticket deleted Successfully");
                    break;
                }
            }
        }
        else
        {
            System.out.println("Ticket Not Fount");
        }

        this.msg();
    }


    /**
     * this method for Update agent no of ticket count  TreeMap
     */
    private void updateCount(String agentName) {

        if (tmCount.containsKey(agentName)) {
            int agCount = tmCount.get(agentName);
            agCount--;
            this.tmCount.put(agentName, agCount);
        }

    }

    /**
     * this method for Ticket count grouped by agent name(order by agent name).
     */
    private void showTicketcountAgent() {

        System.out.println("Agent Count  =>   Total Count");
        for (Map.Entry<String, Integer> entry : this.tmCount.entrySet()) {
            String agentName = entry.getKey();
            Integer count = entry.getValue();
            System.out.println(agentName + " => " + count);
        }
        this.msg();

    }

    /**
     * Search all tickets by specific tag.
     */
    private void searchTicketsUsingtag() {

        System.out.println("Enter tag ");
        String tag = scanIn.next();
        HashSet hsIds = new HashSet();
        if (this.hmticketTags.containsKey(tag)) {
            hsIds = this.hmticketTags.get(tag);
        }

        if (this.arrTicketList.isEmpty()) {
            System.out.println("No record Found");
        } else {
            System.out.println("Id  |  Subject  | Agent Name |  Tags  |      Created      |   Modified    ");
            System.out.println();
            for (Ticket ticket : this.arrTicketList) {
                if (hsIds.contains(ticket.getId())) {
                    System.out.println(ticket.getId() + " | " + ticket.getSubject() + "  |   " + ticket.getAgentName() + "  |   " + ticket.getTags() + "  |  " + ticket.getCreated() + "  |  " + ticket.getModified());
                    break;
                }
            }
        }
        this.msg();

    }

    /**
     * Select single Ticket by id
     */
    private void showSingleTicket() {
        boolean ticketFlag = false;
        System.out.println("Enter ticket Id ");
        int id = 0;
        try {
            id = (scanIn.nextInt());
        } catch (InputMismatchException ie) {
            System.err.println("Please enter the Number");
            scanIn.nextLine();
            this.displayList();
        }
        System.out.println("Id  |  Subject  | Agent Name |  Tags  |      Created      |   Modified    ");
        System.out.println();
        for (Ticket ticket : this.arrTicketList) {

            if (ticket.getId() == id) {
                System.out.println(ticket.getId() + " | " + ticket.getSubject() + "  |   " + ticket.getAgentName() + "  |   " + ticket.getTags() + "  |  " + ticket.getCreated() + "  |  " + ticket.getModified());
                ticketFlag = true;
                break;
            }
        }
        if (ticketFlag == false) {
            System.out.println("Ticket Not found");
        }
        this.msg();
    }


    /**
     * Select all Tickets
     */
    private void showAllTicket() {

        // sort is based on modified date field Descending Order
        // cjm - nice use of Comparable; however, what if you wanted to sometimes sort by other fields?
        Collections.sort(this.arrTicketList);

        System.out.println("Id  |  Subject  | Agent Name |  Tags  |      Created      |   Modified    ");
        System.out.println();

        if (this.arrTicketList.isEmpty()) {
            System.out.println("No record Found");
        } else {
            for (Ticket ticket : this.arrTicketList) {
                System.out.println(ticket.getId() + " | " + ticket.getSubject() + "  |   " + ticket.getAgentName() + "  |   " + ticket.getTags() + "  |  " + ticket.getCreated() + "  |  " + ticket.getModified());
            }
        }
        this.msg();

    }

    /**
     * Update Ticket by id ( only update agent name and tags for ticket)
     */
    private void updateTicket() {

        System.out.println("Enter ticket Id ");

        int id = 0;
        try {
            id = scanIn.nextInt();
        } catch (InputMismatchException ie) {
            System.err.println("Please enter the Number");
            scanIn.nextLine();
            this.displayList();
        }

        if (this.isTicketIdExit(id)) {
            System.out.println("Press a to update Agent name  ");
            String updatedId = (scanIn.next());
            if (updatedId.equals("a")) {
                this.updateAgentName(id);
            }

            System.out.println("Press t to update Tags  ");
            String updatedTag = scanIn.next();
            if (updatedTag.equals("t")) {
                this.updateTags(id);
            }
        } else {
            System.out.println("Ticket is not Exists");
        }

        this.msg();
    }

    /**
     * @param id
     */
    private void updateTags(int id) {

        System.out.println("Enter tags (multiple tags separate by Comma) ");

        String newTags = scanIn.next();
        Date date = new Date();
        List<String> newlist = Arrays.asList(newTags.split(","));

        if (!newlist.isEmpty()) {
            this.updatehmtags(newlist, id);

            for (Ticket ticket : this.arrTicketList) {
                if (ticket.id == id) {
                    List<String> oldTagsList = ticket.getTags();
                    this.removeHmTicketTags(oldTagsList, id);
                    ticket.setTags(newlist);
                    ticket.setModified(date);
                    ticket.setTimestamp(date.getTime());
                    this.updatehmtags(newlist, id);
                    System.out.println("Ticket Tags has been updated");
                    break;
                }
            }

        }
    }

    /**
     * this methode for remove tags form ticket mapping hashmap
     *
     * @param oldTagsList
     * @param id
     */
    private void removeHmTicketTags(List<String> oldTagsList, int id) {

        for (String oldtag : oldTagsList) {
            if (this.hmticketTags.containsKey(oldtag)) {
                HashSet al = this.hmticketTags.get(oldtag);
                al.remove(id);
            }
        }
    }


    /**
     * this method for update agent Name
     *
     * @param id
     */
    private void updateAgentName(int id) {

        Date date = new Date();
        System.out.println("press enter  Agent name  ");
        String newAgentName = (scanIn.next());

        for (Ticket ticket : this.arrTicketList) {
            if (ticket.id == id) {
                if (!ticket.getAgentName().equals(newAgentName)) {
                    this.updateAgentNameInTmCount(ticket.getAgentName(), newAgentName);
                    ticket.setAgentName(newAgentName);
                    ticket.setModified(date);
                    ticket.setTimestamp(date.getTime());
                    System.out.println(" Agent name has been updated ");
                }
                break;
            }
        }
    }

    /**
     * update Agent Name in treemap hashmap
     *
     * @param oldName
     * @param newName
     */
    private void updateAgentNameInTmCount(String oldName, String newName) {

        if (this.tmCount.containsKey(oldName)) {
            int tCount = this.tmCount.get(oldName);
            tmCount.put(newName, tCount);
            tmCount.remove(oldName);
        }
    }

    /**
     * this method for create new ticket
     */
    private void createTicket() {


        int id = 0;
        System.out.println("Enter ticket Id ");
        try {
            id = (scanIn.nextInt());
        } catch (InputMismatchException ie) {
            System.err.println("Please enter the Number");
            scanIn.nextLine();
            this.displayList();
        }

        System.out.println("Enter ticket Subject ");
        String subject = scanIn.next(); // cjm - Note that this will not allow subjects with spaces
        System.out.println("Enter Agent Name ");
        String agentName = scanIn.next();


        System.out.println("Enter tags (multiple tags separate by Comma) ");
        String tags = scanIn.next();

        //ArrayList<String> list = (ArrayList<String>) Arrays.asList(tags.split(","));
        List<String> list = Arrays.asList(tags.split(","));

        if (!list.isEmpty()) {
            this.updatehmtags(list, id);
        }

        this.updatetmCounts(agentName);

        Date date = new Date();

        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setSubject(subject);
        ticket.setAgentName(agentName);
        ticket.setCreated(date);
        ticket.setModified(date);
        ticket.setTags(list);
        ticket.setTimestamp(date.getTime());

        if (this.isTicketIdExit(id)) {
            System.out.println("ticket Id is already Exist");
        } else {
            this.arrTicketList.add(ticket);
            System.out.println("Ticket has been added successfully");
        }
        this.msg();


    }

    /**
     * update counts in agent total ticket count TreeMap
     *
     * @param agentName
     */
    private void updatetmCounts(String agentName) {

        if (tmCount.containsKey(agentName)) {
            int agCount = tmCount.get(agentName);
            this.tmCount.put(agentName, agCount + 1);
        } else {
            this.tmCount.put(agentName, 1);
        }
    }

    /**
     * this method for add ticket id and tags to hashmap
     *
     * @param list
     * @param id
     */
    private void updatehmtags(List<String> list, int id) {

        for (String tag : list) {
            if (this.hmticketTags.containsKey(tag)) {
                System.out.println(tag);
                HashSet<Integer> idSet = new HashSet<>();
                idSet = hmticketTags.get(tag);
                idSet.add(id);
                this.hmticketTags.put(tag, idSet);
            } else {
                HashSet<Integer> idSet = new HashSet<>();
                idSet.add(id);
                this.hmticketTags.put(tag, idSet);
            }

        }

    }

    /**
     * this method is for check ticket exist or not
     *
     * @param id
     * @return
     */
    private boolean isTicketIdExit(int id) {

        for (Ticket ticket : this.arrTicketList) {
            if (ticket.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * this method handle continue process or exit
     */
    private void msg() {

        System.out.println("Please Press 1 : Continue   ");
        int msgId = 0;
        try {
            msgId = scanIn.nextInt();
        } catch (InputMismatchException ie) {

        }
        if (msgId == 1) {
            this.displayList();
        } else {
            System.out.println("thank you");
        }
    }

    /**
     * Display Menu List
     */
    void displayList() {
        System.out.println(" ***************Select Operation******************");
        System.out.println("1. Create Ticket");
        System.out.println("2. Update Ticket");
        System.out.println("3. Show All Tickets");
        System.out.println("4. Show Single Ticket");
        System.out.println("5. Search all tickets by specific tag");
        System.out.println("6. Ticket count grouped by agent name(order by agent name)");
        System.out.println("7. Delete/Remove Ticket by id");
        System.out.println("8. Select tickets assigned to specific agent");
        System.out.println("9. exit");

        int menuId = this.getInput();
        this.operation(menuId);
    }

    /*
    get input for select operation
     */
    int getInput() {
        System.out.println("Select Operation ");
        int menuId = 0;

        try {
            menuId = scanIn.nextInt();
        } catch (InputMismatchException ie) {
            System.err.println("Enter the Number");
            scanIn.nextLine();
            this.displayList();
        }

        return menuId;
    }


    public static void main(String[] args) {
        new CaseStudy();
    }
}

