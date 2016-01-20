package com.casestudy;

/**
 * Created by root on 12/1/16.
 */

import sun.security.krb5.internal.Ticket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Services {

//    final String []arry = {"1) Create Ticket","2) Update Ticket","3) Delete Ticket","4) Get Ticket","5) Get all Tickets","6) Find Tickets assigned to Agent", "7) Get all Agent with Tickets Counts","8) Search Tickets By Tag","9) Exit"};
    static private int max_id = 0;
    static HashMap<Integer, Tickets> tickets = new HashMap<Integer, Tickets>();


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
            this.addTags(tg);
        System.out.println(this.createTicket(subject,agent,tg).toString());
    }

    public Tickets createTicket(String subject, String agent, HashSet<String> tg){
        Date d = new Date();
        if(subject == null || agent == null)
            return null;
        Tickets t = new Tickets(++max_id,subject,agent,tg,d.getTime(),d.getTime());
        tickets.put(Integer.valueOf(t.getId()),t);
        return t;
    }

    public void createDummyTickets(){
        Tickets t = new Tickets(++max_id,"Subject"+max_id,"Agent" + max_id,new HashSet(),new Date().getTime(),new Date().getTime());
        tickets.put(Integer.valueOf(t.getId()),t);
    }

    public void updateTicket(){
        int id = MyReader.readChoice("Enter Ticket ID to update: ");
//        System.out.println("Update ID: " + id);
        if(tickets.keySet().contains(Integer.valueOf(id))){
            String newAgent;
            Tickets t = tickets.get(id);
            do{
                newAgent = MyReader.readInput("Update Agent Name?(Y/N)");
            } while (!(newAgent.equals("Y") || newAgent.equals("N")));
            if(newAgent.equals("Y")){
                do{
                    newAgent = MyReader.readInput("Enter Agent Name: ");
                } while(newAgent.equals("0") || newAgent == null);
                t.setAgent(newAgent);
            }
            String updateTag;
            do{
                updateTag = MyReader.readInput("Add/Remove Tag?(A/R/N): ");
            } while (!(updateTag.equals("A") || updateTag.equals("R") || updateTag.equals("N")));
            if(updateTag.equals("A")){
                this.addTags(tickets.get(id).getTag());
            }
            else if(updateTag.equals("R")){
                this.removeTags(tickets.get(id).getTag());
            }
            t.setUpdated(new Date().getTime());
            System.out.println("Ticket has been updated: " + t.toString());
        }
        else{
            System.out.println("Ticket with given ID not Found.");
        }
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
        Function<Tickets, Long> byUpdated = tm -> tm.getUpdated();
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
                t.put(agent,Integer.valueOf(t.get(agent).intValue() + 1));
            }
            else
                t.put(agent,Integer.valueOf(1));
        });
        if(t.size()>=1){
            t.forEach((k,v)-> System.out.print("{" + k + ":" + v.intValue() + "},"));
            System.out.println();
        }
        else
            System.out.println("No ticket in system or Tickets are not assigned to agent");
    }

    public void ticketsByTag(){
        String tag = MyReader.readInput("Enter A Tag: ");
        TreeSet<Tickets> hs = new TreeSet<Tickets>(Tickets.updateComparator);
        tickets.forEach((k,v)->{
            if(v.getTag().contains(tag))
                hs.add(v);
        });
        if(hs.size() > 0)
            System.out.println(hs);
        else
            System.out.println("No Ticket found with given Tag.");

/*        System.out.println(tickets.values().stream().filter((Tickets t)->t.getTag().contains(tag)));
        System.out.println(tickets);*/
    }

    private void addTags(HashSet<String> tg){
        String tags,temp;
            do{
                temp = MyReader.readInput("Enter Tag: ");
                if(temp != "0" && temp != null)
                    tg.add(temp);
                tags = MyReader.readInput("More tag?(Y/N)");
            } while (tags.equals("Y"));
    }

    private void removeTags(HashSet<String> tg){
        String temp,tags;
        do{
            tags = MyReader.readInput("Enter tag to remove: ");
            if(tg.remove(tags))
                System.out.println("Tag removed.");
            else
                System.out.println("Entered Tag is not applied.");
            temp = MyReader.readInput("More tag to remove?(Y/N): ");
        } while (temp.equals("Y"));
    }
}
