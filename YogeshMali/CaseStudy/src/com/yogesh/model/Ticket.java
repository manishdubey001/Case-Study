package com.yogesh.model;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * Created by root on 31/12/15.
 */
public class Ticket implements Serializable {

    int id;
    String subject;
    String agentName;
    Set<String> tags;
    LocalDateTime created;
    LocalDateTime modified;

    public Ticket() {
    }

    public Ticket(int id, String subject, String agentName, Set tags) {
        this.id = id;
        this.subject = subject;
        this.agentName = agentName;
        this.tags = tags;
        this.modified = this.created = LocalDateTime.now().minusDays(new Random().nextInt(20));
    }

    //Ganesh D : check Chad's email, you don't have to expose each & every getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;

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
        this.modified = LocalDateTime.now();
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set tags) {
        this.tags = tags;
        this.modified = LocalDateTime.now();
    }


    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }


    private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {

        this.id = ois.readInt();
        this.subject = ois.readUTF();
        this.agentName = ois.readUTF();
        this.tags = (Set) ois.readObject();
        this.created = (LocalDateTime) ois.readObject();
        this.modified = (LocalDateTime) ois.readObject();

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
