package com.ticketmaster;

import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.helpers.TicketService;
import com.ticketmaster.utils.AppUtil;
import com.ticketmaster.utils.DetailProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * AppRunner class
 * This class is the entry point for console application
 * Created by Virendra on 31/12/15.
 */
public class AppRunner {
    private static AppRunner _instance = null;
    private BufferedReader b;
    private Scanner s;
    DetailProvider details;
    private AppRunner(){
        if (details == null){
            details = DetailProvider.init();
        }

    }

    /**
     * Default constructor
     * @return AppRunner class instance
     */
    public static AppRunner app(){
        if (! (_instance instanceof AppRunner))
            _instance = new AppRunner();
        return _instance;

    }

    /**
     * start method is the main method which starts the application
     * @throws TicketNotFoundException
     * @throws IOException
     */
    public void start() throws TicketNotFoundException, IOException{
        boolean flag = false;
        int ch =0;

        s = new Scanner(System.in);

        try{
            do{
                ch = printMenu(); //print menu and ask for user selection
                processChoice(ch);
            }while (ch !=0);

        }catch (InputMismatchException | IOException  e){

//            e.printStackTrace(); //check stack trace
            System.out.println(e.getMessage() != null ? e.getMessage() : "Invalid Option Selection");

        }catch (ClassNotFoundException e){
//            e.printStackTrace(); // check stack trace
            System.out.println("Ops! class you are looking does not exist");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Application halted due to exception: "+e.getMessage());

        }finally {

            if(ch!=0){
                System.out.println("Do You want to continue (y/n) ? ");
                if(b == null)
                    b = new BufferedReader(new InputStreamReader(System.in));
                String c = b.readLine();; //read the selection of user
                if(c.equals("y")) {
                    AppRunner.app().start(); //starting application again
                    return;
                }
                else if (c.equals("n")){
                    System.out.println("Thank you for using Application.");
                    flag = true;
                }else{
                    System.out.println("Invalid choice! Exiting Application");
                    flag = true;
                }
                if (b!=null) b.close();
            }
            s.close();
        }
        //flag will be true only if user opts for not to continue or provides invalid option
        if (flag) System.exit(0);

    }

    /**
     * method printMenu is used to print menu list in the application.
     * @return selection of user <p>int</p>
     * @throws InputMismatchException
     * @throws IOException
     */

    private int printMenu()
            throws InputMismatchException, IOException {

        Map<Integer, String> menu = AppUtil.getMenu();

        System.out.println("=============== Menu ===============");
        menu.forEach((k,v)-> System.out.println(k+"\t:\t "+v)); //using lambda expression to print menu

        // not using BufferedReader here
//        b= new BufferedReader(new InputStreamReader(System.in));

        s = new Scanner(System.in);
        System.out.println("Select your option: ");
        int a = s.nextInt();

        return a;
    }

