package com.services;

import com.factory.TicketFactory;
import com.model.Ticket;
import com.util.UserConsoleInput;

import java.util.*;

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
    * Crating ticket using TicketFactory */
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

        for (int i = 0; i<ticketArrayList.size(); i++){
            if(ticketArrayList.get(i).getId() == id){
                tempTicketList = ticketArrayList.subList(i, i+1);
            }
        }

        return tempTicketList;

    }


    /*
    * Show the selected list of tickets */
    public void showTickets(List<Ticket> ticketList){
        if(ticketList == null || ticketList.size() == 0){
            System.out.println("No Ticket available!");
            return;
        }

        /* Modified as per modified date */
        Collections.sort(ticketList, new ModifiedComparator());

        for (Ticket ticket : ticketList){
            System.out.println(ticket.getId()+" | "+ticket.getSubject()+
                    " | "+ticket.getAgent_name()+" | "+ticket.getTags2()+
                    " | "+ticket.getStatus()+" | "+ticket.getModified()+" | "+ticket.getCreated());
        }
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

        String agent_name = "";
        String[] tag_names;
        Set<String> tagHashSet = new HashSet<>();

        agent_name = UserConsoleInput.getAgentName();
        tagHashSet = UserConsoleInput.getTagNames();

        int status = updateTicket(id, agent_name, tagHashSet);

        if(status == 0){
            System.out.println("Ticket update failed!");
        }
        else{
            System.out.println("Ticket update successful");
        }

    }


    public int updateTicket(int id, String agent_name, Set<String> tagHashSet){
        if(id <= 0 || agent_name == null|| agent_name.isEmpty()){
            System.out.println("Please provide proper Agent Name!");
            return 0;
        }

        List<Ticket> tempTicketList = new ArrayList<>();
        tempTicketList = showTicketById(id);
        if(tempTicketList.size() == 0){
            System.out.println("No ticket available to update!s");
            return 0;
        }

        long unixTime = System.currentTimeMillis() / 1000L;

        try {
            tempTicketList.get(0).setAgent_name(agent_name);
            tempTicketList.get(0).setTags2(tagHashSet);
            tempTicketList.get(0).setModified(unixTime);
        }
        catch (Exception e){
            System.out.println("Error in update ticket");
            return 0;
        }

        return id;

    }


    /*
    * Delete ticket by id*/
    public void deleteTicketById(int id){
        List<Ticket> tempTicketList = new ArrayList<>();
        tempTicketList = showTicketById(id);

        if(tempTicketList.size() == 0){
            System.out.println("No ticket available to delete");
            return;
        }

        if(tempTicketList.remove(0) != null){
            Ticket.setCountId((Ticket.getCountId() - 1));
            System.out.println("Ticket id: "+id +" remove successful ");
        }
        else {
            System.out.println("There is some problem in deletion pls try later!");
        }
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

        for (int i = 0; i<ticketArrayList.size(); i++){
            if(ticketArrayList.get(i).getAgent_name().toLowerCase().equals(agent_name.toLowerCase())){
                tempTicketList.add(ticketArrayList.get(i));
            }
        }
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

        for (int i= 0; i<ticketArrayList.size(); i++){
            for(String tag_name : tagHashSet){
                if(ticketArrayList.get(i).getTags2().contains(tag_name)){
                    tempTicketSet.add(ticketArrayList.get(i));
                }
            }
        }

        tempTicketList.addAll(tempTicketSet);
        return tempTicketList;
    }


    /*
    * Show ticket count by agent name */
    public Map<String, Integer> calculateAgentTicketCount() {
        Map<String, Integer> agentCountMap = new HashMap<String, Integer>();
        Set<String> agentsSet = new HashSet<>();
        int count = 0;

        for (int i = 0; i < ticketArrayList.size(); i++) {

            Integer temp = Integer.valueOf(count++);
            String tempStr = ticketArrayList.get(i).getAgent_name();

            if (agentsSet.add(tempStr)) {       // if agent name is new and added in agentsSet
                agentCountMap.put(tempStr, 1);
            }
            else
            {
                if (agentCountMap.containsKey(tempStr)) {       // if agents name is already in countMap
                    int te = (Integer)agentCountMap.get(tempStr);
                    agentCountMap.put(tempStr, ++te);
                }
            }

        }

        return agentCountMap;
    }



    /* To show agent and ticket count */
    public void showAgentTicketCount(Map<String, Integer> agentCountMap){

        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.putAll(agentCountMap);
        System.out.println("Agents  :  Ticket Count");
        for(Map.Entry<String, Integer> entry : treeMap.entrySet()){
            System.out.println(entry.getKey().toString()+"    :    "+entry.getValue());
        }
    }


}
