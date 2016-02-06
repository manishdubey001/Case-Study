package com.casestudy;

/**
 * Created by root on 12/1/16.
 * This is Core Service, providing functions to perform CRUD operations and some other relevant stuffs.
 */

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Service {

    private static int max_id = 0;
    static HashMap<Integer, Ticket> tickets = new HashMap<>();
    static FileHelper fh = new FileHelper("resources/","data.txt");

    public Ticket createTicket(String subject, String agent, HashSet<String> tg){
        if(subject == null || agent == null || subject.length() == 0 || agent.length() == 0)
        {
            throw new InvalidParameterException();
//            return null;
        }
        Ticket t = new Ticket(++max_id,subject,agent,tg, LocalDateTime.now(),LocalDateTime.now());
        tickets.put(t.getId(),t);
        fh.write(t);
        return t;
    }

    public void readAllTicketsFromFile(){
        tickets.putAll(fh.readAll());
        if(tickets.size() > 0)
        {
            max_id = Collections.max(tickets.keySet());
        }
    }

    public Ticket updateTicket(int id, String agent, String updateTag, Set<String> hs){
        Ticket t = tickets.get(id);
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
            fh.write(t);
            return t;
        }
        else
            throw new InvalidParameterException();

    }

    public Ticket deleteTicket(int id){
        Ticket t = tickets.remove(id);
        if(t != null){
            fh.clearAll();
            tickets.values().stream().forEach(ticket->fh.write(ticket));
            return t;
        }
        else
            throw new InvalidParameterException();
    }

    public List<Ticket> getAllTickets(){
        return tickets.values().stream().sorted(Comparator.comparing(Ticket::getUpdated).reversed()).collect(Collectors.toList());
    }

    public Ticket getTicketById(int id){
        if(tickets.keySet().contains(id))
            return tickets.get(id);
        else
            throw new InvalidParameterException();
    }

    public List<Ticket> ticketsOfAgent(String agent){
        return tickets.values().stream().filter((Ticket t)->t.getAgent().equals(agent)).sorted(Comparator.comparing(Ticket::getUpdated)).collect(Collectors.toList());
    }

    public Map<String, Integer> agentTicketCount(){
        TreeMap<String,Integer> agentTicketCount = new TreeMap<>();
        tickets.forEach((k,v)->{
            String agent = v.getAgent();
            if(agentTicketCount.containsKey(agent)){
                agentTicketCount.put(agent,agentTicketCount.get(agent) + 1);
            }
            else
                agentTicketCount.put(agent,1);
        });
        return agentTicketCount;
    }

    public List<Ticket> ticketsByTag(String tag){
        return tickets.values().stream().filter(t->t.getTags().contains(tag)).sorted(Comparator.comparing(Ticket::getUpdated)).collect(Collectors.toList());
    }

    HashSet<String> readTags(){
        String tags,temp;
        HashSet<String> tg = new HashSet<>();
            do{
                temp = InputReader.readInput("Enter Tag: ");
                if(!temp.equals("\u0002"))
                    tg.add(temp);
                tags = InputReader.readInput("More tag?(Y/N)");
            } while (tags.equals("Y"));
        return tg;
    }

    public int countReport(){
        return tickets.size();
    }

    public List<Ticket> oldestTicketsReport(){
        LocalDateTime oldestDate = tickets.values().stream().sorted(Comparator.comparing(Ticket::getCreated)).findFirst().get().getCreated();
        return tickets.values().stream().filter(ticket -> ticket.getCreated().equals(oldestDate)).collect(Collectors.toList());
    }

    public List<Ticket> olderThanDaysReport(int days){
        LocalDateTime l = LocalDateTime.now().minusDays(days);
        return tickets.values().stream().filter(ticket -> ticket.getCreated().compareTo(l) < 0).sorted(Comparator.comparing(Ticket::getCreated)).collect(Collectors.toList());
    }

    public int ticketsCountForTag(String tag){
        return tickets.values().stream().filter(t->t.getTags().contains(tag)).collect(Collectors.toList()).size();
    }
}
