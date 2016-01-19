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
    private String agent_name;
    private String tags;
    private Set<String> tags2;
    private short status = 1;
    private long modified;
    private long created;
    private long unixTime = System.currentTimeMillis() / 1000L;

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
        this.tags2 = tags2;
        System.out.println(this.tags2.size());
        this.modified = unixTime;
        this.created = unixTime;
        countId++;
    }

    public Set<String> getTags2() {
        return tags2;
    }

    public void setTags2(Set<String> tags2) {
        this.tags2 = tags2;
    }

    public static long getCountId() {
        return countId;
    }

    public static void setCountId(long countId) {
        Ticket.countId = countId;
    }

}
