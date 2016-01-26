package com.factory;

import com.model.Ticket;

import java.util.Set;

/**
 * Created by root on 19/1/16.
 */
public class TicketFactory {

    // Generally, factory methods would be named something like "create" or "make". Naming
    // this "get" gives the impression that maybe you would return an existing one.

    public static Ticket getTicketInstance(String subject, String agent_name, Set<String> tags){
        // For invalid input, I would prefer throwing an exception over returning null.
        if(subject == null || agent_name == null){
            return null;
        }
        else
        {
            return new Ticket(subject, agent_name, tags);
        }
    }
}
