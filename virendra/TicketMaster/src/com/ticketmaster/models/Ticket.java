package com.ticketmaster.models;

import com.ticketmaster.Main;
import com.ticketmaster.exceptions.TicketNotFoundException;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Ticket Model class
 * Created by Virendra on 31/12/15.
 */
public class Ticket /*implements Serializable*/{
    // cjm - Use a singular name like Tickets if this represents
    // cjm - Why Serializable?
    int id;
    String subject;
    String agent;
    long created;
    long modified;
    static private int k = 1;
    public Set<String> tags;

    // cjm - Rather than make these static in the Tickets class, I would put them somewhere else.
    // Also don't expose them as public...maybe have a TicketService class with members to provide searches, etc. and these as private members
    static public Map<Integer,? super Ticket> ticketList; //contains all tickets
    // Using '? super Tickets' doesn't hurt anything here (you know the container can hold Tickets() but it doesn't really help either, and just adds complexity
    static public Set<String> agentList;
    public static Set<String> tagList;
    // cjm - Maintaining associated collections like agentList and tagList can be useful; however it can also be
    // hard to keep things in sync. For example when you delete a ticket, stale entries are left behind.

    //creating inner class to setup details in the in ticket
    public static class TicketBuilder{
        private String subject = "";
        private Set<String> tags = new HashSet<>();
        private String agent = "";

        public TicketBuilder withSubject(String subject){
            this.subject = subject;
            return this;
        }

        public TicketBuilder withAgent(String agent){
            this.agent = agent;
            return this;
        }

        public TicketBuilder withTags(Set tags){
            this.tags = (Set<String>)tags;
            return this;
        }

        public String getSubject(){
            return subject;
        }
        public String getAgent(){
            return agent;
        }
        public Set<String> getTags(){
            return tags;
        }

        public Ticket build (){
            return new Ticket(this);
        }

    }

    /**
     * Default constructor
     */
    public Ticket(){
        if (Ticket.ticketList == null){
            switch(Main.collectionChoice) {
                case 1:
                default:
                    Ticket.ticketList = new HashMap<>();
                    break;
                case 2:
                    Ticket.ticketList = new TreeMap<>();
                    break;
                case 3:
                    Ticket.ticketList = new LinkedHashMap<>();
                    break;
            }
        }
        if (Ticket.agentList == null)
            Ticket.agentList = new HashSet<>();
        if (Ticket.tagList == null)
            Ticket.tagList = new HashSet<>();

    }

    /**
     *
     * @param obj TicketBuilder object
     */

    public Ticket(TicketBuilder obj){
        this();
        this.subject = obj.getSubject();
        this.agent = obj.getAgent();
        this.tags = obj.tags;

    }

    //setter methods
    public void setId(int id){
        this.id = id;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void setAgent(String agent){
        this.agent = agent;
    }

    public void setCreated(long created){
        this.created = created;
    }
    public void setModified(long modified){
        this.modified = modified;
    }

    //getter methods

    public int getId(){
        return this.id;
    }
    public String getSubject(){
        return this.subject;
    }

    public String getAgent(){
        return this.agent;
    }

    public long getCreated(){
        return this.created;
    }
    public long getModified(){
        return this.modified;
    }

    /**
     * method to set values of Ticket object
     * This method is redundant and is removed because of concept of Builder pattern
     *
     * @param values
     */
    /*
    public void setValues(Map<String,? extends Object> values){
        // cjm - In general I would avoid trying to represent an object this way. It can be very error prone.
        // Instead rely on serialization frameworks when you need them. (But doing it this way is probably going
        // to be part of the next case study).
        Set s = values.entrySet();
        s.forEach((e)->{ Map.Entry me = (Map.Entry)e;
            switch(me.getKey().toString()) {
                case "id":
                    setId( (Integer)me.getValue());
                    break;
                case "subject":
                    setSubject((String)me.getValue());
                    break;
                case "agent":
                    setAgent((String)me.getValue());
                    break;
                case "tags":
                    this.tags = (Set<String>) me.getValue();
                    break;

            }
        });

    }
    */

    /**
     *
     * @return boolean
     */
    protected boolean beforeSave(){

        if(created == 0){
            setCreated(System.currentTimeMillis());
        }
        setModified(System.currentTimeMillis());

        return true;

    }

    /**
     * save method saves the details of ticket
     * @return boolean
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */
    public boolean save() throws IOException, ClassNotFoundException, TicketNotFoundException {

        beforeSave();
        setId(k++);



        // cjm - Can you explain how, in a single-threaded environment, we would ever fail the condition getId()==k?
        // Also, look at what you are assigning to k. In the case 'getId()==k' is true, you assign
        // k = k+=1;
        // not k=k+1 (the effect is the same; do you see why?)

        // cjm - In other words, can you explain how the above two lines of code are logically different from this line:
        // setId(k++);


        Ticket.ticketList.put(getId(), this);
        Ticket.agentList.add(getAgent());

        if (this.tags != null)
            Ticket.tagList.addAll(this.tags);

        return Ticket.ticketList.get(getId()) != null;
    }

    /**
     * update method updates the details of ticket
     * @return boolean <code></>true</code>
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */
    public boolean update()  throws IOException, ClassNotFoundException, TicketNotFoundException{
        beforeSave();
        Ticket.ticketList.put(this.getId(), this);
        return true;
    }


    /**
     * getListStream method is used to create the stream of ticket list
     * @return Stream
     */
    public static Stream getListStream(){

        return Ticket.ticketList.entrySet().stream();

    }

    /**
     *
     * @param name name of the agent for lookup
     * @return boolean
     */
    public static boolean hasAgent(String name) {
        return Ticket.agentList.contains(name);
    }

    /**
     *
     * @param name name of the tag for lookup
     * @return boolean
     */
    public static boolean hasTag(String name) {
        return Ticket.tagList.contains(name);
    }

    public void deleteTicket(int id){
        Ticket.ticketList.remove(id);
    }

    /**
     * overridden method
     * @return hash of instance <p>int</p>
     */
    @Override
    public int hashCode(){
        return getId()+getSubject().hashCode();
    }


    /*private Map<Integer, ? super Ticket> deserializeTickets() throws IOException, ClassNotFoundException, TicketNotFoundException {

        //save the ticket in TreeMap

        ObjectInputStream in=new ObjectInputStream(new FileInputStream("tickets.ser"));

        ticketList = (TreeMap<Integer, Ticket>) in.readObject();

        if (ticketList.isEmpty()) {
            ticketList = new TreeMap<>();
        }

        return ticketList;

    }

    private void  serializeTickets() throws IOException, ClassNotFoundException, TicketNotFoundException {

        ObjectOutputStream fOut = new ObjectOutputStream(new FileOutputStream("tickets.ser"));

        fOut.writeObject(ticketList);

    }*/




}
