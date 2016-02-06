package com.services;

import com.customexceptions.UserInputException;
import com.model.Ticket;
import java.io.*;
import java.util.*;

/**
 * Created by root on 27/1/16.
 */
public class TicketSerializedClass {

    /**
     * save ticket in file
     * @param ticketMap, append, file
     * @return boolean */
    public static boolean saveTicketsInFile(Map<Long, Ticket> ticketMap, boolean append, File file){

        ObjectOutputStream oos = null;
        try {
            // Lokesh: Why do you need to create/check file for every ticket? Can't it be done one time when application starts?
             /** File file = UserConsoleInput.createFile();*/
            /** Deepak:
             * Check it at the time of start the application. */
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

            return true;
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

        return false;
    }

    /**
     * read ticket from file
     * @param  file
     * @return Map<Long, Ticket> */
    public static Map<Long, Ticket> readTicketsFromFile(File file){

        /*File file = createFile();*/
        Map<Long, Ticket> tempTicketMap = new HashMap<>();
        if(file.length() == 0) {
            try {
                throw new UserInputException("No data in file!");
            } catch (UserInputException e) {
                System.out.println(e.getMessage());
                return tempTicketMap;
            }
        }


/*        ObjectInputStream ois = null;
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
        finally {
            if(fis != null) {
                try {
                    fis.close();
                    if(ois != null)
                        ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return tempTicketMap;*/

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                Ticket ticket = (Ticket)ois.readObject();
                tempTicketMap.put(ticket.getId(),ticket);
            }
        }catch (EOFException e){
            System.out.println("Reached EOF file");
        }catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return tempTicketMap;
    }

    /**
     * To check file and create if not present*/
    public static File createFile(String nameOfFile){
        File file = null;
        try {
            file = new File("src/com/resources/"+nameOfFile);
            if(!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        return file;
    }

    /**
     * to update property file*/
    public static void updatePropertyFile(File file, Properties properties, Long ticketId){
        FileWriter writer = null;
        try{
            properties = new Properties();
            properties.setProperty("ticketId", ticketId.toString());
            writer = new FileWriter(file);
            properties.store(writer, "host settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(writer != null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
