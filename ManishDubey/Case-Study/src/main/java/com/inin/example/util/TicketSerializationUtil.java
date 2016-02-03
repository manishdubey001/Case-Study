package com.inin.example.util;

import com.inin.example.model.Ticket;

import java.io.*;
import java.util.*;

/**
 * Created by root on 22/1/16.
 */
public class TicketSerializationUtil {

    /**
     * Serialize the list of tickets
     * @param tickets
     * @param append
     */
    public static synchronized void serializeTickets(Map<Integer, Ticket> tickets, boolean append)
    {
        ObjectOutputStream oos = null;
        try {
            File file = TicketUtil.createFile(TicketUtil.getProperty("serializeTicketFile"));
            if(append && file.length() != 0)
            {
                oos = new ObjectOutputStream(new FileOutputStream(file, true)) {
                    protected void writeStreamHeader() throws IOException {
                        reset();
                    }
                };
            }else
                oos = new ObjectOutputStream(new FileOutputStream(file));
            List<Ticket> ticketList = new ArrayList<>(tickets.values());
            Iterator<Ticket> iterator = ticketList.iterator();
            while (iterator.hasNext())
            {
                Ticket ticket = iterator.next();
                oos.writeObject(ticket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (oos != null)
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * Deserialized  all Ticket from file and return Map of Ticket
     * @return Map<Integer,Ticket>
     */
    public static Map<Integer,Ticket> deserializeTickets()
    {
        Map<Integer,Ticket> tickets = new HashMap<>();
        File file = TicketUtil.createFile(TicketUtil.getProperty("serializeTicketFile"));
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                Ticket ticket = (Ticket)ois.readObject();
                tickets.put(ticket.getId(),ticket);
            }
        }catch (EOFException e){
//            System.out.println("Reached EOF file");
        }catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return tickets;
    }



    /**
     * Delete all ticket from file
     */
    public static void clearTicket(){
        try {
            new FileOutputStream(TicketUtil.createFile(TicketUtil.getProperty("serializeTicketFile"))).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
