package com.Tickets;

// Why this import?



// Best practice is to avoid wildcard imports;
// it can cause confusion and conflicts. IntelliJ
// has a setting to manage this automatically.
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by root on 13/1/16.
 */
public class Ticket implements Comparable<Ticket>, Serializable{
    private int id;
    private String subject;
    private String agentName;
    private LocalDateTime created;
    private LocalDateTime modified;
    private Set<String> tags;


    Ticket(int id, String subject, Set tags, String name){
        this.id = id;
        this.agentName = name;
        this.subject = subject;
        this.tags = tags;
        this.created = LocalDateTime.now();
    }

    Ticket(int id, String subject, Set tags, String name, boolean  b){
        this.id = id;
        this.agentName = name;
        this.subject = subject;
        this.tags = tags;
        if (b)
            this.created = this.modified = LocalDateTime.now().minusDays(new Random().nextInt(10));
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

    public LocalDateTime getCreated() {
        return created;
    }

    private void setCreated() {
        this.created = LocalDateTime.now();
    }

    public LocalDateTime getModified() {
        return modified;
    }

    private void setModified() {
            this.modified = LocalDateTime.now();
    }


    /**
     Overriding Read / Write for Serialization
     */
    public void writeSingleObject(ObjectOutputStream oos) throws IOException{
        oos.writeInt(getId());
        oos.writeUTF(getSubject());
        oos.writeUTF(getAgent_name());
        oos.writeObject(getTags());
        oos.writeObject(getCreated());
        oos.writeObject(getModified());
    }

    public void writeMultipleListOfObjects(List<Ticket> tickets, ObjectOutputStream oos) throws IOException{
        oos.writeObject(tickets);
    }

    public List<Ticket> readMultipleListOfObjects(ObjectInputStream ois) throws IOException, ClassNotFoundException{
        return (List) ois.readObject();
    }

    public Ticket readSingleObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
        this.setId(ois.readInt());
        this.setSubject(ois.readUTF());
        this.setAgent_name(ois.readUTF());
        this.setTags((Set<String>) ois.readObject());
        return new Ticket(this.id, this.subject, this.tags, agentName);
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
        LocalDateTime dt  = this.created;
        LocalDateTime dt2 = this.modified;
        return "  "+this.id+"  |  "+this.agentName+"  |  "+this.subject+"  |  "+this.tags+"  |  "+dt+"  |   "+dt2;
    }
}
