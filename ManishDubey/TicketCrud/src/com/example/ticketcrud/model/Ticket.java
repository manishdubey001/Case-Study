package com.example.ticketcrud.model;

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

    public Ticket(int id, String subject, String agentName, HashSet<String> tags, Date created, Date modified) {
        this.id = id;
        this.subject = subject;
        this.agentName = agentName;
        this.tags = tags;
        this.created = created;
        this.modified = modified;
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
    }

    public HashSet<String> getTags() {
        return tags;
    }

    public void setTags(HashSet<String> tags) {
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

    // cjm - I would not expose this and setCreated; let the Ticket class manage these.
    public void setModified(Date modified) {
        this.modified = modified;
    }
}
