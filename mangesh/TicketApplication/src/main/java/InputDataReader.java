import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by root on 1/2/16.
 */
public class InputDataReader {
    // Lokesh: This need not required to be public.
    public static BufferedReader consoleReader = null;

    public static int readInteger(){
        int userInput = 0;
        // Lokesh: Next level: use try-with-resources, rather than instance variable and local references to BufferedReader
        try{
            // Lokesh: think wisely, you are calling a function to get the member of same class. Can't it be avoided?
            // Why it is required to have new Local reference "consoleReader"?
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
        // Lokesh: Next level: do not allow user to go ahead with null or empty value when expected something other than empty value. Here you may return null or empty in some case.
        return userInput;
    }

    // Lokesh: this method need not required to be public.
    public static BufferedReader getInputReader(){
        if(consoleReader == null) {
            System.out.println("Returning new object for reader");
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
        }
        return consoleReader;
    }
}
