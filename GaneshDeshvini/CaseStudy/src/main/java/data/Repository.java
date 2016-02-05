package data;

import model.TicketModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 7/1/16.
 */
public class Repository {
    // use a Map for the type of 'ticketData'; that way you don't depend on it being a HashMap
    // UPDATE : Used Map
    public Map<Integer, TicketModel> ticketData = new HashMap<Integer, TicketModel>();

    private static Repository repository = new Repository();

    private Repository() {
    }

    // The singleton pattern can make it hard to test. We will cover other approaches later.
    // UPDATE : Need to discuss with chat for other approaches
    public static Repository getInstance() {
        return repository;
    }
}
