package com.ticket.service;

import com.ticket.model.Ticket;

import java.io.*;
import java.util.List;

/**
 * Created by root on 28/1/16.
 */
public class SerializeService {
    private File file = new File("tickets.ser");
    private FileOutputStream fileOutputStream;

    /**
     * writes single ticket to file
     * @param ticket
     * @return
     */
    public boolean writeSingleTicketToFile(Ticket ticket){
        try {
            fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            ticket.writeObject(outputStream);
            fileOutputStream.close();
            return true;
        }catch (FileNotFoundException Nf){
            Nf.printStackTrace();
        }catch (IOException Io){
            Io.printStackTrace();
        }
        return false;
    }

    /**
     * writes multiple tickets to file
     * @param ticketsList
     * @param tickets
     * @return
     */
    public boolean writeMultipleTicketsToFile(List<Ticket> ticketsList, Ticket tickets){
        try {
            fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            tickets.writeMultipleListOfObjects(ticketsList, outputStream);
            fileOutputStream.close();
            return true;
        }catch (FileNotFoundException Nf){
            Nf.printStackTrace();
        }catch (IOException Io){
            Io.printStackTrace();
        }
        return false;
    }

    /**
     * read single ticket from file
     * @param ticketObj
     */
    public void readSingleTicketFromFile(Ticket ticketObj){
        try (FileInputStream fileInputStream = new FileInputStream(file)){
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ticketObj.readObject(objectInputStream);
            System.out.println(ticketObj);
        }catch (FileNotFoundException Nf){
            Nf.printStackTrace();
        }catch (IOException Io){
            Io.printStackTrace();
        }catch (ClassNotFoundException Cf){
            Cf.printStackTrace();
        }
    }

    /**
     * read multiple tickets from file
     * @param ticketObj
     */
    public void readMultipleTicketsFromFile(Ticket ticketObj){
        try (FileInputStream fileInputStream = new FileInputStream(file)){
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<Ticket> tickets = ticketObj.readMultipleListOfObjects(objectInputStream);
            TicketService ticketService = new TicketService();
            ticketService.display(tickets);
        }catch (FileNotFoundException Nf){
            Nf.printStackTrace();
        }catch (IOException Io){
            Io.printStackTrace();
        }catch (ClassNotFoundException Cf){
            Cf.printStackTrace();
        }
    }

}
