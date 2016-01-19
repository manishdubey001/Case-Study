package com.yogesh.pattern;

import com.yogesh.model.Ticket;

import java.util.List;

/**
 * Created by root on 19/1/16.
 */
public class TicketFactory {

    public static Ticket newInstance(int id, String subject, String agentName, List<String> list) {
        return new Ticket(id,subject,agentName,list);
    }
}
