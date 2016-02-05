import helpers.FileIOUtil;
import model.TicketModel;
import service.TicketService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by root on 28/1/16.
 */
public class SerializationExample {
    static int limit = 10;

    public static void main(String[] args) {
//        serializeSingleTicketExample();
//        deserializeSingleTicketExample();
        serializeMultipleTicketExample();
        deserializeMultipleTicketExample();
    }

    /**
     * serialize single ticket
     */
    public static void serializeSingleTicketExample() {
        try {

            int id = 1;
            String subject = "sub";
            String agent = "a1";
            String tags = "a, b";
            TicketService ticketService = TicketService.newInstance();
            if (ticketService.createTicket(id, subject, agent, tags)) {
                TicketModel ticketModel = ticketService.getTicketDetail(id);
                FileIOUtil.serializeTicket(ticketModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * de-serialize single ticket
     */
    public static void deserializeSingleTicketExample() {
        try {

            int id = 1;
            String subject = "sub";
            String agent = "a1";
            HashSet<String> hsTags = new HashSet<>(Arrays.asList("a", "b"));
            TicketModel ticketModel = FileIOUtil.deserializeTicket();
            if (ticketModel.getId() == id && ticketModel.getSubject().equals(subject) && ticketModel.getAgentName().equals(agent) && ticketModel.getTags().equals(hsTags)) {
                System.out.println("de-serialization successfull");
            } else
                System.out.println("de-serialization un-successfull");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * serialize single ticket
     */
    public static void serializeMultipleTicketExample() {
        try {
            int id;
            String subject, agent, tags;
            List<TicketModel> ticketModelList = new ArrayList<TicketModel>();
            for (int i = 1; i <= limit; i++) {
                id = i;
                subject = "sub" + i;
                agent = "a" + i;
                tags = "a" + i;
                TicketService ticketService = TicketService.newInstance();
                if (ticketService.createTicket(id, subject, agent, tags)) {
                    TicketModel ticketModel = ticketService.getTicketDetail(id);
                    ticketModelList.add(ticketModel);
                }
            }
            FileIOUtil.serializeTickets(ticketModelList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * de-serialize single ticket
     */
    public static void deserializeMultipleTicketExample() {
        try {

            int id;
            String subject, agent;
            List<TicketModel> ticketModelList = new ArrayList<TicketModel>();
            ticketModelList = FileIOUtil.deserializeTickets();
            int i = 1;
            for (TicketModel ticketModel : ticketModelList) {
                System.out.println(ticketModel.toString());
                id = i;
                subject = "sub" + i;
                agent = "a" + i;
                HashSet<String> hsTags = new HashSet<>(Arrays.asList("a" + i));
                if (ticketModel.getId() == id && ticketModel.getSubject().equals(subject) && ticketModel.getAgentName().equals(agent) && ticketModel.getTags().equals(hsTags)) {
                    System.out.println("de-serialization successfull");
                } else {

                    System.out.println("de-serialization un-successfull " + i);
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
