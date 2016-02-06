package com.services;

import com.customexceptions.UserInputException;
import com.factory.TicketFactory;
import com.model.Ticket;
import com.util.UserConsoleInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by root on 15/1/16.
 */
public class TicketOperations {

    // Consider whether this the best way to store the tickets? Every operation by ID
    // requires scanning the entire array. If there are thousands or millions of tickets,
    // this could be expensive.
    // Lokesh: When not required(or changed), why it is left declared at all? When making modification, don't forget to remove/comment redundant code.
    /*private ArrayList<Ticket> ticketArrayList = null;
    private Map<Long, Ticket> ticketHashMap = null;
    public Set<String> allTagHashSet = null;*/

    // Lokesh: When no inheritance in picture, no need to define a constructor just to initialize instance variables.
    // This can be done at the time of declaration itself or when they are actually used.

    /** Deepak:
     * To initialize instance variable we can used single liner or initializer block or constructor.
     * Now I am not performing any pre processing operation so single liner is better way. */
    /** TicketOperations(){
        ticketHashMap = new HashMap<>();
        allTagHashSet = new HashSet<>();

    }*/

    private Map<Long, Ticket> ticketHashMap; //* = new HashMap<>();
//    public Set<String> allTagHashSet = new HashSet<>();
    private static long ticketId;
    File file;
    File ticketIdFile;
    Properties properties;

    /** Deepak: Initializer block used for file creating */

