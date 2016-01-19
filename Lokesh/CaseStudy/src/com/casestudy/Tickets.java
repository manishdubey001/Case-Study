package com.casestudy;

/**
 * Created by root on 12/1/16.
 * Tickets Model Class
 */

import java.util.*;

public class Tickets implements Comparable<Tickets>{
    private int id;
    private String subject;
    private String agent;
    private HashSet<String> tags;
    private long created;
    private long updated;

    public Tickets(int id, String subject, String agent, HashSet<String> tags, long created, long updated) {
        this.id = id;
        this.subject = subject;
        this.agent = agent;
        this.tags = tags;
        this.created = created;
        this.updated = updated;
    }

    @Override
    public int compareTo(Tickets t){
        return Long.valueOf(t.getUpdated()).compareTo(Long.valueOf(this.getUpdated()));
    }

    public static final Comparator<Tickets> updateComparator = new Comparator<Tickets>() {
        @Override
        public int compare(Tickets t1, Tickets t2) {
            return Long.valueOf(t2.getUpdated()).compareTo(Long.valueOf(t1.getUpdated()));
        }
    };

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getAgent() {
        return agent;
    }

    public long getCreated() {
        return created;
    }

    public HashSet<String> getTag() {
        return tags;
    }

    public long getUpdated() {
        return updated;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public void setTag(HashSet<String> tags) {
        this.tags = tags;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String toString(){
        return "{Id: " + this.id + ",Subject: " + this.subject + ",Agent: " + this.agent + ",Tags: " + this.tags.toString() + ",Created: " + this.created + ",Updated: " + this.updated + "}";
    }
}
