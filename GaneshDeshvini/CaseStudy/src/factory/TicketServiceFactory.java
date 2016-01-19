package factory;

import service.TicketService;

/**
 * Created by root on 14/1/16.
 */
public class TicketServiceFactory {
    public static TicketService getInstance() {
        return new TicketService();
    }
}
