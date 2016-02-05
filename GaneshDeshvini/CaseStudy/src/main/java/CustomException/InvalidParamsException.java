package CustomException;

/**
 * Created by root on 4/2/16.
 */
public class InvalidParamsException extends Exception{
    public InvalidParamsException(String message) {
        super(message);
    }
}
