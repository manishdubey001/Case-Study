package com.casestudy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lokesh on 28/1/16.
 * A FileHelper class to Create File, Serialize objects into file and de-serialize objects written to file.
 */
public class FileHelper {

    File f = null;

    public FileHelper(String basePath, String fileName) {
        f = new File(basePath);
        if(!f.exists()  || !f.isDirectory()){
            f.mkdirs();
        }
        f = new File(basePath,fileName);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void write(Object obj){
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        try {
            fileOutputStream = new FileOutputStream(f,true);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /*public Ticket read(){
        Ticket t = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try{
            fileInputStream = new FileInputStream(f);
            if(f.length() > 0 && fileInputStream.available() > 0)
            {
                objectInputStream = new ObjectInputStream(fileInputStream);
                t = (Ticket)objectInputStream.readObject();
            }
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }*/

    public Map<Integer,Ticket> readAll(){
        Map<Integer, Ticket> tickets = new HashMap<>();
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream = null;
        try{
            fileInputStream = new FileInputStream(f);
            if(f.length() > 0)
            {
                while (fileInputStream.available() > 0)
                {
                    objectInputStream = new ObjectInputStream(fileInputStream);
                    Ticket t = (Ticket)objectInputStream.readObject();
                    tickets.put(t.getId(),t);
                }
                assert objectInputStream != null;
                objectInputStream.close();
            }
            fileInputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return tickets;
    }

    public void clearAll(){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            fileOutputStream.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean checkFile() {
        return f.exists() && f.length() > 0;
    }
}
