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
    int id;
    String subject;
    String agent;
    long created;
    long modified;
    public String mode = "insert";
    static private int k = 1;
    public Set<String> tags;

    static public Map<Integer,? super Tickets> ticketList; //contains all tickets
    static public Set<String> agentList;
    public static Set<String> tagList;

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