    public TicketOperations() {
        file = TicketSerializedClass.createFile("tickets.ser");
        ticketHashMap = TicketSerializedClass.readTicketsFromFile(file);
        ticketIdFile = TicketSerializedClass.createFile("ticketIdFile.properties");
        try {
            if (properties == null) {
                if(ticketIdFile.length() == 0)
                {
                    properties = new Properties();
                    TicketSerializedClass.updatePropertyFile(ticketIdFile, properties, Long.valueOf(1));
                }
                FileReader reader = new FileReader(ticketIdFile);
                properties = new Properties();
                properties.load(reader);
                ticketId = Long.parseLong(properties.getProperty("ticketId"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * To set parameters for Create ticket from console input */
    public void create(){
        // Generally, try to define variables at the same time you
        // assign them. I would combine these declarations and assignments.


        // Note that you create a new HashSet here but never use it; it is thrown
        // away when you assign it the result of getTagNames() below.

        /*String subject = UserConsoleInput.getSubject();
        String agentName = UserConsoleInput.getAgentName();*/
        String subject = UserConsoleInput.acceptString("Enter subject");
        String agentName = UserConsoleInput.acceptString("Enter Agent name");

        System.out.println("Do you want to enter Tag? y/n");
        String updateTag = UserConsoleInput.getPermission("y", "n");
        Set<String> tagHashSet;

        if(updateTag.toLowerCase().equals("y"))
            tagHashSet = UserConsoleInput.getTagNames();
        else
            tagHashSet = new HashSet<>();

        try {
            createTicket(subject, agentName, tagHashSet);
            System.out.println("Ticket created successful!");
        }
        catch(UserInputException e){
            System.out.println(e.getMessage());
        }

        // Lokesh: Why do you really need this check? Do you really suspect that call to createTicket can return any object other than Ticket?
       /* if(ticket instanceof Ticket)
            System.out.println("Ticket create successful!");
        else
            System.out.println("Sorry your ticket has not been created!");*/

    }

    /*
    * Crating ticket using  */
    // Returning error codes can be a confusing way to indicate success or failure.
    // If it matters to the caller, I would return the Ticket object directly, or void
    // if it does't matter.
    // If a Ticket can't be created in a method named 'createTicket', I would throw an exception.

    // Lokesh: Seems like Chad's comment not interpreted completely.
    // Lokesh: By returning null values, you force caller to check for null returns, this should not be a practical case.
    public Ticket createTicket(String subject, String agentName, Set<String> tagHashSet) throws UserInputException {

        Ticket ticket;
//        try {
            // Avoid accepting Null Values to your own functions. By accepting null as valid input, you force yourself to series of null checks.
            if (subject == null || subject.isEmpty())
                throw new UserInputException("Please enter proper subject! Ticket Not created");
            else if (agentName == null || agentName.isEmpty())
                throw new UserInputException("Please enter proper agent name! Ticket Not created");
            else
                ticket = TicketFactory.createTicketInstance(ticketId, subject, agentName, tagHashSet);

       /* }catch (UserInputException e) {
            System.out.println(e.getMessage());
        }*/

        if(ticket != null) {
            boolean append = true;
            if(file.length() == 0)
                append = false;

            ticketHashMap.put(ticket.getId(), ticket);

            if (TicketSerializedClass.saveTicketsInFile(ticketHashMap, append, file)){
                ticketId++;
                TicketSerializedClass.updatePropertyFile(ticketIdFile, properties, ticketId);

                return ticket;
            }
            else
                throw new UserInputException("Sorry! ticket creation failed!");

            /** Deepak:
             * throw exception on failure of ticket create and save in file.*/
            /*return null;*/
        }
        else{
            throw new UserInputException("Sorry! ticket creation failed!");
        }
    }


    public void getAll(){
        Map<Long, Ticket> allTickets = getAllTicket();
        getTickets(allTickets);
    }

    /*
    * Show all the tickets in ArrayList */
    // call this 'get' if you are just returning the list.
    public Map<Long, Ticket> getAllTicket(){
        /*return TicketSerializedClass.readTicketsFromFile(file);*/
        return ticketHashMap;
    }


    /*
    * Show Ticket By Id */
    // Same comment about 'get' versus 'show'
    public Ticket getTicketById(int id) throws UserInputException {

        /* if id beyond the limit */
        //Map<Long, Ticket> tempTicketMap = null;

        if(id <= 0 || id > ticketId-1){
            //return new HashMap<>();
            throw new UserInputException("Invalid ticket id! please enter valid ticket id");
        }
        // Lokesh: Rather than de-serializing for every ticket, you can use your "ticketHashMap" for this purpose, and keep serialized file and "ticketHashMap" in sync.
        /** Deepak:
         * I am using this file substitute for db for the time being. If Ticket is created successful and stored in my
         * ticketHashMap but problem in Save in file. Thus to avoid redundancy I Used file */
        /*Map<Long, Ticket> tempMap = TicketSerializedClass.readTicketsFromFile();*/

        // Stream and Lambda implementation.
        // usually you avoid storing intermediate results and just chain this together.
        /*tempTicketMap = ticketHashMap.values().stream().filter(ticket -> ticket.getId() == id)
                                        .collect(Collectors.toMap(Ticket::getId, Function.identity()));
        return tempTicketMap;*/

        Ticket ticket = ticketHashMap.get(Long.valueOf(id));
        if(ticket == null)
            throw new UserInputException("Sorry Ticket not found!");
        else
            return ticket;

    }


    /*
    * Show the selected list of tickets */
    public void getTickets(Map<Long, Ticket> ticketMap){
        if(ticketMap == null || ticketMap.size() == 0 || ticketMap.isEmpty()){
            System.out.println("No Ticket available!");
            return;
        }

        /*
        * Stream implementation of sorting and comparator */


        // Lokesh: No lesson leaned from Chad's comment: usually you avoid storing intermediate results and just chain this together.
        // Redundant "stream" in below line can be avoided.
        /*Stream<Ticket> stream = ticketMap.values().stream();*/
        // Lokesh: Generating Long is not required for Comparator.
        // Lokesh: More effective ways to use stream API available: This simple line would have done for below:
        // ticketMap.values().stream().sorted((t1,t2) -> Long.compare(t2.getModified(),t1.getModified())).forEach((t)-> System.out.println("YOUR TICKET OBJECT"));

        /** stream.sorted((Ticket t1, Ticket t2) -> Long.valueOf(t2.getModified()).compareTo(Long.valueOf( t1.getModified()))) */

        /*ticketMap.values().stream().sorted((Ticket t1, Ticket t2) -> t2.getModified().compareTo(t1.getModified()))
              .forEach((Ticket ticket) -> System.out.println(
                      ticket.getId()+" | "+ticket.getSubject()+" | "+ticket.getAgentName()+
                              " | "+ticket.getTags()+" | "+ticket.getModified()));*/

        ticketMap.values().stream().sorted((Ticket t1, Ticket t2) -> t2.getModified().compareTo(t1.getModified()))
                .forEach(System.out::println);
   }

    /**
     * Display single ticket */
    public void getTicket(){
        System.out.println("Enter ticket Id");
        int id = UserConsoleInput.acceptNumber();
        try {
            Ticket ticket = getTicketById(id);
            System.out.println(ticket);
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }
    }


    /*
    * Inner class for sorting using comparator on modified date */
    // Lokesh: We can avoid Comparator for primitives types. Find ways to use: Comparator.comparing. There may be some more.
    // Lokesh: Why custom comparator, when not used?
    /*class ModifiedComparator implements Comparator<Ticket>{

        @Override
        public int compare(Ticket t1, Ticket t2) {
            return t1.getModified().compareTo(t2.getModified());
        }
    }*/


    /*
    * To update ticket's agent name and tags by ticket id */

    // Lokesh: You are forcing to update Agent and Tags. Consider below cases:
    // What if I don't want to update Agent and just want to update Tag? In your case I need to enter same agent name again :D
    // What if I want to update only agent and expect to tags unchanged? In your case I need to enter same tags string :D
    // What if I just want to add one tag only existing ones? In your case I have to enter string of tags including tags which are already there.
    // What if I want to remove one tag only? In  your case I have to enter string of tags skipping the one that I don't want.
    public void updateTicketById(){
        System.out.println("Enter ticket Id");
        int id = UserConsoleInput.acceptNumber();

        /*if(id <= 0){
            System.out.println("Ticket id is not valid");
            return;
        }*/

        Ticket modifyTicket;
        try {
            modifyTicket = getTicketById(id);

        } catch (UserInputException e) {
            System.out.println(e.getMessage());
            return;
        }

        /*if(modifyTicket == null || modifyTicket.isEmpty()){
            System.out.println("Ticket with id: "+id+" is not available");
            return;
        }
        else{
            getTickets(modifyTicket);
        }*/

        System.out.println(modifyTicket);

        String agentName;
        Set<String> tagHashSet;

        System.out.println("Do you want to update agent? : y/n");
        String updateAgent = UserConsoleInput.getPermission("y", "n");

        if(updateAgent.toLowerCase().equals("y")){
            /*agentName = UserConsoleInput.getAgentName();*/
            agentName = UserConsoleInput.acceptString("Enter Agent Name");
        }
        else {
            agentName = modifyTicket.getAgentName();
        }

        System.out.println("Do you want to update Tag? y/n");
        String updateTag = UserConsoleInput.getPermission("y", "n");

        if(updateTag.toLowerCase().equals("y")){

            System.out.println("You want to add/remove Tag? (a/r):");
            String addOrRemove = UserConsoleInput.getPermission("a", "r");
            tagHashSet = UserConsoleInput.getTagNames();
            if(addOrRemove.equals("a"))
                tagHashSet.addAll(modifyTicket.getTags());
            if(addOrRemove.equals("r")) {
                Set<String> tempTags = modifyTicket.getTags();
                tempTags.removeAll(tagHashSet);
                tagHashSet = tempTags;
            }
        }
        else {
            tagHashSet = modifyTicket.getTags();
        }

        /*tagHashSet = UserConsoleInput.getTagNames();*/

        try {
            Ticket ticket = updateTicket(id, agentName, tagHashSet);
            System.out.println("Ticket update successful...!");
            getTicketById((int) ticket.getId());

        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }

    /*    if(ticket == null){
            System.out.println("Ticket update failed!");
        }
        else{
            System.out.println("Ticket update successful");
        }*/

    }

    public Ticket updateTicket(int id, String agentName, Set<String> tagHashSet) throws UserInputException {
        // A method that returns null can lead to problems for callers. In the case
        // of invalid input, I prefer throwing exceptions to returning null.
        // Lokesh: No-lesson learned from Chad's above comment, still returning null?
            // Lokesh: avoid accepting null in your function and avoid null checks.
            if (id <= 0 || agentName == null || agentName.isEmpty()) {
               throw new UserInputException("Please give proper agent name!");
            }


        // Lokesh: Use LocalDateTime or Joda DateTime. Gives you control for timezones etc.
        //LocalDateTime unixTime = LocalDateTime.now();

        // Streams are kind of an awkward way to get at one element. I would expose a method
        // to get the ticket by ID explicitly.
        Ticket ticket = ticketHashMap.get(Long.valueOf(id));

        if(ticket != null){
            ticket.setAgentName(agentName);
            ticket.setTags(tagHashSet);
            //ticket.setModified(unixTime);
            TicketSerializedClass.saveTicketsInFile(ticketHashMap, false, file);

            return ticket;
        }
        else
            throw new UserInputException("Ticket update failed!");
    }


    public void deleteById(){
        System.out.println("Enter ticket Id to delete ticket");
        int id = UserConsoleInput.acceptNumber();

        try {
            if(id <= 0 || id > ticketId-1){
                throw new UserInputException("Invalid ticket id! please enter valid ticket id");
            }

            if(deleteTicketById(id))
                System.out.println("Ticket delete successful!");
            else
                System.out.println("Ticket not able to delete!");
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    * Delete ticket by id*/
    public boolean deleteTicketById(int id) throws UserInputException{

        // Delete is also pretty inefficient for the data structure. Consider a different structure to hold the tickets.
        // To do this with streams, note you could use the opposite filter (not equal to this id) to construct a
        // new list. Or you could define Ticket.equals() to use the ticket ID; then you could call remove
        // on the arraylist directly (but that doesn't make it any more efficient).
                if(ticketHashMap.remove(Long.valueOf(id)) != null)
                    if(TicketSerializedClass.saveTicketsInFile(ticketHashMap, false, file))
                        return true;
                    else
                        return false;
                else
                    throw new UserInputException("Ticket with id is not available!");

    }


    /*
    * Search tickets assign to specific agent */
    public void searchTicketByAgent(){
        /*String agentName = UserConsoleInput.getAgentName();*/
        String agentName = UserConsoleInput.acceptString("Enter Agent name");

        getTickets(searchTicketsWithAgent(agentName));
    }


    public Map<Long, Ticket> searchTicketsWithAgent(String agentName){

        Map<Long,Ticket> tempTicketHashMap;
        // This is good. Returning an empty list (rather than null or throwing an
        // exception is a good strategy when a collection is the return type.
        // however, don't create the empty list unless you need to.
        if(agentName == null || agentName.isEmpty()){
            return new HashMap<>();
        }

        tempTicketHashMap = ticketHashMap.values().stream().filter(ticket -> ticket.getAgentName().toLowerCase().equals(agentName.toLowerCase()))
                                                .collect(Collectors.toMap(Ticket::getId, Function.identity()));
        return tempTicketHashMap;

    }


    /*
    * Search tickets by tag names*/
    public void searchTicketByTag(){
        // No reason to create a new HashSet when you assign to something else on the next line.
        Set<String> tagHashSet = UserConsoleInput.getTagNames();

        getTickets(searchTicketsWithTags(tagHashSet));

    }


    public Map<Long, Ticket> searchTicketsWithTags(Set<String> tagHashSet){

//        Map<Long, Ticket> tempTicketHashMap;
        // you never use tempTicketSet

        if(tagHashSet == null || tagHashSet.isEmpty()){
            return new HashMap<>();
        }


        // note that 'lst' here is a Ticket; give it a better name.
        // I would recommend implementing a method like
        // boolean hasTag(String tag)
        // directly on the Ticket class. This code is somewhat
        // inefficient in that it creates a new list of tags
        // and sees if the size is zero, just to know if the tag
        // is present.
        return ticketHashMap.values().stream().
                filter(ticket -> ticket.getTags()
                        .stream()
                        .anyMatch(tag -> tagHashSet.contains(tag)))
                .collect(Collectors.toMap(Ticket::getId, Function.identity()));
    }


    /*
    * NEW Search Ticket agent Count using lambda and stream
    * */
    public Map<String, List<Ticket>> calculateAgentTicketCount() {
        return new TreeMap<>(ticketHashMap.values().stream().collect(Collectors.groupingBy(Ticket::getAgentName)));
    }


    /*
    * NEW Show agent and ticket count */
    public void showAgentTicketCount(){
        Map<String, List<Ticket>> agentCountMap = calculateAgentTicketCount();
        agentCountMap.forEach((String agentName,List<Ticket> ticketList)-> System.out.println(agentName+"   :   "+ticketList.size()));
    }


    public Map<Long, Ticket> autoLoadTickets(int noOfTickets){

        Random rand = new Random();

        for(int i=0; i<noOfTickets; i++){
            //String agent = "Agent"+rand.nextInt(noOfTickets/10);

            String agent = "Agent"+rand.nextInt(50);
            String tag1 = "Tag"+rand.nextInt(10);
            String tag2 = "Tag"+rand.nextInt(10);
            Set<String> tagSet = new HashSet<>(Arrays.asList(tag1,tag2));

            String subject = "Subject"+i;

            Ticket ticket = null;
            try {
                ticket = TicketFactory.createTicketInstance(ticketId, subject, agent, tagSet);
            } catch (UserInputException e) {
                e.printStackTrace();
            }

            /*if(ticketHashMap.put(ticket.getId(), ticket) != null)
                TicketSerializedClass.saveTicketsInFile(ticketHashMap, append, file);*/

            if(ticketHashMap.put(ticket.getId(), ticket) != null)
                ticketId++;
        }

        boolean append = true;
        if(ticketHashMap.isEmpty())
            append = false;

        if (TicketSerializedClass.saveTicketsInFile(ticketHashMap, append, file)){
            TicketSerializedClass.updatePropertyFile(ticketIdFile, properties, ticketId);
        }

        return ticketHashMap;
    }

/*    protected void finalize() throws Throwable{
        System.out.println("Inside finalize block....");
        TicketSerializedClass.updatePropertyFile(ticketIdFile, properties, ticketId);
    }*/
}
