package model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Ticket {
    private int id;
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
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    // Lokesh: Accept Set interface type in-place of HashSet.
    public void setTags(HashSet<String> tags) {
        // Lokesh: Don't directly set reference to user's passed Collection for internal object, Instead copy them.
        this.tags = tags;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    // Lokesh: why do you still require this, that too public?
    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public Ticket(int id, String subject, String agentName, HashSet<String> tagSet){
        this.id = id;
        this.subject = subject;
        this.agentName = agentName;
        this.tags = tagSet;
        this.created = this.modified = LocalDateTime.now();
    }
}
