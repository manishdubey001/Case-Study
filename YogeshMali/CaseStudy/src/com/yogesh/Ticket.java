package com.yogesh;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 31/12/15.
 */
public class Ticket {

    int id;
    String subject;
    String agentName;
    List<String> tags;
    Date created;
    Date modified;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    Long timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
        setModified(new Date());
    }

    public List getTags() {
        return tags;
    }

    public void setTags(List tags) {
        this.tags = tags;
        setModified(new Date());
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    private void setModified(Date modified) {
        this.modified = modified;
    }



}

class DateComparator implements Comparator<Ticket> {
    @Override
    public int compare(Ticket date1, Ticket date2) {
        Date d = date1.getModified();
        Date date =  date2.getModified();

        if (d.before(date) ) {
            return 1;
        } else if (d.after(date) ) {
            return -1;
        } else {
            return 0;
        }
    }
}