package com.ticket;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 6/2/16.
 */
public class TicketSerializationTest {
    static TicketService ticketService = new TicketService();

    public static void main(String[] args) throws Exception{
        //Save a ticket to a file
        serializeSingleTicket();

        //Load a ticket from a file
        deserializeSingleTicket();

        //Save a list of tickets to a file
        serializeMultiTicket();

        //Load a list of tickets from a file
        deserializeMultiTicket();
    }

    /**
     * Serialize a single ticket
     * @throws Exception
     */
    public static void serializeSingleTicket() throws Exception {
        Set<String> setOfTags = new HashSet<>();
        setOfTags.add("issue");
        setOfTags.add("Hardware");
        Ticket ticket = ticketService.createTicket(1, "subject", "agent", setOfTags);

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ticket.txt"));

        out.writeObject(ticket);

        out.flush();
    }

    /**
     * Deserialize a single ticket
     * @throws Exception
     */
    public static void deserializeSingleTicket() throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("ticket.txt"));

        Ticket ticket = (Ticket) in.readObject();
        System.out.println(ticket);

        in.close();
    }

    /**
     * Serialize multiple tickets
     */
    public static void serializeMultiTicket() throws Exception{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("multiTicket.txt"));

        for (int i = 1; i <= 10; i++){
            Set<String> setOfTags = new HashSet<>();
            setOfTags.add("issue"+i);
            setOfTags.add("Hardware"+i);
            Ticket ticket = ticketService.createTicket((i+1), "subject"+i, "agent"+i, setOfTags);

            out.writeObject(ticket);
        }

        out.flush();
    }

    /**
     * Deserialize multiple tickets
     */
    public static void deserializeMultiTicket() throws Exception{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("multiTicket.txt"));

        for (int i = 1; i <= 10; i++){
            Ticket ticket = (Ticket) in.readObject();
            System.out.println(ticket);
        }

        in.close();
    }
}
