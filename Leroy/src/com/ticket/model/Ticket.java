package com.ticket.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.io.ObjectInputStream;
import java.util.*;

/**
 * Created by root on 13/1/16.
 */
public class Ticket implements Serializable{
    private int id;
    private String subject;
    private String agentName;
    private LocalDateTime created;
    private LocalDateTime modified;
    private Set<String> tags;


    public Ticket(int id, String subject,String name, Set tags){
        this.id = id;
        this.agentName = name;
        this.subject = subject;
        this.tags = tags;
        this.created = this.modified = LocalDateTime.now();
    }

    public Ticket(int id, String subject, Set tags, String name, boolean  b){
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agent_name) {
        this.setModified();
        this.agentName = agent_name;
    }

    public LocalDateTime getCreated() {
        return created;
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
    public void writeSingleObject(ObjectOutputStream oos) throws IOException {
        oos.writeInt(getId());
        oos.writeUTF(getSubject());
        oos.writeUTF(getAgentName());
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
        this.setAgentName(ois.readUTF());
        this.setTags((Set<String>) ois.readObject());
        this.created = (LocalDateTime) ois.readObject();
        this.modified  = (LocalDateTime) ois.readObject();
        return new Ticket(this.id, this.subject, agentName, this.tags);
    }

    @Override
    public String toString(){
        LocalDateTime dt  = this.created;
        LocalDateTime dt2 = this.modified;
        return "  "+this.id+"  |  "+this.agentName+"  |  "+this.subject+"  |  "+this.tags+"  |  "+dt+"  |   "+dt2;
    }
}
