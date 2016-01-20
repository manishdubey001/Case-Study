package factory;

import service.TicketService;

/**
 * Created by root on 14/1/16.
 */
// When factories are simple like this, it can make sense to put them as members of the
// class they are a factory for.
public class TicketServiceFactory {
    public static TicketService getInstance() {
        return new TicketService();
    }
}
