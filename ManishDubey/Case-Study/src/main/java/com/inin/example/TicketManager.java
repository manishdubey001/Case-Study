package com.inin.example;

import com.inin.example.model.Ticket;
import com.inin.example.service.TicketService;
import com.inin.example.util.InputReader;

import java.util.*;

/**
 * Created by root on 18/1/16.
 */
public class TicketManager {
    private TicketService ticketService = null;

    public TicketManager()
    {
        ticketService = new TicketService();
    }
    /**
     * Create new ticket by taking user input
     */
    public void create()
    {
        System.out.println("Enter Ticket subject: ");
        String subject = InputReader.readString();
        System.out.println("Enter Agent name: ");
        String agentName = InputReader.readString();
        System.out.println("Enter comma separated tags of ticket: ");
        String tags = InputReader.readString();
        HashSet<String> tagsSet = new HashSet<>(Arrays.asList(tags.toLowerCase().split(",")));

        if(subject != null && !subject.isEmpty() && agentName != null && !agentName.isEmpty()) {
            Ticket ticket = ticketService.create(subject, agentName, tagsSet);
            if (ticket != null)
                System.out.println("New Ticket is created with id " + ticket.getId());
            else
                System.out.println("Failed to create ticket");
        }
        else
            System.out.println("Ticket subject and Agent name must not be blank.");
    }


    /**
     * Update ticket by id
     */
    public void update()
    {
        System.out.print("Please enter ticket id you want to update: ");
        int id  = InputReader.readInt();
        if(ticketService.isTicketExist(id)){
            System.out.println("Enter Agent name. Click enter to continue");
            String agentName = InputReader.readString();
            System.out.println("Enter comma separated tags of ticket. Click enter to continue or enter 'clear' for clear tags");
            String tags = InputReader.readString();
            HashSet<String>  tagSet = null;
            if(!tags.isEmpty()) {
                if(tags.equals("clear"))
                    tagSet = new HashSet<>();
                else
                    tagSet = new HashSet<>(Arrays.asList(tags.split(",")));
            }
            if(!agentName.isEmpty() || !tags.isEmpty()) {
                Ticket ticket = ticketService.update(id,agentName,tagSet);
                System.out.println("Ticket with id: " + id + " updated with following details : ");
                System.out.println(ticket.getId() + "\t" + ticket.getSubject() + "\t" + ticket.getAgentName() + "\t" + ticket.getTags() + "\t" + ticket.getCreated() + "\t" + ticket.getModified() + "\t");
            }else
                System.out.println("Ticket with id: "+id+" not updated because you not entered any value");
        }
        else{
            System.out.println("Invalid ticket id. Please try with valid ticket id");
        }
    }

    /**
     * Delete Ticket by id
     */
    public void delete(){
        System.out.print("Please enter ticket id you want to delete: ");
        int id = InputReader.readInt();
        if(ticketService.delete(id))
            System.out.println("Ticket with id "+id+" deleted successfully");
        else
            System.out.println("Invalid ticket Id . Please try with valid ticket id");
    }

    /**
     * Display single ticket by id
     */
    public void displaySingleTicketDetails()
    {
        System.out.print("Please enter ticket id you want to ticket detail: ");
        int id = InputReader.readInt();
        Ticket ticket = ticketService.getTicket(id);
        if(ticket != null)
        {
            System.out.println("Ticket detail with id "+id+" as:");
            System.out.println(ticket.getId()+"\t"+ticket.getSubject()+"\t"+ticket.getAgentName()+"\t"+ticket.getTags()+"\t"+ticket.getCreated()+"\t"+ticket.getModified()+"\t");
        }
        else{
            System.out.println("Invalid ticket Id . Please try with valid ticket id");
        }
    }

    /**
     * Display all ticket on console
     */
    public void displayAllTickets()
    {
        HashMap<Integer,Ticket> tickets = ticketService.getTickets();
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
     * Display Ticket assigned agent
     */
    public void displayAgentTickets()
    {
        System.out.println("Enter Agent name, you want to see all assigned Tickets");
        String agentName = InputReader.readString();
        if (!agentName.isEmpty())
        {
            List<Ticket> ticketList = ticketService.getTicketsByAgent(agentName);
            if(ticketList.size()>0) {
                System.out.println(" Following are the Tickets assigned to agent: " + agentName);
                System.out.println("-----------------------------------------------------------------");
                ticketList.forEach(ticket -> System.out.println(ticket.getId() + "\t" + ticket.getSubject() + "\t" + ticket.getAgentName() + "\t" + ticket.getTags() + "\t" + ticket.getCreated() + "\t" + ticket.getModified() + "\t"));
            }else
                System.out.println("No record found ...");
        }
        else {
            System.out.println("No agent name entered...");
        }
    }

    /**
     * Display ticket tags by tag
     */
    public void displayTicketsByTag()
    {
        System.out.println("Enter tag name want to see all Tickets");
        String tag = InputReader.readString();
        if (!tag.isEmpty()) {
            List<Ticket> ticketList = ticketService.getTicketsByTag(tag);
            if(ticketList.size() > 0) {
                System.out.println(" Following are the Tickets are tagged by tag " + tag);
                System.out.println("---------------------------------------------");
                ticketService.getTicketsByTag(tag)
                        .forEach(ticket -> System.out.println(ticket.getId() + "\t" + ticket.getSubject() + "\t" + ticket.getAgentName() + "\t" + ticket.getTags() + "\t" + ticket.getCreated() + "\t" + ticket.getModified() + "\t"));
            }else
                System.out.println("No  Record found ........");

        } else {
            System.out.println("No tag name entered by you");
        }
    }

    /**
     * Display ticket ascending order of agent with assigned ticket count
     */
    public void displayTicketCountOfAgentInAsc()
    {
        System.out.println(" Ticket count grouped by agent (ascending order). ");
        System.out.println("---------------------------------------------");
        Map<String,List<Ticket>> ticketsGroupByAgent = ticketService.getTicketsGroupByAgent();
        if(ticketsGroupByAgent.size() > 0)
            ticketsGroupByAgent.forEach((String agentName,List<Ticket> ticketsList)->System.out.println(agentName+"\t\t"+ticketsList.size()));
        else
            System.out.println("No ticket founds");
    }

    public void loadDummyTickets(){
        System.out.println("Enter the number of dummy ticket should be load");
        int noOfTickets = InputReader.readInt();
        if(noOfTickets > 0)
            ticketService.loadDummyTickets(noOfTickets);
        else
            System.out.println("Please enter valid integer value");
    }
}
