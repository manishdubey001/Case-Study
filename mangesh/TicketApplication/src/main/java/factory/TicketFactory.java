package factory;

import model.Ticket;

import java.util.HashSet;

public class TicketFactory {
    public static Ticket newInstanceWithData(int id, String subject, String agentName, HashSet<String> tagSet)
    {
        return new Ticket(id, subject,agentName,tagSet);
    }
}
