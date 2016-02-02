package model;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 15/1/16.
 */
public class Ticket implements Comparable<Ticket>{
    int id;
    String subject;
    String agent_name;
    // List allows duplicate entries, why would you allow a ticket to have same Tag multiple time?
    List<String> tags;
    // timeStamp is never used in entire case study? What is significance of putting here?
    long timeStamp;

    // Use of Java 8's LocalDateTime or Other implementation like Joda DateTime are recommended in-place of Java 7's Date.
    Date created;
    Date modified;

    public Ticket() {

    }
    public Ticket(int id, String subject, String agentName, List<String> tagsList) {
        // Why to use setters in Constructor?
        this.setId(id);
        this.setSubject(subject);
        this.setAgent_name(agentName);
        this.setTags(tagsList);
    }

    // This is not always required to implement getters and setters for all the member variables. be very wise in selecting members for which you require getters and setters
    public int getId() {
        return id;
    }

    // Do you ever set ID of any Ticket already created? Why this setter.
    public void setId(int id) {
        this.id = id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

//  Returning your internal collection directly allows callers to modify it outside this class's implementation. One way to account
//  for this is to make a defensive copy, or to apply an immutable wrapper like Collections.unmodifiableSet(). So I would recommend this be
//  public List<String> getTags() { return Collections.unmodifiableList(tags); }
    public List<String> getTags() {
        return tags;
    }

//  Setters have same issue as getters when taking Collection as input. It's good practice to make a copy of the caller's input. Otherwise the caller can modify it after you receive it.
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getCreated() {
        return created;
    }

    // once set, it's never ever going to change. "created" can be final in this case. No need to have it's setter at all.
    public void setCreated(Date created) {
        this.created = created;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Date getModified() {
        return modified;
    }

    // You would never wish to update the modified without any other changes like tags/agent. You can actually updated "modified" when something actually changing for Ticket rather than using this setter.
    public void setModified(Date modified) {
        this.modified = modified;
    }

    public int compareTo(Ticket obj) {
        return obj.getModified().compareTo(getModified());
    }
}
