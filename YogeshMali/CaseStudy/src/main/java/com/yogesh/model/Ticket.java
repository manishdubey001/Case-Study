package com.yogesh.model;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

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

    public Ticket(Ticket ticket) {
        this.id = ticket.id;
        this.subject = ticket.subject;
        this.agentName = ticket.agentName;
        this.tags = ticket.tags;
        this.modified = ticket.created;
        this.created = ticket.modified;

    }

//    public Ticket(int id, String subject, String agentName, Set tags) {
//        this.id = id;
//        this.subject = subject;
//        this.agentName = agentName;
//        this.tags = tags;
//        this.modified = this.created = LocalDateTime.now();
//    }


    //Ganesh D : check Chad's email, you don't have to expose each & every getters & setters
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


    private Ticket(Builder builder) {
        this.id = builder.id;
        this.subject = builder.subject;
        this.agentName = builder.agentName;
        this.tags = builder.tags;
        this.modified = builder.created;
        this.created = builder.modified;
    }

    public static class Builder {
        private int id;
        private String subject;
        private String agentName;
        private Set<String> tags;
        private LocalDateTime created;
        private LocalDateTime modified;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withSubject(final String subject) {
            this.subject = subject;
            return this;
        }

        public Builder withAgentName(final String agentName) {
            this.agentName = agentName;
            return this;
        }

        public Builder withTags(final Set tags) {
            this.tags = tags;
            return this;
        }

        public Builder withCreated(final LocalDateTime created) {
            this.created = created;
            return this;
        }

        public Builder withModified(final LocalDateTime modified) {
            this.modified = modified;
            return this;
        }


        public Ticket build() {
            return new Ticket(this);
        }

    }


}
