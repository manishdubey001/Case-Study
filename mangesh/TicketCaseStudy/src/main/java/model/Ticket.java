package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 15/1/16.
 */
public class Ticket implements Comparable<Ticket>, Serializable {
    int id;
    String subject;
    String agent_name;
    List<String> tags;
    Date created;
    Date modified;

    public Ticket() {

    }
    public Ticket(int id, String subject, String agentName, List<String> tagsList) {
        this.setId(id);
        this.setSubject(subject);
        this.setAgent_name(agentName);
        this.setTags(tagsList);
    }

    public int getId() {
        return id;
    }

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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public int compareTo(Ticket obj) {
        return obj.getModified().compareTo(getModified());
    }

    private void writeObject(ObjectOutputStream os) throws IOException{
        os.writeInt(getId());
        os.writeUTF(getSubject());
        os.writeUTF(getAgent_name());
        os.writeObject(getTags());
        os.writeObject(getCreated());
        os.writeObject(getModified());
    }

    private void  readObject(ObjectInputStream oi) throws IOException {
        this.id = oi.readInt();
        this.subject = oi.readUTF();
        this.agent_name = oi.readUTF();
        try {
            this.tags = (List) oi.readObject();
            this.created = (Date) oi.readObject();
            this.modified = (Date) oi.readObject();
        }
        catch(ClassNotFoundException e){e.printStackTrace();}
    }

}
