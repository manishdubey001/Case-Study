//Below comment for folder structure, class naming
// 1:-Unit test cases and source code should be in different folder. (For reference see maven default folder structure core java project)
// 2:-Group classes into different package so the class purpose and searching is more clear rather than put it all in single package.
// 3:-In class naming don't append Class eg. "TicketFactoryClass" because each file it self represent this is a class
// 4:-Don't put your name also in any class name. This is bad practice.
// 5: Also put method level doc comment for what purpose you defined method

//For best practice package name should be lower case e.g. java,lang,sql and must be singular not in plural form.
package com.Tickets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//This is unused import statement. Make sure you not import any class which is not used in class and also avoid wild card import
import java.util.*;

public class TicketsCaseStudyLeroy {
    public static void main(String[] args) {
        Sout sout = new Sout();
        sout.getAllCommands();
        int count = 0;
        //Two word constant should be separated by underscore.
        int MAXTRY = 3;

        //Here we can use do while instead of while(true)
        while(true) {
            //Give proper name because this variable not used for loop iteration, this variable is used for user input purpose.If you give the
            // proper name then readability and debugging will be increase.
            int i;
            try {
                do {
                    System.out.println(Sout.ACT_CHOOSE);
                    //Each iteration new Buffered reader object is created and also this is not close any where . BufferedReader and InputStreamReader
                    // both are auto closable you can user it with Try-With-Resources
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
                            //Unused semicolon
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
