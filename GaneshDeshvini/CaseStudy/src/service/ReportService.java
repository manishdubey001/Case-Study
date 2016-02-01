package service;

import helpers.ConsoleReader;
import helpers.DateTimeUtil;
import helpers.Util;
import model.TicketModel;
import operations.TicketOperations;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by root on 29/1/16.
 */
public class ReportService {
    public static ReportService newInstance() {
        return new ReportService();
    }

    /**
     * total number of tickets in the system
     */
    public void totalNumberOfTickets() {
        System.out.println("Total number of tickets : " + TicketOperations.newInstance().getTotalNumberOfTickets());
    }

    /**
     * oldest ticket in the system
     */
    public void oldestTicket() {
        TicketModel ticketModel = TicketOperations.newInstance().getOldestTicket();
        System.out.println("--oldest ticket in the system--");
        TicketService.newInstance().printTicketDetails(ticketModel);
        System.out.println("-------------------------------");
    }


    /**
     * ticket older than certain days
     */
    public void ticketsOlderThanCertainDays() {
        try {
            Scanner scanner = ConsoleReader.newInstance();
            System.out.println("Enter number of days");
            int noOfDays = scanner.nextInt();
            if (noOfDays > 0) {
                processTicketsOlderThanNDays(noOfDays - 1);
            } else
                System.out.println("days should be greater than 0..please try again\n");
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input..please try again\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void processTicketsOlderThanNDays(int noOfDays) {
        long startTimeStamp = DateTimeUtil.minusDaysGetTimestamp(LocalDateTime.now(), noOfDays);
        long endTimeStamp = DateTimeUtil.getTimeStampWithHMS(LocalDateTime.now(), 23, 59, 59);
        List<TicketModel> ticketModelList = TicketOperations.newInstance().findAll(startTimeStamp, endTimeStamp);
        if (Util.isCollectionValid(ticketModelList)) {
            TicketService ticketService = TicketService.newInstance();
            ticketModelList.forEach(ticketModel -> ticketService.printTicketDetails(ticketModel));
        } else {
            System.out.println("No data found!!!");
        }
    }

    /**
     * tags in used with ticket count
     */
    public void tagInUsedWithTicketCount() {
        Map<String, Integer> s = TicketOperations.newInstance().tagsInUsedWithCount();
        if (Util.isMapValid(s)) {
            System.out.println("---------------------------------");
            System.out.println("|Tag Name\t|Count|");
            s.forEach((tagName, count) -> System.out.println("| " + tagName + " | " + count + " |"));
            System.out.println("---------------------------------");
        }
    }
}
