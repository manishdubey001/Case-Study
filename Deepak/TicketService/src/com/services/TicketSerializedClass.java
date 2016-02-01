package com.services;

import com.customexceptions.UserInputException;
import com.model.Ticket;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import com.util.UserConsoleInput;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by root on 27/1/16.
 */
public class TicketSerializedClass {

    public static boolean saveTicketsInFile(Map<Long, Ticket> ticketMap, boolean append){

        ObjectOutputStream oos = null;
        try {
            // Lokesh: Why do you need to create/check file for every ticket? Can't it be done one time when application starts?
            File file = UserConsoleInput.createFile();
            if(append){
                // append to the file.
                oos = new ObjectOutputStream(new FileOutputStream(file, true)){
                    protected void writeStreamHeader() throws IOException{
                        reset();
                    }
                };
            }
            else {
                // update whole file.
                oos = new ObjectOutputStream(new FileOutputStream(file));
            }

                Set<Map.Entry<Long, Ticket>> entrySet = ticketMap.entrySet();
                for (Map.Entry entry : entrySet)
                    oos.writeObject(entry.getValue());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(oos != null)
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return true;
    }


    public static Map<Long, Ticket> readTicketsFromFile(){

        File file = UserConsoleInput.createFile();
        Map<Long, Ticket> tempTicketMap = new HashMap<>();
        if(file.length() == 0) {
            try {
                throw new UserInputException("No data in file!");
            } catch (UserInputException e) {
                System.out.println(e.getMessage());
                return tempTicketMap;
            }
        }


        ObjectInputStream ois = null;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);

            while(fis.available() > 0){
                try {
                    Ticket ticket = (Ticket) ois.readObject();
                    tempTicketMap.put(ticket.getId(), ticket);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempTicketMap;
    }
}
