package factory;

import model.Ticket;

import java.util.List;

/**
 * Created by root on 19/1/16.
 */
public class TicketFactory {
    public static Ticket newInstance() {
        return new Ticket();
    }

    public static Ticket newInstance(int id, String subject, String agentName, List<String> tagsList) {
        return new Ticket(id, subject, agentName, tagsList);
    }
}
