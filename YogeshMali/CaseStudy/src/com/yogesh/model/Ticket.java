package com.yogesh.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 31/12/15.
 */
public class Ticket  implements  Serializable{

    int id;
    public String subject;
    String agentName;
    List<String> tags;
    Date created;
    Date modified;

    public Ticket() {

    }

    public Ticket(int id, String subject, String agentName, List tags) {
        this.setId(id);
        this.setSubject(subject);
        this.setAgentName(agentName);
        this.setTags(tags);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.created = new Date();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
        this.modified = new Date();
    }

    public List getTags() {
        return tags;
    }

    public void setTags(List tags) {
        this.tags = tags;
        this.modified = new Date();
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

    private void setModified(Date modified) {
        this.modified = modified;
    }

    private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        this.id = ois.readInt();
        this.subject = ois.readUTF();
        this.agentName = ois.readUTF();
        this.tags = (List)ois.readObject();

    }  //  public void readObject()
    private void writeObject(ObjectOutputStream oos)
            throws IOException {
        oos.writeInt(id);
        oos.writeUTF(subject);
        oos.writeUTF(agentName);
        oos.writeObject(tags);
        //	The above two lines are equivalent to adding:
        //	oos.defaultWriteObject();
    }  //  public void writeObject()



}
