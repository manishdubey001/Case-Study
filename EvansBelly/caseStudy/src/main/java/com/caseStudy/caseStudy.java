package com.caseStudy;

import com.caseStudy.Factory.TicketOperations;
import com.caseStudy.Util.Util;

import java.util.*;

/**
 * Created by root on 30/12/15.
 */
public class caseStudy {

	public Scanner scanner = new Scanner(System.in);
	TicketOperations ticketOperations = new TicketOperations();
	Util utilOps = new Util();

	// main method
	public static void main(String[] args) {

		String proceed;
		caseStudy caseStudy = new caseStudy();
		do {
			caseStudy.selectCase();
			System.out.println("\nContinue with other operations??  y/n");
			proceed = caseStudy.scanner.next();
		}
		while (proceed.toLowerCase().equals("y"));
	}

	// select operations
	public void selectCase() {

		try {
			// menu section for displaying operations
			utilOps.menuPopUp();
			// select cases for each operation.
			switch (scanner.nextInt()) {
				case 1:
					ticketOperations.createTicket();
					break;
				case 2:
					ticketOperations.updateTicketOps();
					break;
				case 3:
					ticketOperations.deleteTicketOps();
					break;
				case 4:
					ticketOperations.showTicketOps();
					break;
				case 5:
					ticketOperations.showTicketList();
					break;
				case 6:
					ticketOperations.agentSearchTicketOps();
					break;
				case 7:
					ticketOperations.ticketCountOps();
					break;
				case 8:
					ticketOperations.tagSearchOps();
					break;
				case 9:
					System.exit(0);
					break;
				default:
					System.out.println("The input option seems to be invalid. Please enter a valid case.");
					break;
			}
		}
		catch (Exception e) {
			System.out.println("No input or wrong input selected");
		}
	}
}
