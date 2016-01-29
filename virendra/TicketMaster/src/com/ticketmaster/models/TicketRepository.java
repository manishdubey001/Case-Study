package com.ticketmaster.models;

import com.ticketmaster.Main;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by root on 29/1/16.
 */
public class TicketRepository {

    private static TicketRepository _instance;
    private Map<Integer, Ticket> ticketList ;

    private TicketRepository(){
        if (Ticket.ticketList == null){
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
    }

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

    public Map<?,?> getList(){
        return ticketList;
    }

    public Ticket deleteTicket(Integer id){

        if (!ticketList.containsKey(id)){
            return null;
        }
        return ticketList.remove(id);

    }

    public Ticket update (Integer id, Ticket ticket){
        if (!ticketList.containsKey(id)){
            return null;
        }
        ticketList.put(id,ticket);
        return ticketList.get(id);

    }

}
