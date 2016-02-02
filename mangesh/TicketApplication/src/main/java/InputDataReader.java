import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by root on 1/2/16.
 */
public class InputDataReader {
    public static BufferedReader consoleReader = null;

    public static int readInteger(){
        int userInput = 0;

        try{
            BufferedReader consoleReader = getInputReader();
            userInput = Integer.parseInt(consoleReader.readLine());
        }
        catch (NumberFormatException ne) {
            System.out.println("Invalid integer Number!!!");
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
        return userInput;
    }

    public static String readString(){
        String userInput = null;
        try{
            BufferedReader consoleReader = InputDataReader.getInputReader();
            userInput = consoleReader.readLine();
        }catch(IOException ie) {
            ie.printStackTrace();
        }

        return userInput;
    }

    public static BufferedReader getInputReader(){
        if(consoleReader == null)
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
        return consoleReader;
    }
}
