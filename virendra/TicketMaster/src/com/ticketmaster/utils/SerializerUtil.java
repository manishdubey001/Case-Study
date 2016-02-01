package com.ticketmaster.utils;

import com.ticketmaster.Main;
import com.ticketmaster.models.Ticket;

import java.io.*;
import java.util.*;

/**
 * Created by root on 27/1/16.
 */
public class SerializerUtil {

    ObjectOutputStream fOut;
    ObjectInputStream fIn;
    final String base = "files";
    File file;
    String fileName = SerializerUtil.getSerializedFileName(Main.fileChoice);
    String propertyFile = "conf.properties";

    public SerializerUtil(){

    }

    // EB : Unused Method.
    public SerializerUtil(String filename){
        fileName = filename;

    }


    private void checkFiles(String filename) throws IOException {
        File directory = new File (base);
        if (!directory.exists() && !directory.isDirectory()){
            directory.mkdirs();
        }
        file = new File(base, filename);
        if (!file.exists()){
            file.createNewFile();
        }
    }


    private void connectWriter()
            throws IOException{

        if (file.length() <= 0){
            fOut = new ObjectOutputStream(new FileOutputStream(file));
        }else {
            fOut = new ObjectOutputStream(new FileOutputStream(file, true)) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
        }
    }

    private void disconnectWriter()
            throws IOException{

        fOut.close();

    }

    private void connectReader()
            throws IOException{

        fIn = new ObjectInputStream(new FileInputStream(file));

    }

    private void disconnectReader()
            throws IOException{
        fIn.close();
    }

    public void emptyObjectFile()
            throws IOException{
        new ObjectOutputStream(new FileOutputStream(file)).close();
        new ObjectOutputStream(new FileOutputStream(file)).close();
    }

    /**
     *
     * @param objects
     * @throws IOException
     */
    public void writeToFile(Map<?, ?> objects) throws IOException{

        //check and setup file
        if (file == null) {
            checkFiles(fileName);
        }

        //connect write to file
        connectWriter();

        //take list from the supplied value
        List<?> tempList = new ArrayList<>(objects.values());

        //parse data and write to file
        if (tempList!= null){

            tempList.stream().forEach((e)->{
                try {
                    fOut.writeObject(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

        }

        //disconnect writer
        disconnectWriter();
    }

    // EB : Return type Object is obvious. Returning specific Data type/Collection makes the method confined to specific/expected operations.
    public Object readFromFile()
            throws IOException, ClassNotFoundException{

        checkFiles(fileName); // EB: Good practice to use 'this' keyword.

        Map<Integer, Ticket> tempMap = new HashMap<>();

        if (file.length() !=0){

            connectReader();

            try{

                while(true){ // EB : Not a recommended/standard way, instead use flag.
                    Ticket temp = (Ticket) fIn.readObject();
                    tempMap.put(new Integer(temp.getId()), temp);
                }
            }catch (EOFException e){

            }

            disconnectReader();

        }

        return tempMap;
    }

    public void writeProperty(String key , String value)
            throws IOException {

        Properties prop = loadPropertiesFile();

        FileOutputStream fOut = new FileOutputStream(base+File.separator+propertyFile);

        prop.setProperty(key, value);
        prop.store(fOut,null);

    }

    public Properties loadPropertiesFile()
            throws IOException {
        Properties prop = new Properties();

        File tmpFile = new File(base+ File.separator+propertyFile);
        if (!tmpFile.exists()){
            tmpFile.createNewFile();
        }

        FileInputStream in = new FileInputStream(base+ File.separator+propertyFile);
        prop.load(in);

        return prop;
    }

    public String readProperty(String key)
            throws IOException{
        //add or read properties for id of ticket list
        Properties prop = loadPropertiesFile();

        if (prop.getProperty(key) == null){
            //no value found, initial id
            writeProperty(key,"1");
        }

        return prop.getProperty(key);

    }


    public static String getSerializedFileName(int ch){
        String strReturn;

        switch(ch){
            case 1 :
                if (Main.collectionChoice == 3) {
                    strReturn = "linkedhashmap_tickets.ser";
                }else if (Main.collectionChoice == 2){
                    strReturn = "treemap_tickets.ser";
                }else {
                    strReturn = "hashmap_tickets.ser";
                }
                break;
            case 2: default:
                strReturn = "tickets.ser";
                break;

        }
        return strReturn;

    }

}
