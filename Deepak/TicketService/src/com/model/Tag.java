package com.model;

/**
 * Created by root on 14/1/16.
 */
// How do you plan to use this class?
    // This will allowed you to have default tags and one can add own custom tags in system.
    // One can select from these predefined tags.
    // Duplicate tags are not allowed.
    // default tags are not deletable, custom tags can be deleted.
    // In case of deletion of custom tag, one of default tag will applicable to respective tickets.
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
