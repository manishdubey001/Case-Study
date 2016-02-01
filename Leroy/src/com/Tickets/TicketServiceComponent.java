package com.Tickets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Created by root on 14/1/16.
 */
public class TicketServiceComponent{
    public boolean print = true;
    public HashMap<Integer, Ticket> thm = new HashMap<>();
    public boolean createTicket(String sub, String agent, String tags){
        if(!sub.isEmpty() && !agent.isEmpty() && !tags.isEmpty()){
            String[] parsedtags = tags.split(",");
            Set<String> set = new HashSet<>(Arrays.asList(parsedtags));

            Ticket ticket = TicketFactoryClass.getInstance(sub,set,agent);

            thm.put(ticket.getId(),ticket);

            if (checkIfExists(ticket.getId())) {
                return true;
            }
        }

        return false;
    }

    public boolean updateTicket(int id, String type, String val){
        try{
            boolean updated = false;
            Ticket ticket = thm.get(id);
            if (ticket != null){
                if (type.equals("agent") && !val.isEmpty()) {
                    ticket.setAgent_name(val);
                    thm.put(id, ticket);
                    updated = true;
                } else if (type.equals("tags") && !val.isEmpty()) {
                    String [] parsedtags = val.split(",");
                    Set set1 = new HashSet<>(Arrays.asList(parsedtags));

                    ticket.setTags(set1);

                    thm.put(id,ticket);
                    updated = true;
                }
                if (updated){
                    return true;
                }
            }
        }catch(InputMismatchException Im){
            System.out.println(Sout.ACT_INVALID+Im);
            return false;
        }catch (NullPointerException Null){
            return false;
        }
        return false;
    }

    public boolean removeTicketById(int id){
        if (id > 0){
            if (checkIfExists(id)){
                thm.remove(id);
                return true;
            }
        }
        return false;
    }


    public Ticket getTicketById(int id){

        boolean check = checkIfExists(id);
        if (check){
            Ticket t3 = thm.get(id);
            return t3;
        }
        return null;
    }

    public List<Ticket> getTicketsByAgentName(String name){
        List<Ticket> l = new ArrayList<>();
        if (!name.isEmpty()){
            l = thm.values().stream()
                    .filter(l2 -> l2.getAgent_name().equals(name))
                    .collect(Collectors.toList());
        }
        return l;// sorted by modified date
    }

    public List getAllTickets(){
        List<Ticket> t3 = new ArrayList<>();
        if (!thm.isEmpty()){
            t3 = new ArrayList<>(thm.values());
            if (print){
                this.display(t3);
            }
            return t3;
        }else {
            System.out.println(Sout.ACT_NOT_FOUND);
        }
        return t3;
    }

    /**
     * function to fetch count of ticket with respect to its agent.
     * Data is grouped by Agent name in ascending order.
     */
    public Map<String,Integer> getTicketsGroupByAgent(){
        Map<String, Integer> agentsTickets = new TreeMap<>();
        if (!thm.isEmpty()){

            List<Ticket> l = new ArrayList<>(thm.values());
            Collections.sort(l,Ticket.ByAgentNameComparator);
            for (int index = 0; index < l.size(); index++){
                int countTicket = agentsTickets.containsKey(l.get(index).getAgent_name()) ? agentsTickets.get(l.get(index).getAgent_name()) : 0;
                agentsTickets.put(l.get(index).getAgent_name(), countTicket+ 1);
            }
            if (print){
                for (Map.Entry<String, Integer> entry : agentsTickets.entrySet())
                    System.out.println(entry.getKey()+" : "+entry.getValue());
            }
        }else {
            System.out.println(Sout.ACT_NOT_FOUND);
        }
        return agentsTickets;
    }

    /**
     * Function to fetch all ticket with respect to Tag.
     */
    public Set<Ticket> getAllTicketsByTag(String tag){
        Set<Ticket> set2 = new HashSet<>();
        if (!thm.isEmpty()){

            List<Ticket> l = new ArrayList<>(thm.values());
            set2 = l.stream()
                    .filter(list -> list.getTags().contains(tag))
                    .collect(Collectors.toSet());
            if(!set2.isEmpty()){
                List<Ticket> t3 = new ArrayList<>(set2);
                Collections.sort(t3);
                if (print){
                    System.out.println(Sout.ACT_TABLE_HEADER);
                    for (Ticket str: t3) {
                        System.out.println(str);
                    }
                }
            }else {
                System.out.println(Sout.ACT_NOT_FOUND);
            }
        }
        return set2;
    }

    public void display(List<Ticket> tickets){
        Collections.sort(tickets);
        System.out.println(Sout.ACT_TABLE_HEADER);

        for (Ticket str: tickets) {
            System.out.println(str);
        }
    }

    public boolean checkIfExists(int id){
        return (thm.containsKey(id)) ? true : false;
    }

    public Ticket prepareTicketDataForFile(){
        String sub = "Test Subject";
        String agent = "agent1";
        Set<String> set = new HashSet<String>();
        set.add("Tag1");
        set.add("Tag2");

        Ticket ticket = TicketFactoryClass.getInstance(sub,set,agent);
        return ticket;
    }

    public List<Ticket> prepareMultipleTicketDataForFile(){
        List<Ticket> listTickets = new ArrayList<>();
        for (int i = 1; i <=5; i++){
            Set<String> set = new HashSet<String>();
            String sub = "Test Subject"+i;
            String agent = "agent"+i;

                if (i > 3)
                    set.add("Tag"+i);
                else
                    set.add("Tag");

            listTickets.add(new Ticket(i,sub,set,agent,true));
        }
        return listTickets;
    }

    public void getNumberOfTicketsInSystem(List<Ticket> list){
        System.out.println("Total Tickets present are :"+list.size());
    }

    public void getOldestTicketInSystem(List<Ticket> list){
        Comparator<Ticket> sortByAscTickets = (e1, e2) -> e1.getModified().compareTo(e2.getModified());
        java.util.Optional<Ticket> ticket = list.stream()
            .sorted(sortByAscTickets).findFirst();

        System.out.println("\nOldest Ticket in the System:");
        System.out.println(ticket.get());
    }

    public void getCountOfTags(List<Ticket> list){
        Map<String, Integer> map = new TreeMap<>();
        int i = 1;
        for (Ticket t: list) {
            Set<String> tags = t.getTags();
            for (String s : tags) {
                if (map.containsKey(s)) {
                    i = map.get(s)+1;
                    map.put(s, i);
                } else {
                    map.put(s, 1);
                }
            }
        }
        System.out.println("\nTotal count of Tags used in tickets:");
        for (Map.Entry<String, Integer> entry : map.entrySet())
            System.out.println(entry.getKey()+" : "+entry.getValue());

    }


    public void getDateDiff(int noOfDays, List<Ticket> ticketList){
            LocalDateTime l2 = LocalDateTime.now().minusDays(noOfDays);
            List<Ticket> newList = new ArrayList();
            for (Ticket ticket : ticketList){
                if (ticket.getCreated().isBefore(l2)){
                    newList.add(ticket);
                }
            }
                if (!newList.isEmpty()){
                    this.display(newList);
                }
    }
}
