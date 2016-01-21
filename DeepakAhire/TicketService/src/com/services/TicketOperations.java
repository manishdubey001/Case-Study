package com.services;

import com.model.Ticket;

import java.util.*;

/**
 * Created by root on 15/1/16.
 */
public class TicketOperations {

    public ArrayList<Ticket> ticketArrayList = new ArrayList<>();
    public Set<String> allTagHashSet = new HashSet<>();


    /*
    * To Create ticket from console input */
    public void create(){
        String subject = null, agent_name =  null;
        String[] tag_names;
        Set<String> tagHashSet = new HashSet<>();

        subject = UserConsoleInput.getSubject();

        agent_name = UserConsoleInput.getAgentName();

        tagHashSet = UserConsoleInput.getTagNames();

        Ticket ticket = new Ticket(subject, agent_name, tagHashSet);

        ticketArrayList.add(ticket);
    }


    /*
    * Show all the tickets in ArrayList */
    public void showAllTicket(){
        showTickets(ticketArrayList);
    }


    /*
    * Show Ticket By Id */
    public List<Ticket> showTicketById(int id){

        /* if id beyond the limit */
        List<Ticket> tempTicketList = new ArrayList<>();
        if(id <= 0 || id > Ticket.getCountId()){
            System.out.println("No ticket available for Ticket Id: "+id);
            return tempTicketList;
        }


        for (int i = 0; i<ticketArrayList.size(); i++){
            if(ticketArrayList.get(i).getId() == id){
                //tempTicketList.add(ticketArrayList.get(i));
                tempTicketList = ticketArrayList.subList(i, i+1);
            }
        }

        return tempTicketList;

    }


    /*
    * Show the selected list of tickets */
    public void showTickets(List<Ticket> ticketList){
        if(ticketList.size() == 0 || ticketList == null){
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
        List<Ticket> tempTicketList = new ArrayList<>();
        tempTicketList = showTicketById(id);
        if(tempTicketList.size() == 0){
            System.out.println("No ticket available to update!s");
            return;
        }

        String agent_name = "";
        String[] tag_names;
        Set<String> tagHashSet = new HashSet<>();

        agent_name = UserConsoleInput.getAgentName();
        tagHashSet = UserConsoleInput.getTagNames();

        tempTicketList.get(0).setAgent_name(agent_name);
        tempTicketList.get(0).setTags2(tagHashSet);

        long unixTime = System.currentTimeMillis() / 1000L;
        tempTicketList.get(0).setModified(unixTime);

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

        if(tempTicketList.remove(0) != null)
            System.out.println("Ticket id: "+id +" remove successful ");
        else
            System.out.println("There is some problem in deletion pls try later!");
    }


    /*
    * Search tickets assign to specific agent */
    public List<Ticket> searchTicketByAgent(){
        String agent_name = "";
        agent_name = UserConsoleInput.getAgentName();
        List<Ticket> tempTicketList = new ArrayList<>();

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
        List<Ticket> tempTicketList = new ArrayList<>();
        Set<Ticket> tempTicketSet = new HashSet<>();

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
