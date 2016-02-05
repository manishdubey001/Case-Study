package service;

import CustomException.InvalidParamsException;
import helpers.ConsoleReader;
import helpers.DateTimeUtil;
import helpers.Util;
import model.TicketModel;
import operations.TicketOperations;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by root on 29/1/16.
 */
public class ReportService {
    TicketOperations ticketOperations = new TicketOperations();
    TicketService ticketService = new TicketService();

    public static ReportService newInstance() {
        return new ReportService();
    }

    /**
     * total number of tickets in the system
     */
    public void totalNumberOfTickets() {
        System.out.println("Total number of tickets : " + ticketOperations.getTotalNumberOfTickets());
    }

    /**
     * oldest ticket in the system
     */
    public void oldestTicket() {
        TicketModel ticketModel = ticketOperations.getOldestTicket();
        System.out.println("--oldest ticket in the system--");
        ticketService.printTicketDetails(ticketModel);
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
            List<TicketModel> ticketModelList = getTicketsOlderThanNDays(noOfDays);
            if (Util.isCollectionValid(ticketModelList)) {
                ticketModelList.forEach(ticketModel -> ticketService.printTicketDetails(ticketModel));
            } else {
                System.out.println("No data found!!!");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input..please try again\n");
        } catch (InvalidParamsException ipe) {
            System.out.println("Invalid parameters!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<TicketModel> getTicketsOlderThanNDays(int noOfDays) throws InvalidParamsException {
        if (noOfDays <= 0) {
            throw new InvalidParamsException("Invalid params");
        }
        return ticketOperations.getAllTicketFromTimestamp(DateTimeUtil.minusDaysGetTimestamp(LocalDateTime.now(), noOfDays));
    }

    /**
     * tags in used with ticket count
     */
    public void tagInUsedWithTicketCount() {
        Map<String, Integer> s = getAllTagWithTicketCount();
        if (Util.isMapValid(s)) {
            System.out.println("---------------------------------");
            System.out.println("|Tag Name\t|Count|");
            s.forEach((tagName, count) -> System.out.println("| " + tagName + " | " + count + " |"));
            System.out.println("---------------------------------");
        }
    }

    Map<String, Integer> getAllTagWithTicketCount() {
        return ticketOperations.tagsInUsedWithCount();
    }
}
