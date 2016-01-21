package com.Tickets;

// Why this import?
import sun.invoke.empty.Empty;


// Best practice is to avoid wildcard imports;
// it can cause confusion and conflicts. IntelliJ
// has a setting to manage this automatically.
import java.util.*;

/**
 * Created by root on 13/1/16.
 */
public class Ticket implements Comparable<Ticket>{
    public int id;
    public String subject;
    public String agent_name; // more common convention is 'camel case', agentName
    public Long created; // generally, prefer primitive types over objects (long instead of Long)
    public Set tags; // use the type parameter for safety; Set<String>


    // You will find some disagreement about this, but in general
    // I do not like calling accessors internally, especially from
    // a constructor. I would set the fields directly.
    Ticket(int id, String subject, Set tags, String name){
        this.setId(id);
        this.setAgent_name(name);
        this.setStr(subject);
        this.setTags(tags);
        this.setCreated();
        this.setModified(null);
    }

    // when you return an internal object like this directly to
    // the caller, they can modify it. Either make a defensive copy,
    // or use Collections.unmodifiableSet() as a wrapper.
    public Set getTags() {
        return tags;
    }

    // Reverse problem from getTags() here. The caller could still modify this after
    // you store it. It's best to make a defensive copy.
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

    // Give this a descriptive name like getSubject()
    // Also you never call this nor getCreated(). If you
    // don't have a need for an accessor, there is no reason
    // to provide one.
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

    // I would not make this public
    public void setCreated() {
        this.created = System.currentTimeMillis();
    }

    public Long getModified() {
        return modified;
    }

    // I would have the ticket class handle its modification timestamp internally
    public void setModified(Long modified) {
        // Having a method where "null" is a trigger for one action and non-null
        // is a trigger for another can cause problems. It's easy to forget
        // this check, and to forget which methods you can pass null to or not.
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
        // Java's original Date class has a lot of problems. Usually it's better to use Java 8's LocalDate/Time classes.
        Date dt  = new Date(this.created);
        Date dt2 = new Date(this.modified);
        return "  "+this.id+"  |  "+this.agent_name+"  |  "+this.subject+"  |  "+this.tags+"  |  "+dt+"  |   "+dt2;
    }
}
