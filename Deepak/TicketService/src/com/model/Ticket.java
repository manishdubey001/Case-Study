package com.model;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by root on 13/1/16.
 */
public class Ticket implements Serializable, Function<Object, Object> {

    private static long countId = 1;
    // Lokesh: With each new Run of this application, your tickets are always starting from ID 1, even though they are already created and serialized in file.
    // They should start after max_id present in file.
    /** Deepak :
     * I noticed this point and I am working on it, but the MaxId from file is not the correct solution for this.
     * As if we delete from the file it will not reflect properly. So I am going to maintain one more file to append Id's and get maxNo from it.
     */
    private long id;
    private String subject;
    // generally Java avoids underscores in names, and uses 'camel case' like agentName
    private String agentName;
    private Set<String> tags;
    private short status = 1;

    // Lokesh: Use LocalDateTime or Joda Time for Date-Time purpose. gives more controlled operations on different time zones and other stuffs.
    private LocalDateTime modified;
    private LocalDateTime created;
    private LocalDateTime unixTime = LocalDateTime.now();

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
    // Lokesh: not in use till now, then there is no sense of defining it.


    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }


    public LocalDateTime getCreated() {
        return created;
    }

    // Have the ticket class manage its own created and modified timestamps

    // Lokesh: no lesson learned from Chad's above comment.


    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    // Lokesh: no use of this constructor.
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


    private void writeObject(ObjectOutputStream oos) throws IOException{
        oos.writeLong(this.id);
        oos.writeUTF(this.subject);
        oos.writeUTF(this.agentName);
        oos.writeObject(this.tags);
        oos.writeObject(this.modified);
        oos.writeObject(this.created);
    }

    private void readObject(ObjectInputStream ois) throws IOException{
        try {
            this.id = ois.readLong();
            this.subject = ois.readUTF();
            this.agentName = ois.readUTF();
            this.tags = (Set<String>) ois.readObject();
            this.modified = (LocalDateTime) ois.readObject();
            this.created = (LocalDateTime) ois.readObject();
        }  catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Lokesh: just because Chad written somewhere in comment, you created this function, but not in use at all?
    /** Deepak:
     * it is in used earlier and I kept this function to show to chad and evaluate from him*/
    public boolean hasTag(String tag){
        return this.getTags().contains(tag);
    }

    @Override
    public Object apply(Object o) {
        return null;
    }
}
