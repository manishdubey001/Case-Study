package com.Tickets;

import java.io.*;
import java.util.List;

/**
 * Created by root on 28/1/16.
 */
public class SerializeService {
    private File file = new File("tickets.ser");
    private FileOutputStream fileOutputStream;

    public boolean writeSingleTicketToFile(Ticket ticket){
        try {
            fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            ticket.writeSingleObject(outputStream);
            fileOutputStream.close();
            return true;
        }catch (FileNotFoundException Nf){
            Nf.printStackTrace();
        }catch (IOException Io){
            Io.printStackTrace();
        }
        return false;
    }

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
    public void readSingleTicketFromFile(Ticket ticketObj){
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Ticket ticket = (Ticket) ticketObj.readSingleObject(objectInputStream);
            System.out.println(ticket);
        }catch (FileNotFoundException Nf){
            Nf.printStackTrace();
        }catch (IOException Io){
            Io.printStackTrace();
        }catch (ClassNotFoundException Cf){
            Cf.printStackTrace();
        }
    }

    public void readMultipleTicketsFromFile(Ticket ticketObj){
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<Ticket> tickets = (List) ticketObj.readMultipleListOfObjects(objectInputStream);
            TicketServiceComponent ticketServiceComponent = new TicketServiceComponent();
            ticketServiceComponent.display(tickets);
        }catch (FileNotFoundException Nf){
            Nf.printStackTrace();
        }catch (IOException Io){
            Io.printStackTrace();
        }catch (ClassNotFoundException Cf){
            Cf.printStackTrace();
        }
    }

}
