import model.Ticket;

import java.io.*;
import java.util.*;

/**
 * Created by root on 28/1/16.
 */
public class TestSerialization {
    public static void main(String[] args) throws EOFException{
        String tags = "mumbai,delhi,pune";
        List<String> tagList = Arrays.asList(tags.split(","));
        ArrayList<Ticket> arr = new ArrayList<Ticket>();

        for(int i=1; i<=2; i++) {
            Ticket ticket = new Ticket(i, "mangesh", "mangesh11", tagList);

          //System.out.println("Before Writing into file ::: " + ticket.getId() + " === "+ ticket.getSubject());

            try{
                FileOutputStream fl = new FileOutputStream("testSer1.ser", true);  // passing true for appending;
                ObjectOutputStream oos = new ObjectOutputStream(fl);
                oos.writeObject(ticket);
                fl.close();
                oos.close();
                ticket = null;
            }catch(IOException ie) {
                ie.printStackTrace();
            }
        }

        // reading from file

        try{
            FileInputStream fi = new FileInputStream("testSer1.ser");
            ObjectInputStream oi = new ObjectInputStream(fi);


            while (true){
                Ticket t;
                try {
                    t = (Ticket) oi.readObject();

                    System.out.println("====" + t.getId() + " === " + t.getSubject() + "====" + t.getAgent_name());
                    arr.add(t);
                }
                catch (EOFException eof){
                    break;
                }
                catch(IOException ie){
                    ie.printStackTrace(); break;
                }
            }
        }
        catch (EOFException e){
            e.printStackTrace();
        }
        catch(IOException ie) {
            ie.printStackTrace();
        }
        catch(ClassNotFoundException ce){
            ce.printStackTrace();
        }

        System.out.println("size :: " + arr.size());
        System.out.println(":data :: " + arr);
    }
}
