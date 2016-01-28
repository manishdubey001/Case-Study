package com.yogesh.model;

import java.io.*;
import java.util.*;

/**
 * Created by root on 31/12/15.
 */
public class Ticket implements Serializable {

    int id;
    String subject;
    String agentName;
    Set<String> tags;
    Date created;
    Date modified;

    public Ticket() {

    }

    public Ticket(int id, String subject, String agentName, Set tags) {
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

    public Set getTags() {
        return tags;
    }

    public void setTags(Set tags) {
        this.tags = tags;
        this.modified = new Date();
    }


    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }


    private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {

        this.id = ois.readInt();
        this.subject = ois.readUTF();
        this.agentName = ois.readUTF();
        this.tags = (Set) ois.readObject();
        this.created = (Date) ois.readObject();
        this.modified = (Date) ois.readObject();

    }


    private void writeObject(ObjectOutputStream oos)
            throws IOException {
        oos.writeInt(id);
        oos.writeUTF(subject);
        oos.writeUTF(agentName);
        oos.writeObject(tags);
        oos.writeObject(created);
        oos.writeObject(modified);

    }

    public static void serialize(ArrayList obj) throws IOException {

        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("SerTicket.ser"));

        os.writeObject(obj);
    }

    public static ArrayList deserialize() throws IOException, ClassNotFoundException {

        ObjectInputStream objIn = new ObjectInputStream(new FileInputStream("SerTicket.ser"));

        return ((ArrayList) objIn.readObject());
    }


}
