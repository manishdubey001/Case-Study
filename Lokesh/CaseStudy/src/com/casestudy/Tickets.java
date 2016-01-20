package com.casestudy;

/**
 * Created by root on 12/1/16.
 * Tickets Model Class
 */

import java.util.*;

// Generally use singular name for classes (Ticket instead of Tickets) unless the class stores more than one per instance.
public class Tickets implements Comparable<Tickets>{
    private int id;
    private String subject;
    private String agent;
    private HashSet<String> tags;
    private long created;
    private long updated;

    public Tickets(int id, String subject, String agent, HashSet<String> tags, long created, long updated) {
        this.id = id;
        this.subject = subject;
        this.agent = agent;
        this.tags = tags;
        this.created = created;
        this.updated = updated;
    }


    // rather than create Longs here you can instead use Long.compare() on the primitive types
    @Override
    public int compareTo(Tickets t){
        return Long.valueOf(t.getUpdated()).compareTo(Long.valueOf(this.getUpdated()));
    }

    public static final Comparator<Tickets> updateComparator = new Comparator<Tickets>() {
        @Override
        public int compare(Tickets t1, Tickets t2) {
            return Long.valueOf(t2.getUpdated()).compareTo(Long.valueOf(t1.getUpdated()));
        }
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

    public long getCreated() {
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
    public HashSet<String> getTag() {
        return tags;
    }

    public long getUpdated() {
        return updated;
    }

    // Without any requirement to change a ticket's ID, I wouldn't even implement this. Not all fieldsshould have getter and setter.
    public void setId(int id) {
        this.id = id;
    }

    // Since changing subject is not allowed, I would not implement this.
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    // Getters have similar problems to setters when you are using collections.
    // 1. Prefer just taking a Set<> over a HashSet<> unless you have a specific reason to require HashSet<>.
    // 2. It's good practice to make a copy of the caller's input. Otherwise the caller can modify it after you receive it.
    // 3. Call it setTags() since there can be many
    public void setTag(HashSet<String> tags) {
        this.tags = tags;
    }

    // I wouldn't implement this. I would make 'created' final because it shouldn't ever change.
    public void setCreated(long created) {
        this.created = created;
    }

    // rather than allowing clients to set this, I would incorporate the logic to change 'updated' into
    // the methods of this class that update the ticket (setSubject()/setAgent()/setTags()).
    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String toString(){
        return "{Id: " + this.id + ",Subject: " + this.subject + ",Agent: " + this.agent + ",Tags: " + this.tags.toString() + ",Created: " + this.created + ",Updated: " + this.updated + "}";
    }
}
