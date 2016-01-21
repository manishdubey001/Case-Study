package com.Tickets;

// Same question
import sun.invoke.empty.Empty;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Created by root on 14/1/16.
 */
public class TicketServiceComponent {
    public boolean print = true;
    // I would not make this public.
    public Map<Integer, Ticket> thm = new HashMap<>();

    public boolean createTicket(String sub, String agent, String tags){
        if(!sub.isEmpty() && !agent.isEmpty() && !tags.isEmpty()){
            // I generally use plural names for collections
            // (you already have 'tags' so maybe 'parsedTags' here).
            String[] tag = tags.split(",");
            Set set = new HashSet<>();
            // There are a couple of other ways to convert an array to a
            // set. One is to use the for-each construct:
            //      for(String t : tag) { set.add(t); }
            // Or, even easier:
            //      set = new HashSet<>(Arrays.asList(tag));
            for (int index = 0; index<tag.length; index++)
                set.add(tag[index]);

            Ticket ticket = TicketWareHouse.getInstance(sub,set,agent);

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
            // why t3? Try to use the most direct variable names you can
            // I would call this 'ticket'
            Ticket t3 = thm.get(id);
            // note that you did not check if thm contains a ticket for
            // this ID. If it doesn't, t3 is null. You did check in the
            // calling method, but I would check here as well. Be careful
            // with Map because it returns null when the key is not present.
            if (type.equals("agent") && !val.isEmpty()) {
                // I would not use a code like 'type'. I would impement
                // explicit methods like updateTicketTags().
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

    public List getTicketsByAgentName(String name){
        List<Ticket> l = new ArrayList<>();
        if (!name.isEmpty()){
            List<Ticket> c = new ArrayList<>(thm.values());
            // note, it is not necessary to create the list
            // in order to iterate. You can iterate over the
            // values of a map like this:
            // for(Ticket t : thm.values()) { ... }
            for (int index = 0; index < c.size(); index++){
                if((c.get(index).agent_name).equals(name)){
                    l.add(c.get(index));
                }
            }
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
    // avoid "raw" types like Map without type parameters
    public Map getTicketsGroupByAgent(){
        Map<String, Integer> agentsTickets = new HashMap<>();
        if (!thm.isEmpty()){

            List<Ticket> l = new ArrayList<>(thm.values());
            Collections.sort(l,Ticket.ByAgentNameComparator);
            for (int index = 0; index < l.size(); index++){
                // map has compute() and merge() methods that are good for this kind of thing
                int countTicket = agentsTickets.containsKey(l.get(index).agent_name) ? agentsTickets.get(l.get(index).agent_name) : 0;
                agentsTickets.put(l.get(index).agent_name, countTicket+ 1);
                // note that HashMap makes no order guarantees, so sorting before constructing this map
                // didn't really accomplish the intended effect.
                // One approach would be to use a map that guarantees order (like TreeMap), or else use
                // a different structure altogether like ArrayList
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
    public Set getAllTicketsByTag(String tag){
        Set set2 = new HashSet<>();
        if (!thm.isEmpty()){

            List<Ticket> l = new ArrayList<>(thm.values());
            Map<String, Set> map = new HashMap<>(); // you don't use this map
            set2 = new HashSet<>();
            for (int index = 0; index<l.size(); index++){
                if(l.get(index).getTags().contains(tag))
                    set2.add(l.get(index));
            }
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
