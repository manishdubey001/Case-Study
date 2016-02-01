package com.yogesh.service;

import com.yogesh.model.Ticket;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by root on 29/1/16.
 */
public class ReportingService {

    public ReportingService() {
        createBulkTickets();
    }

    public HashMap<Integer, Ticket> hmTicketList = new HashMap();

    public void createBulkTickets() {
        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            hmTicketList.put(i, new Ticket(i, "SubJect" + i, "Agent" + i, (new HashSet(Arrays.asList("Tag" + random.nextInt(5), "Tag" + random.nextInt(5), "Tag" + random.nextInt(5))))));
        }
    }

    /**
     * Find Total no of ticket in System
     *
     * @return
     */
    public int totalTicket() {

        return this.hmTicketList.size();
    }

    public Ticket oldestTicket() {

        int minid = 0;
        LocalDateTime minDate = LocalDateTime.now().minusDays(1);
        ArrayList<Ticket> arrTicketList = new ArrayList<>(this.hmTicketList.values());

        //Ganesh D: logic is good, you can also see how to do with streams, it has inbuild functions to check minimum, maximum, etc.,
        for (Ticket ticket : arrTicketList) {
            if (ticket.getCreated().isBefore(minDate)) {
                minid = ticket.getId();
                minDate = ticket.getCreated();
            }
        }
        return hmTicketList.get(minid);
    }

    public ArrayList<Ticket> olderTicketFromDays(int noofDays) {

        ArrayList<Ticket> listofTicket = new ArrayList<>();
        ArrayList<Ticket> arrTicketList = new ArrayList<>(this.hmTicketList.values());
        for (Ticket ticket : arrTicketList) {
            if (ticket.getCreated().isBefore(LocalDateTime.now().minusDays(noofDays))) {
                listofTicket.add(ticket);
            }
        }
        return listofTicket;

    }

    public HashMap tagWithTicketCounts() {
        HashMap<String, Integer> hmtTotalTags = new HashMap();

        ArrayList<Ticket> arrTicketList = new ArrayList<>(this.hmTicketList.values());

        for (Ticket ticket : arrTicketList) {
            for (String tag : ticket.getTags()) {
                if (!hmtTotalTags.containsKey(tag)) {
                    hmtTotalTags.put(tag, 1);
                } else {
                    int tcount = hmtTotalTags.get(tag) + 1;
                    hmtTotalTags.put(tag, tcount);
                }
            }
        }

        return hmtTotalTags;

    }

    public ArrayList<Ticket> showAllTicket() {

        return new ArrayList<>(this.hmTicketList.values());
    }
}
