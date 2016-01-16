package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TicketsCaseStudyLeroy {
    public static void main(String[] args) {
        Sout.getAllCommands();
        TicketComponent ticketComponent = new TicketComponent();
        Ticket t  = new Ticket();
        Map<Integer, Ticket> thm = new HashMap<Integer, Ticket>();
        int count = 0;
        int MAXTRY = 3;
        while(true) {
            int i;
            try {
                do {
                    System.out.println(Sout.ACT_CHOOSE);
                    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                    i = Integer.parseInt(bf.readLine());
                    switch (i) {
                        case 1:
                            ticketComponent.createTicket(t, thm);
                            break;

                        case 2:
                            if(!thm.isEmpty())
                                ticketComponent.updateTicket(thm);
                            else
                                System.out.println(Sout.ACT_NOT_FOUND);
                            break;

                        case 3:
                            if(!thm.isEmpty())
                                ticketComponent.removeTicketById(thm);
                            else
                                System.out.println(Sout.ACT_NOT_FOUND);
                            break;

                        case 4:
                            if(!thm.isEmpty())
                                ticketComponent.getAllTicketsById(thm);
                            else
                                System.out.println(Sout.ACT_NOT_FOUND);
                            break;

                        case 5:
                            if(!thm.isEmpty())
                                ticketComponent.getAllTickets(thm);
                            else
                                System.out.println(Sout.ACT_NOT_FOUND);
                            break;

                        case 6:
                            if(!thm.isEmpty())
                                ticketComponent.getTicketsByAgentName(thm);
                            else
                                System.out.println(Sout.ACT_NOT_FOUND);
                            break;

                        case 7:
                            if(!thm.isEmpty())
                                ticketComponent.getTicketsGroupByAgent(thm);
                            else
                                System.out.println(Sout.ACT_NOT_FOUND);
                            break;


                        case 8:
                            if(!thm.isEmpty())
                                ticketComponent.getAllTicketsByTag(thm);
                            else
                                System.out.println(Sout.ACT_NOT_FOUND);
                            break;
                    }
                }
                while (i != 9);
                System.out.println(Sout.ACT_THANKYOU);
                System.exit(0);
            } catch (NumberFormatException Ne) {
                System.out.print(Sout.ACT_INVALID+ Ne);
                if(++count == MAXTRY)
                    System.exit(1);

            } catch (IOException e) {
                System.out.print(Sout.ACT_ERROR + e);
                if (++count == MAXTRY)
                    System.exit(2);
            }
        }

    }
}
