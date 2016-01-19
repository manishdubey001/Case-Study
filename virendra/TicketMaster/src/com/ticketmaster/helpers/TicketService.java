package com.ticketmaster.helpers;

import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.utils.DetailProvider;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TicketService class
 * This class is service class to perform actions on ticket object
 * Created by Virendra on 31/12/15.
 */
public class TicketService /*implements Comparable<Ticket>*/ {
    private Ticket ticket;
    private TicketService ticketService;
    public Map<String, ? super Object> map;

    /**
     * Default constructor
     */

    public TicketService(){
        map = new TreeMap<>();

    }

    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */
    public Ticket createTicket(String subject,String agent,Set tags)
            throws IOException, ClassNotFoundException, TicketNotFoundException {

        if (subject == null || agent == null || tags == null){
            //if any of one is not set then return null
            return null;
        }

        ticket = new Ticket.TicketBuilder().withSubject(subject).withAgent(agent).withTags(tags).build();

        if(ticket.save()){
            return ticket;
        }
        return null;
    }

    /**
     *
     * @param ticketObj
     * @param agent
     * @param tags
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */
    public Ticket updateTicket(Ticket ticketObj, String agent, Set tags)
            throws IOException, ClassNotFoundException, TicketNotFoundException {

        boolean flag = false;
        if (agent != null){
            ticketObj.setAgent(agent);
            flag  =true;
        }

        if (tags != null){
            ticketObj.tags.addAll(tags);
            flag = true;
        }

        if (!flag){
            return null;
        }else if (ticketObj.update()){
            return ticketObj;
        }else {
            return null;
        }

    }

    /**
     *
     * @param id
     * @param object
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */
    public void deleteTicket(int id, DetailProvider object)
            throws IOException, ClassNotFoundException, TicketNotFoundException {


        Ticket ticket = this.getTicketDetail(id);

        if (ticket ==null){
            throw new TicketNotFoundException("Record with id: "+id +" does not exists");
        }

        System.out.printf("Are you sure you want to delete ticket #%010d ? (y/n)", ticket.getId());
        if (object.readStringInput().equals("y")) {
            ticket.deleteTicket(ticket.getId());
            System.out.println("Ticket deleted. (New size: " + Ticket.ticketList.size() + ")");

        }



    }

    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */
    public Map getTicket() throws IOException, ClassNotFoundException, TicketNotFoundException {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter Ticket id:");
        int id = s.nextInt();

        Ticket ticket = (Ticket) Ticket.ticketList.get(id);
        if (ticket ==null){
            throw new TicketNotFoundException("Record with id: "+id +" does not exists");
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
     * @throws TicketNotFoundException
     */
    public List<Map<String,? super Object>> getTickets() throws IOException, ClassNotFoundException, TicketNotFoundException {

        if (Ticket.ticketList == null){
            return null;//(List<Map<String, ? super Object>>) Ticket.ticketList;
//            throw new TicketNotFoundException("No Records Found");
        }

        List l = new LinkedList<>(Ticket.ticketList.entrySet());

        //traditional coding
        /*Collections.sort(l, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {

                if ( ( (Ticket)(((Map.Entry) o1).getValue()) ).getModified() <=
                ( (Ticket)(((Map.Entry) o2).getValue()) ).getModified() )
                    return 1;
                else return -1;

            }
        });*/


        //lambda code : v1.8 or later
        Collections.sort(l, (o1, o2) ->{
            if ( ( (Ticket)(((Map.Entry) o1).getValue()) ).getModified() <
                    ( (Ticket)(((Map.Entry) o2).getValue()) ).getModified() )
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

            a= (List) Ticket.getListStream().filter(
                    (obj) ->{ Map.Entry me = (Map.Entry)obj;
                        if (key.equals("agent")){
                            return ( (Ticket)(me.getValue())).getAgent().toLowerCase().equals(searchKey);
                        }else {
                            return ( (Ticket)(me.getValue())).tags.contains(searchKey);
                        }
                    } )
                    .sorted((o1, o2) ->{
                if ( ( (Ticket)(((Map.Entry) o1).getValue()) ).getModified() <=
                        ( (Ticket)(((Map.Entry) o2).getValue()) ).getModified() )
                    return 1;
                else return -1;
                }).collect(Collectors.toList());

        }else {
            //this block is reserved for future usage
        }

        //adding sorted section in streams to further optimize

       /* Collections.sort(a, (o1, o2) ->{
            if ( ( (Ticket)(((Map.Entry) o1).getValue()) ).getModified() <=
                    ( (Ticket)(((Map.Entry) o2).getValue()) ).getModified() )
                return 1;
            else return -1;
        });*/

        return formatPrintData(a);

    }

    /**
     * method getTicketCount is used to collect tickets assigned to each users
     * @return Map of agent and count of tickets for each agent
     */

    public Map<String,Integer> getTicketCount(){
        Map<String,Integer> m = new LinkedHashMap<>();

        Set s= Ticket.ticketList.entrySet();
        Iterator it = s.iterator();
        Ticket tmp;
        while (it.hasNext()){
            int count = 0;
            Map.Entry me = (Map.Entry)it.next();
            tmp = (Ticket) me.getValue();

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
     * used to get the cusrrent object of Ticket class
     * @return
     */
    public Ticket getTicketObject(){
        return this.ticket;
    }

    protected List<Map<String,? super Object>> formatPrintData(List l){
        List<Map<String,? super Object>> l1 = new ArrayList<>();

        l.forEach( (e)-> {
            Map<String, ? super Object> m = new LinkedHashMap();

            Map.Entry t1 = (Map.Entry) e;

            Ticket t = (Ticket) t1.getValue();

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
    public int compareTo(Ticket obj) {

        return  obj.getAgent().toLowerCase().compareTo(obj.getAgent().toLowerCase());
    }*/



    public Ticket getTicketDetail(int id){

        if (id == 0 ) {
            return null;
        }else {
            return  (Ticket) Ticket.ticketList.get(id);
        }

    }
}
