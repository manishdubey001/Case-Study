package com.example.ticketcrud;

/**
 * Created by root on 4/1/16.
 */
public class App {
    /**
     * Main method to run application
     * @param args
     */
    public static void main(String[] args) {
        new AppManager().startTicketApp();
    }


//    I have finished with the first 4 of these and I’m putting up my comments now. Here are some suggested paths to extend this case study. I will put my own solution up for review sometime this week.
//
//    Persist the tickets. Define a serialization format for tickets and (manually parse, not with libraries):
//    Save a ticket from a file
//    Load a ticket from a file
//    Save a list of tickets to a file
//    Load a list of tickets from a file
//    Report statistics on:
//    How many tickets are in the system
//    The oldest ticket in the system
//    Tickets older than a certain number of days
//    Tags in use/# of tickets with a tag
//    Unit tests and code coverage analysis
//            (Later!) Use Executor to perform these operations in a threaded environment, and demonstrate proper synchronization of the objects involved.
}
