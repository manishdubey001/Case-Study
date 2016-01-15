package com.yogesh;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 31/12/15.
 */
public class Ticket implements Comparable<Ticket> {

    int  id;
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
    }

    public List getTags() {
        return tags;
    }

    public void setTags(List tags) {
        this.tags = tags;
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

    public void setModified(Date modified) {
        this.modified = modified;
    }


    @Override
    public int compareTo(Ticket o) {
        return o.getModified().compareTo(getModified());
    }


}
