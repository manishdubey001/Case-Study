package com.yogesh.service;

import com.yogesh.model.Ticket;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

        //Ganesh D: logic is good, you can also see how to do with streams, it has inbuild functions to check minimum, maximum, etc.,

        // update :: used Stream
        return hmTicketList.values().stream().max((objTicket1, objTicket2) -> objTicket2.getCreated().compareTo(objTicket1.getCreated())).get();

    }

    public List<Ticket> olderTicketFromDays(int noofDays) {

        return this.hmTicketList.values().stream()
                .filter(tickets -> tickets.getCreated().isBefore(LocalDateTime.now().minusDays(noofDays))).collect(Collectors.toList());

    }

    public HashMap tagWithTicketCounts() {
        HashMap<String, Integer> hmtTotalTags = new HashMap();

        for (Ticket ticket : this.hmTicketList.values()) {
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
