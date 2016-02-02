package com.casestudy;

/**
 * Created by root on 12/1/16.
 * Ticket Model Class
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.*;

// Generally use singular name for classes (Ticket instead of Ticket) unless the class stores more than one per instance.
//Update: Changed class name to singular
public class Ticket implements Comparable<Ticket>, Serializable{
    private int id;
    private String subject;
    private String agent;
    private HashSet<String> tags;
    //Update: Use of LocalDateTime in place of long (from Date class)
    private LocalDateTime created;
    private LocalDateTime updated;

    public Ticket(int id, String subject, String agent, HashSet<String> tags, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.subject = subject;
        this.agent = agent;
        this.tags = tags;
        this.created = created;
        this.updated = updated;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(id);
        objectOutputStream.writeUTF(subject);
        objectOutputStream.writeUTF(agent);
        objectOutputStream.writeObject(tags);
        /*oos.writeUTF(created.toString());
        oos.writeUTF(updated.toString());*/
        objectOutputStream.writeObject(created);
        objectOutputStream.writeObject(updated);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException{
        id = objectInputStream.readInt();
        subject = objectInputStream.readUTF();
        agent = objectInputStream.readUTF();
        tags = (HashSet<String>) objectInputStream.readObject();
        /*created = LocalDateTime.parse(ois.readUTF());
        updated = LocalDateTime.parse(ois.readUTF());*/
        created = (LocalDateTime) objectInputStream.readObject();
        updated = (LocalDateTime) objectInputStream.readObject();
    }

    // rather than create Longs here you can instead use Long.compare() on the primitive types
    // Update: Used Long.compare in place of long primitives
    // Update: Changes for LocalDateTime in place of

    @Override
    public int compareTo(Ticket t){
//        return Long.valueOf(t.getUpdated()).compareTo(Long.valueOf(this.getUpdated()));
//        return Long.compare(t.getUpdated(),this.getUpdated());
        return this.getUpdated().compareTo(t.getUpdated());
    }

    public static final Comparator<Ticket> updateComparator = (t1, t2) -> {
//            return Long.valueOf(t2.getUpdated()).compareTo(Long.valueOf(t1.getUpdated()));
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

    // A few points here.
    // 1. Generally prefer to return an interface (Set<>) rather than the concrete implementation; this allows you to change implementation
    // later without changing the accessor.
    // 2. Call it getTags() since it returns a set.
    // 3. Returning your internal collection directly allows callers to modify it outside this class's implementation. One way to account
    // for this is to make a defensive copy, or to apply an immutable wrapper like Collections.unmodifiableSet(). So I would recommend this be
    //
    // public Set<String> getTags() { return Collections.unmodifiableSet(tags); }
    //

    //Update: I was intentionally making changes to internal object outside this class's scope. Understood why it is bad practice. Changes applied.
    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    // Without any requirement to change a ticket's ID, I wouldn't even implement this. Not all fieldsshould have getter and setter.
    // Update: This is never required and should not be implemented. Commented.
    /*public void setId(int id) {
        this.id = id;
    }*/

    // Since changing subject is not allowed, I would not implement this.
    // Update: Commented, implementation not required at all
    /*public void setSubject(String subject) {
        this.subject = subject;
    }*/

    public void setAgent(String agent) {
        this.agent = agent;
        this.updated = LocalDateTime.now();
    }

    // Getters have similar problems to setters when you are using collections.
    // 1. Prefer just taking a Set<> over a HashSet<> unless you have a specific reason to require HashSet<>.
    // 2. It's good practice to make a copy of the caller's input. Otherwise the caller can modify it after you receive it.
    // 3. Call it setTags() since there can be many

    //Update: Changes applied. Rather then copying, used method addAll from Collection. now even if caller modify the original data sent, no effect on tags
    public void setTags(Set<String> tags) {

        this.tags.clear();
        this.tags.addAll(tags);
        this.updated = LocalDateTime.now();
    }

    // I wouldn't implement this. I would make 'created' final because it shouldn't ever change.
    // Update Not require to implement, commented
    /*public void setCreated(LocalDateTime created) {
        this.created = created;
    }*/

    // rather than allowing clients to set this, I would incorporate the logic to change 'updated' into
    // the methods of this class that update the ticket (setSubject()/setAgent()/setTags()).
    //Update: Not required to implement, commented, Agent and Tags are only modifiable fields, so "updated" will change only when any of these 2 changes. Moved logic
   /* public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }*/

    public String toString(){
        return "{Id: " + this.id + ",Subject: " + this.subject + ",Agent: " + this.agent + ",Tags: " + this.tags.toString() + ",Created: " + this.created + ",Updated: " + this.updated + "}";
    }
}