    /**
     * method processChoice is used to perform action based
     * on user selection from menu.
     * @param ch int <p>input given by application user</p>
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */
    protected void processChoice(int ch)
            throws IOException, ClassNotFoundException, TicketNotFoundException {
        TicketService helper= new TicketService();
        Map tmpMap;

        // cjm - 'b' is an OK name for a BufferedReader, but 'a' is not a descriptive name for the list.
        // Prefer to declare the list in the context where you plan to use it (unless you need it to survive longer.
        // In this case if you use a type parameter List<T>, T would be different from block to block, so you would have
        // to use a separate list.

        List tempList;
        Set tagsSet;
        int id;
        String txtAgent, txtSubject;
        Ticket ticket;

        switch (ch){

            case 1:
                //read details for ticket

                System.out.println("Enter Ticket Subject: ");
                txtSubject = details.readStringInput();

                System.out.println("Enter Agent Name: ");
                txtAgent = details.readStringInput();

                tagsSet = details.readTagsInput();

                Ticket object = helper.createTicket(txtSubject, txtAgent, tagsSet);

                if(object != null){
                    System.out.println("Ticket "+String.format("#%010d", object.getId())+" saved successfully");
                }

                break;
            case 2:

                System.out.println("Enter Ticket id:");
                id = details.readIntInput();

                ticket = helper.getTicketDetail(id);

                if (ticket == null){
                    throw new TicketNotFoundException("Record with id: "+id +" does not exists");
                }

                System.out.println("Update Agent?(y/n)");

                if(details.readStringInput().equals("y")){
                    System.out.println("Enter Agent Name: ");
                    txtAgent = details.readStringInput();
                    ticket.setAgent(txtAgent);
                }else{
                    txtAgent = null;
                }


                tagsSet = details.readTagsInput();

                ticket = helper.updateTicket(ticket, txtAgent, tagsSet);

                if (ticket == null){
                    System.out.println("Nothing to update");
                }else {
                    System.out.println("Ticket (id: "+String.format("%010d", ticket.getId())+") updated successfully");
                }

                break;
            case 3:
                System.out.println("Enter Ticket id:");
                id = details.readIntInput();
                ticket = null;


                System.out.printf("Are you sure you want to delete ticket #%010d ? (y/n)\n", id);
                if(details.readStringInput().equals("y")){

                    ticket = helper.deleteTicket(id);

                    if (ticket == null){
                        throw new TicketNotFoundException("Unable to delete ticket "+id +". Please try again later");
                    }
                    System.out.printf("Ticket %010d deleted. (New size: %d)\n", id, Ticket.getSize());

                }else {
                    System.out.println("Delete operation cancelled by User");
                }

                break;

            case 4:
                System.out.println("Enter Ticket id:");
                id = details.readIntInput();

                tmpMap = helper.getTicket(id);

                if (tmpMap!= null){
                    System.out.printf("==== Ticket #%010d ====\n",tmpMap.get("id"));
                    tmpMap.forEach((k,v)->System.out.println(k+"\t:\t"+v));
                }

                break;

            case 5:case 9:
                tempList = helper.getTickets();
                System.out.println("Total tickets: "+(tempList!= null ? tempList.size(): 0)+"\n\t=============== Results ===============");
                if (tempList != null) {
                    tempList.forEach(System.out::println); //printing result by method referencing of lambda expressions
                }

                break;

            case 6:
                System.out.println("Enter name of agent: ");
                String name = b.readLine();


                // cjm - here let Tickets or another class manage this search. Don't expose the collection directly
                // (so implement something like Tickets.hasAgent())
                if (!Ticket.hasAgent(name)){
                    System.out.println(String.format("Agent \"%s\" is not present in the system", name ));
                }else {
                    tempList = helper.searchTicket("agent", name);
                    if (tempList != null) {
                        System.out.println("Total tickets: "+tempList.size()+"\nResults:");
                        tempList.forEach(System.out::println);
                    }else {
                        System.out.println("Total tickets: 0");
                    }
                }


                break;
            case 7:
                tmpMap= helper.getTicketCount();
                System.out.println("Agent Name\t\t|\tNo. of Ticket");
                tmpMap.forEach((k,e)-> System.out.println(k+"\t\t| \t\t"+e));
                break;

            case 8: case 13:
                System.out.println("Enter tag name: ");

                String tag = details.readStringInput();

                if (! Ticket.hasTag(tag)){
                    System.out.println(String.format("Tag \"%s\" is not present in the system", tag ));
                }else {
                    tempList=  helper.searchTicket("tags", tag);
                    if (tempList!=null){
                        System.out.println("Total tickets: "+tempList.size()+"\nResults:");
                        tempList.forEach(System.out::println);  //printing result by method referencing of lambda expressions
                    }else {
                        System.out.println("Total tickets: 0");
                    }
                }

                break;

            /*Reporting options in case study*/


            case 10:
                //get the oldest ticket in the system
                tmpMap = helper.getOldestTicket();

                if (tmpMap!= null){
                    System.out.printf("==== Ticket #%010d ====\n",tmpMap.get("id"));
                    tmpMap.forEach((k,v)->System.out.println(k+"\t:\t"+v));
                }

                break;
            case 11:
                //ticket older than number of days
                System.out.println("Enter no. of days: ");
                int days= details.readIntInput();
                tempList = helper.getOlderTickets(days);

                System.out.println("Total tickets: "+(tempList!= null ? tempList.size(): 0)+
                        "\n\t=============== Results ===============");
                if (tempList != null) {
                    tempList.forEach(System.out::println); //printing result by method referencing of lambda expressions
                }





                break;
            case 12:
                //print all the tags present in the system
                Set<String> obj = (Set<String>)helper.getTagsOfTicket();
                System.out.println("Total Tags present in the System: ");
                System.out.println(obj);

                break;

            case 0: System.out.println("Thank you for using application"); break;
            default: throw new IOException("option selected is out of bounds");

        }

    }

}
