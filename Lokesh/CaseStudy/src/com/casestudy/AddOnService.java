package com.casestudy;

import java.util.HashSet;
import java.util.List;

/**
 * Created by lokesh on 5/2/16.
 * This is AddOnService extending Core Service and takes inputs form user and displays output to user.
 */
public class AddOnService extends Service{

    public AddOnService(String path, String fileName){
        super(path,fileName);
    }
    public void createTicket(){
        String subject,agent,tags;
        HashSet<String> tg = new HashSet<>();
        do{
            subject = InputReader.readInput("Enter Subject: ");
        }while(subject.equals("\u0002"));

        do{
            agent = InputReader.readInput("Enter Agent Name: ");
        }while (agent.equals("\u0002"));

        do{
            tags = InputReader.readInput("Assign tag?(Y/N)");
        } while (!(tags.equals("Y") || tags.equals("N")));

        if(tags.equals("Y"))
            tg = this.readTags();
        try {
            System.out.println(this.createTicket(subject, agent, tg).toString());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
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
        int id = InputReader.readChoice("Enter Ticket ID to update: ");
        if(tickets.keySet().contains(id)){
            String newAgent, updateTag;
            HashSet<String> newTags = new HashSet<>();
            Ticket t ;
            do{
                newAgent = InputReader.readInput("Update Agent Name?(Y/N)");
            } while (!(newAgent.equals("Y") || newAgent.equals("N")));
            if(newAgent.equals("Y")){
                do{
                    newAgent = InputReader.readInput("Enter Agent Name: ");
                } while(newAgent.equals("\u0002"));
            }
            else
                newAgent = "";
            do{
                updateTag = InputReader.readInput("Add/Remove Tag?(A/R/N): ");
            } while (!(updateTag.equals("A") || updateTag.equals("R") || updateTag.equals("N")));
            if(updateTag.equals("A") || updateTag.equals("R")){
                newTags = this.readTags();
            }
            try {
                t = this.updateTicket(id, newAgent, updateTag, newTags);
                System.out.println("Ticket has been updated: " + t.toString());
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Ticket with given ID not Found.");
        }
    }

    public void deleteTicket(){
        int id;
        id = InputReader.readChoice("Enter Ticket ID to delete: ");
        try {
            Ticket t = this.deleteTicket(id);
            if (t == null)
                System.out.println("No ticket found with Given ID.");
            else
                System.out.println("Ticket has been deleted: " + t.toString());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void getTickets(){
        this.getAllTickets().forEach(System.out::println);
        System.out.println();
    }

    public void getTicket(){
        int id;
        id = InputReader.readChoice("Enter ticket id to get detail: ");
        try{
            Ticket t = this.getTicketById(id);
            System.out.println("Ticket Detail: " + t.toString());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void oldestTicket(){
        System.out.println("Oldest tickets in System: ");
        System.out.println(this.oldestTicketsReport());
    }

    public void ticketCountReport(){
        System.out.println("Total Tickets in System: " + this.countReport());
    }

    public void olderThanDaysTickets(){
        int days = InputReader.readChoice("Enter the number of days");
        System.out.println("Tickets older than " + days + " days are: ");
        System.out.println(this.olderThanDaysReport(days));
    }

    public void ticketsReportForTag(){
        String tag = InputReader.readInput("Enter A Tag: ");
        System.out.println("Number of Tickets with tag \'" + tag + "\': " + this.ticketsCountForTag(tag));
    }

    public void ticketsOfAgent(){
        String agent;
        agent = InputReader.readInput("Enter Agent Name: ");
        List<Ticket> l = this.ticketsOfAgent(agent);
        if(l.size()>0)
            System.out.println("Ticket for given Agent are: " + l.toString());
        else
            System.out.println("Agent has no assigned any Ticket.");
    }

    public void ticketsByTag(){
        String tag = InputReader.readInput("Enter A Tag: ");
        this.ticketsByTag(tag).forEach(System.out::print);
        System.out.println();
    }

    public void allAgentTicketCount(){
        agentTicketCount().forEach((k,v)-> System.out.print("{" + k + ":" + v + "},"));
    }

}