package com.ticket;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 18/1/16.
 */

// "Base" usually means "Base Class". I would call this TicketService or TicketStore or something similar.
public class TicketService {
    // Just store this as a List<Ticket>
    // Also, consider if List is the right structure at all--it means you always have to search (potentially)
    // the whole list to find a ticket.
    //UPDATE:
    static Map<Integer, Ticket> ticketList = new HashMap<>();

    public void createTicketProcess(){
        System.out.println("Enter the number of tickets you want to create");
        try
        {
            Scanner read = new Scanner(System.in);
            int numberOfTickets = read.nextInt();

            for (int j = 0; j < numberOfTickets; j++){
                System.out.println("please enter ticket id");
                //Taking input from user
                int ticketId = read.nextInt();

                System.out.println("please enter subject");
                //Taking input from user
                String subject = read.next();

                Ticket ticketModel = createTicket(ticketId, subject, getTicketAgentName(), getTicketTags());

                if(ticketModel != null){
                    System.out.println("Ticket created successfully !!");
                }else {
                    System.out.println("This Ticket ID already exist!! Please use different ticket id");
                }
            }
        }
        catch (InputMismatchException ime) {
            System.out.println("Invalid input...please try again");
        }
    }

    public void updateTicketProcess(){
        System.out.println("Please enter ticket id to update");
        try{
            Scanner read = new Scanner(System.in);
            int ticketId = read.nextInt();

            System.out.println("Do want to change agent name? press 'y' for yes or 'n' for no");

            String agentName = null;
            if(read.next().equals("y")){
                agentName = getTicketAgentName();
            }

            System.out.println("Do want to change tags of ticket? press 'y' for yes or 'n' for no");
            Set<String> setOfTags = null;
            if(read.next().equals("y")){
                //UPDATE
                setOfTags = getTicketTags();
            }

            Ticket ticketModel = updateTicket(ticketId, agentName, setOfTags);
            if(ticketModel != null){
                System.out.println(ticketModel);
            }else{
                System.out.println("Ticket does not exist! Please try again");
            }
        }catch (InputMismatchException ime){
            System.out.println("Invalid input...please try again");
        }
    }

    public void deleteTicketProcess(){
        Scanner read = new Scanner(System.in);
        System.out.println("Please enter ticket id to delete");
        try{
            int ticketId = read.nextInt();
            if(deleteTicket(ticketId)){
                System.out.println("Deleted successfully");
            }else {
                System.out.println("Ticket does not exist! Please try again");
            }
        }catch (InputMismatchException ime){
            System.out.println("Invalid input...please try again");
        }
    }

    public void detailTicketProcess(){
        Scanner read = new Scanner(System.in);
        System.out.println("Please enter ticket id to get detail");
        try {
            int ticketId = read.nextInt();
            Ticket ticketModel = detail(ticketId);
            if(ticketModel != null) {
                System.out.println(ticketModel);
            }else {
                System.out.println("Ticket does not exist! Please try again");
            }
        }catch (InputMismatchException ime){
            System.out.println("Invalid input...please try again");
        }
    }

    public  void getTicketListProcess(){
        List<Ticket> ticketList = getTicketList();
        if(ticketList.isEmpty()){
            System.out.println("List is empty");
        }else{
            //UPDATE:
            printTicketListValues(ticketList);
        }
    }

    public void getTicketListByAgent(){
        Scanner read = new Scanner(System.in);
        System.out.println("Please enter agent name to search");
        try {
            String agentName = read.next();
            List<Ticket> ticketList  = agentListTicket(agentName);
            if(ticketList.isEmpty()){
                System.out.println("No ticket available for this agent");
            }else{
                //UPDATE:
                printTicketListValues(ticketList);
            }
        }catch (InputMismatchException ime){
            System.out.println("Invalid input...please try again");
        }
    }

    public void getTicketListByTag(){
        Scanner read = new Scanner(System.in);
        System.out.println("Please enter tag to search");
        try {
            String tag = read.next();
            List<Ticket> ticketList = tagListTicket(tag);
            if(ticketList.isEmpty()){
                System.out.println("NO ticket available for this tag");
            }else{
                //UPDATE:
                printTicketListValues(ticketList);
            }
        }catch (InputMismatchException ime){
            System.out.println("Invalid input...please try again");
        }
    }

    public void getAgentTicketCount(){
        Map<String, Integer> agentTicketCount  = ticketCountByAgent();
        if(agentTicketCount == null){
            System.out.println("Ticket list is empty");
        }else {
            System.out.println("Below is the list that contains ticket count by agent");
            //UPDATE:
            printAgentTicketCount(agentTicketCount);
        }
    }

