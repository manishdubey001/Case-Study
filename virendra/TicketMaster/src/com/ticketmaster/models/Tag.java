package com.ticketmaster.models;

import com.ticketmaster.utils.SerializerUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 28/1/16.
 */
public class Tag implements Serializable {

    private String id;
    private String name;
    private long created;
    private long modified;

    //setter methods
    private void setId(String id){
        this.id = id;
    }
    private void setName(String name){
        this.name = name;
    }

    private void setCreated(long created){
        this.created = created;
    }
    private void setModified(long modified){
        this.modified = modified;
    }

    //getter methods

    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    public long getCreated(){
        return this.created;
    }
    public long getModified(){
        return this.modified;
    }

    public static class TagBuilder{

        private String name;

        public TagBuilder withName(String name){
            this.name = name;
            return this;
        }

        public Tag build(){
            return new Tag(this);

        }



    }
    public Tag(TagBuilder object){
        this.name = object.name;

    }

    public boolean save() throws IOException{

        //fetch id from properties file
        SerializerUtil util = new SerializerUtil("tags.ser");
        String tagId = util.readProperty("tagId");
        int id = Integer.parseInt(tagId)+1;

        setId(new Integer(id).toString());

        Map<String, Tag> tempMap = new HashMap<>();
        tempMap.put(getId(), this);

        //add ticket in file
        util.writeToFile(tempMap);

        util.writeProperty("tagId",new Integer(id).toString());

//        return Ticket.ticketList.get(getId()) != null;
        return true;

    }

    private void writeObject(ObjectOutputStream out)
            throws IOException {
        out.writeUTF(getId());
        out.writeUTF(getName());
        out.writeLong(getCreated());
        out.writeLong(getModified());

    }


    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {

        setId(in.readUTF());
        setName(in.readUTF());
        setCreated(in.readLong());
        setModified(in.readLong());

    }

}
