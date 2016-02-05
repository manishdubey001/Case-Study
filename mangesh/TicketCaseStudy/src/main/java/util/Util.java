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

    public static void displayMenuList() {
        String[] menuArray = {"1. Create Ticket.", "2. Update Ticket By Id.", "3. Delete Ticket By Id.", "4. Select Single ticket by Id.", "5. Select all Tickets.",
                "6. Select Tickets assigned to specific agent.", "7. Ticket count grouped by agent name.", "8. Search all tickets by specific tag.",
                "9. Total Number of tickets in the system", "10. Oldest ticket", "11.Tickets older than a certain number of days", "12.Tags in use/# of tickets with a tag",
                "13. Exit", "What do you want to perform? Please enter your choice :: "};

        for (String menu : menuArray){ System.out.println(menu); }
    }
}
