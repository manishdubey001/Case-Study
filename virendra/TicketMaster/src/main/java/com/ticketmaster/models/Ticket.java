package com.ticketmaster.models;

import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.utils.SerializerUtil;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Ticket Model class
 * Created by Virendra on 31/12/15.
 */
public class Ticket implements Serializable{

    int id;
    long created;
    long modified;
    static private int masterId = 1;
    public static final long serialVersionUID = 881811645564116084L;

    String subject;
    String agent;
    public Set<String> tags;

    // cjm - Rather than make these static in the Tickets class, I would put them somewhere else.
    // Also don't expose them as public...maybe have a TicketService class with members to provide searches, etc. and these as private members
//    static public Map<Integer,? super Ticket> ticketList; //contains all tickets

    public TicketRepository repository ;

    // Using '? super Tickets' doesn't hurt anything here (you know the container can hold Tickets() but it doesn't really help either, and just adds complexity
//    static public Set<String> agentList;
//    public static Set<String> tagList;
    // cjm - Maintaining associated collections like agentList and tagList can be useful; however it can also be
    // hard to keep things in sync. For example when you delete a ticket, stale entries are left behind.

    //creating inner class to setup details in the in ticket - Builder Pattern
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
            if (tags != null){
                this.tags = (Set<String>)tags;
            }
            return this;
        }
        public String getSubject(){
            return subject;
        }
        public String getAgent(){
            return agent;
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
    public Ticket delete()
            throws IOException, ClassNotFoundException {

        if(! (repository instanceof TicketRepository )){
            repository = TicketRepository.init();
        }

        //before delete update local data set
        SerializerUtil util = new SerializerUtil();
        System.out.println(repository);
        repository.updateList((Map<Integer, Ticket>)util.readFromFile());

        //delete ticket from the local data set
        Ticket ticket = repository.deleteTicket(this.getId());

        if (ticket != null){
            //clean file contents
            util.emptyObjectFile();
            //write complete data again to file
            util.writeToFile(repository.getList());
        }
        return ticket;
    }
    /**
     *
     * @return boolean
     */
    protected boolean beforeSave(){

        long time = LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC).toEpochMilli();
        if(created == 0)
            setCreated(time);
        setModified(time);
        return true;
    }

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
        String tempId = util.readProperty("id");

        Ticket.masterId = Integer.parseInt(tempId);

        setId(Ticket.masterId++);

        Map<Integer, Ticket> tempMap = new HashMap<>();
        tempMap.put(getId(), this);

        //add ticket in file
        util.writeToFile(tempMap);
        //read new entries
        repository.updatePool();
        repository.addAgent(getAgent());
        repository.addTags(this.tags);
        //update id in file
        util.writeProperty("id",new Integer(Ticket.masterId).toString());
        return repository.getTicket(this.getId()) != null;
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
        SerializerUtil util = new SerializerUtil();
        Map<Integer, Ticket> tempMap = new HashMap<>();
        tempMap.put(getId(), this);
        //add ticket in file
        util.writeToFile(tempMap);

        if(! (repository instanceof TicketRepository )){
            repository = TicketRepository.init();
        }
        repository.update(this.getId(),this);
        return true;
    }



    /**
     * getListStream method is used to create the stream of ticket list
     * @return Stream
     */
    public static Stream getListStream(){
        return TicketRepository.init().getList().entrySet().stream();
    }

    /**
     * getSize method is used to get the size of ticket collection
     * @return integer count of tickets
     */
    public static int getSize(){
        return TicketRepository.init().getList().size();
    }

    /**
     *
     * @param name name of the agent for lookup
     * @return boolean
     */
    public static boolean hasAgent(String name) {
        return TicketRepository.init().getAgentList().contains(name);
    }

    /**
     *
     * @param name name of the tag for lookup
     * @return boolean
     */
    public static boolean hasTag(String name) {
        return TicketRepository.init().getTagList().contains(name);
    }

    public static void clearList(){
        TicketRepository.init().getList().clear();
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
        masterId = val;
    }

    private void writeObject(ObjectOutputStream out)
            throws IOException{
        out.writeInt(getId());
        out.writeUTF(getSubject());
        out.writeUTF(getAgent());
        out.writeObject(this.tags);
        out.writeLong(getCreated());
        out.writeLong(getModified());
    }


    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {

        setId(in.readInt());
        setSubject(in.readUTF());
        setAgent(in.readUTF());
        this.tags = (HashSet) (in.readObject());
        setCreated(in.readLong());
        setModified(in.readLong());

    }

    @Override
    public String toString(){
        return "Ticket: id"+getId()+"|subject:"+getSubject();
    }
}