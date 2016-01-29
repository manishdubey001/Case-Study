package Serialization;

import model.Ticket;
import service.TicketOperations;
import service.TicketService;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 22/1/16.
 */
public class TicketOperationSerialization {

    /**
     * Serialize tickets
     * @param ticket
     */
    public static void serialize(Ticket ticket, ArrayList masterTicketData, boolean action)
    {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
           try {
               File fileName = createFile();
               if(fileName.length()<=0 || !action) {
                   fos = new FileOutputStream(fileName);
                   oos = new ObjectOutputStream(fos);
               }
               else {
                   fos = new FileOutputStream("src/main/data/tickets.ser", true);
                   oos = new ObjectOutputStream(fos) {
                       protected void writeStreamHeader() throws IOException {
                           reset();
                       }
                   };
               }

               if(!action) {
                   Iterator itr = masterTicketData.iterator();
                   while(itr.hasNext()){
                       oos.writeObject(itr.next());
                       System.out.println();
                   }
               }else{
                   oos.writeObject(ticket);
               }

               if(oos != null )
                   oos.close();
               if (fos != null )
                   fos.close();
           }catch(IOException ie){
               ie.printStackTrace();
           }
    }

    /**
     * deserialize the ticket list
     * @return
     */
    public static ArrayList deserialize(){
        ArrayList ticktsData = new ArrayList();
        ObjectInputStream ois = null;
        FileInputStream fis = null;

        try{
            File file = createFile();
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);

            ////while(true){
            while (fis.available() > 0){
                try{
                    Ticket t = (Ticket) ois.readObject();
                    ticktsData.add(t);
                }
                catch(EOFException eofe){
                    System.out.println("firsssttt");
                    eofe.printStackTrace(); break;
                }
            }
            ois.close();
            fis.close();
        }
        catch(EOFException ee){
            System.out.println("secondddd");
            ee.printStackTrace();
        }
        catch(IOException ie) {
            ie.printStackTrace();
        }
        catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }

        return ticktsData;
    }

    public static File createFile() throws IOException{
       File newFile = new File("src/main/data/tickets.ser");
        newFile.createNewFile();
        return newFile;
    }
}
