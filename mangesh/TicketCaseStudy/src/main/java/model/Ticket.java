package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Ticket implements Serializable{
    private int id;
    private String subject;
    private String agentName;
    private HashSet<String> tags;
    private LocalDateTime created;
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

    public void setTags(HashSet<String> tags) {
        this.tags = tags;
        this.modified = LocalDateTime.now();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public Ticket(int id, String subject, String agentName, HashSet<String> tagSet){
        this.id = id;
        this.subject = subject;
        this.agentName = agentName;
        this.tags = tagSet;
        this.created = this.modified = LocalDateTime.now();
    }

    private void writeObject(ObjectOutputStream oos) throws Exception{
        oos.writeInt(id);
        oos.writeUTF(subject);
        oos.writeUTF(agentName);
        oos.writeObject(tags);
        oos.writeObject(created);
        oos.writeObject(modified);
    }

    private void readObject(ObjectInputStream ois) throws Exception{
        id = ois.readInt();
        subject = ois.readUTF();
        agentName = ois.readUTF();
        tags = (HashSet<String >) ois.readObject();
        created = (LocalDateTime) ois.readObject();
        modified = (LocalDateTime)ois.readObject();
    }

}
