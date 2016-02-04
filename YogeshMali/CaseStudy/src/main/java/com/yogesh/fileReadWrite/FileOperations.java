package com.yogesh.fileReadWrite;

import com.yogesh.model.Ticket;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by root on 3/2/16.
 */
public class FileOperations {

    public static void serialize(ArrayList obj) throws IOException {

        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("SerTicket.ser"));

        os.writeObject(obj);
    }

    public static ArrayList deserialize() throws IOException, ClassNotFoundException {

        ObjectInputStream objIn = new ObjectInputStream(new FileInputStream("SerTicket.ser"));

        return ((ArrayList) objIn.readObject());
    }

    public static void writeObjectToFile(Ticket ticket, String filepath) {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filepath))) {
            os.writeObject(ticket);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Ticket readObjectFromFile(String filepath) {
        Ticket ticket = null;
        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(filepath))) {
            ticket = (Ticket) objIn.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ticket;
    }

}
