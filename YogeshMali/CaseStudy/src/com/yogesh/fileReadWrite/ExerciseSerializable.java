package com.yogesh.fileReadWrite;

/**
 * Created by root on 22/1/16.
 */
import com.yogesh.model.Ticket;
import java.io.*;
import java.util.*;

/**
 * JDK before version 7.
 */

public class ExerciseSerializable {


    public static void main(String... tag1rguments) {
        singleTicketReadWriteOperation();
        listOfTicketReadWriteOperation();
    }

    private static void singleTicketReadWriteOperation() {

        Ticket ticket = new Ticket(1, "Subject", "Yogesh", new HashSet<>(Arrays.asList("tag1")));

        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("SerTicket.ser"));
            os.writeObject(ticket);
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream("SerTicket.ser"));
            Ticket objTicket = (Ticket) objIn.readObject();
            System.out.println(objTicket.getId());
            System.out.println(objTicket.getSubject());
            System.out.println(objTicket.getAgentName());
            System.out.println(objTicket.getTags());
            System.out.println(objTicket.getCreated());
            System.out.println(objTicket.getModified());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void listOfTicketReadWriteOperation() {
        Ticket ticket = new Ticket(1, "Subject", "Yogesh", new HashSet<>(Arrays.asList("tag1")));
        Ticket tic = new Ticket(2, "Subject1", "Ganesh", new HashSet<>(Arrays.asList("tag1")));

        ArrayList<Ticket> arr = new ArrayList<>();
        arr.add(tic);
        arr.add(ticket);
        ArrayList<Ticket> arrlist = null;
        try {
            Ticket.serialize(arr);
            arrlist = Ticket.deserialize();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }


        for (Ticket objTicket : arrlist) {

            System.out.println(objTicket.getSubject());
            System.out.println(objTicket.getAgentName());
            System.out.println(objTicket.getTags());
            System.out.println(objTicket.getCreated());
            System.out.println(objTicket.getModified());
        }
    }





}
