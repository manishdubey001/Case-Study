package com.ticket;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

/**
 * Created by root on 18/1/16.
 */
// Usually I would leave the "Model" off the name and just call this Ticket
//updated Ticket name to Ticket
public class Ticket implements Comparable<Ticket>{
    private final int id;
    private final String subject;
    private String agentName;
    // Do you need this to be HashSet? If not, just make it a Set<String>

    //UPDATE
    private Set<String> tags;
    private final LocalDateTime created;
    private LocalDateTime modified;

    Ticket(int ticketId, String subject, String agentName, Set<String> setOfTags){
        this.id         = ticketId;
        this.subject    = subject;
        this.agentName  = agentName;
        this.tags       = setOfTags;
        this.created    = Util.timestamp();
        // most commonly if you have a modified date you set it to created date on creation.

        //UPDATE
        this.modified   = this.created;
    }

    //UPDATE
    public Set<String> getTags() {
        //UPDATE
        //unmodifiable or read only set
        return Collections.unmodifiableSet(tags);
    }

    // Good use of encapsulation to set the modified timestamp directly.
    // Since you have the same logic in several places I would created
    // a private setModified() method that gets the current timestamp
    // and sets it.
    // Also see email to API team about possibly making a copy of tags.
    // Finally, just take a Set<String> as input; no reason to require it to be a HashSet.

    //UPDATE
    public void setTags(Set<String> tags) {
        this.setModified(Util.timestamp());
        this.tags = tags;
    }

    public LocalDateTime getCreated() {
        return created;
    }
    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.setModified(Util.timestamp());
        this.agentName = agentName;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    private void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "Ticket id is: " + this.id +" Subject is: "+ this.subject + " Agent is: "+ this.agentName + " Tags: "+ this.tags + " Created: "+ Util.getDateFormat(this.created)
                +" Modified: " + Util.getDateFormat(this.modified);
    }

    @Override
    public int compareTo(Ticket ticketModel) {
        return ticketModel.getModified().compareTo(this.modified);
    }

    /**
     * Create new ticket object
     *
     * @param ticketId
     * @param subject
     * @param agentName
     * @param setOfTags
     * @return new object of ticket model
     */
    public static Ticket newInstance(int ticketId, String subject, String agentName, Set<String> setOfTags) {
        //UPDATE
        return new Ticket(ticketId, subject, agentName, setOfTags);
    }
}
