package com.Tickets;

// Why this import?
import sun.invoke.empty.Empty;


// Best practice is to avoid wildcard imports;
// it can cause confusion and conflicts. IntelliJ
// has a setting to manage this automatically.
import java.io.Serializable;
import java.util.*;

/**
 * Created by root on 13/1/16.
 */
public class Ticket implements Comparable<Ticket>,Serializable{
    private int id;
    private String subject;
    private String agentName;
    private long created;
    public long modified;
    private Set<String> tags;


    Ticket(int id, String subject, Set tags, String name){
        this.setId(id);
        this.setAgent_name(name);
        this.setSubject(subject);
        this.setTags(tags);
        this.setCreated();
    }

    public Set getTags() {
        return tags;
    }

    public void setTags(Set tags) {
        this.setModified();
        this.tags = tags;
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

    public void setSubject(String str) {
        this.subject = str;
    }

    public String getAgent_name() {
        return agentName;
    }

    public void setAgent_name(String agent_name) {
        this.setModified();
        this.agentName = agent_name;
    }

    public long getCreated() {
        return created;
    }

    private void setCreated() {
        this.created = System.currentTimeMillis();
    }

    public long getModified() {
        return modified;
    }

    private void setModified() {
            this.modified = System.currentTimeMillis();
    }

    @Override
    public int compareTo(Ticket compareTicket){
        return Long.compare(compareTicket.getModified(), this.getModified());
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
        return "  "+this.id+"  |  "+this.agentName+"  |  "+this.subject+"  |  "+this.tags+"  |  "+dt+"  |   "+dt2;
    }
}
