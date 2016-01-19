package factory;

import model.TicketModel;

/**
 * Created by root on 19/1/16.
 */
public class TicketModelFactory {
    public static TicketModel getInstance() {
        return new TicketModel();
    }
}
