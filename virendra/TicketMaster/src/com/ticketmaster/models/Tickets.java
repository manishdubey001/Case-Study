package com.ticketmaster.models;

import com.ticketmaster.Main;
import com.ticketmaster.exceptions.DuplicateEntryException;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by root on 31/12/15.
 */
public class Tickets implements Serializable{
    // cjm - Use a singular name like Tickets if this represents
    // cjm - Why Serializable?
    int id;
    String subject;
    String agent;
    long created;
    long modified;
    public String mode = "insert";
    static private int k = 1;
    public Set<String> tags;

    // cjm - Rather than make these static in the Tickets class, I would put them somewhere else.
    // Also don't expose them as public...maybe have a TicketService class with members to provide searches, etc. and these as private members
    static public Map<Integer,? super Tickets> ticketList; //contains all tickets
    // Using '? super Tickets' doesn't hurt anything here (you know the container can hold Tickets() but it doesn't really help either, and just adds complexity
    static public Set<String> agentList;
    public static Set<String> tagList;
    // cjm - Maintaining associated collections like agentList and tagList can be useful; however it can also be
    // hard to keep things in sync. For example when you delete a ticket, stale entries are left behind.

    /**
     * Default constructor
     */
    public Tickets(){
        if (Tickets.ticketList == null){
            switch(Main.collectionChoice) {
                case 1:
                default:
                    Tickets.ticketList = new HashMap<>();
                    break;
                case 2:
                    Tickets.ticketList = new TreeMap<>();
                    break;
                case 3:
                    Tickets.ticketList = new LinkedHashMap<>();
                    break;
            }


        }
        if (Tickets.agentList == null)
            Tickets.agentList = new HashSet<>();
        if (Tickets.tagList == null)
            Tickets.tagList = new HashSet<>();

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
     *
     * @param values
     */
    public void setValues(Map<String,? extends Object> values){
        // cjm - In general I would avoid trying to represent an object this way. It can be very error prone.
        // Instead rely on serialization frameworks when you need them. (But doing it this way is probably going
        // to be part of the next case study).
        Set s = values.entrySet();
        s.forEach((e)->{ Map.Entry me = (Map.Entry)e;
            switch(me.getKey().toString()) {
//                case "id":
//                    setId( (Integer)me.getValue());
//                    break;
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

    /**
     *
     * @return
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
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicateEntryException
     */
    public boolean save() throws IOException, ClassNotFoundException, DuplicateEntryException {

        beforeSave();
        setId(k);

        k = getId() == k ?  k+=1 : k;
        // cjm - Can you explain how, in a single-threaded environment, we would ever fail the condition getId()==k?
        // Also, look at what you are assigning to k. In the case 'getId()==k' is true, you assign
        // k = k+=1;
        // not k=k+1 (the effect is the same; do you see why?)

        // cjm - In other words, can you explain how the above two lines of code are logically different from this line:
        // setId(k++);

        Tickets.ticketList.put(getId(), this);
        Tickets.agentList.add(getAgent());

        if (this.tags != null)
            Tickets.tagList.addAll(this.tags);

        return Tickets.ticketList.get(getId()) != null;
    }

    /**
     * update method updates the details of ticket
     * @return boolean <code></>true</code>
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicateEntryException
     */
    public boolean update()  throws IOException, ClassNotFoundException, DuplicateEntryException{
        beforeSave();
        Tickets.ticketList.put(this.getId(), this);
        return true;
    }


    /**
     * getListStream method is used to create the stream of ticket list
     * @return Stream
     */
    public static Stream getListStream(){

        return Tickets.ticketList.entrySet().stream();

    }

    /**
     * overridden method
     * @return hash of instance <p>int</p>
     */
    @Override
    public int hashCode(){

        return getId()+getSubject().hashCode();
    }


    /*private Map<Integer, ? super Tickets> deserializeTickets() throws IOException, ClassNotFoundException, DuplicateEntryException {

        //save the ticket in TreeMap

        ObjectInputStream in=new ObjectInputStream(new FileInputStream("tickets.ser"));

        ticketList = (TreeMap<Integer, Tickets>) in.readObject();

        if (ticketList.isEmpty()) {
            ticketList = new TreeMap<>();
        }

        return ticketList;

    }

    private void  serializeTickets() throws IOException, ClassNotFoundException, DuplicateEntryException {

        ObjectOutputStream fOut = new ObjectOutputStream(new FileOutputStream("tickets.ser"));

        fOut.writeObject(ticketList);

    }*/




}
