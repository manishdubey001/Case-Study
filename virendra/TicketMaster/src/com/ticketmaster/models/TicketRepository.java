package com.ticketmaster.models;

import com.ticketmaster.Main;
import com.ticketmaster.utils.SerializerUtil;

import java.io.IOException;
import java.util.*;

/**
 * Created by root on 29/1/16.
 */
public class TicketRepository {

    private static TicketRepository _instance;
    private Map<Integer, Ticket> ticketList ;
    private Set<String> agentList;
    private Set<String> tagList;

    private TicketRepository(){
        if (ticketList == null){
            switch(Main.collectionChoice) {
                case 1:
                default:
                    ticketList = new HashMap<>();
                    break;
                case 2:
                    ticketList = new TreeMap<>();
                    break;
                case 3:
                    ticketList = new LinkedHashMap<>();
                    break;
            }
        }

        if (agentList == null || ! (agentList instanceof HashSet)){
            agentList = new HashSet<>();
        }

        if (tagList == null || ! (tagList instanceof HashSet)){
            tagList = new HashSet<>();

        }
    }

    // EB : Avoid using static methods. It will create issues when running multiple threads.
    //update: for time being I know this approach to have data in the application.
    // I am looking for the workaround for this. I want to discuss this approach with Chad as well.
    
    public static TicketRepository init(){
        if(! (_instance instanceof TicketRepository)){
            _instance = new TicketRepository();
        }
        return _instance;
    }

    public Ticket getTicket(Integer id){

        if (!ticketList.containsKey(id)){
            return null;
        }
        return ticketList.get(id);


    }

    public Map<Integer, Ticket> getList(){
        return Collections.unmodifiableMap(ticketList);
    }

    public Ticket deleteTicket(int id){

        if (!ticketList.containsKey(id)){
            return null;
        }
        return ticketList.remove(id);

    }

    public int getTicketListSize(){
        if (ticketList.isEmpty()){
            return -1;
        }
        return ticketList.size();

    }


    public void updateList(Map<Integer, Ticket> object) {
        ticketList.putAll(object);

    }


    public Ticket update (int id, Ticket ticket){

        if (!ticketList.containsKey(id)){
            return null;
        }
        ticketList.put(id,ticket);
        return ticketList.get(id);

    }

    public void updatePool()
            throws ClassNotFoundException, IOException{

        SerializerUtil util = new SerializerUtil();

        Map<Integer,Ticket> temp = (Map<Integer,Ticket>) util.readFromFile();

        this.updateList(temp);
    }




    public Set<String> getTagList(){
        return Collections.unmodifiableSet(tagList);
    }

    // EB : Unused code
    //update: Done
    /*public void updateTagList(Set<String> object){
        tagList.addAll(object);

    }*/

    public void addTags(Set<String> tag){
        agentList.addAll(tag);
    }

    public Set<String> getAgentList(){
        return Collections.unmodifiableSet(agentList);
    }

    // EB : Unused code
    //update: Done
    /*public void updateAgentList(Set<String> object){
        tagList.addAll(object);

    }*/

    public void addAgent(String agent){
        agentList.add(agent);
    }


    public void initTagList(){

        if (ticketList != null){
            ticketList.values().stream().forEach(e-> tagList.addAll( e.tags ) );
        }
    }

    public void initAgentList(){

        if (ticketList != null){
            ticketList.values().stream().forEach(e-> agentList.add(e.getAgent()) );
        }

    }

    public Ticket getOldestObject(){
        // EB : Use ternary operator or compareTo method.
        //update: done
        return  ticketList.values().stream().min( (obj1,obj2)-> obj1.getCreated() < obj2.getCreated() ?  -1 : 1)
                .get();

    }



}
