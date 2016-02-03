package util;
import java.io.*;
import java.util.Properties;

public class Util {
    public static void writeTicketId(int id){
        try {
            File fileName = createFile("ticket.properties");
            Properties prop = new Properties();
            prop.setProperty("id",String.valueOf(id+1));
            FileWriter writer = new FileWriter(fileName);
            prop.store(writer,"TicketIdCount");           writer.close();
        }catch (FileNotFoundException fe){
            fe.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int getTicketId(){
        int id = 0;
        try {
            File fileName = createFile("ticket.properties");

            if(fileName.length()>0) {
                FileReader reader = new FileReader(fileName);
                Properties prop = new Properties();
                prop.load(reader);
                id = Integer.valueOf(prop.getProperty("id"));
                reader.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static File createFile(String fileName){
        File newFile = null;
        try {
            newFile = new File("src/main/data/" + fileName);
            newFile.createNewFile();
        }catch(IOException ie){
            ie.printStackTrace();
        }
        return newFile;
    }
}
