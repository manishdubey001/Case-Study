package com.ticketmaster.helpers;

import com.ticketmaster.models.Tag;
import com.ticketmaster.models.Ticket;

import java.io.IOException;

/**
 * Created by root on 28/1/16.
 */
public class TagService {
    private Tag tag;

    public Tag createTags(String name)
            throws IOException{

        if (name == null){
            return null;
        }

        tag = new Tag.TagBuilder().withName(name).build();

        if(tag.save()){
            return tag;
        }
        return null;

    }

    public boolean removeTags(Ticket ticket){
        if (ticket.tags != null){

        }
        return true;
    }
}
