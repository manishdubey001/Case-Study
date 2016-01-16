package com.company;

import java.util.*;

/**
 * Created by root on 14/1/16.
 */
public class TicketComponent {
    /**
     * Function to Create Ticket
     * @param t
     * @param thm
     */
    public void createTicket(Ticket t, Map thm){
        System.out.println(Sout.ACT_CREATE_TICKET);

        System.out.println(Sout.ACT_TSUBJECT);
        String sub = scanWhat().next();

        System.out.println(Sout.ACT_TAGENTNAME);
        String agent = scanWhat().next();

        System.out.println(Sout.ACT_TTAGS);
        String tags = scanWhat().next();

        String[] tag = tags.split(",");
        Set set = new HashSet<>();
        for (int index = 0; index<tag.length; index++)
            set.add(tag[index]);

        t.count++;
        Ticket t2 = new Ticket();
        t2.setId(t.count);
        t2.setAgent_name(agent);
        t2.setStr(sub);
        t2.setTags(set);
        t2.setCreated(System.currentTimeMillis());
        t2.setModified(System.currentTimeMillis());
        thm.put(t.count,t2);
    }

    /**
     * Function to Update Ticket as per Id.
     * Only Agent name or Tags are updated.
     * @param thm
     */
    public void updateTicket(Map<Integer, Ticket> thm){
        System.out.println(Sout.ACT_TIDUPDATE);
        try{
            int id = scanWhat().nextInt();
            if(thm.containsKey(id)) {
                Ticket t3 = thm.get(id);

                System.out.println(Sout.ACT_CHOOSE_TAG_AGENT);
                String sel = scanWhat().next();

                List<Ticket> listTickets = new ArrayList<>(thm.values());
                Collections.sort(listTickets);
                System.out.println(Sout.ACT_TICKETS_IN_SYSTEM);
                System.out.println(Sout.ACT_TABLE_HEADER);
                for (Ticket obj : listTickets)
                    System.out.println(obj);

                if (Objects.equals(sel, "a" )) {
                    System.out.println(Sout.ACT_TAGENTNAME);
                    String selA = scanWhat().next();
                    t3.setAgent_name(selA);
                    t3.setModified(System.currentTimeMillis());
                    thm.put(id, t3);

                } else if (Objects.equals(sel, "b")) {
                    System.out.println(Sout.ACT_TTAGS);
                    String selB = scanWhat().next();
                    String [] tags1 = selB.split(",");
                    Set set1 = new HashSet<>();
                    for (int index = 0; index <tags1.length; index++)
                        set1.add(tags1[index]);

                    t3.setTags(set1);

                    t3.setModified(System.currentTimeMillis());
                    thm.put(id,t3);
                }
                System.out.println(Sout.ACT_TICKETS_IN_SYSTEM);
                System.out.println(Sout.ACT_TABLE_HEADER);
                List<Ticket> listTickets2 = new ArrayList<>(thm.values());
                Collections.sort(listTickets2);
                for (Ticket obj : listTickets2)
                    System.out.println(obj);


            }// Entered wrong
        }catch(InputMismatchException Im){
            System.out.println(Sout.ACT_INVALID+Im);
        }

    }

    /**
     * Function to remove a particular ticket from the system
     * @param thm
     */
    public void removeTicketById(Map<Integer, Ticket> thm){
        System.out.println(Sout.ACT_TABLE_HEADER);
        List<Ticket> listTickets = new ArrayList<>(thm.values());
        Collections.sort(listTickets);
        for (Ticket obj : listTickets)
            System.out.println(obj);

        System.out.println(Sout.ACT_TID);

        int selT = scanWhat().nextInt();
        if(thm.containsKey(selT)){
            System.out.println(Sout.ACT_ARE_YOU_SURE+selT);
            System.out.println(Sout.ACT_YES_OR_NO);
            int selA = scanWhat().nextInt();
            if (selA > 0){
                thm.remove(selT);
                System.out.println(Sout.ACT_REMOVE_SUCCESS+selT);
            }
        }
        System.out.println(Sout.ACT_TICKETS_IN_SYSTEM);

        List<Ticket> listTickets2 = new ArrayList<>(thm.values());
        Collections.sort(listTickets2);
        for (Ticket obj2 : listTickets2)
            System.out.println(obj2);

    }

    /**
     * Function to fetch a ticket by it Id
     * @param thm
     */
    public void getAllTicketsById(Map<Integer, Ticket> thm){
        System.out.println(Sout.ACT_TICKETS_IN_SYSTEM);
        System.out.println(Sout.ACT_TABLE_HEADER);
        List<Ticket> listTickets2 = new ArrayList<>(thm.values());
        Collections.sort(listTickets2);
        for (Ticket obj2 : listTickets2)
            System.out.println(obj2);

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
            System.out.println(Sout.ACT_TABLE_HEADER);
            Collections.sort(l);
            for (Ticket str : l)
                System.out.println(str.toString());
        }

    }

    /**
     * Function to fetch all tickets present in the system
     * @param thm
     */
    public void getAllTickets(Map<Integer, Ticket> thm){
        List<Ticket> t3 = new ArrayList<>(thm.values());
        Collections.sort(t3);
        System.out.println(Sout.ACT_TABLE_HEADER);

        for (Ticket str: t3) {
            System.out.println(str);
        }
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
    public static Scanner scanWhat(){
        Scanner sc = new Scanner(System.in);
        return sc;
    }
}
