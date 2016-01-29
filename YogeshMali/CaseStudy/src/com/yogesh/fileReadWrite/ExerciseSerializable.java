package com.yogesh.fileReadWrite;

/**
 * Created by root on 22/1/16.
 */
import com.yogesh.ConsolIO;
import com.yogesh.model.Ticket;
import java.io.*;
import java.util.*;

/**
 * JDK before version 7.
 */

public class ExerciseSerializable {

    public static String filepath =  "SerTicket.ser";

    public static void main(String... arguments) {
        singleTicketReadWriteOperation();
        listOfTicketReadWriteOperation();
    }

    private static void singleTicketReadWriteOperation() {

        Ticket ticket = new Ticket(1, "Subject", "Yogesh", new HashSet<>(Arrays.asList("tag1")));

        try {

            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filepath));
            os.writeObject(ticket);

            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(filepath));
            Ticket objTicket = (Ticket) objIn.readObject();

            ConsolIO.ticketListHeader();
            ConsolIO.showTicket(ticket);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void listOfTicketReadWriteOperation() {
        Ticket ticket = new Ticket(1, "Subject", "Yogesh", new HashSet<>(Arrays.asList("tag1")));
        Ticket tic = new Ticket(2, "Subject1", "Ganesh", new HashSet<>(Arrays.asList("tag1")));



        ArrayList<Ticket> arr = new ArrayList<>();
        arr.add(ticket);
        arr.add(tic);

        ArrayList<Ticket> arrlist = null;
        try {
            Ticket.serialize(arr);
            arrlist = Ticket.deserialize();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        ConsolIO.ticketListHeader();
        for (Ticket objTicket : arrlist) {

            ConsolIO.showTicket(objTicket);
        }
    }





}
