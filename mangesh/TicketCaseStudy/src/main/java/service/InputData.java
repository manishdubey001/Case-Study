package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by root on 19/1/16.
 */
public class InputData {
    Scanner console = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String getString(){
        boolean subjLoop = true;
        String subject = null;
        while (subjLoop){
            subject = console.nextLine();
            subject.trim();
            if(!subject.isEmpty()){
                subjLoop = false;
            }
        }
        return subject;
    }

    public String getAgentName(){
        boolean agentLoop = true;
        String agentName = null;
        System.out.println("Enter Agent Name : ");
        while (agentLoop){
            agentName = console.nextLine();
            agentName.trim();
            if(!agentName.isEmpty()){
                agentLoop = false;
                System.out.println("");
            }
        }
        return agentName;
    }

    public List getTagsList() {
        System.out.println("Enter Multiple tags separated by comma(,) : ");
        String tags = null;
        try {
            tags = br.readLine();
        }catch (IOException io){
            io.printStackTrace();
        }
        List<String> tagsList = Arrays.asList(tags.split(","));
        return tagsList;
    }

    public int getTicketIdFromUser() {
        int id = 0;
        System.out.println("Enter Ticket Id :: ");
        boolean updateLoop = true;
        while (updateLoop){
            try {
                //id = Integer.valueOf(console.nextLine());
                id = console.nextInt();
                updateLoop = false;
            }
            catch (InputMismatchException ie){
                System.out.println("Invalid Input!!!");
                updateLoop = false;
            }
        }
        return id;
    }

    public int getUserChoiceForUpdate() {

        int userChoice = 0;
        System.out.println("What do you want to update?");
        System.out.println("1. Agent Name");
        System.out.println("2. Tags");

        try {
            userChoice = Integer.valueOf(br.readLine());
        }
        catch(IOException ie){
            ie.printStackTrace();
        }

        return userChoice;
    }
}
