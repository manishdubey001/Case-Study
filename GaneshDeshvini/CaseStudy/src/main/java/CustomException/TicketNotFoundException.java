package CustomException;

/**
 * Created by root on 4/2/16.
 */
public class TicketNotFoundException extends Exception {
    public TicketNotFoundException(String message) {
        super(message);
    }
}
