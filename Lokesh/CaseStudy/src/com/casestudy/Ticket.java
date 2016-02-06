package com.casestudy;

/**
 * Created by root on 12/1/16.
 * Ticket Model Class representing a Ticket
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Ticket implements Comparable<Ticket>, Serializable{
    private int id;
    private String subject;
    private String agent;
    private HashSet<String> tags;
    private LocalDateTime created;
    private LocalDateTime updated;

    public Ticket(int id, String subject, String agent, HashSet<String> tags, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.subject = subject;
        this.agent = agent;
        this.tags = new HashSet<>(tags);
        this.created = created;
        this.updated = updated;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(id);
        objectOutputStream.writeUTF(subject);
        objectOutputStream.writeUTF(agent);
        objectOutputStream.writeObject(tags);
        objectOutputStream.writeObject(created);
        objectOutputStream.writeObject(updated);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException{
        id = objectInputStream.readInt();
        subject = objectInputStream.readUTF();
        agent = objectInputStream.readUTF();
        tags = (HashSet<String>) objectInputStream.readObject();
        created = (LocalDateTime) objectInputStream.readObject();
        updated = (LocalDateTime) objectInputStream.readObject();
    }

    @Override
    public int compareTo(Ticket t){
        return this.getUpdated().compareTo(t.getUpdated());
    }

    public static final Comparator<Ticket> updateComparator = (t1, t2) -> {
        return t1.getUpdated().compareTo(t2.getUpdated());
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

    public LocalDateTime getCreated() {
        return created;
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setAgent(String agent) {
        this.agent = agent;
        this.updated = LocalDateTime.now();
    }

    public void setTags(Set<String> tags) {

        this.tags.clear();
        this.tags.addAll(tags);
        this.updated = LocalDateTime.now();
    }

    public String toString(){
        return "{Id: " + this.id + ",Subject: " + this.subject + ",Agent: " + this.agent + ",Tags: " + this.tags.toString() + ",Created: " + this.created + ",Updated: " + this.updated + "}";
    }
}
