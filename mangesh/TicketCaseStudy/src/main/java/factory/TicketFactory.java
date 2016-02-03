package factory;

import model.Ticket;
import util.Util;

import java.util.HashSet;

public class TicketFactory {
    public static Ticket newInstanceWithData(String subject, String agentName, HashSet<String> tagSet)
    {
        int id = getAutoId();
        return new Ticket(id, subject,agentName,tagSet);
    }

    public static int getAutoId(){
        return Util.getTicketId();
    }
}
