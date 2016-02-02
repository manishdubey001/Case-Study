package com.casestudy;

/**
 * Created by root on 12/1/16.
 */

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

// Same comment as Ticket; just call this Service instead of Services
// Update: Refactor to change Service from Services
public class Service {

    // No reason to make these static since you have an actual instance of Service to work with
    // Update: Removed static from max_id since already have instance to access it within class
    private int max_id = 0;

    HashMap<Integer, Ticket> tickets = new HashMap<>();
    FileHelper fh = new FileHelper("resources/","data.txt");

public void createTicket(){
        String subject,agent,tags;
        HashSet<String> tg = new HashSet<>();
        do{
            subject = MyReader.readInput("Enter Subject: ");
        }while(subject.equals("\u0002"));

        do{
            agent = MyReader.readInput("Enter Agent Name: ");
        }while (agent.equals("\u0002"));

        do{
            tags = MyReader.readInput("Assign tag?(Y/N)");
        } while (!(tags.equals("Y") || tags.equals("N")));

        if(tags.equals("Y"))
            tg = this.readTags();
        System.out.println(this.createTicket(subject,agent,tg).toString());
    }

    public Ticket createTicket(String subject, String agent, HashSet<String> tg){
        // Java's original Date classes don't work very well. Java8 now has LocalDate/LocalDateTime
        // which fix a lot of problems. Using these or another implementation like JodaTime is recommended.
        // Update: Changed from Date to LocalDateTime
        // Date d = new Date();
        if(subject == null || agent == null || subject.length() == 0 || agent.length() == 0)
            return null;
        // I think Ganesh's intention was to have the user provide the ticket ID, but this wasn't spelled out in the case study.
        // Update: I don't think so because I was also involved with Ganesh to define case study and we left it how one wish to implement. Just specific mention was for not allowing Duplicate IDs for Tickets
        Ticket t = new Ticket(++max_id,subject,agent,tg, LocalDateTime.now(),LocalDateTime.now());
        tickets.put(t.getId(),t);
//        System.out.println(t.toString());
        fh.write(t);
        return t;
    }

    public void readAllTicketsFromFile(){
        tickets.putAll(fh.readAll());
        //virendra: for the below code it can be problematic to have max id from ticket list size. Let's say at
        // one time we have 10 tickets in the list and we have deleted 2 tickets from it . In this case once
        // application starts again and tries to read from file at that time max id will start from 8 and if
        // there is a ticket with id 8 present in file. it will get duplicated.
        // You should use some other approach for this. Like managing the number in file.
        // Case 2: Consider you have deleted last ticket (id:10) and now you try to add new ticket it will be
        // created with same id which was deleted lastly.


        // Update for Virendra: For first case: Seems you didn't tried running the code and checking result.
        // What the logic do is, on every fresh start, max_id will be initialized to the maximum id of ticket present in file, and all new tickets thereafter will be with next value to that.

        // Case 2: Why should not I re-use the Ticket ID of a (hard)deleted ticket. That deleted ticket is now no more in existence and I can re-use same ID to create new Ticket.

        if(tickets.size() > 0)
            max_id = tickets.get(tickets.size()).getId();
//        System.out.println(max_id);
    }

    public void createDummyTickets(){
        if(!fh.checkFile())
            for(int i = 1;i <= 100; i++){
                HashSet<String > hs = new HashSet<>();
                hs.add("tag"+i);
                this.createTicket("Subject" + i , "agent" + i, hs);
            }
    }

    public void updateTicket(){
        int id = MyReader.readChoice("Enter Ticket ID to update: ");
        if(tickets.keySet().contains(id)){
            String newAgent, updateTag;
            HashSet<String> newTags = new HashSet<>();
            Ticket t ;
            do{
                newAgent = MyReader.readInput("Update Agent Name?(Y/N)");
            } while (!(newAgent.equals("Y") || newAgent.equals("N")));
            if(newAgent.equals("Y")){
                do{
                    newAgent = MyReader.readInput("Enter Agent Name: ");
                } while(newAgent.equals("\u0002"));
            }
            else
                newAgent = "";
            do{
                updateTag = MyReader.readInput("Add/Remove Tag?(A/R/N): ");
            } while (!(updateTag.equals("A") || updateTag.equals("R") || updateTag.equals("N")));
            if(updateTag.equals("A") || updateTag.equals("R")){
                newTags = this.readTags();
            }
            t = this.updateTicket(id,newAgent,updateTag,newTags);

            System.out.println("Ticket has been updated: " + t.toString());
        }
        else{
            System.out.println("Ticket with given ID not Found.");
        }
    }

