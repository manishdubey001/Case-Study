package com.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


/**
 * Created by root on 18/1/16.
 */
public class Start {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in); //Declaring scanner variable to take input from user.
        TicketBase ticketBase  =  new TicketBase();
        int choice = 0;
        int ticketId = 0;
        TicketModel ticketModel = null;
        ArrayList<TicketModel> list = null;

        do{
            System.out.println("1. Create Ticket");
            System.out.println("2. Update Ticket");
            System.out.println("3. Delete Ticket by id");
            System.out.println("4. Select single Ticket by Id");
            System.out.println("5. Get Ticket List");
            System.out.println("6. Select ticket assigned to specific agent");
            System.out.println("7. Select all tickets by specific tags");
            System.out.println("8. Ticket count group by agent name");
            System.out.println("Please enter your choice");

            choice = read.nextInt();

            //Ganesh D: segregate logic for each case by dividing them into functions
            switch (choice){
                case 1:
                    System.out.println("Enter the number of tickets you want to create");
                    int numberOfTickets = read.nextInt();
                    for (int j = 0; j < numberOfTickets; j++){
                        boolean test = true;
                        System.out.println("please enter ticket id");
                        ticketId = read.nextInt(); //Taking input from user
                            for (int i = 0; i < ticketBase.ticketList.size(); i++) {
                                if (ticketBase.ticketList.get(i).getId() == ticketId) {
                                    System.out.println("This Ticket ID already exist!! Please use different ticket id");
                                    test = false;
                                    break;
                                }
                            }
                            if(!test){
                                j--;
                                continue;

                            }

                            System.out.println("please enter subject");
                            String subject = read.next(); //Taking input from user

                            System.out.println("please enter agent name");
                            String agentName = read.next(); //Taking input from user

                            System.out.println("Please enter the number of tags you want to enter");

                            int numberOfTags = read.nextInt(); //Taking input from user

                            HashSet<String> setOfTags = new HashSet<String>();
                            for (int i=0; i < numberOfTags; i++){
                                System.out.println("please enter tag");
                                setOfTags.add(read.next());
                            }

                            ticketModel = ticketBase.createTicket(ticketId, subject, agentName, setOfTags);
                            if(ticketModel != null){
                                System.out.println("Ticket created successfully !!");
                            }
                    }
                    break;
                case 2:
                    System.out.println("Please enter ticket id to update");
                    ticketId = read.nextInt();

                    System.out.println("Do want to change agent name? press 'y' for yes or 'n' for no");

                    String agentName = null;
                    if(read.next().equals("y")){
                        System.out.println("Please enter agent name");
                        agentName = read.next();
                    }

                    System.out.println("Do want to change tags of ticket? press 'y' for yes or 'n' for no");
                    HashSet<String> setOfTags = new HashSet<String>();

                    if(read.next().equals("y")){
                        System.out.println("Please enter the number of tags you want to enter");

                        int numberOfTags = read.nextInt(); //Taking input from user
                        for (int i=0; i< numberOfTags; i++){
                            System.out.println("please enter tag");
                            setOfTags.add(read.next());
                        }
                    }

                    ticketModel = ticketBase.updateTicket(ticketId, agentName, setOfTags);
                    if(ticketModel != null){
                        System.out.println(ticketModel);
                    }else{
                        System.out.println("Ticket does not exist! Please try again");
                    }

                    break;
                case 3:
                    System.out.println("Please enter ticket id to delete");
                    ticketId = read.nextInt();
                    if(ticketBase.deleteTicket(ticketId)){
                        System.out.println("Deleted successfully");
                    }else {
                        System.out.println("Ticket does not exist! Please try again");
                    }
                    break;
                case 4:
                    System.out.println("Please enter ticket id to get detail");
                    ticketId = read.nextInt();
                    ticketModel = ticketBase.detail(ticketId);
                    if(ticketModel != null) {
                        System.out.println(ticketModel);
                    }else {
                        System.out.println("Ticket does not exist! Please try again");
                    }
                    break;
                case 5:
                    //Ganesh D, here you have logic for printing list
                    list = ticketBase.listTicket(1, null);
                    if(list == null){
                        System.out.println("List is empty");
                    }else{
                        System.out.println(list);
                    }
                    break;
                case 6:
                    System.out.println("Please enter agent name to search");
                    agentName = read.next();
                    list = ticketBase.listTicket(2, agentName);
                    if(list == null){
                        System.out.println("No ticket available for this agent");
                    }else{
                        System.out.println(list);
                    }
                    break;
                case 7:
                    System.out.println("Please enter tag to search");
                    String tag = read.next();
                    list =  ticketBase.listTicket(3, tag);
                    if(list == null){
                        System.out.println("NO ticket available for this tag");
                    }else{
                        System.out.println(list);
                    }
                    break;
                case 8:
                    HashMap<String, Integer> agentTicketCount  = ticketBase.ticketCountByAgent();
                    if(agentTicketCount == null){
                        System.out.println("Ticket list is empty");
                    }else {
                        System.out.println("Below is the list that contains ticket count by agent");
                        System.out.println(agentTicketCount);
                    }
                    break;
            }
            System.out.println("Do you want to continue? press 'y' for yes or 'n' for no");
        }while (read.next().equals("y"));
    }
}
