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
    public static synchronized void serializedTickets(Map<Integer,Ticket> tickets, boolean append)
    {
        ObjectOutputStream oos = null;
        try {
            File file = createFile();
            if(append && file.length() != 0)
            {
                oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/ticket.ser", true)) {
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
            try {
                if(oos != null)
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
    public static Map<Integer,Ticket> deserializedTickets()
    {
        Map<Integer,Ticket> tickets = new HashMap<>();
        ObjectInputStream ois = null;
        try {
            File file = createFile();
            ois = new ObjectInputStream(new FileInputStream(file));
            int i = 0;
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
        finally {
            try {
                if(ois != null)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tickets;
    }

    /**
     * Create file if not present and return File object
     * @return File
     * @throws IOException
     */
    private static File createFile() throws IOException{
        File file = new File("src/main/resources/ticket.ser");
        file.createNewFile();
        return file;
    }

    /**
     * Delete all ticket from file
     */
    public static void clearTicket(){
        try {
            new FileOutputStream(createFile()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
