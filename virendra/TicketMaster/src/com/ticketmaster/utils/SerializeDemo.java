package com.ticketmaster.utils;

import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.models.Ticket;

import java.io.*;

/**
 * SerializeDemo class
 * This class is used to demonstrate the serialization in application
 * Created by Virendra on 21/1/16.
 */
public class SerializeDemo {

    final String fileName = "files/tickets.ser";
    FileOutputStream fOut;
    FileInputStream fIn;

    public static void main(String[] args) {
        System.out.println("== Serialization Demo Program ==");
        SerializeDemo object = new SerializeDemo();
        try{
            //serialize object
            object.serializeObject();

            //deserialize object
            object.deSerializeObject();


        }catch (IOException | ClassNotFoundException | TicketNotFoundException e){
            System.out.println(e.getMessage()+"\n\n");
            e.printStackTrace();
        }
    }

    public void serializeObject() throws IOException, TicketNotFoundException, ClassNotFoundException {
        Ticket ticket, ticket1;
        ticket = new Ticket.TicketBuilder().withSubject("s1").withAgent("a1").build();
        ticket.save();
        ticket1 = new Ticket.TicketBuilder().withSubject("s2").withAgent("a2").build();
        ticket1.save();

        ObjectOutputStream sOut = this.getWriter();

        sOut.writeObject(ticket); //write object 1
        sOut.writeObject(ticket1); //write object 2


        //close  streams
        this.closeObjectStream(sOut);
        this.closeFileStream(fOut);

        System.out.println("OK!! objects written to the file: "+fileName);
    }



    public void deSerializeObject() throws IOException, ClassNotFoundException {

        Ticket ticket ;

        ObjectInputStream sIn = this.getReader();

        boolean completed = false;

        while (!completed){
            try{
                ticket = (Ticket)sIn.readObject();
                System.out.println(ticket.hashCode()+ "===="+ ticket.toString());
                System.out.println(ticket.getSubject());
                System.out.println(ticket.getAgent());


            }catch (EOFException e){
                completed = true;
            }
        }



    }

    private ObjectOutputStream getWriter() throws IOException{

        //connect stream to file with FileOutputStream
        if (!new File(fileName).exists()){
            new File(fileName).getParentFile().mkdirs();
        }

        fOut = new FileOutputStream(fileName);

        //program uses ObjectOutputStream to write objects
        //parameter supplied to instance will be File stream
        return new ObjectOutputStream(fOut);


    }

    private ObjectInputStream getReader() throws IOException{

        //connect stream to file with FileOutputStream
        if (!new File(fileName).exists()){
            new File(fileName).getParentFile().mkdirs();
        }

        fIn = new FileInputStream(fileName);

        //program uses ObjectOutputStream to write objects
        //parameter supplied to instance will be File stream
        return new ObjectInputStream(fIn);


    }



    private void closeObjectStream(ObjectOutputStream stream) throws IOException{

        if(stream != null){
            stream.close();
        }
    }
    private void closeObjectStream(ObjectInputStream stream) throws IOException{

        if(stream != null){
            stream.close();
        }

    }



    private void closeFileStream(FileOutputStream stream) throws IOException{

        if(stream != null){
            stream.close();
        }

    }

    private void closeFileStream(FileInputStream stream) throws IOException{

        if(stream != null){
            stream.close();
        }
    }


}
