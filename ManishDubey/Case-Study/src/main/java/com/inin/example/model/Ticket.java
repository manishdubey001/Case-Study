package com.inin.example.model;


import java.util.Date;
import java.util.HashSet;

/**
 * Created by root on 31/12/15.
 */
public class Ticket {
    private int id;
    private String subject;
    private String agentName;
    private HashSet<String> tags;
    private Date created;
    private Date modified;

    public Ticket(int id, String subject, String agentName, HashSet<String> tags) {
        this.id = id;
        this.subject = subject;
        this.agentName = agentName;
        this.tags = tags;
        this.created = new Date();
        this.modified = new Date();
    }

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
        this.setModified(new Date());
    }

    public HashSet<String> getTags() {
        return tags;
    }

    public void setTags(HashSet<String> tags) {
        this.tags = tags;
        this.setModified(new Date());
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }

    private void setModified(Date modified) {
        this.modified = modified;
    }
}

