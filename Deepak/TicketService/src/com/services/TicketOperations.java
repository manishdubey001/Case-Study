package com.services;

import com.factory.TicketFactory;
import com.model.Ticket;
import com.util.UserConsoleInput;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by root on 15/1/16.
 */
public class TicketOperations {

    private ArrayList<Ticket> ticketArrayList = null;
    public Set<String> allTagHashSet = null;

    public TicketOperations(){
        ticketArrayList = new ArrayList<>();
        allTagHashSet = new HashSet<>();
    }


    /*
    * To set parameters for Create ticket from console input */
    public void create(){
        String subject = null, agent_name =  null;
        String[] tag_names;
        Set<String> tagHashSet = new HashSet<>();

        subject = UserConsoleInput.getSubject();

        agent_name = UserConsoleInput.getAgentName();

        tagHashSet = UserConsoleInput.getTagNames();

        long status = createTicket(subject, agent_name, tagHashSet);
        if(status == 0){
            System.out.println("Sorry your ticket has not been created!");
        }
        else
        {
            System.out.println("Ticket create successful!");
        }
    }

    /*
    * Crating ticket using  */
    public long createTicket(String subject, String agent_name, Set<String> tagHashSet){

        Ticket ticket = null;
        if(subject == null || subject.isEmpty()){
            System.out.println("Please enter proper subject!");
        }
        else if(agent_name == null || agent_name.isEmpty()){
            System.out.println("Please enter proper Agent Name!");
        }
        else
        {
            ticket = TicketFactory.getTicketInstance(subject, agent_name, tagHashSet);
        }

        if(ticket != null) {
            if (ticketArrayList.add(ticket)) {
                return ticket.getId();
            } else {
                return 0;
            }
        }
        else
        {
            return 0;
        }
    }


    /*
    * Show all the tickets in ArrayList */
    public List<Ticket> showAllTicket(){
        return ticketArrayList;
        //showTickets(ticketArrayList);
    }


    /*
    * Show Ticket By Id */
    public List<Ticket> showTicketById(int id){

        /* if id beyond the limit */
        List<Ticket> tempTicketList = null;
        if(id <= 0 || id > Ticket.getCountId()){
            System.out.println("No ticket available for Ticket Id: "+id);
            return tempTicketList;
        }

        tempTicketList = new ArrayList<>();

        // Stream and Lambda implementation.
        Stream<Ticket> stream = ticketArrayList.stream();
        tempTicketList = stream.filter(lst -> lst.getId() == id).collect(Collectors.toList());

        return tempTicketList;

    }


    /*
    * Show the selected list of tickets */
    public void showTickets(List<Ticket> ticketList){
        if(ticketList == null || ticketList.size() == 0){
            System.out.println("No Ticket available!");
            return;
        }

        /*
        * Stream implementation of sorting and comparator */
        Stream<Ticket> stream = ticketList.stream();

        stream.sorted((Ticket t1, Ticket t2) -> Long.valueOf(t2.getModified()).compareTo(Long.valueOf( t1.getModified())))
              .forEach((Ticket ticket) -> System.out.println(
                      ticket.getId()+" | "+ticket.getSubject()+" | "+ticket.getAgent_name()+
                              " | "+ticket.getTags2()+" | "+ticket.getModified()));
   }


    /*
    * Inner class for sorting using comparator on modified date */
    class ModifiedComparator implements Comparator<Ticket>{

        @Override
        public int compare(Ticket t1, Ticket t2) {
            if(t1.getModified() < t2.getModified())
                return 1;
            else
                return -1;

        }
    }


    /*
    * To update ticket's agent name and tags by ticket id */
    public void updateTicketById(int id){
        if(id <= 0){
            System.out.println("Ticket id is not valid");
            return;
        }

        List<Ticket> ticketU = showTicketById(id);

        if(ticketU == null || ticketU.isEmpty()){
            System.out.println("Ticket with id: "+id+" is not available");
            return;
        }
        else{
            showTickets(ticketU);
        }


        String agent_name = "";
        String[] tag_names;
        Set<String> tagHashSet = new HashSet<>();

        agent_name = UserConsoleInput.getAgentName();
        tagHashSet = UserConsoleInput.getTagNames();

        Ticket ticket = updateTicket(id, agent_name, tagHashSet);

        if(ticket == null){
            System.out.println("Ticket update failed!");
        }
        else{
            System.out.println("Ticket update successful");
        }

    }


