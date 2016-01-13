package com.ticketmaster.helpers;

import com.ticketmaster.exceptions.DuplicateEntryException;
import com.ticketmaster.models.Tickets;

import javax.xml.transform.sax.SAXSource;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TicketHelper class
 * This class is service class to perform actions on ticket object
 * Created by Virendra on 31/12/15.
 */
public class TicketHelper /*implements Comparable<Tickets>*/ {
    private Tickets ticket;
    public Map<String, ? super Object> map;

    /**
     * Default constructor
     */

    public TicketHelper(){
        map = new TreeMap<>();

    }

    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicateEntryException
     */
    public boolean createTicket() throws IOException, ClassNotFoundException, DuplicateEntryException {
        ticket = new Tickets();
        Scanner s = new Scanner(System.in);


        System.out.println("Enter Ticket Subject: ");
        map.put("subject",s.next());
        System.out.println("Enter Agent Name: ");
        map.put("agent",s.next());


        System.out.println("Enter tags (y/n): ");

        if (s.next().equals("y")){
            System.out.println("Enter tags separated by colon (:) : ");
            String tmp = s.next();
            String[] tmp1 = tmp.split(":");
            Set s1 = new HashSet<>();

            for (int i=0;i<tmp1.length;i++){
                s1.add(tmp1[i]);
            }

            map.put("tags",s1);
            s1 = null; tmp1= null; // cjm - this isn't necessary

        }

        ticket.setValues(map);
        if(ticket.save()){
            System.out.println("Ticket "+String.format("#%010d", ticket.getId())+" saved successfully");
        }
        return true;

    }

    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicateEntryException
     */
    public boolean updateTicket() throws IOException, ClassNotFoundException, DuplicateEntryException {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter Ticket id:");
        int id = s.nextInt();

        Tickets ticket = (Tickets) Tickets.ticketList.get(id);
        if (ticket ==null){
            // cjm - I would not call this a Duplicate Entry. I think in all cases I would simply use an existing exception or else define TicketNotFoundException
            throw new DuplicateEntryException("Record with id: "+id +" does not exists");
        }

        System.out.println("Enter details you want to update");

        String ch ;


        System.out.println("Update agent?(y/n)");
        ch = s.next();
        if (ch.equals("y")) {
            System.out.println("Enter Agent Name: ");
            map.put("agent", s.next());

        }

        System.out.println("Enter tags (y/n): ");

        if (s.next().equals("y")) {
            Set s1 = new HashSet<>();
            System.out.println("Enter tags separated by colon (:) : ");
            String tmp = s.next();
            String[] tmp1 = tmp.split(":");

            for (int i = 0; i < tmp1.length; i++) {
                s1.add(tmp1[i]);
            }

            map.put("tags", s1);
            s1 = null;
            tmp1 = null;

        }
        ticket.setValues(map);
        if(ticket.update()){
            System.out.println("Ticket (id: "+String.format("%010d", ticket.getId())+") updated successfully");
        }
        return true;
    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicateEntryException
     */
    public void deleteTicket() throws IOException, ClassNotFoundException, DuplicateEntryException {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter Ticket id:");
        int id = s.nextInt();

        Tickets ticket = (Tickets) Tickets.ticketList.get(id);
        if (ticket ==null){
            throw new DuplicateEntryException("Record with id: "+id +" does not exists");
        }
        System.out.printf("Are you sure you want to delete ticket #%010d ? (y/n)", ticket.getId());

        if (s.next().equals("y")) {
            Tickets.ticketList.remove(ticket.getId());
            System.out.println("Ticket deleted. (New size: "+ Tickets.ticketList.size()+")");

        }

    }

    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicateEntryException
     */
    public Map getTicket() throws IOException, ClassNotFoundException, DuplicateEntryException {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter Ticket id:");
        int id = s.nextInt();

        Tickets ticket = (Tickets) Tickets.ticketList.get(id);
        if (ticket ==null){
            throw new DuplicateEntryException("Record with id: "+id +" does not exists");
        }
        Map m = new LinkedHashMap<>();
        m.put("id", ticket.getId());
        m.put("subject", ticket.getSubject());
        m.put("agent", ticket.getAgent());
        m.put("tags", ticket.tags == null ? new HashSet(): ticket.tags.toString());
        m.put("created", new Date(ticket.getCreated()));
        m.put("updated", new Date(ticket.getModified()) );

        return m;

    }

    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicateEntryException
     */
    public List<Map<String,? super Object>> getTickets() throws IOException, ClassNotFoundException, DuplicateEntryException {

        if (Tickets.ticketList == null){
            return null;//(List<Map<String, ? super Object>>) Tickets.ticketList;
//            throw new DuplicateEntryException("No Records Found");
        }

        List l = new LinkedList<>(Tickets.ticketList.entrySet());

        //traditional coding
        /*Collections.sort(l, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {

                if ( ( (Tickets)(((Map.Entry) o1).getValue()) ).getModified() <=
                ( (Tickets)(((Map.Entry) o2).getValue()) ).getModified() )
                    return 1;
                else return -1;

            }
        });*/


        //lambda code : v1.8 or later
        Collections.sort(l, (o1, o2) ->{
            if ( ( (Tickets)(((Map.Entry) o1).getValue()) ).getModified() <
                    ( (Tickets)(((Map.Entry) o2).getValue()) ).getModified() )
                    return 1;
            else return -1;
            });

        return formatPrintData(l);

    }

    /**
     *
     * @param values
     * @return
     */
    public List<Map<String,? super Object>> searchTicket(String key, String... values){

        String searchKey;
        List a = null;


        if(values.length == 1){ //fetch first value

            searchKey = values[0].toLowerCase();

            // cjm - good use of streams
            a= (List) Tickets.getListStream().filter(
                    (obj) ->{ Map.Entry me = (Map.Entry)obj;
                        if (key.equals("agent")){
                            return ( (Tickets)(me.getValue())).getAgent().toLowerCase().equals(searchKey);
                        }else {
                            return ( (Tickets)(me.getValue())).tags.contains(searchKey);
                        }
                    } )
                    .collect(Collectors.toList());

        }else {
            //this block is reserved for future usage
        }

        // cjm - can you see how to combine the sort with the stream operations above?
        Collections.sort(a, (o1, o2) ->{
            if ( ( (Tickets)(((Map.Entry) o1).getValue()) ).getModified() <=
                    ( (Tickets)(((Map.Entry) o2).getValue()) ).getModified() )
                return 1;
            else return -1;
        });

        return formatPrintData(a);

    }

    /**
     * method getTicketCount is used to collect tickets assigned to each users
     * @return Map of agent and count of tickets for each agent
     */

    public Map<String,Integer> getTicketCount(){
        Map<String,Integer> m = new LinkedHashMap<>();

        Set s= Tickets.ticketList.entrySet();
        Iterator it = s.iterator();
        Tickets tmp;
        while (it.hasNext()){
            int count = 0;
            Map.Entry me = (Map.Entry)it.next();
            tmp = (Tickets) me.getValue();

            if(m.containsKey(tmp.getAgent()) ){
                count = m.get(tmp.getAgent());

                m.put(tmp.getAgent(), count+1);
            }else if (tmp.getAgent() !=null){
                m.put(tmp.getAgent(), 1);
            }


        }

        return m;
    }

    /**
     * getTicketObject method
     * used to get the cusrrent object of Tickets class
     * @return
     */
    public Tickets getTicketObject(){
        return this.ticket;
    }

    protected List<Map<String,? super Object>> formatPrintData(List l){
        List<Map<String,? super Object>> l1 = new ArrayList<>();

        l.forEach( (e)-> {
            Map<String, ? super Object> m = new LinkedHashMap();

            Map.Entry t1 = (Map.Entry) e;

            Tickets t = (Tickets) t1.getValue();

            m.put("id", t.getId());
            m.put("subject", t.getSubject());
            m.put("agent", t.getAgent());
            m.put("tags", t.tags == null ? new HashSet(): t.tags.toString());
            m.put("created", new Date(t.getCreated()));
            m.put("updated", new Date(t.getModified()));

            l1.add(m); //add in temporary storage
            m = null; t= null;
        });

        return l1;
    }

/*
    @Override
    public int compareTo(Tickets obj) {

        return  obj.getAgent().toLowerCase().compareTo(obj.getAgent().toLowerCase());
    }*/
}
