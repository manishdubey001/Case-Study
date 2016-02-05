package com.yogesh.fileReadWrite;

/**
 * Created by root on 22/1/16.
 */

import com.yogesh.ConsoleIO;
import com.yogesh.model.Ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * JDK before version 7.
 */

public class ExerciseSerializable {

    public static String filepath = "SerTicket.ser";

    public static void main(String... arguments) {
//        singleTicketReadWriteOperation();
//        listOfTicketReadWriteOperation();
    }

//    private static void singleTicketReadWriteOperation() {
//
//        Ticket ticket = new Ticket(1, "Subject", "Yogesh", new HashSet<>(Arrays.asList("tag1")));
//
//        FileOperations.writeObjectToFile(ticket, filepath);
//
//
//        Ticket objTicket = FileOperations.readObjectFromFile(filepath);
//        ConsoleIO.ticketListHeader();
//        ConsoleIO.showTicket(ticket);
//    }
//
//    private static void listOfTicketReadWriteOperation() {
//        Ticket ticket = new Ticket(1, "Subject", "Yogesh", new HashSet<>(Arrays.asList("tag1")));
//        Ticket tic = new Ticket(2, "Subject1", "Ganesh", new HashSet<>(Arrays.asList("tag1")));
//
//
//        ArrayList<Ticket> arr = new ArrayList<>();
//        arr.add(ticket);
//        arr.add(tic);
//
//        ArrayList<Ticket> arrlist = null;
//        try {
//            FileOperations.serialize(arr);
//            arrlist = FileOperations.deserialize();
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        } catch (ClassNotFoundException cnfe) {
//            cnfe.printStackTrace();
//        }
//
//        ConsoleIO.ticketListHeader();
//        for (Ticket objTicket : arrlist) {
//
//            ConsoleIO.showTicket(objTicket);
//        }
//    }


}
