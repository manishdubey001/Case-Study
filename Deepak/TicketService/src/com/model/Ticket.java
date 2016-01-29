package com.model;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by root on 13/1/16.
 */
public class Ticket implements Serializable, Function<Object, Object> {

    private static long countId = 1;
    private long id;
    private String subject;
    // generally Java avoids underscores in names, and uses 'camel case' like agentName
    private String agentName;
    private Set<String> tags;
    private short status = 1;
    private long modified;
    private long created;
    private long unixTime = System.currentTimeMillis() / 1000L;

    // Note that you don't use many of these accessors; if you don't know a need
    // for an accessor, don't write it. Expose as little of your class as possible.


    public String getSubject() {
        return subject;
    }

    public long getId() {
        return id;
    }

    // since you are controlling ID's by an auto-increment, I wouldn't allow this.


    // It may be allowed, to update the subject of Ticket.
    // As ticket has been created with inappropriate subject.
    // Or in case of transfer ticket to agent with proper subject.
    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }


    public long getCreated() {
        return created;
    }

    // Have the ticket class manage its own created and modified timestamps
    public void setCreated(long created) {
        this.created = created;
    }


    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }


    public Ticket(){

    }

    // Note that you don't use this constructor. Can you see how to
    // combine the code so you don't have all the same code duplicated
    // between the two constructors?


    public Ticket(String subject, String agentName, Set<String> tags){

        this.id = countId;
        this.subject = subject;
        this.agentName = agentName;
        // See the email I sent the API team about potential dangers of
        // just storing a reference to this set.
        if(tags == null)
            this.tags = new HashSet<>();
        else
            this.tags = new HashSet<>(tags);
        this.modified = unixTime;
        this.created = unixTime;
        countId++;
    }

    // why 2?

    public Set<String> getTags() {
        if(tags != null)
            return new HashSet<>(tags);
        else
            return null;
    }

    public void setTags(Set<String> tags) {
        this.tags = new HashSet<>(tags);
    }

    public static long getCountId() {
        return countId;
    }


    private void writeObject(ObjectOutputStream o) throws IOException{
            o.writeLong(this.id);
            o.writeUTF(this.subject);
            o.writeUTF(this.agentName);
            o.writeObject(this.tags);
            o.writeLong(this.modified);
            o.writeLong(this.created);
    }

    private void readObject(ObjectInputStream i) throws IOException{
        try {
            this.id = i.readLong();
            this.subject = i.readUTF();
            this.agentName = i.readUTF();
            this.tags = (Set<String>) i.readObject();
            this.modified = i.readLong();
            this.created = i.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean hasTag(String tag){
        return this.getTags().contains(tag);
    }

    @Override
    public Object apply(Object o) {
        return null;
    }
}
