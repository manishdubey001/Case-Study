package com.example.ticketcrud.service;

import com.example.ticketcrud.factory.TicketFactory;
import com.example.ticketcrud.model.Ticket;
import com.example.ticketcrud.util.InputReader;
import com.example.ticketcrud.util.TicketUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by root on 31/12/15.
 */
public class TicketService {

    public HashMap<Integer,Ticket> tickets = new HashMap<>();

    /**
     * Create new ticket by taking user input
     */
    public void create()
    {
        try {
            BufferedReader reader = InputReader.getReader();

            System.out.println("Enter Ticket subject: ");
            String subject = reader.readLine();
            System.out.println("Enter Agent name: ");
            String agentName = reader.readLine();
            System.out.println("Enter comma separated tags of ticket: ");
            String tags = reader.readLine();
            HashSet<String> tagsSet = new HashSet<>(Arrays.asList(tags.toLowerCase().split(",")));
            if(!subject.isEmpty() && !agentName.isEmpty()) {
                Ticket ticket = TicketFactory.newInstance(subject, agentName, tagsSet);
                tickets.put(ticket.getId(),ticket );
                System.out.println("Ticket successfully created with id : "+ticket.getId());
            }
            else
                System.out.println("Ticket subject and Agent name must not be blank.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Display all ticket on console
     */
    public void displayAllTickets()
    {
        System.out.println("Ticket List ....");
        if(tickets.size() > 0)
        {
            tickets.values()
                    .stream()
                    .sorted((Ticket o1, Ticket o2) -> -o1.getModified().compareTo(o2.getModified()))
                    .forEach(ticket -> System.out.println(ticket.getId() + "\t" + ticket.getSubject() + "\t" + ticket.getAgentName() + "\t" + ticket.getTags() + "\t" + ticket.getCreated() + "\t" + ticket.getModified() + "\t"));
        }else
            System.out.println("No Ticket found...");
    }

    /**
     * Delete Ticket by id
     */
    public void delete(){
        try{
            BufferedReader reader = InputReader.getReader();
            System.out.print("Please enter ticket id you want to delete: ");
            int id = Integer.parseInt(reader.readLine());
            if(tickets.containsKey(id))
            {
                tickets.remove(id);
                System.out.println("Ticket with id "+id+" deleted successfully");
            }
            else{
                System.out.println("Invalid ticket Id . Please try with valid ticket id");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ticket Id should be Integer. Please try with numeric ticket id.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Display single ticket by id
     */
    public void displaySingleTicketDetails()
    {
        System.out.println("You entered option: 4");
        try{
            BufferedReader reader = InputReader.getReader();
            System.out.print("Please enter ticket id you want to ticket detail: ");
            int id = Integer.parseInt(reader.readLine());;
            if(tickets.containsKey(id))
            {
                System.out.println("Ticket detail with id "+id+" as:");
                System.out.println(tickets.get(id).getId()+"\t"+tickets.get(id).getSubject()+"\t"+tickets.get(id).getAgentName()+"\t"+tickets.get(id).getTags()+"\t"+tickets.get(id).getCreated()+"\t"+tickets.get(id).getModified()+"\t");
            }
            else{
                System.out.println("Invalid ticket Id . Please try with valid ticket id");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ticket Id should be Integer. Please try with numeric ticket id.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update ticket by id
     */
    public void update()
    {
        try{
            BufferedReader reader = InputReader.getReader();
            System.out.print("Please enter ticket id you want to update: ");
            int id  = Integer.parseInt(reader.readLine());
            if(isTicketExist(id)){
                Ticket ticket = tickets.get(id);
                System.out.println("Enter Agent name. Click enter to continue");
                String agentName = reader.readLine();
                if(!agentName.isEmpty())
                    ticket.setAgentName(agentName);
                System.out.println("Enter comma separated tags of ticket. Click enter to continue");
                String tags = reader.readLine();
                if (!tags.isEmpty())
                    ticket.setTags(new HashSet<>(Arrays.asList(tags.split(","))));
                if(!tags.isEmpty() || !agentName.isEmpty()) {
                    System.out.println("Ticket with id: " + id + " updated with following details : ");
                    System.out.println(tickets.get(id).getId() + "\t" + tickets.get(id).getSubject() + "\t" + tickets.get(id).getAgentName() + "\t" + tickets.get(id).getTags() + "\t" + tickets.get(id).getCreated() + "\t" + tickets.get(id).getModified() + "\t");
                }else
                    System.out.println("Ticket with id: "+id+" not updated because you not entered any value");
            }
            else{
                System.out.println("Invalid ticket id. Please try with valid ticket id");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ticket Id should be Integer. Please try with numeric ticket id.");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Check provided id exists or not
     * @param id
     * @return boolean
     */
    public boolean isTicketExist(int id)
    {

        return tickets.containsKey(id);
    }

    /**
     * Display Ticket assigned agent
     */
    public void displayAgentTickets()
    {
        try{
            BufferedReader reader = InputReader.getReader();
            System.out.println("Enter Agent name, you want to see all assigned Tickets");
            String agentName = reader.readLine();
            if (!agentName.isEmpty())
            {
                System.out.println(" Following Tickets assigned to agent " + agentName);
                tickets.values()
                        .stream()
                        .filter(ticket -> ticket.getAgentName().toLowerCase().equals(agentName.toLowerCase()))
                        .sorted((Ticket o1, Ticket o2) -> -o1.getModified().compareTo(o2.getModified()))
                        .forEach(ticket -> System.out.println(ticket.getId() + "\t" + ticket.getSubject() + "\t" + ticket.getAgentName() + "\t" + ticket.getTags() + "\t" + ticket.getCreated() + "\t" + ticket.getModified() + "\t"));
            }
            else {
                System.out.println("No agent name entered");
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Display ticket tags by tag
     */
    public void displayTicketsByTag()
    {
        try {
            BufferedReader reader =  InputReader.getReader();
            System.out.println("Enter tag name want to see all Tickets");
            String tag = reader.readLine();
            if (!tag.isEmpty()) {
                System.out.println(" Following Tickets are tagged by tag "+tag);
                System.out.println("---------------------------------------------");
//                boolean flag = false;
//                List<Ticket> ticketList = new ArrayList<>(tickets.values());
//                Collections.sort(ticketList,(Ticket o1, Ticket o2) -> {return -o1.getModified().compareTo(o2.getModified());});
//                Iterator<Ticket> iterator  = ticketList.iterator();
//                while (iterator.hasNext())
//                {
//                    Ticket ticket = iterator.next();
//                    if(ticket.getTags().contains(tag.toLowerCase())) {
//                        System.out.println(ticket.getId() + "\t" + ticket.getSubject() + "\t" + ticket.getAgentName() + "\t" + ticket.getTags() + "\t" + ticket.getCreated() + "\t" + ticket.getModified() + "\t");
//                        System.out.println("---------------------------------------------");
//                        flag = true;
//                    }
//                }
//                if(!flag)
//                {
//                    System.out.println("No tickets tagged by this tag");
//                }
                tickets.values()
                        .stream()
                        .filter(ticket -> ticket.getTags().contains(tag.toLowerCase()))
                        .sorted((Ticket o1, Ticket o2) -> -o1.getModified().compareTo(o2.getModified()))
                        .forEach(ticket -> System.out.println(ticket.getId() + "\t" + ticket.getSubject() + "\t" + ticket.getAgentName() + "\t" + ticket.getTags() + "\t" + ticket.getCreated() + "\t" + ticket.getModified() + "\t"));

            } else {
                System.out.println("No tag name entered by you");
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    /**
     * Display ticket ascending order of agent with assigned ticket count
     */
    public void displayTicketCountOfAgentInAsc()
    {
        System.out.println(" Ticket count grouped by agent (ascending order). ");
        Iterator<Integer> iterator  = tickets.keySet().iterator();
        System.out.println("---------------------------------------------");
        if(tickets.size() > 0) {
            TreeMap<String, Integer> agent = new TreeMap<>((String s1, String s2) -> s1.toLowerCase().compareTo(s2.toLowerCase()));
            while (iterator.hasNext()) {
                int id = iterator.next();
                String agentName = tickets.get(id).getAgentName();
                if (agent.containsKey(agentName)) {
                    agent.put(agentName, agent.get(agentName) + 1);
                } else {
                    agent.put(agentName, 1);
                }
            }
            Iterator<String> agentIterator = agent.keySet().iterator();
            while (agentIterator.hasNext()) {
                String agentName = agentIterator.next();
                System.out.println("AgentName \t\t\t Count");
                System.out.println(agentName + "\t\t" + agent.get(agentName));
                System.out.println("---------------------------------------------");
            }
        }
        else{
            System.out.println("No ticket founds");
        }
    }

}
