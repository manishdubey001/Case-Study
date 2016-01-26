package com.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 13/1/16.
 */
public class Ticket {

    private static long countId = 1;
    private long id;
    private String subject;
    // generally Java avoids underscores in names, and uses 'camel case' like agentName
    private String agent_name;
    private String tags;
    private Set<String> tags2;
    private short status = 1;
    private long modified;
    private long created;
    private long unixTime = System.currentTimeMillis() / 1000L;

    // Note that you don't use many of these accessors; if you don't know a need
    // for an accessor, don't write it. Expose as little of your class as possible.
    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }
    public String getSubject() {
        return subject;
    }

    public long getId() {
        return id;
    }

    // since you are controlling ID's by an auto-increment, I wouldn't allow this.
    public void setId(long id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    public long getCreated() {
        return created;
    }

    // Have the ticket class manage its own created and modified timestamps
    public void setCreated(long created) {
        this.created = created;
    }


    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }


    public Ticket(){

    }

    // Note that you don't use this constructor. Can you see how to
    // combine the code so you don't have all the same code duplicated
    // between the two constructors?
    public Ticket(String subject, String agent_name){
        this.id = countId;
        this.subject = subject;
        this.agent_name = agent_name;
        this.modified = unixTime;
        this.created = unixTime;
        countId++;
    }


    public Ticket(String subject, String agent_name, Set<String> tags2){

        this.id = countId;
        this.subject = subject;
        this.agent_name = agent_name;
        // See the email I sent the API team about potential dangers of
        // just storing a reference to this set.
        this.tags2 = tags2;
        this.modified = unixTime;
        this.created = unixTime;
        countId++;
    }

    // why 2?

    public Set<String> getTags2() {
        //return tags2;
        if(tags2 != null)
            return new HashSet<>(tags2);
        else
            return null;
    }

    public void setTags2(Set<String> tags2) {
        //this.tags2 = tags2;
        this.tags2 = new HashSet<>(tags2);
    }

    public static long getCountId() {
        return countId;
    }

    public static void setCountId(long countId) {
        Ticket.countId = countId;
    }

}
