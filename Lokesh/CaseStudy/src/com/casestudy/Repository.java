package com.casestudy;

import java.util.HashMap;

/**
 * Created by lokesh on 11/2/16.
 */
public class Repository {
    HashMap<Integer, Ticket> tickets = new HashMap<>();
    FileHelper fh;

    public Repository(String path, String fileName){
        fh = new FileHelper(path, fileName);
    }

    public boolean addTicket(Ticket ticket){
        tickets.put(ticket.getId(), ticket);
        fh.write(ticket);
        return true;
    }
}
