package com.casestudy;

/**
 * Created by root on 12/1/16.
 */

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// Same comment as Tickets; just call this Service instead of Services
public class Services {

//    final String []arry = {"1) Create Ticket","2) Update Ticket","3) Delete Ticket","4) Get Ticket","5) Get all Tickets","6) Find Tickets assigned to Agent", "7) Get all Agent with Tickets Counts","8) Search Tickets By Tag","9) Exit"};

    // No reason to make these static since you have an actual instance of Services to work with
    static private int max_id = 0;

    static HashMap<Integer, Tickets> tickets = new HashMap<>();


    public void createTicket(){
        String subject,agent,tags;
        HashSet<String> tg = new HashSet<>();
        do{
            subject = MyReader.readInput("Enter Subject: ");
        }while(subject.equals("0"));

        do{
            agent = MyReader.readInput("Enter Agent Name: ");
        }while (agent.equals("0"));

        do{
            tags = MyReader.readInput("Assign tag?(Y/N)");
        } while (!(tags.equals("Y") || tags.equals("N")));

        if(tags.equals("Y"))
            tg = this.readTags();
        System.out.println(this.createTicket(subject,agent,tg).toString());
    }

    public Tickets createTicket(String subject, String agent, HashSet<String> tg){
        // Java's original Date classes don't work very well. Java8 now has LocalDate/LocalDateTime
        // which fix a lot of problems. Using these or another implementation like JodaTime is recommended.
        Date d = new Date();
        if(subject == null || agent == null || subject.length() == 0 || agent.length() == 0)
            return null;
        // I think Ganesh's intention was to have the user provide the ticket ID, but this wasn't spelled out in the case study.
        Tickets t = new Tickets(++max_id,subject,agent,tg,d.getTime(),d.getTime());
        tickets.put(t.getId(),t);
        return t;
    }

    public void createDummyTickets(){
        Tickets t = new Tickets(++max_id,"Subject"+max_id,"Agent" + max_id,new HashSet<>(),new Date().getTime(),new Date().getTime());
        tickets.put(t.getId(),t);
    }

    public void updateTicket(){
        int id = MyReader.readChoice("Enter Ticket ID to update: ");
//        System.out.println("Update ID: " + id);
        if(tickets.keySet().contains(id)){
            String newAgent, updateTag;
            HashSet<String> newTags = new HashSet<>();
            Tickets t ; // = tickets.get(id);
            do{
                newAgent = MyReader.readInput("Update Agent Name?(Y/N)");
            } while (!(newAgent.equals("Y") || newAgent.equals("N")));
            if(newAgent.equals("Y")){
                do{
                    newAgent = MyReader.readInput("Enter Agent Name: ");
                } while(newAgent.equals("0"));
//                t.setAgent(newAgent);
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

    public Tickets updateTicket(int id,String agent,String updateTag, HashSet<String> hs){
        Tickets t = tickets.get(id);
        // I strongly recommend against taking "null" as a valid input to your own methods.
        // The reality is that you force yourself to nest a bunch of null checks;
        // So, you might as well just make separate, simpler methods.
        // And rather than taking "updateTag" as an operator type with a string code, I would implement
        // Add/Remove tag separately.
        if(t != null) {
            if(!(agent == null || agent.length() == 0))
                t.setAgent(agent);
            if(updateTag.equals("A"))
                hs.forEach((obj)->{
                    HashSet ta = t.getTag();
                    ta.add(obj);
                });
            else if(updateTag.equals("R"))
                hs.forEach((obj)->{
                    HashSet ta = t.getTag();
                    ta.remove(obj);
                });
            t.setUpdated(new Date().getTime());
        }
        return t;
    }

    public void deleteTicket(){
        int id;
        id = MyReader.readChoice("Enter Ticket ID to delete: ");
        Tickets t = tickets.remove(id);
        if(t == null)
            System.out.println("No ticket found with Given ID.");
        else
            System.out.println("Ticket has been deleted: " + t.toString());
    }

    public void getTickets(){
        // good use of streams. Two suggestions:
        // 1. Inline the method reference to Tickets::getUpdated
        // 2. No need to create a collection if all you want to do is print it; just use forEachOrdered(System.out::println)
        Function<Tickets, Long> byUpdated = Tickets::getUpdated; //tm -> tm.getUpdated();
        List<Tickets> list= tickets.values().stream().sorted(Comparator.comparing(byUpdated).reversed()).collect(Collectors.toList());

        // Another Approach
        /*List<Tickets> list= tickets.values().stream().collect(Collectors.toList());
        Collections.sort(list, Tickets.updateComparator);*/

        System.out.println(list);
    }

    public void tickets(){
        int id;
        id = MyReader.readChoice("Enter ticket id to get detail: ");
        Tickets t = tickets.get(id);
        if(t == null)
            System.out.println("No ticket found with given ID.");
        else
            System.out.println("Ticket Detail: " + t.toString());
    }

    public void ticketsOfAgent(){
        String agent;
        agent = MyReader.readInput("Enter Agent Name: ");
        TreeSet<Tickets> l = new TreeSet<>(Tickets.updateComparator);
        // see my comment below in ticketsByTag(); can you see how to do this with streams?
        tickets.forEach((k,v)->{
            if(v.getAgent().equals(agent))
                l.add(v);
        });
        if(l.size()>0)
            System.out.println("Tickets for given Agent are: " + l.toString());
        else
            System.out.println("Agent has no assigned any Ticket.");
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
            System.out.println("No ticket in system or Tickets are not assigned to agent");
    }

    public void ticketsByTag(){
        String tag = MyReader.readInput("Enter A Tag: ");
        TreeSet<Tickets> hs = new TreeSet<>(Tickets.updateComparator);
        // your code works here. For practice, t's good to get used to java 8's streams.
        // These can be more convenient and sometimes more efficient.
        // The following line of code would output a sorted list of
        // tickets with this tag, with no need to create an intermediate container.
        // tickets.values().stream().filter(t->t.getTag().contains(tag)).sorted(Tickets.updateComparator).forEachOrdered(System.out::println);
        // or by using comparing() you can get rid of updateComparator.

        tickets.forEach((k, v) -> {
            if (v.getTag().contains(tag))
                hs.add(v);
        });
        if(hs.size() > 0)
            System.out.println(hs);
        else
            System.out.println("No Ticket found with given Tag.");

/*        System.out.println(tickets.values().stream().filter((Tickets t)->t.getTag().contains(tag)));
        System.out.println(tickets);*/
    }

    private HashSet<String> readTags(){
        String tags,temp;
        HashSet<String> tg = new HashSet<>();
            do{
                temp = MyReader.readInput("Enter Tag: ");
                if(!temp.equals("0"))
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
}