    public Ticket updateTicket(int id, String agent, String updateTag, Set<String> hs){
        Ticket t = tickets.get(id);
        // I strongly recommend against taking "null" as a valid input to your own methods.
        // The reality is that you force yourself to nest a bunch of null checks;
        // So, you might as well just make separate, simpler methods.
        // And rather than taking "updateTag" as an operator type with a string code, I would implement
        // Add/Remove tag separately.

        // Update: I do not see any "null" value passed here. What check above is for checking if ticket with passed id exists or not.
        // I have set the flow in case-study that way, so it needs to take input from user whether he wants to update tags or not. That's why the "updateTag" become an input here.
        // Even if I separate the Add/Remove function, I will need some way to identify whether to perform remove or add operation.
        // If "updateTag" is not having "A" or "R" that means an empty Set<String> will pe passed for tags (but not null)
        // Correct me if I have wrongly taken the comment.

        if(t != null) {
            if(!(agent == null || agent.length() == 0))
                t.setAgent(agent);
            if(updateTag.equals("A")){
                hs.addAll(t.getTags());

                t.setTags(hs);
            }
            else if(updateTag.equals("R")){
                Set s = new HashSet<>();
                s.addAll(t.getTags());
                t.getTags().forEach((tag)->{
                    if(hs.contains(tag)){
                        s.remove(tag);
                    }
                });
                t.setTags(s);
            }
        }
        fh.write(t);
        return t;
    }

    public void deleteTicket(){
        int id;
        id = MyReader.readChoice("Enter Ticket ID to delete: ");
        Ticket t = this.deleteTicket(id);
        if(t == null)
            System.out.println("No ticket found with Given ID.");
        else
            System.out.println("Ticket has been deleted: " + t.toString());
    }

    public Ticket deleteTicket(int id){
        return tickets.remove(id);
    }

    public void getTickets(){
        // good use of streams. Two suggestions:
        // 1. Inline the method reference to Ticket::getUpdated
        // 2. No need to create a collection if all you want to do is print it; just use forEachOrdered(System.out::println)

        //Update: Got a single line for 3 line of code. I will need to explore more on stream api.
//      tickets.values().stream().sorted(Comparator.comparing(Ticket::getUpdated).reversed()).forEachOrdered(System.out::print);
        this.getAllTickets().forEach(System.out::println);
        System.out.println();

        /*Function<Ticket, LocalDateTime> byUpdated = Ticket::getUpdated; //tm -> tm.getUpdated();
        List<Ticket> list= tickets.values().stream().sorted(Comparator.comparing(byUpdated).reversed()).collect(Collectors.toList());
        System.out.println(list);*/

        // Another Approach
        /*List<Ticket> list= tickets.values().stream().collect(Collectors.toList());
        Collections.sort(list, Ticket.updateComparator);*/


    }

    public List<Ticket> getAllTickets(){
        return tickets.values().stream().sorted(Comparator.comparing(Ticket::getUpdated).reversed()).collect(Collectors.toList());
    }

    public void getTicket(){
        int id;
        id = MyReader.readChoice("Enter ticket id to get detail: ");
        Ticket t = this.getTicketById(id);
        if(t == null)
            System.out.println("No ticket found with given ID.");
        else
            System.out.println("Ticket Detail: " + t.toString());

    }

    public Ticket getTicketById(int id){
        return tickets.get(id);
    }

    public void ticketsOfAgent(){
        String agent;
        agent = MyReader.readInput("Enter Agent Name: ");
        List<Ticket> l = this.ticketsOfAgent(agent);
        if(l.size()>0)
            System.out.println("Ticket for given Agent are: " + l.toString());
        else
            System.out.println("Agent has no assigned any Ticket.");
    }

    public List<Ticket> ticketsOfAgent(String agent){

        return tickets.values().stream().filter((Ticket t)->t.getAgent().equals(agent)).sorted(Comparator.comparing(Ticket::getUpdated)).collect(Collectors.toList());

        /*TreeSet<Ticket> l = new TreeSet<>(Ticket.updateComparator);
        // see my comment below in ticketsByTag(); can you see how to do this with streams?
        tickets.forEach((k,v)->{
            if(v.getAgent().equals(agent))
                l.add(v);
        });*/

    }

    public void allAgentTicketCount(){
        TreeMap<String,Integer> t = new TreeMap<>();
        tickets.forEach((k,v)->{
            String agent = v.getAgent();
            if(t.containsKey(agent)){
                t.put(agent,t.get(agent) + 1);
            }
            else
                t.put(agent,1);
        });
        if(t.size()>=1){
            t.forEach((k,v)-> System.out.print("{" + k + ":" + v + "},"));
            System.out.println();
        }
        else
            System.out.println("No ticket in system or Ticket are not assigned to agent");
    }

