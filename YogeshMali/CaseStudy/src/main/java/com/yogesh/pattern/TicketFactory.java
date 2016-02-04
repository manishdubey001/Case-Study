package com.yogesh.pattern;

import com.yogesh.model.Ticket;

import java.util.List;
import java.util.Set;

/**
 * Created by root on 19/1/16.
 */
public class TicketFactory {

    public static Ticket newInstance(int id, String subject, String agentName, Set<String> list) {
        return new Ticket(id,subject,agentName,list);
    }
}
