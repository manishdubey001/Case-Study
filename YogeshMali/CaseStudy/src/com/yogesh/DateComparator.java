package com.yogesh;

import com.yogesh.model.Ticket;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by root on 19/1/16.
 */

public class DateComparator implements Comparator<Ticket> {
    @Override
    public int compare(Ticket date1, Ticket date2) {
        Date d = date1.getModified();
        Date date = date2.getModified();

        if (d.before(date)) {
            return 1;
        } else if (d.after(date)) {
            return -1;
        } else {
            return 0;
        }
    }
}