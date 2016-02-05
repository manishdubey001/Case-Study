package model;


import data.Repository;
import helpers.DateTimeUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Created by root on 31/12/15.
 */
public class TicketModel implements Serializable {
    private int id;
    private String subject;
    private String agent_name;
    private Set<String> tags;
    private long created;
    private long modified;
    private static final long serialVersionUID = 2878186121351618967L;


    @Override
    public String toString() {
        return "id = " + id
                + ", subject = " + subject
                + ", agent = " + agent_name
                + ", tags = " + tags;
    }

    public TicketModel() {

    }

    public TicketModel(int id, String subject, String agentName, Set<String> tags) {
        this.id = id;
        this.subject = subject;
        this.agent_name = agentName;
        this.tags = new HashSet<>(tags);
        this.created = this.modified = DateTimeUtil.getCurrentTimeStampInSeconds();
    }


    private void writeObject(ObjectOutputStream os) throws IOException {
        os.writeInt(this.id);
        os.writeUTF(this.subject);
        os.writeUTF(this.agent_name);
        os.writeObject(this.tags);
        os.writeLong(this.created);
        os.writeLong(this.modified);
    }

    private void readObject(ObjectInputStream oi) throws IOException, ClassNotFoundException {
        this.id = oi.readInt();
        this.subject = oi.readUTF();
        this.agent_name = oi.readUTF();
        this.tags = (HashSet) oi.readObject();
        this.created = oi.readLong();
        this.modified = oi.readLong();
    }

    //
    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    // I would not expose setters for all properties; only the ones absolutely necessary.
    // UPDATE : Removed setters for id & subject field
    public String getAgentName() {
        return agent_name;
    }

    public void setAgentName(String agent_name) {
        this.agent_name = agent_name;
        this.modified = DateTimeUtil.getCurrentTimeStampInSeconds();
    }

    // Here you are allowing the caller to modify the tags collection directly;
    // instead either make a defensive copy, or use a wrapper like Collections.unmodifiableList()
    //UPDATE : used Collections.unmodifiableSet
    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    // allowing a caller to give you a collection directly can be a problem; what if they
    // change it after they give it to you? Sometimes the right thing to do is to create a
    // defensive copy. Also, there is no reason to require the caller to pass a HashSet
    // (instead of just a Set).
    // UPDATE : added defensive copy
    public void setTags(Set<String> tags) {
        this.tags = new HashSet<>(tags);
        this.modified = DateTimeUtil.getCurrentTimeStampInSeconds();
    }

    public long getCreated() {
        return this.created;
    }

    public long getModified() {
        return this.modified;
    }

    // I like that the TicketModel class is responsible for managing the timestamps;
    // however I would not have clients be required to set the isUpdate flag directly.
    // Not everyone will agree with the following recommendations but I think it is
    // usually the way to go
    // 1. Have the accessors like setAgentName() update the modified timestamp; that
    // way when a caller changes the agent, the modification update is automatic.
    // 2. Have a constructor (or use the builder pattern) that takes all the required
    // parameters and sets the create timestamp. This way you know that any call
    // after the constructor is a modify (does away with the need for isUpdate).
    // 3. Do NOT use accessors internally in the class's implementation. You do need
    // to consider carefully if there is any impact on sbuclasses when you do this.


    /**
     * insert
     *
     * @return boolean
     */
    public boolean save() {
        Repository.getInstance().ticketData.put(this.id, this);
        return true;
    }
}