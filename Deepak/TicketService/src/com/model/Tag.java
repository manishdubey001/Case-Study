package com.model;

/**
 * Created by root on 14/1/16.
 */
// How do you plan to use this class?
public class Tag {
    private static long countId = 1;
    private long id;
    private String name;
    private short status = 1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Tag(String name){
        this.id = countId;
        this.name = name;
        countId++;
    }


    public int hashCode(){
        int hashCode = name.hashCode();
        return hashCode;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Tag)
        {
            return this.name.equals(((Tag) obj).name);
        }
        else
            return false;
    }
}
