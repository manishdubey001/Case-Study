package CustomException;

/**
 * Created by root on 4/2/16.
 */
public class DuplicateIdException extends Exception {
    public DuplicateIdException(String message) {
        super(message);
    }
}
