package com.ticket.util;

import com.ticket.model.Ticket;
import com.ticket.service.TicketService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by root on 14/1/16.
 */
public class ConsoleScan {
    public static final String ACT_CHOOSE = "\n\nEnter an appropriate command:";
    public static final String ACT_CREATE_TICKET = "\n---------------------------------------\n 1 - Create Ticket initialized\n---------------------------------------";
    public static final String ACT_NOT_FOUND = "Ticket/Tickets not found in the system";
    public static final String ACT_THANKYOU = "THANK YOU..!!:";
    public static final String ACT_SUCCESS = "Success.!!";
    public static final String ACT_INVALID = "Invalid Input: ";
    public static final String ACT_ERROR = "IO Error: ";
    public static final String ACT_TSUBJECT = "Enter a Subject: ";
    public static final String ACT_AGENTNAME = "Enter an Agent Name: ";
    public static final String ACT_TTAGS = "Enter a TagName (use ',' for multiple TagsNames)";
    public static final String ACT_TTAGS_SINGLE = "Enter a Tag name";
    public static final String ACT_TIDUPDATE = "Please enter a Ticket Id to update: ";
    public static final String ACT_CHOOSE_TAG_AGENT = "\n---------------------------------------\n a - Change Agent name\n b - Change Tag name\n----------------------------------------";
    public static final String ACT_TABLE_HEADER = "  ID  |  AGENT  |  SUBJECT  |  TAGS  |  CREATED  |  MODIFIED  ";
    public static final String ACT_TID = "Enter a Ticket Id: ";
    public static final String ACT_ARE_YOU_SURE = "Are you sure: ";
    public static final String ACT_YES_OR_NO = "1 - Yes\n0 - No";
    public static final String ACT_REMOVE_SUCCESS = "Removed Successfully ";
    public static final String ACT_GET_TICKETS_OLD  = "Enter the number to fetch ticket older than that number of Days..:";

    public TicketService ticketService = null;
    private static Scanner scanner;

    // get all Sout commands initially
    public void getAllCommands(){
        System.out.println("Please choose an appropriate input.");
        System.out.println("1 - Create Ticket.");
        System.out.println("2 - Update Ticket by Id.");
        System.out.println("3 - Delete/Remove Ticket by Id.");
        System.out.println("4 - Select single Ticket by Id.");
        System.out.println("5 - Select all Tickets.");
        System.out.println("6 - Select Tickets assigned to specific Agent.");
        System.out.println("7 - Ticket count grouped by Agent name (order by Agent name).");
        System.out.println("8 - Select all Tickets by specific Tags.");
        System.out.println("9 - Quit.");
    }

    public ConsoleScan(){
        ticketService = new TicketService();
        scanner = new Scanner(System.in);
    }

    // scan for create ticket
    public void scanCreate(){
        System.out.println(ACT_CREATE_TICKET);
        System.out.println(ACT_TSUBJECT);
        String sub = getScanner().nextLine();

        System.out.println(ACT_AGENTNAME);
        String agent = getScanner().nextLine();

        System.out.println(ACT_TTAGS);
        String tags = getScanner().nextLine();

        if (sub !=null && agent !=null && tags !=null){
            boolean create = ticketService.createTicket(sub, agent, tags);
            if (create)
                System.out.println(ACT_SUCCESS);

        }else{
            System.out.println(ACT_NOT_FOUND);
        }
    }

    // scan for update
    public void scanUpdate(){
        try {
            System.out.println(ACT_TIDUPDATE);
            int tid = getScanner().nextInt();
            String type = null;
            String value = null;
            if (ticketService.checkIfExists(tid)){
                System.out.println(ACT_CHOOSE_TAG_AGENT);
                String sel = getScanner().next();
                if(sel.equals("a")){
                    getScanner().nextLine();
                    System.out.println(ConsoleScan.ACT_AGENTNAME);
                    String selA = getScanner().nextLine();
                    type = "agent";
                    value = selA;
                }else if (sel.equals("b")){
                    getScanner().nextLine();
                    System.out.println(ConsoleScan.ACT_TTAGS);
                    String selB = getScanner().nextLine();
                    type = "tags";
                    value = selB;
                }
                boolean update = ticketService.updateTicket(tid, type, value);
                if (update){
                    ticketService.display(ticketService.getAllTickets());
                }else{
                    System.out.println(ACT_NOT_FOUND);
                }
            }else
                System.out.println(ACT_NOT_FOUND);
         }catch(InputMismatchException Im){
            System.out.println(ConsoleScan.ACT_INVALID+Im);
         }
    }

    // scan for remove ticket
    public void scanRemoveTicket(){
        try {
            System.out.println(ConsoleScan.ACT_TID);
            int selT = getScanner().nextInt();
            if (ticketService.checkIfExists(selT)){
                System.out.println(ConsoleScan.ACT_ARE_YOU_SURE+selT);
                System.out.println(ConsoleScan.ACT_YES_OR_NO);
                int selA = getScanner().nextInt();

                if (selA == 1){

                    if(ticketService.removeTicketById(selT)){

                    }else {

                        System.out.println(ConsoleScan.ACT_REMOVE_SUCCESS);
                    }
                }
            }else
                System.out.println(ACT_NOT_FOUND);
        }catch (InputMismatchException Im){
            Im.printStackTrace();
        }
    }

    // scan for Ticket with Id
    public void scanTicketId(){
        try{
            System.out.println(ConsoleScan.ACT_TID);
            int selT = getScanner().nextInt();
            Ticket obj = ticketService.getTicketById(selT);
            if (obj != null){
                System.out.println(ConsoleScan.ACT_TABLE_HEADER);
                System.out.println(obj);
            }else{
                System.out.println(ACT_NOT_FOUND);
            }
        }catch (InputMismatchException Im){
            Im.printStackTrace();
        }
    }

    // scan for agentname
    public void scanAgent() {
        System.out.println(ConsoleScan.ACT_AGENTNAME);
        String selA = getScanner().nextLine();
        if (selA != null && !selA.isEmpty()) {
            List l = ticketService.getTicketsByAgentName(selA);

            if (!l.isEmpty())
                ticketService.display(l);
            else
                System.out.println(ACT_NOT_FOUND);
        }
    }

    // scan for Tags
    public void scanTags(){
        System.out.println(ConsoleScan.ACT_TTAGS_SINGLE);
        String selT = getScanner().nextLine();
        List list = ticketService.getAllTicketsByTag(selT);
        if (!list.isEmpty()){
            ticketService.display(list);
        }
    }

    // scan for Ticket count against Agent
    public void scanAgentsTicketsCount(){

       Map<String, List<Ticket>> tickets = ticketService.getTicketsGroupByAgent();
        if (!tickets.isEmpty()){
            ticketService.printTicketMap(tickets);
        }
    }

    public void scanAll(){
        ticketService.display(ticketService.getAllTickets());
    }

    public Scanner getScanner(){
        return scanner;
    }

    // scan for Old tickets from certain numnber of days.
    public void scanOldTickets(List<Ticket> list) {
        try {

            System.out.println(ConsoleScan.ACT_GET_TICKETS_OLD);
            int sel = getScanner().nextInt();
            List<Ticket> ticketList = ticketService.getDateDiff(sel, list);
            if (!ticketList.isEmpty()){
                ticketService.display(ticketList);
            }
        }catch (InputMismatchException Im){
            Im.printStackTrace();
        }
    }

}


