package model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Ticket {
    final private int id;
    private String subject;
    private String agentName;
    private HashSet<String> tags;
    final private LocalDateTime created;
    private  LocalDateTime modified;

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
        this.modified = LocalDateTime.now();
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    // Lokesh: Accept Set interface type in-place of HashSet.
    public void setTags(Set<String> tags) {
        // Lokesh: Don't directly set reference to user's passed Collection for internal object, Instead copy them.
        this.tags = (HashSet)tags;
        this.modified = LocalDateTime.now();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    /*// Lokesh: why do you still require this, that too public?    --- done
   // Update : As a practice remove the setter for modified
    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }*/

    public Ticket(int id, String subject, String agentName, HashSet<String> tagSet){
        this.id = id;
        this.subject = subject;
        this.agentName = agentName;
        this.tags = tagSet;
        this.created = this.modified = LocalDateTime.now();
    }

    @Override
    public String toString(){
        return this.id + "  |  " + this.subject + "  |  " + this.agentName + "  |  " + this.tags + "  |  " + this.created + "  |  " + this.modified;
    }
}
