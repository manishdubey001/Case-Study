package com.ticketmaster;

import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.helpers.TicketHelper;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.utils.AppUtil;

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
    private AppRunner(){

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
            }while (ch !=9);

        }catch (InputMismatchException | IOException  e){

//            e.printStackTrace(); //check stack trace
            System.out.println("Invalid Option Selection");

        }catch (ClassNotFoundException e){
//            e.printStackTrace(); // check stack trace

            System.out.println("Ops! class you are looking does not exist");

        }catch (Exception e){
//            e.printStackTrace();
            System.out.println("Application halted due to exception: "+e.getMessage());

        }finally {

            if(ch!=9){
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

    private int printMenu() throws InputMismatchException, IOException {

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
     * @param ch int <p></p>
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TicketNotFoundException
     */
    protected void processChoice(int ch) throws IOException, ClassNotFoundException, TicketNotFoundException {
        TicketHelper helper= new TicketHelper();
        Map tmpMap;
        List tempList = null;
        b = new BufferedReader(new InputStreamReader(System.in));
        // cjm - 'b' is an OK name for a BufferedReader, but 'a' is not a descriptive name for the list.
        // Prefer to declare the list in the context where you plan to use it (unless you need it to survive longer.
        // In this case if you use a type parameter List<T>, T would be different from block to block, so you would have
        // to use a separate list.


        switch (ch){

            case 1: helper.createTicket(); break;
            case 2: helper.updateTicket(); break;
            case 3: helper.deleteTicket(); break;

            case 4:
                tmpMap = helper.getTicket();
                if (tmpMap!= null){
                    System.out.printf("==== Ticket #%010d ====\n",tmpMap.get("id"));
                    tmpMap.forEach((k,v)->System.out.println(k+"\t:\t"+v));
                }

                break;

            case 5:
                tempList = helper.getTickets();
                System.out.println("Total tickets: "+(tempList!= null ? tempList.size(): 0)+"\nResults:");
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

            case 8:
                System.out.println("Enter tag name: ");
                String tag = b.readLine();
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

            case 9: System.out.println("Thank you for using application"); break;
            default: case 0: throw new IOException("option selected is out of bounds");

        }

    }

}
