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
            System.out.println("Enter Subject: ");
            subject = MyReader.readInput();
        }while(subject.equals("0"));

        do{
            System.out.println("Enter Agent Name: ");
            agent = MyReader.readInput();
        }while (agent.equals("0"));

        do{
            System.out.println("Assign tag?(Y/N)");
            tags = MyReader.readInput();
        } while (!(tags.equals("Y") || tags.equals("N")));

        if(tags.equals("Y"))
            this.addTags(tg);
        Date d = new Date();
        Tickets t = new Tickets(++max_id,subject,agent,tg,d.getTime(),d.getTime());
        tickets.put(Integer.valueOf(t.getId()),t);
        System.out.print("Ticket has been created: ");
        System.out.println(t.toString());
    }

    public void createDummyTickets(){
        Tickets t = new Tickets(++max_id,"Subject"+max_id,"Agent" + max_id,new HashSet(),new Date().getTime(),new Date().getTime());
        tickets.put(Integer.valueOf(t.getId()),t);
    }

    public void updateTicket(){
        System.out.println("Enter Ticket ID to update: ");
        int id = MyReader.readChoice();
//        System.out.println("Update ID: " + id);
        if(tickets.keySet().contains(Integer.valueOf(id))){
            String newAgent;
            Tickets t = tickets.get(id);
            do{
                System.out.println("Update Agent Name?(Y/N)");
                newAgent = MyReader.readInput();
            } while (!(newAgent.equals("Y") || newAgent.equals("N")));
            if(newAgent.equals("Y")){
                do{
                    System.out.println("Enter Agent Name: ");
                    newAgent = MyReader.readInput();
                } while(newAgent.equals("0") || newAgent == null);
                t.setAgent(newAgent);
            }
            String updateTag;
            do{
                System.out.println("Add/Remove Tag?(A/R/N): ");
                updateTag = MyReader.readInput();
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
        System.out.println("Enter Ticket ID to delete: ");
        id = MyReader.readChoice();
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
        System.out.println("Enter ticket id to get detail: ");
        id = MyReader.readChoice();
        Tickets t = tickets.get(id);
        if(t == null)
            System.out.println("No ticket found with given ID.");
        else
            System.out.println("Ticket Detail: " + t.toString());
    }

    public void ticketsOfAgent(){
        String agent;
        System.out.println("Enter Agent Name: ");
        agent = MyReader.readInput();
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
        System.out.println("Enter A Tag: ");
        String tag = MyReader.readInput();
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
                System.out.println("Enter Tag: ");
                temp = MyReader.readInput();
                if(temp != "0" && temp != null)
                    tg.add(temp);
                System.out.println("More tag?(Y/N)");
                tags = MyReader.readInput();
            } while (tags.equals("Y"));
    }

    private void removeTags(HashSet<String> tg){
        String temp,tags;
        do{
            System.out.println("Enter tag to remove: ");
            tags = MyReader.readInput();
            if(tg.remove(tags))
                System.out.println("Tag removed.");
            else
                System.out.println("Entered Tag is not applied.");
            System.out.println("More tag to remove?(Y/N): ");
            temp = MyReader.readInput();
        } while (temp.equals("Y"));
    }
}
