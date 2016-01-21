package data;

import model.TicketModel;

import java.util.HashMap;

/**
 * Created by root on 7/1/16.
 */
public class Repository {
    // use a Map for the type of 'ticketData'; that way you don't depend on it being a HashMap
    public HashMap<Integer, TicketModel> ticketData = new HashMap<Integer, TicketModel>();

    private static Repository repository = new Repository();

    private Repository() {
    }

    // The singleton pattern can make it hard to test. We will cover other approaches later.
    public static Repository getInstance() {
        return repository;
    }

    /**
     * ToDo logic for implementing indexing
     * public HashMap<String, HashSet<Integer>> indexAgentName = new HashMap<String, HashSet<Integer>>();
     * public HashMap<String, HashSet<Integer>> indexTags      = new HashMap<String, HashSet<Integer>>();
     */
}
