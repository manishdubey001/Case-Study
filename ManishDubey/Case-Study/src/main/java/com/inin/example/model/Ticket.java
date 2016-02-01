package com.inin.example.model;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 31/12/15.
 */
public class Ticket implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private String subject;
    private String agentName;
    private Set<String> tags;
    private LocalDateTime created;
    private LocalDateTime modified;

    public Ticket(){}
    public Ticket(int id, String subject, String agentName, HashSet<String> tags) {
        this.id = id;
        this.subject = subject;
        this.agentName = agentName;
        this.tags = tags;
        /**
         * Leroy Comments :
         * create and modified are having the same values for LocalDateTime
         *
         * It can also be written as :
         * this.created = this.modified = LocalDateTime.now();
         *
         * this reduced lines of code and improves readability.
          */
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
    }
    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
        this.setModified(LocalDateTime.now());
    }

    public Set<String> getTags() {
        return tags == null ? null : Collections.unmodifiableSet(tags);
    }

    public void setTags(Set<String> tags) {
        this.tags = tags != null ? new HashSet<>(tags): null;
        this.setModified(LocalDateTime.now());
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    private void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    private void writeObject(ObjectOutputStream oos) throws Exception{
        oos.writeInt(id);
        oos.writeUTF(subject);
        oos.writeUTF(agentName);
        oos.writeObject(tags);
        oos.writeObject(created);
        oos.writeObject(modified);
    }

    private void readObject(ObjectInputStream ois) throws Exception{
        id = ois.readInt();
        subject = ois.readUTF();
        agentName = ois.readUTF();
        tags = (HashSet<String >) ois.readObject();
        created = (LocalDateTime) ois.readObject();
        modified = (LocalDateTime)ois.readObject();
    }
    public Ticket copy(Ticket ticket){
        Ticket newTicket = new Ticket();
        newTicket.id = ticket.id;
        newTicket.subject = ticket.subject;
        newTicket.agentName = ticket.agentName;
        newTicket.tags = ticket.getTags();
        newTicket.created = ticket.getCreated();
        newTicket.modified = ticket.modified;
        return newTicket;
    }

}

