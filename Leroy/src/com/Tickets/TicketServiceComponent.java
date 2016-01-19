package com.Tickets;

import java.util.*;

/**
 * Created by root on 14/1/16.
 */
public class TicketServiceComponent {
    public Map<Integer, Ticket> thm = new HashMap<>();

    public boolean createTicket(String sub, String agent, String tags){
        if(!sub.isEmpty() && !agent.isEmpty() && !tags.isEmpty()){
            String[] tag = tags.split(",");
            Set set = new HashSet<>();
            for (int index = 0; index<tag.length; index++)
                set.add(tag[index]);

            Ticket ticket = TicketWareHouse.getInstance(sub,set,agent);

            thm.put(ticket.getId(),ticket);
            if (checkIfExists(ticket.getId())) {
                System.out.println(Sout.ACT_TICKETS_IN_SYSTEM);
                System.out.println(thm.get(ticket.getId()));
                return true;
            }
        }

        return false;
    }

    public void updateTicket(int id, String type, String val){
        try{
            Ticket t3 = thm.get(id);
            if (type.equals("agent")) {
                t3.setAgent_name(val);
                t3.setModified(System.currentTimeMillis());
                thm.put(id, t3);

            } else if (type.equals("tags")) {
                String [] tags1 = val.split(",");
                Set set1 = new HashSet<>();
                for (int index = 0; index <tags1.length; index++)
                    set1.add(tags1[index]);

                t3.setTags(set1);

                t3.setModified(System.currentTimeMillis());
                thm.put(id,t3);
            }
                System.out.println(Sout.ACT_TICKETS_IN_SYSTEM);
                List<Ticket> listTickets2 = new ArrayList<>(thm.values());
                this.display(listTickets2);

        }catch(InputMismatchException Im){
            System.out.println(Sout.ACT_INVALID+Im);
        }

    }

    public void removeTicketById(int id){
        if (id > 0){
            thm.remove(id);
            System.out.println(Sout.ACT_REMOVE_SUCCESS+id);
        }
        System.out.println(Sout.ACT_TICKETS_IN_SYSTEM);
        List<Ticket> listTickets2 = new ArrayList<>(thm.values());
        this.display(listTickets2);
    }

    /**
     * Function to fetch a ticket by it Id
     * @param thm
     */
    public void getAllTicketsById(Map<Integer, Ticket> thm){
        System.out.println(Sout.ACT_TICKETS_IN_SYSTEM);
        List<Ticket> listTickets2 = new ArrayList<>(thm.values());
        this.display(listTickets2);
        System.out.println(Sout.ACT_TID);

        int selT = scanWhat().nextInt();
        if (thm.containsKey(selT)){
            System.out.println(Sout.ACT_TABLE_HEADER);
            Ticket t3 = thm.get(selT);
            System.out.println(t3);
        }
    }

    /**
     * Function to fetch tickets by Agent name
     * @param thm
     */
    public void getTicketsByAgentName(Map<Integer, Ticket> thm){
        System.out.println(Sout.ACT_TAGENTNAME);
        String selA = scanWhat().next();

        List<Ticket> c = new ArrayList<>(thm.values());
        List<Ticket> l = new ArrayList<>();
        for (int index = 0; index < c.size(); index++){
            if((c.get(index).agent_name).equals(selA)){
                l.add(c.get(index));
            }
        }

        if(!l.isEmpty()){
            this.display(l);
        }

    }

    /**
     * Function to fetch all tickets present in the system
     * @param thm
     */
    public void getAllTickets(Map<Integer, Ticket> thm){
        List<Ticket> t3 = new ArrayList<>(thm.values());
        this.display(t3);
    }

    /**
     * function to fetch count of ticket with respect to its agent.
     * Data is grouped by Agent name in ascending order.
     * @param thm
     */
    public void getTicketsGroupByAgent(Map<Integer, Ticket> thm){
        List<Ticket> l = new ArrayList<>(thm.values());
        Collections.sort(l,Ticket.ByAgentNameComparator);
        Map<String,Integer> map = new HashMap<String, Integer>();
        for (int index = 0; index < l.size(); index++){
            int countTicket = map.containsKey(l.get(index).agent_name) ? map.get(l.get(index).agent_name) : 0;
            map.put(l.get(index).agent_name, countTicket+ 1);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet())
            System.out.println(entry.getKey()+" : "+entry.getValue());
    }

    /**
     * Function to fetch all ticket with respect to Tag.
     * @param thm
     */
    public void getAllTicketsByTag(Map<Integer, Ticket> thm){
        List<Ticket> l = new ArrayList<>(thm.values());
        Map<String, Set> map = new HashMap<>();
        System.out.println(Sout.ACT_TTAGS_SINGLE);
        String selT = scanWhat().next();
        Set set2 = new HashSet<>();
        for (int index = 0; index<l.size(); index++){
            if(l.get(index).getTags().contains(selT))
                set2.add(l.get(index));
        }
        if(!set2.isEmpty()){
            List<Ticket> t3 = new ArrayList<>(set2);
            Collections.sort(t3);
            System.out.println(Sout.ACT_TABLE_HEADER);

            for (Ticket str: t3) {
                System.out.println(str);
            }
        }

    }

    public void display(List<Ticket> t3){
        Collections.sort(t3);
        System.out.println(Sout.ACT_TABLE_HEADER);

        for (Ticket str: t3) {
            System.out.println(str);
        }
    }

    public boolean checkIfExists(int id){
        return (thm.containsKey(id)) ? true : false;
    }
    public static Scanner scanWhat(){
        Scanner sc = new Scanner(System.in);
        return sc;
    }
}
