package Serialization;

import model.Ticket;
import util.Util;

import java.io.*;
import java.util.*;

public class TicketSerialization {

    public static void serialize(Map<Integer, Ticket> masterTicketData, boolean action)
    {
        ObjectOutputStream oos = null;
           try {
               File fileName = Util.createFile("tickets.ser");
               if(fileName.length()<=0 || !action) {
                   oos = new ObjectOutputStream(new FileOutputStream(fileName));
               }
               else {
                   oos = new ObjectOutputStream(new FileOutputStream(fileName, true)) {
                       protected void writeStreamHeader() throws IOException {
                           reset();
                       }
                   };
               }
               List<Ticket> ticketList = new ArrayList<>(masterTicketData.values());
               Iterator itr = ticketList.iterator();
               while(itr.hasNext()){
                   oos.writeObject(itr.next());
               }
           }catch(IOException ie){
               ie.printStackTrace();
           }
    }

    public static Map<Integer, Ticket> deserialize(){
        Map<Integer, Ticket> ticketsData = new HashMap<>();
        try{
            File file = Util.createFile("tickets.ser");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while(fis.available() > 0){
                Ticket t = (Ticket) ois.readObject();
                ticketsData.put(t.getId(),t);
            }
        }
        catch(IOException ie) {
            ie.printStackTrace();
        }
        catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        return ticketsData;
    }
}
