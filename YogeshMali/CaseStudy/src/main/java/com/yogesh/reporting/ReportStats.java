package com.yogesh.reporting;


import com.yogesh.ConsoleIO;
import com.yogesh.model.Ticket;
import com.yogesh.service.ReportingService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Created by root on 29/1/16.
 */
public class ReportStats {

    ReportingService rService = new ReportingService();

    ReportStats() {
        dislayList();
    }

    private void dislayList() {
        ConsoleIO.showReportingMenu();
        ConsoleIO.showMsg("Select Operation ");
        int menuId = ConsoleIO.getIntvalue();
        this.menu(menuId);
        msg();
    }


    public static void main(String[] args) {

        ReportStats rStat = new ReportStats();
    }

    private void menu(int menuId) {
        switch (menuId) {
            case 1:
                showAllTicket();
                break;
            case 2:
                totalTicket();
                break;
            case 3:
                oldestTicket();
                break;
            case 4:
                olderTicketFromDays();
                break;
            case 5:
                TagWithTicketCounts();
                break;
            default:
                ;
                break;
        }
    }

    /**
     * this method counts  Total no of tickets are in the system
     */

    private void totalTicket() {
        ConsoleIO.showMsg("No Of Ticket in the System --> " + rService.totalTicket());
    }

    /**
     * this method is for The oldest ticket in the system
     */


    private void oldestTicket() {
        ConsoleIO.showMsg("Oldest Ticket in the System --> ");
        ConsoleIO.showTicket(rService.oldestTicket());
    }

    /**
     * this method is for Tickets older than a certain number of days
     */

    private void olderTicketFromDays() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        ConsoleIO.showMsg("Tickets older than a certain number of days : Enter no of days");
        int noofDays = scanner.nextInt();
        for (Ticket ticket : rService.olderTicketFromDays(noofDays)) {
            ConsoleIO.showTicket(ticket);
        }
    }

    /**
     * this methodTags in use/# of tickets  with a tag
     */
    private void TagWithTicketCounts() {
        HashMap<String, Integer> hmtTotalTags = rService.tagWithTicketCounts();
        ConsoleIO.showMsg("Tag -- > Ticket Count");
        for (Map.Entry<String, Integer> entry : hmtTotalTags.entrySet()) {
            String tag = entry.getKey();
            Integer count = entry.getValue();
            ConsoleIO.showMsg(tag + " => " + count);
        }
    }

    /**
     * this method Show all Ticket
     */
    private void showAllTicket() {

        for (Ticket ticket : rService.showAllTicket()) {
            ConsoleIO.showTicket(ticket);
        }
    }

    /**
     * this method handle continue process or exit
     */
    public void msg() {

        ConsoleIO.showMsg("Please Press 1 : Continue   ");
        int msgId = ConsoleIO.getIntvalue();
        if (msgId == 1) {
            dislayList();

        } else {
            ConsoleIO.showMsg("thank you");
        }
    }


}
