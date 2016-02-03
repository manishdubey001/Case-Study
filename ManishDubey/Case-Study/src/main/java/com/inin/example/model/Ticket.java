package com.inin.example.model;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
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

    public Ticket(int id, String subject, String agentName, Set<String> tags) {
        this.id = id;
        this.subject = subject;
        this.agentName = agentName;
        this.tags = tags;
        this.created = this.modified = LocalDateTime.now();
    }

    public Ticket(Ticket ticket){
        this.id = ticket.getId();
        this.subject= ticket.getSubject();
        this.agentName = ticket.getAgentName();
        this.tags = new HashSet<>(ticket.getTags());
        this.created = ticket.getCreated();
        this.modified = ticket.getModified();
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
        this.modified =LocalDateTime.now();
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
        this.modified =LocalDateTime.now();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    /**
     * Custom serialization of ticket object
     * @param oos
     * @throws Exception
     */
    private void writeObject(ObjectOutputStream oos) throws Exception{
        oos.writeInt(getId());
        oos.writeUTF(getSubject());
        oos.writeUTF(getAgentName());
        oos.writeObject(getTags());
        oos.writeObject(getCreated());
        oos.writeObject(getModified());
    }

    /**
     * Custom deserialization of ticket object
     * @param ois
     * @throws Exception
     */
    private void readObject(ObjectInputStream ois) throws Exception{
        this.id = ois.readInt();
        this.subject = ois.readUTF();
        this.agentName = ois.readUTF();
        this.tags = (Set<String >)ois.readObject();
        this.created = (LocalDateTime)ois.readObject();
        this.modified = (LocalDateTime)ois.readObject();
    }

    @Override
    public String toString(){
        return getId() + "\t" + getSubject() + "\t" + getAgentName() + "\t" + getTags() + "\t" + getCreated() + "\t" + getModified() + "\t";
    }

}

