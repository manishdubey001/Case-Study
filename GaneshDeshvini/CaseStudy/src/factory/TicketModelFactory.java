package factory;

import model.TicketModel;

import java.util.Set;

/**
 * Created by root on 14/1/16.
 */
public class TicketModelFactory {
    public static TicketModel newInstance(int id, String subject, String agentName, Set<String> tags) {
        return new TicketModel(id, subject, agentName, tags);
    }
}
