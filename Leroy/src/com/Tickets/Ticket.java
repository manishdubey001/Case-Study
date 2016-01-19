package com.Tickets;

import sun.invoke.empty.Empty;

import java.util.*;

/**
 * Created by root on 13/1/16.
 */
public class Ticket implements Comparable<Ticket>{
    public int id;
    public String subject;
    public String agent_name;
    public Long created;
    public Set tags;


    Ticket(int id, String subject, Set tags, String name){
        this.setId(id);
        this.setAgent_name(name);
        this.setStr(subject);
        this.setTags(tags);
        this.setCreated();
        this.setModified(null);
    }
    public Set getTags() {
        return tags;
    }

    public void setTags(Set tags) {
        this.tags = tags;
    }

    public Long modified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStr() {
        return subject;
    }

    public void setStr(String str) {
        this.subject = str;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated() {
        this.created = System.currentTimeMillis();
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        if(modified == null)
            this.modified = System.currentTimeMillis();
        else
            this.modified = modified;
    }

    @Override
    public int compareTo(Ticket compareTicket){
        return compareTicket.getModified().compareTo(this.getModified());
    }
    public static Comparator<Ticket> ByAgentNameComparator = new Comparator<Ticket>() {
        @Override
        public int compare(Ticket ticket1, Ticket ticket2) {
            String agent1 = ticket1.getAgent_name();
            String agent2 = ticket2.getAgent_name();
            return agent1.compareTo(agent2);
        }
    };

    @Override
    public String toString(){
        Date dt  = new Date(this.created);
        Date dt2 = new Date(this.modified);
        return "  "+this.id+"  |  "+this.agent_name+"  |  "+this.subject+"  |  "+this.tags+"  |  "+dt+"  |   "+dt2;
    }
}
