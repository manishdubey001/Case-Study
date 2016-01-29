package com.ticketmaster.models;

import com.ticketmaster.Main;
import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.utils.SerializerUtil;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Ticket Model class
 * Created by Virendra on 31/12/15.
 */
public class Ticket implements Serializable{
    // cjm - Use a singular name like Tickets if this represents
    // cjm - Why Serializable?
    int id;
    String subject;
    String agent;
    long created;
    long modified;
    static private int k = 1;
    public Set<Tag> tags;

    // cjm - Rather than make these static in the Tickets class, I would put them somewhere else.
    // Also don't expose them as public...maybe have a TicketService class with members to provide searches, etc. and these as private members
    static public Map<Integer,? super Ticket> ticketList; //contains all tickets

    TicketRepository repository ;

    // Using '? super Tickets' doesn't hurt anything here (you know the container can hold Tickets() but it doesn't really help either, and just adds complexity
    static public Set<String> agentList;
    public static Set<String> tagList;
    // cjm - Maintaining associated collections like agentList and tagList can be useful; however it can also be
    // hard to keep things in sync. For example when you delete a ticket, stale entries are left behind.

    //creating inner class to setup details in the in ticket
    public static class TicketBuilder{
        private String subject = "";
        private Set<Tag> tags = new HashSet<>();
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
            if (tags != null){
                this.tags = (Set<Tag>)tags;
            }
            return this;
        }

        public String getSubject(){
            return subject;
        }
        public String getAgent(){
            return agent;
        }
        public Set<Tag> getTags(){
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
        repository = TicketRepository.init();
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
    private void setId(int id){
        this.id = id;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void setAgent(String agent){
        this.agent = agent;
    }

    private void setCreated(long created){
        this.created = created;
    }
    private void setModified(long modified){
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
     * delete method to delete the ticket entry from the list
     * @return delete ticket details
     */
    public Ticket delete(){
        int id = this.getId();
        Ticket temp = null;
        if (Ticket.ticketList.containsKey(id)){
            temp = (Ticket) Ticket.ticketList.remove(id);
        }
        return temp == null ? null : temp;

    }
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
    /*public boolean save() throws IOException, ClassNotFoundException, TicketNotFoundException {

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

        if (this.tags != null && !this.tags.isEmpty())
            Ticket.tagList.addAll(this.tags);

        return Ticket.ticketList.get(getId()) != null;
    }*/

    //updated code for save

    /**
     * Updated save code to handle serialization for tickets
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */

    public boolean save() throws IOException, ClassNotFoundException, TicketNotFoundException {

        beforeSave();

        //fetch id from properties file
        SerializerUtil util = new SerializerUtil();
        String masterId = util.readProperty("id");

        Ticket.k = Integer.parseInt(masterId);

        setId(Ticket.k++);

        Map<Integer, Ticket> tempMap = new HashMap<>();
        tempMap.put(getId(), this);

        //add ticket in file
        util.writeToFile(tempMap);

        //read new entries
        Ticket.ticketList = (Map<Integer,Ticket>) util.readFromFile();

        Ticket.ticketList.put(getId(), this);
        Ticket.agentList.add(getAgent());

//        if (this.tags != null && !this.tags.isEmpty())
//            Ticket.tagList.addAll(this.tags);

        //update id in file
        util.writeProperty("id",new Integer(Ticket.k).toString());

        return Ticket.ticketList.get(getId()) != null;
    }

    /**
     * update method updates the details of ticket
     * @return boolean <code></>true</code>
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */
    /*public boolean update()  throws IOException, ClassNotFoundException, TicketNotFoundException{
        beforeSave();
        Ticket.ticketList.put(this.getId(), this);
        return true;
    }*/

    //updated code for update
    public boolean update()  throws IOException, ClassNotFoundException, TicketNotFoundException{

        beforeSave();

        SerializerUtil util = new SerializerUtil();

        Map<Integer, Ticket> tempMap = new HashMap<>();
        tempMap.put(getId(), this);

        //add ticket in file
        util.writeToFile(tempMap);

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
     * getSize method is used to get the size of ticket collection
     * @return integer count of tickets
     */
    public static int getSize(){
        return Ticket.ticketList.size();
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

    public static void clearList(){
        Ticket.ticketList.clear();
    }

    /**
     * overridden method
     * @return hash of instance <p>int</p>
     */
    @Override
    public int hashCode(){
        return getId()+getSubject().hashCode();
    }

    public static void setMasterId(int val){
        k = val;
    }

    private void writeObject(ObjectOutputStream out)
            throws IOException{
        out.writeInt(getId());
        out.writeUTF(getSubject());
        out.writeUTF(getAgent());
        out.writeLong(getCreated());
        out.writeLong(getModified());

    }


    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {

        setId(in.readInt());
        setSubject(in.readUTF());
        setAgent(in.readUTF());
        setCreated(in.readLong());
        setModified(in.readLong());

    }

    @Override
    public String toString(){
        return "Ticket: id"+getId()+"|subject:"+getSubject();
    }


}
