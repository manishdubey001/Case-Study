package factory;

import model.Ticket;

import java.util.List;

/**
 * Created by root on 19/1/16.
 */
public class TicketFactory {

    // If you use same name for all methods to return Ticket instance, you wouldn't be able to have two one for only subject and other for agent name (both are String)
    // Rather use different names like instanceWithNoData, instanceWithAllData, instanceWithAgentName, instanceWithSubject etc.
    public static Ticket newInstance() {
        return new Ticket();
    }

    public static Ticket newInstance(int id, String subject, String agentName, List<String> tagsList) {
        // it's good to create a copy of Collection object passed by caller. It may happen that caller changes that object after you receive it, It will change your internal data as well.
        return new Ticket(id, subject, agentName, tagsList);
    }
}
