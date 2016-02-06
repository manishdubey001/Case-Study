package com.ticket;

import java.util.Comparator;

/**
 * Created by root on 5/2/16.
 */
public class CreatedDateComparator implements Comparator<Ticket>{
    @Override
    public int compare(Ticket t1, Ticket t2) {
        return t1.getCreated().compareTo(t2.getCreated());
    }
}