    public void ticketsByTag(){
        String tag = MyReader.readInput("Enter A Tag: ");
        TreeSet<Ticket> hs = new TreeSet<>(Ticket.updateComparator);
        // your code works here. For practice, it's good to get used to java 8's streams.
        // These can be more convenient and sometimes more efficient.
        // The following line of code would output a sorted list of
        // tickets with this tag, with no need to create an intermediate container.
        // tickets.values().stream().filter(t->t.getTags().contains(tag)).sorted(Ticket.updateComparator).forEachOrdered(System.out::println);
        // or by using comparing() you can get rid of updateComparator.

        // Update: Updated for improvement, practice on stream api require.
        tickets.values().stream().filter(t->t.getTags().contains(tag)).sorted(Comparator.comparing(Ticket::getUpdated)).forEachOrdered(System.out::print);
        System.out.println();

        /*tickets.forEach((k, v) -> {
            if (v.getTags().contains(tag))
                hs.add(v);
        });
        if(hs.size() > 0)
            System.out.println(hs);
        else
            System.out.println("No Ticket found with given Tag.");*/

/*        System.out.println(tickets.values().stream().filter((Ticket t)->t.getTags().contains(tag)));
        System.out.println(tickets);*/
    }

    private HashSet<String> readTags(){
        String tags,temp;
        HashSet<String> tg = new HashSet<>();
            do{
                temp = MyReader.readInput("Enter Tag: ");
                if(!temp.equals("\u0002"))
                    tg.add(temp);
                tags = MyReader.readInput("More tag?(Y/N)");
            } while (tags.equals("Y"));
        return tg;
    }

    /*private HashSet<String> removeTags(HashSet<String> tg){
        String temp,tags;
        do{
            tags = MyReader.readInput("Enter tag to remove: ");
            if(tg.remove(tags))
                System.out.println("Tag removed.");
            else
                System.out.println("Entered Tag is not applied.");
            temp = MyReader.readInput("More tag to remove?(Y/N): ");
        } while (temp.equals("Y"));
        return tg;
    }*/

    public void ticketCountReport(){
        System.out.println("Total Tickets in System: " + this.countReport());
    }

    public int countReport(){
        return tickets.size();
    }

    public void oldestTicket(){
        System.out.println("Oldest tickets in System: ");
        System.out.println(this.oldestTicketsReport());
    }

    public List<Ticket> oldestTicketsReport(){
        LocalDateTime oldestDate = tickets.values().stream().sorted(Comparator.comparing(Ticket::getCreated)).findFirst().get().getCreated();
        //Virendra: What's the difference between above and below line ?
        //This should return one ticket and get would return the single ticket if you use findFirst over filter in
        // lambda expression.

        // Update: I can have multiple tickets created at Same time.
        // First line give me LocalDateTime object for one of the oldest tickets and second line gets me List of Tickets which are created on that oldest LocalDateTime.
        return tickets.values().stream().filter(ticket -> ticket.getCreated().equals(oldestDate)).collect(Collectors.toList());
    }

    public void olderThanDaysTickets(){
        int days = MyReader.readChoice("Enter the number of days");
        System.out.println("Tickets older than " + days + " days are: ");
        System.out.println(this.olderThanDaysReport(days));
    }

    public List<Ticket> olderThanDaysReport(int days){
        LocalDateTime l = LocalDateTime.now().minusDays(days);
        return tickets.values().stream().filter(ticket -> ticket.getCreated().compareTo(l) < 0).sorted(Comparator.comparing(Ticket::getCreated)).collect(Collectors.toList());
    }

    public void ticketsReportForTag(){
        String tag = MyReader.readInput("Enter A Tag: ");
        //Virendra: I think this is misinterpreted. You have to print list of tags with no. of tickets in which they are
        // used. Ex. Tag 1 | 5 , Tag 2 | 3 etc.
        // Please make the necessary changes

        // Update: What this Line read from Chad's mail: "# of tickets with a tag" => I think I did not misread it.
        // Nevertheless, I am displaying # of tickets for just one tag for which user queried, it's not of any much difference to get it for all tags the same thing.
        System.out.println("Number of Tickets with tag \'" + tag + "\': " + this.ticketsCountForTag(tag));
    }
    public int ticketsCountForTag(String tag){
        int size = tickets.values().stream().filter(t->t.getTags().contains(tag)).collect(Collectors.toList()).size();
        return size;
    }
}
