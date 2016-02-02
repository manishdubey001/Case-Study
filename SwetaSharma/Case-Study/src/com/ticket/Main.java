package com.ticket;

import java.util.*;


/**
 * Created by root on 18/1/16.
 */
public class Main {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in); //Declaring scanner variable to take input from user.
        do{
            showMenuDetails();
            int choice = read.nextInt();
            switch (choice){
                case 1:
                    createTicketProcess();
                    break;
                case 2:
                    updateTicketProcess();
                    break;
                case 3:
                    deleteTicketProcess();
                    break;
                case 4:
                    detailTicketProcess();
                    break;
                case 5:
                    defaultTicketListProcess();
                    break;
                case 6:
                    listByAgentTickets();
                    break;
                case 7:
                    listByTagTickets();
                    break;
                case 8:
                    agentTicketCount();
                    break;
            }
            System.out.println("Do you want to continue? press 'y' for yes or 'n' for no");
        }while (read.next().equals("y"));
    }

    public static void createTicketProcess(){
        Scanner read = new Scanner(System.in); //Declaring scanner variable to take input from user.
        TicketService ticketService  =  new TicketService();

        System.out.println("Enter the number of tickets you want to create");
        int numberOfTickets = read.nextInt();
        for (int j = 0; j < numberOfTickets; j++){
            System.out.println("please enter ticket id");
            int ticketId = read.nextInt(); //Taking input from user

            System.out.println("please enter subject");
            String subject = read.next(); //Taking input from user

            System.out.println("please enter agent name");
            String agentName = read.next(); //Taking input from user

            System.out.println("Please enter the number of tags you want to enter");

            int numberOfTags = read.nextInt(); //Taking input from user

            Set<String> setOfTags = new HashSet<>();
            for (int i=0; i < numberOfTags; i++){
                System.out.println("please enter tag");
                setOfTags.add(read.next());
            }

            Ticket ticketModel = ticketService.createTicket(ticketId, subject, agentName, setOfTags);

            if(ticketModel != null){
                System.out.println("Ticket created successfully !!");
            }else {
                System.out.println("This Ticket ID already exist!! Please use different ticket id");
            }
        }
    }

    public static void updateTicketProcess(){
        Scanner read = new Scanner(System.in); //Declaring scanner variable to take input from user.
        TicketService ticketService  =  new TicketService();

        System.out.println("Please enter ticket id to update");
        int ticketId = read.nextInt();

        System.out.println("Do want to change agent name? press 'y' for yes or 'n' for no");

        String agentName = null;
        if(read.next().equals("y")){
            System.out.println("Please enter agent name");
            agentName = read.next();
        }

        System.out.println("Do want to change tags of ticket? press 'y' for yes or 'n' for no");
        Set<String> setOfTags = new HashSet<>();

        if(read.next().equals("y")){
            System.out.println("Please enter the number of tags you want to enter");

            int numberOfTags = read.nextInt(); //Taking input from user
            for (int i=0; i< numberOfTags; i++){
                System.out.println("please enter tag");
                setOfTags.add(read.next());
            }
        }

        Ticket ticketModel = ticketService.updateTicket(ticketId, agentName, setOfTags);
        if(ticketModel != null){
            System.out.println(ticketModel);
        }else{
            System.out.println("Ticket does not exist! Please try again");
        }
    }

    public static void deleteTicketProcess(){
        Scanner read = new Scanner(System.in); //Declaring scanner variable to take input from user.
        TicketService ticketService  =  new TicketService();

        System.out.println("Please enter ticket id to delete");
        int ticketId = read.nextInt();
        if(ticketService.deleteTicket(ticketId)){
            System.out.println("Deleted successfully");
        }else {
            System.out.println("Ticket does not exist! Please try again");
        }
    }

    public static void detailTicketProcess(){
        Scanner read = new Scanner(System.in); //Declaring scanner variable to take input from user.
        TicketService ticketService  =  new TicketService();

        System.out.println("Please enter ticket id to get detail");
        int ticketId = read.nextInt();
        Ticket ticketModel = ticketService.detail(ticketId);
        if(ticketModel != null) {
            System.out.println(ticketModel);
        }else {
            System.out.println("Ticket does not exist! Please try again");
        }
    }

    public static void defaultTicketListProcess(){
        TicketService ticketService  =  new TicketService();
        List<Ticket> ticketList = ticketService.defaultListTicket();
        if(ticketList.isEmpty()){
            System.out.println("List is empty");
        }else{
           Util.printTicketListValues(ticketList);
        }
    }

    public static void listByAgentTickets(){
        Scanner read = new Scanner(System.in); //Declaring scanner variable to take input from user.
        TicketService ticketService  =  new TicketService();

        System.out.println("Please enter agent name to search");
        String agentName = read.next();
        List<Ticket> ticketList  = ticketService.agentListTicket(agentName);
        if(ticketList.isEmpty()){
            System.out.println("No ticket available for this agent");
        }else{
            Util.printTicketListValues(ticketList);
        }
    }

    public static void listByTagTickets(){
        Scanner read = new Scanner(System.in); //Declaring scanner variable to take input from user.
        TicketService ticketService  =  new TicketService();

        System.out.println("Please enter tag to search");
        String tag = read.next();
        List<Ticket> ticketList =  ticketService.tagListTicket(tag);
        if(ticketList.isEmpty()){
            System.out.println("NO ticket available for this tag");
        }else{
            Util.printTicketListValues(ticketList);
        }
    }

    public static void agentTicketCount(){
        TicketService ticketService  =  new TicketService();

        Map<String, Integer> agentTicketCount  = ticketService.ticketCountByAgent();
        if(agentTicketCount == null){
            System.out.println("Ticket list is empty");
        }else {
            System.out.println("Below is the list that contains ticket count by agent");
            //UPDATE
            agentTicketCount.forEach((agent,count) -> System.out.println("Agent : " + agent + " Count : " + count));
        }
    }

    public static void showMenuDetails(){
        System.out.println("1. Create Ticket");
        System.out.println("2. Update Ticket");
        System.out.println("3. Delete Ticket by id");
        System.out.println("4. Select single Ticket by Id");
        System.out.println("5. Get Ticket List");
        System.out.println("6. Select ticket assigned to specific agent");
        System.out.println("7. Select all tickets by specific tags");
        System.out.println("8. Ticket count group by agent name");
        System.out.println("Please enter your choice");
    }
}
