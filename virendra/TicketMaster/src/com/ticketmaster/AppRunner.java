package com.ticketmaster;

import com.ticketmaster.exceptions.DuplicateEntryException;
import com.ticketmaster.helpers.TicketHelper;
import com.ticketmaster.models.Tickets;
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
     * @throws DuplicateEntryException
     * @throws IOException
     */
    public void start() throws DuplicateEntryException, IOException{
        boolean flag = false;
        int ch =0;

        s = new Scanner(System.in);
        try{
            do{
                ch = printMenu(); //print menu and ask for user selection
                processChoice(ch);
            }while (ch !=9);

        }catch (InputMismatchException | IOException  e){

            e.printStackTrace(); //check stack trace
            System.out.println("Invalid Option Selection");

        }catch (ClassNotFoundException e){
//            e.printStackTrace(); // check stack trace

            System.out.println("Ops! class you are looking does not exist");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Application halted due to exception: "+e.getMessage());

        }finally {

            if(ch!=9){
                System.out.println("Do You want to continue (y/n) ? ");
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
     * @throws DuplicateEntryException
     */
    protected void processChoice(int ch) throws IOException, ClassNotFoundException, DuplicateEntryException {
        TicketHelper helper= new TicketHelper();
        Map m;
        List a = null;
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
                m = helper.getTicket();
                if (m!= null){
                    System.out.printf("==== Ticket #%010d ====\n",m.get("id"));
                    m.forEach((k,v)->System.out.println(k+"\t:\t"+v));
                }

                break;

            case 5:
                a = helper.getTickets();
                System.out.println("Total tickets: "+(a!= null ? a.size(): 0)+"\nResults:");
                if (a!= null)
                    a.forEach(System.out::println); //printing result by method referencing of lambda expressions

                //traditional method
               /* Iterator i = a.iterator();
                while (i.hasNext()){
                    LinkedHashMap lm = (LinkedHashMap) i.next();
                    System.out.println(lm);
                }*/

                break;

            case 6:
                b = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Enter name of agent: ");
                String name = b.readLine();

                // cjm - here let Tickets or another class manage this search. Don't expose the collection directly
                // (so implement something like Tickets.hasAgent())
                if (! Tickets.agentList.contains(name)){
                    System.out.println(String.format("Agent \"%s\" is not present in the system", name ));

                }else
                    a=  helper.searchTicket("agent", name);
                // cjm - I recommend always using braces around blocks in if/else. This prevents confusion with the following lines.


                if (a!=null)
                    System.out.println("Total tickets: "+a.size()+"\nResults:");
                else
                    System.out.println("Total tickets: 0\nResults:");

                a.forEach(System.out::println);


                break;
            case 7:
                m= helper.getTicketCount();
                System.out.println("Agent Name\t\t|\tNo. of Tickets");
                m.forEach((k,e)-> System.out.println(k+"\t\t| \t\t"+e));
//                System.out.println(m);
                break;

            case 8:

                System.out.println("Enter tag name: ");
                String t = b.readLine();
                // cjm - Same comment as above about agents and exposing this collection
                if (! Tickets.tagList.contains(t)){
                    System.out.println(String.format("Tag \"%s\" is not present in the system", t ));

                }else
                   a=  helper.searchTicket("tags", t);

                if (a!=null){
                    System.out.println("Total tickets: "+a.size()+"\nResults:");
                }else {
                    System.out.println("Total tickets: 0\nResults:");
                }

                a.forEach(System.out::println); //printing result by method referencing of lambda expressions

                break;

            case 9: System.out.println("Thank you for using application"); break;
            default: case 0: throw new IOException("option selected is out of bounds");

        }

    }

}
