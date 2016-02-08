package com.ticketmaster.helpers;

import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.models.TicketRepository;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;


/**
 * TicketService class
 * This class is service class to perform actions on ticket object
 * Created by Virendra on 31/12/15.
 */
public class TicketService {
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

        if (ticket == null){
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

        repository.updatePool();

        Ticket ticket = getTicketDetail(id);
        if (ticket ==null){
            throw new TicketNotFoundException("Record with id: "+id +" does not exists");
        }

        return prepareTicketMap(ticket);

    }

    public Map getOldestTicket() throws IOException, ClassNotFoundException, TicketNotFoundException {
        Ticket ticket = repository.getOldestObject();
        return prepareTicketMap(ticket);
    }

    private Map<String, Object> prepareTicketMap(Ticket ticket){

        Map tempMap = new LinkedHashMap<>();
        if (ticket == null){
            return tempMap;
        }
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
    public List<Map<String,? super Object>> getTickets() throws ClassNotFoundException, IOException{

        repository.updatePool();

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
    public List<Map<String,? super Object>> searchTicket(String key, String... values)
            throws IOException, ClassNotFoundException{

        String searchKey;
        List list = null;

        repository.updatePool();


        if(values.length == 1){ //fetch first value

            searchKey = values[0];

            list = (List) Ticket.getListStream().filter(
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
        return formatPrintData(list);

    }

    /**
     * method getTicketCount is used to collect tickets assigned to each users
     * @return Map of agent and count of tickets for each agent
     */

    public Map<String,? super Object> getTicketCount()
            throws IOException, ClassNotFoundException{
        Map<String,? super Object> m = new LinkedHashMap<>();

        repository.updatePool();

        Set s= repository.getList().entrySet();
        Iterator it = s.iterator();
        Ticket tmp;
        while (it.hasNext()){
            int count = 0;
            Map.Entry me = (Map.Entry)it.next();
            tmp = (Ticket) me.getValue();

            if(m.containsKey(tmp.getAgent()) ){
                count = (Integer)m.get(tmp.getAgent());

                m.put(tmp.getAgent(), count+1);
            }else if (tmp.getAgent() !=null){
                m.put(tmp.getAgent(), 1);
            }


        }

        return m;
    }

    public Map<String,? super Object> getTagsTicketCount()
            throws IOException, ClassNotFoundException {

        Map<String,? super Object> m = new TreeMap<>();

        repository.updatePool();

        //get entrySet from the map
        Set tmpSet = repository.getList().entrySet();

        //get list of tags from the set
        Set<String> tagList = repository.getTagList();

        Ticket tmp;

        for (String tagName: tagList ) {

            Iterator itr = tmpSet.iterator();
            while (itr.hasNext()){
                int count = 0;

                Map.Entry me = (Map.Entry)itr.next();
                tmp = (Ticket) me.getValue();
                if(tmp.tags.contains(tagName)){
                    if(m.containsKey(tagName)){
                        count = (Integer)m.get(tagName);
                        m.put(tagName, count+1);
                    }else {
                        m.put(tagName,1);
                    }
                }
            }

        }

        return m;

    }

    protected List<Map<String,? super Object>> formatPrintData(List l){
        List<Map<String,? super Object>> l1 = new ArrayList<>();

        if (l == null){
            return l1;
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

    public void setTicketList(Map<Integer, Ticket> values){
        TicketRepository.init().updateList(values);
    }

    public void initTags(){
        repository.initTagList();
    }
    public void initAgents(){
        repository.initAgentList();
    }

    public Set<?> getTagsOfTicket(){
        return repository.getTagList();
    }

    public List<Map<String,? super Object>> getOlderTickets(int days){

        if (repository.getTicketListSize() <= 0){
            return null;
        }

        long time = LocalDateTime.now(ZoneId.of("UTC")).minusDays(days).toInstant(ZoneOffset.UTC).toEpochMilli();
        List list = new LinkedList(repository.getList().entrySet());

        List lst = (ArrayList) list.stream()
                .filter((e)->((Ticket) ((Map.Entry) e).getValue()).getCreated() <= time )
                .sorted((o1, o2) ->{
                    if ( ( (Ticket)(((Map.Entry) o1).getValue()) ).getModified() <=
                            ( (Ticket)(((Map.Entry) o2).getValue()) ).getModified() ) {
                        return 1;
                    }
                    else return -1;
                })
                .collect(Collectors.toList());

        return formatPrintData(lst);
    }
}