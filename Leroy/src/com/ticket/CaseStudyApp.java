package com.ticket;
import com.ticket.util.ConsoleScan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CaseStudyApp {
    public static void main(String[] args) {
        ConsoleScan consoleScan = new ConsoleScan();
        consoleScan.getAllCommands();
        int count = 0;
        int MAX_TRY = 3;

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        do {
            int userInput;
            try {
                do {
                    System.out.println(ConsoleScan.ACT_CHOOSE);
                    userInput = Integer.parseInt(bf.readLine());
                    switch (userInput) {
                        case 1:
                            consoleScan.scanCreate();
                            break;

                        case 2:
                            consoleScan.scanUpdate();
                            break;

                        case 3:
                            consoleScan.scanRemoveTicket();
                            break;

                        case 4:
                            consoleScan.scanTicketId();
                            break;

                        case 5:
                            consoleScan.scanAll();
                            break;

                        case 6:
                            consoleScan.scanAgent();
                            break;

                        case 7:
                                consoleScan.scanAgentsTicketsCount();
                            break;


                        case 8:
                                consoleScan.scanTags();
                            break;
                    }
                }
                while (userInput != 9);
                System.out.println(ConsoleScan.ACT_THANKYOU);
                bf.close();
                System.exit(0);
            } catch (NumberFormatException Ne) {
                System.out.print(ConsoleScan.ACT_INVALID+ Ne);
                if(++count == MAX_TRY)
                    System.exit(1);

            } catch (IOException e) {
                System.out.print(ConsoleScan.ACT_ERROR + e);
                if (++count == MAX_TRY)
                    System.exit(2);
            }
        }while (MAX_TRY <4);
    }
}
