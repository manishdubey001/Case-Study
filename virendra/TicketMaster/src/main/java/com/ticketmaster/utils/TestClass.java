package com.ticketmaster.utils;

import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.models.Ticket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 25/1/16.
 */
public class TestClass {

    static final String[] subs  = {"s1","s2","s3","s4","s5", "s6","s7","s8","s9","s10"};
    static final String[] a = {"a1","a2","a3","a4","a5", "a6","a7","a1","a3","a5"};


    public static void main (String[] args) throws TicketNotFoundException, IOException, ClassNotFoundException {
        System.out.println("Hello");

//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("demo.ser"));

        File base = new File ("files");
        if (!base.exists() && !base.isDirectory()){
            base.mkdirs();
        }

        File file = new File (base, "tickets.ser");

        if (!file.exists()){
            file.createNewFile();
        }

        ObjectOutputStream oos;


        if(file.length() <=0){
            oos = new ObjectOutputStream(new FileOutputStream(file));
        }else {
            oos = new ObjectOutputStream(new FileOutputStream(file, true)) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
        }

        for (int i=0;i<subs.length/2;i++){

            Ticket t1 = new Ticket.TicketBuilder().withAgent(a[i]).withSubject(subs[i]).build();
            t1.save();
            oos.writeObject(t1);
        }
        oos.close();

        oos = new ObjectOutputStream(new FileOutputStream(file, true)) {
            protected void writeStreamHeader() throws IOException {
                reset();
            }
        };


        for (int i=subs.length/2;i<subs.length;i++){

            Ticket t1 = new Ticket.TicketBuilder().withAgent(a[i]).withSubject(subs[i]).build();
            t1.save();
            oos.writeObject(t1);
        }
        oos.close();

//        now read object
        FileInputStream fIn = new FileInputStream(file);

        ObjectInputStream ois = new ObjectInputStream(fIn);


        List<Ticket> list = new ArrayList<>();

        try{

            while(true){
                Ticket temp = (Ticket) ois.readObject();
                list.add(temp);
                System.out.println(temp.toString());
            }

        }catch(EOFException e){
            System.out.println("reached at end!!");
        }

        ois.close();

        System.out.println("============");

        list.forEach(System.out::println);





    }



}
