package com.ticketmaster.helpers;

import com.ticketmaster.Main;
import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.models.TicketRepository;
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
    TicketRepository repository;

    /**
     * Default constructor
     */

    public TicketService(){
        repository = TicketRepository.init();

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

        if (subject == null || agent == null){
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

        if (!tags.isEmpty()){
            if (ticketObj.tags == null){
                ticketObj.tags = new HashSet<>();
            }
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
     * @param id integer ticket id
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */
    public Ticket deleteTicket(int id)
            throws IOException, ClassNotFoundException, TicketNotFoundException {

        Ticket ticket = this.getTicketDetail(id);

        if (ticket ==null){
            throw new TicketNotFoundException("Record with id: "+id +" does not exists");
        }

        return ticket.delete();
    }

    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */
    public Map getTicket(int id) throws IOException, ClassNotFoundException, TicketNotFoundException {

        Ticket ticket = getTicketDetail(id);
        if (ticket ==null){
            throw new TicketNotFoundException("Record with id: "+id +" does not exists");
        }

        Map tempMap = new LinkedHashMap<>();
        tempMap.put("id", ticket.getId());
        tempMap.put("subject", ticket.getSubject());
        tempMap.put("agent", ticket.getAgent());
        tempMap.put("tags", ticket.tags == null ? new HashSet(): ticket.tags.toString());
        tempMap.put("created", new Date(ticket.getCreated()));
        tempMap.put("updated", new Date(ticket.getModified()) );

        return tempMap;

    }

    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */
    public List<Map<String,? super Object>> getTickets() {

        if (repository.getTicketListSize() <= 0){
            return null;
        }

        List l = new LinkedList(repository.getList().entrySet());

        Collections.sort(l, (obj1, obj2) ->{
            if ( ( (Ticket) ((Map.Entry) obj1).getValue()).getModified() <
                    ( (Ticket) ((Map.Entry) obj2).getValue()).getModified() ){
                return 1;
            }else {
                return -1;
            }

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

            searchKey = values[0];

            a= (List) Ticket.getListStream().filter(
                    (obj) ->{ Map.Entry me = (Map.Entry)obj;
                        if (key.equals("agent")){
                            return ( (Ticket)(me.getValue())).getAgent().toLowerCase().equals(searchKey.toLowerCase());
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


        return formatPrintData(a);

    }

    /**
     * method getTicketCount is used to collect tickets assigned to each users
     * @return Map of agent and count of tickets for each agent
     */

    public Map<String,Integer> getTicketCount(){
        Map<String,Integer> m = new LinkedHashMap<>();

        Set s= repository.getList().entrySet();
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

        if (l == null){
            return null;
        }

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
        });

        return l1;
    }

    public Ticket getTicketDetail(int id){

        if (id == 0 ) {
            return null;
        }else {
            return  repository.getTicket(id);
        }

    }

    public void clearList(){
        Ticket.clearList();
    }

    public static void setTicketList(Map<Integer, Ticket> values){
        TicketRepository.init().updateList(values);
    }
}