    public Ticket updateTicket(int id, String agent_name, Set<String> tagHashSet){

        if(id <= 0 || agent_name == null|| agent_name.isEmpty()){
            System.out.println("Please provide proper Agent Name!");
            return null;
        }

        long unixTime = System.currentTimeMillis() / 1000L;

        Optional optional = ticketArrayList.stream().filter(lst -> lst.getId() == id).findFirst();
        if(optional.isPresent()) {
            Ticket ticket = (Ticket) optional.get();
            ticket.setAgent_name(agent_name);
            ticket.setTags2(tagHashSet);
            ticket.setModified(unixTime);
            return ticket;
        }

        return null;

    }


    /*
    * Delete ticket by id*/
    public boolean deleteTicketById(int id){

        Optional optional = ticketArrayList.stream().filter(lst -> lst.getId() == id).findFirst();
        if(optional.isPresent())
            return ticketArrayList.remove(optional.get());

        return optional.isPresent();
    }


    /*
    * Search tickets assign to specific agent */
    public List<Ticket> searchTicketByAgent(){
        String agent_name = "";
        agent_name = UserConsoleInput.getAgentName();

        return searchTicketsWithAgent(agent_name);
    }


    public List<Ticket> searchTicketsWithAgent(String agent_name){

        List<Ticket> tempTicketList = new ArrayList<>();
        if(agent_name == null || agent_name.isEmpty()){
            return tempTicketList;
        }

        Stream<Ticket> stream = ticketArrayList.stream();

        tempTicketList = stream.filter(lst -> lst.getAgent_name().toLowerCase().equals(agent_name.toLowerCase())).collect(Collectors.toList());
        return tempTicketList;

    }


    /*
    * Search tickets by tag names*/
    public List<Ticket> searchTicketByTag(){
        Set<String> tagHashSet = new HashSet<>();
        tagHashSet = UserConsoleInput.getTagNames();

        return searchTicketsWithTags(tagHashSet);

    }


    public List<Ticket> searchTicketsWithTags(Set<String> tagHashSet){

        List<Ticket> tempTicketList = new ArrayList<>();
        Set<Ticket> tempTicketSet = new HashSet<>();

        if(tagHashSet == null || tagHashSet.isEmpty()){
            return tempTicketList;
        }

        // Above code using Stream and Lambda

        Stream<Ticket> stream = ticketArrayList.stream();

        tempTicketList = stream.filter(lst -> lst.getTags2().stream()
                                    .filter(tag -> tagHashSet.contains(tag))
                                    .collect(Collectors.toList()).size() > 0)
                                    .collect(Collectors.toList());

        return tempTicketList;

    }


    /*
    * NEW Search Ticket agent Count using lambda and stream
    * */
    public Map<String, List<Ticket>> calculateAgentTicketCount() {
        return new TreeMap<>(ticketArrayList.stream().collect(Collectors.groupingBy(Ticket::getAgent_name)));
    }


    /*
    * NEW Show agent and ticket count */
    public void showAgentTicketCount(Map<String, List<Ticket>> agentCountMap){
        agentCountMap.forEach((String agentName,List<Ticket> ticketList)-> System.out.println(agentName+"   :   "+ticketList.size()));
    }



    public List<Ticket> autoLoadTickets(int noOfTickets){

        Random rand = new Random();

        for(int i=0; i<noOfTickets; i++){
            //String agent = "Agent"+rand.nextInt(noOfTickets/10);

            String agent = "Agent"+rand.nextInt(50);
            String tag1 = "Tag"+rand.nextInt(10);
            String tag2 = "Tag"+rand.nextInt(10);
            Set<String> tagSet = new HashSet<>(Arrays.asList(tag1,tag2));

            String subject = "Subject"+i;

            Ticket ticket = TicketFactory.getTicketInstance(subject, agent, tagSet);
            ticketArrayList.add(ticket);
        }

        return ticketArrayList;
    }
}
