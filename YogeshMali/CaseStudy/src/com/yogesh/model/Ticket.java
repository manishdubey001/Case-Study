package com.yogesh.model;

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

    public Ticket()
    {

    }

    public Ticket(int id, String subject, String agentName, List tags)
    {
        this.setId(id);
        this.setSubject(subject);
        this.setAgentName(agentName);
        this.setTags(tags);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        setCreated(new Date());
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
