package com.Tickets;

import sun.invoke.empty.Empty;

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

    public boolean updateTicket(int id, String type, String val){
        try{
            boolean updated = false;
            Ticket t3 = thm.get(id);
            if (type.equals("agent") && !val.isEmpty()) {
                t3.setAgent_name(val);
                t3.setModified(System.currentTimeMillis());
                thm.put(id, t3);
                updated = true;
            } else if (type.equals("tags") && !val.isEmpty()) {
                String [] tags1 = val.split(",");
                Set set1 = new HashSet<>();
                for (int index = 0; index <tags1.length; index++)
                    set1.add(tags1[index]);

                t3.setTags(set1);

                t3.setModified(System.currentTimeMillis());
                thm.put(id,t3);
                updated = true;
            }
            if (updated){
                System.out.println(Sout.ACT_TICKETS_IN_SYSTEM);
                List<Ticket> listTickets2 = new ArrayList<>(thm.values());
                this.display(listTickets2);
                return true;
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
                System.out.println(Sout.ACT_REMOVE_SUCCESS+id);
                return true;
            }
        }
        System.out.println(Sout.ACT_TICKETS_IN_SYSTEM);
        List<Ticket> listTickets2 = new ArrayList<>(thm.values());
        this.display(listTickets2);
        return false;
    }


    public Ticket getTicketById(int id){

        boolean check = checkIfExists(id);
        if (check){
            System.out.println(Sout.ACT_TABLE_HEADER);
            Ticket t3 = thm.get(id);
            System.out.println(t3);
            return t3;
        }
        return null;
    }

    public List getTicketsByAgentName(String name){
        List<Ticket> l = new ArrayList<>();
        if (!name.isEmpty()){
            List<Ticket> c = new ArrayList<>(thm.values());
            for (int index = 0; index < c.size(); index++){
                if((c.get(index).agent_name).equals(name)){
                    l.add(c.get(index));
                }
            }
            if(!l.isEmpty()){
                this.display(l);
            }else {
                System.out.println(Sout.ACT_NOT_FOUND);
            }
        }else{
            System.out.println(Sout.ACT_NOT_FOUND);
        }
        return l;
    }

    public List getAllTickets(){
        List<Ticket> t3 = new ArrayList<>();
        if (!thm.isEmpty()){
            t3 = new ArrayList<>(thm.values());
            this.display(t3);
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
    public void getTicketsGroupByAgent(){
        if (!thm.isEmpty()){

            List<Ticket> l = new ArrayList<>(thm.values());
            Collections.sort(l,Ticket.ByAgentNameComparator);
            Map<String,Integer> map = new HashMap<String, Integer>();
            for (int index = 0; index < l.size(); index++){
                int countTicket = map.containsKey(l.get(index).agent_name) ? map.get(l.get(index).agent_name) : 0;
                map.put(l.get(index).agent_name, countTicket+ 1);
            }

            for (Map.Entry<String, Integer> entry : map.entrySet())
                System.out.println(entry.getKey()+" : "+entry.getValue());
        }else {
            System.out.println(Sout.ACT_NOT_FOUND);
        }
    }

    /**
     * Function to fetch all ticket with respect to Tag.
     */
    public Set getAllTicketsByTag(String tag){
        Set set2 = new HashSet<>();
        if (!thm.isEmpty()){

            List<Ticket> l = new ArrayList<>(thm.values());
            Map<String, Set> map = new HashMap<>();
            set2 = new HashSet<>();
            for (int index = 0; index<l.size(); index++){
                if(l.get(index).getTags().contains(tag))
                    set2.add(l.get(index));
            }
            if(!set2.isEmpty()){
                List<Ticket> t3 = new ArrayList<>(set2);
                Collections.sort(t3);
                System.out.println(Sout.ACT_TABLE_HEADER);

                for (Ticket str: t3) {
                    System.out.println(str);
                }
            }else {
                System.out.println(Sout.ACT_NOT_FOUND);
            }
        }
        return set2;
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
}
