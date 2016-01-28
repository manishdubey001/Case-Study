package com.ticket;

import java.util.HashSet;

/**
 * Created by root on 18/1/16.
 */
// Usually I would leave the "Model" off the name and just call this Ticket
public class TicketModel implements Comparable<TicketModel>{
    private final int id;
    private final String subject;
    private String agentName;
    // Do you need this to be HashSet? If not, just make it a Set<String>
    private HashSet<String> tags;
    private final long  created;
    private long modified;

    TicketModel(int ticketId, String subject, String agentName, HashSet<String> setOfTags){
        this.id         = ticketId;
        this.subject    = subject;
        this.agentName  = agentName;
        this.tags       = setOfTags;
        this.created    = Util.Timestamp();
        // most commonly if you have a modified date you set it to created date on creation.
    }

    // return Set<String>
    public HashSet<String> getTags() {
        return tags;
    }

    // Good use of encapsulation to set the modified timestamp directly.
    // Since you have the same logic in several places I would created
    // a private setModified() method that gets the current timestamp
    // and sets it.
    // Also see email to API team about possibly making a copy of tags.
    // Finally, just take a Set<String> as input; no reason to require it to be a HashSet.
    public void setTags(HashSet<String> tags) {
        this.setModified(Util.Timestamp());
        this.tags = tags;
    }

    public long getCreated() {
        return created;
    }
    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.setModified(Util.Timestamp());
        this.agentName = agentName;
    }

    public String getSubject() {
        return subject;
    }

    public int getId() {
        return id;
    }

    public long getModified() {
        return modified;
    }

    private void setModified(long modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "Ticket id is: " + getId() +" subject is: "+ getSubject() + " agent is: "+ getAgentName() + " Tags: "+ getTags() + " Created: "+ Util.getDateFormat(getCreated())
                +" Modified: " + Util.getDateFormat(getModified());
    }

    @Override
    public int compareTo(TicketModel ticketModel) {
        return (int) (ticketModel.getModified() - this.getModified());
    }
}