    //Need to put try catch statement in below two functions
    public static Set<String> getTicketTags(){
        //Declaring scanner variable to take input from user.
        Scanner read = new Scanner(System.in);
        System.out.println("Please enter the number of tags you want to enter");

        Set<String> setOfTags = new HashSet<>();

        //Taking input from user
        int numberOfTags = read.nextInt();
        for (int i=0; i< numberOfTags; i++){
            System.out.println("please enter tag");
            setOfTags.add(read.next());
        }
        return setOfTags;
    }

    public static  String getTicketAgentName(){
        //Declaring scanner variable to take input from user.
        Scanner read = new Scanner(System.in);
        System.out.println("please enter agent name");
        //Taking input from user
        return read.next();
    }

    /**
     * Create ticket
     * @param ticketId
     * @param subject
     * @param agentName
     * @param setOfTags
     * @return ticket object
     */
    public Ticket createTicket(int ticketId, String subject, String agentName, Set<String> setOfTags){
        if(ticketList.get(ticketId) == null){
            //UPDATE
            Ticket ticketModel = Ticket.newInstance(ticketId, subject, agentName, new HashSet<>(setOfTags));
            ticketList.put(ticketId, ticketModel);
            return ticketModel;
        }
        return null;
    }

    /**
     * Get ticket detail by id
     * @param ticketId
     * @return ticket object
     */
    public Ticket detail(int ticketId){
        // no reason for the if statement; the for loop
        // will not execute if the condition fails.

        //Also, the more modern way to iterate would be
        // for(Ticket t : ticketList) { if (t.getId() == ticketId) ...
        // But see comments above about choosing a different
        // collection for tickets.

        //UPDATE:
        return ticketList.get(ticketId);
    }

    /**
     * Update ticket by id
     * @param ticketId
     * @param agentName
     * @param setOfTags
     * @return ticket object
     */
    public Ticket updateTicket(int ticketId, String agentName, Set<String> setOfTags){
        Ticket ticket = ticketList.get(ticketId);
        if(ticket != null){
            if(agentName != null){
                ticket.setAgentName(agentName);
            }
            if(setOfTags.size() > 0){
                ticket.setTags(setOfTags);
            }
            return ticket;
        }
        return null;
    }

    /**
     * Delete / Remove ticket by id
     * @param ticketId
     * @return boolean
     */
    public boolean deleteTicket(int ticketId){
        //UPDATE
        return ticketList.remove(ticketId) != null ;
    }

    /**
     * List all tickets sorted by modified date
     * @return list of tickets
     */
    // Generally return interfaces rather than concrete collection types;
    // in this case return a List<Ticket>
    // Also, I would not use a 'searchType' parameter. I would implement separate methods
    // (you could implement a common method with a comparator or Predicate.

    //UPDATE
    public List<Ticket> getTicketList(){
        List<Ticket> ticketValues = new ArrayList<>(ticketList.values());
        Collections.sort(ticketValues);
        return ticketValues;
    }

    /**
     * List tickets search by agent sorted by modified date
     * @return list of tickets
     */
    public List<Ticket> agentListTicket(String agentName){
        List<Ticket> ticketValues = new ArrayList<>(ticketList.values());
        return ticketValues.stream().sorted().filter( ticket -> ticket.getAgentName().equalsIgnoreCase(agentName) ).collect(Collectors.toList());
    }

    /**
     * List tickets search by tags sorted by modified date
     * @return list of tickets
     */
    public List<Ticket> tagListTicket(String tags){
        List<Ticket> ticketValues = new ArrayList<>(ticketList.values());
        return ticketValues.stream().sorted().filter( ticket -> ticket.getTags().contains(tags) ).collect(Collectors.toList());
    }

    /**
     * Ticket count group by agent name order by agent name
     */
    public Map<String, Integer> ticketCountByAgent(){
            // note that on the right-hand side you do not need to
            // specify the parameter again; you can just say "new ArrayList<>();"

            //UPDATE:

            TreeMap<String, Integer> treeMap = new TreeMap<>();
            for (Ticket ticket : ticketList.values()){
                if (treeMap.containsKey(ticket.getAgentName())){
                    treeMap.put(ticket.getAgentName(), treeMap.get(ticket.getAgentName()) + 1);
                }else {
                    treeMap.put(ticket.getAgentName(), 1);
                }
            }
            return treeMap;
    }

    //UPDATE:
    public void printTicketListValues(List<Ticket> ticketList){
        //UPDATE:
        ticketList.forEach(System.out::println);
    }

    //UPDATE:
    public void printAgentTicketCount(Map<String, Integer>agentTicketCount){
        agentTicketCount.forEach((agent, count) -> System.out.println("Agent : " + agent + " Count : " + count));
    }
}
