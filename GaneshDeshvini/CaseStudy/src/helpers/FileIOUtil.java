package helpers;

import model.TicketModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 28/1/16.
 */
public class FileIOUtil {

    /**
     * get file name
     *
     * @return String
     */
    static String getFileNameWithLocation() {
        return "/tmp/ticketData.ser";
    }

    /**
     * serialize single ticket
     *
     * @param ticketModel
     * @return boolean
     * @throws IOException
     */
    public static boolean serializeTicket(TicketModel ticketModel) throws IOException {
        String fileName = getFileNameWithLocation();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(ticketModel);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * de-serialize single ticket
     *
     * @return TicketModel
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static TicketModel deserializeTicket() throws IOException, ClassNotFoundException {
        String fileName = getFileNameWithLocation();
        TicketModel ticketModel;
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ticketModel = (TicketModel) objectInputStream.readObject();
            objectInputStream.close();
            return ticketModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new TicketModel();
    }

    /**
     * serialize multiple/list of tickets
     *
     * @param ticketModelArrayList
     * @return boolean
     * @throws IOException
     */
    public static boolean serializeTickets(List<TicketModel> ticketModelArrayList) throws IOException {
        String fileName = getFileNameWithLocation();
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            for (TicketModel ticketModel : ticketModelArrayList) {
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(ticketModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
        return false;
    }

    /**
     * de-serialize multiple/list of tickets
     *
     * @return List<TicketModel>
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<TicketModel> deserializeTickets() throws IOException, ClassNotFoundException {
        String fileName = getFileNameWithLocation();
        TicketModel ticketModel;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        ArrayList<TicketModel> ticketModelArrayList = new ArrayList<TicketModel>();
        try {
            fileInputStream = new FileInputStream(fileName);
            while (fileInputStream.available() > 0) {
                objectInputStream = new ObjectInputStream(fileInputStream);
                ticketModel = (TicketModel) objectInputStream.readObject();
                ticketModelArrayList.add(ticketModel);
            }
        } catch (EOFException end) {
//            end.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                fileInputStream.close();
            }
        }
        return ticketModelArrayList;
    }
}
