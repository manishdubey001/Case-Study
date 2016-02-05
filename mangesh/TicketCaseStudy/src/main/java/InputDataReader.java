import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputDataReader {
    public static int readInteger(){
        int userInput = 0;
        // Lokesh: Next level: use try-with-resources, rather than instance variable and local references to BufferedReader
        try{
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            userInput = Integer.parseInt(consoleReader.readLine());
            //consoleReader.close();
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
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            userInput = consoleReader.readLine();
            //consoleReader.close();
        }catch(IOException ie) {
            ie.printStackTrace();
        }
        // Lokesh: Next level: do not allow user to go ahead with null or empty value when expected something other than empty value.
        // Here you may return null or empty in some case.
        return userInput;
    }
}
