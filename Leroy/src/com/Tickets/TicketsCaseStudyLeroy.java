package com.Tickets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TicketsCaseStudyLeroy {
    public static void main(String[] args) {
        Sout sout = new Sout();
        sout.getAllCommands();
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
                            sout.soutCreate();
                            break;

                        case 2:
                            sout.soutUpdate();
                            break;

                        case 3:
                            sout.soutRemoveTicket();;
                            break;

                        case 4:
                            sout.soutGetTicketById();
                            break;

                        case 5:
                            sout.ticketServiceComponent.getAllTickets();
                            break;

                        case 6:
                            sout.soutTicketsByAgent();
                            break;

                        case 7:
                                sout.ticketServiceComponent.getTicketsGroupByAgent();
                            break;


                        case 8:
                                sout.soutGetAllTicketsByTag();
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
