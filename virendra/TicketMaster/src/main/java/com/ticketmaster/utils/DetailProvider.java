package com.ticketmaster.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by root on 18/1/16.
 */
public class DetailProvider {

    Scanner scanner;
    private static DetailProvider _instance;

    /**
     * DetailProvider constructor
     */
    private DetailProvider(){
        if(scanner == null){
            scanner = new Scanner(System.in);
        }
    }

    /**
     * init method is used to initialize the object of DetailProvider
     * @return
     */
    public static DetailProvider init(){
        if (! (_instance instanceof DetailProvider)){
            _instance = new DetailProvider();
        }
        return _instance;

    }

    /**
     * readStringInput Method is used to read the string input from the user from console
     * @return
     */
    public String readStringInput(){
        return scanner.nextLine();
    }
    /**
     * readIntInput Method is used to read the integer input from the user from console
     */
    public int readIntInput() {
        int value = scanner.nextInt();
        this.readStringInput();
        return value;
    }

    /**
     * readTagsInput method is used to read the data from console for tag creation
     * @return
     */
    public Set readTagsInput(){

        Set<String> tagSet = new HashSet<>();
        System.out.println("Enter tags (y/n): ");
        String t = this.readStringInput();
        if (t.toLowerCase().equals("y")){
            System.out.println("Enter tags separated by colon (:) : ");
            String tmp = this.readStringInput();
            String[] tmp1 = tmp.split(":");

            for (int i=0;i<tmp1.length;i++){
                tagSet.add(tmp1[i]);
            }
        }
        return tagSet;
    }
}
