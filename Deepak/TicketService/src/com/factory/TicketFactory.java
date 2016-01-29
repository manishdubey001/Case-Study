package com.factory;

import com.customexceptions.UserInputException;
import com.model.Ticket;

import java.util.Set;

/**
 * Created by root on 19/1/16.
 */
public class TicketFactory {

    // Generally, factory methods would be named something like "create" or "make". Naming
    // this "get" gives the impression that maybe you would return an existing one.

        // For invalid input, I would prefer throwing an exception over returning null.
    public static Ticket createTicketInstance(String subject, String agentName, Set<String> tags) throws UserInputException {

        if(subject == null || subject.equals("") || agentName == null || agentName.equals("")){
            throw new UserInputException("Please give proper input!");
        }
        else
        {
            return new Ticket(subject, agentName, tags);
        }
    }
}
