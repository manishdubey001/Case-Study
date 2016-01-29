package com.yogesh;

import com.yogesh.model.Ticket;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by root on 19/1/16.
 */

public class DateComparator implements Comparator<Ticket> {
    @Override
    public int compare(Ticket date1, Ticket date2) {
        LocalDateTime d = date1.getModified();
        LocalDateTime date = date2.getModified();
        if (d.isBefore(date)) {
            return 1;
        } else if (d.isAfter(date)) {
            return -1;
        } else {
            return 0;
        }
    }
}