package com.services;

import com.model.Ticket;

import java.util.Set;

/**
 * Created by root on 19/1/16.
 */
public class TicketFactory {

    public static Ticket getTicketInstance(String subject, String agent_name, Set<String> tags){
        if(subject == null || agent_name == null || tags == null){
            return null;
        }
        else
        {
            return new Ticket(subject, agent_name, tags);
        }
    }
}
