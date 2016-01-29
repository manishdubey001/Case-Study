/*
package com.caseStudy;

import com.caseStudy.Factory.TicketOperations;
import com.caseStudy.Util.Util;

import java.util.*;

*/
/**
 * Created by root on 30/12/15.
 *//*

public class caseStudy {

	public Scanner scanner = new Scanner(System.in);
	TicketOperations ticketOperations = new TicketOperations();
	Util utilOps = new Util();

	// main method
	public static void main(String[] args) {
		HashMap<Integer, com.caseStudy.Ticket> ticketDetails = new HashMap();

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
					do {
						System.out.println("Do you wish to add more tickets? Y/N");
						String answer = scanner.next();
						if (answer.equals("y") || answer.equals("Y")) // cjm - Another option here is answer.toLowerCase().equals("y")
							createTicket();
						else if (answer.equals("n") || answer.equals("N"))
							createFlag = false; // cjm - This is never set back to true, so the next time through, it won't work as expected
					}
					while (createFlag);
					System.out.println("Ticket(s) created successfully");
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

	//create ticket
	public void createTicket() {
		System.out.println("Enter the ticket id");
		try {
			int id = scanner.nextInt();
			if (ticketDetails.containsKey(id))
				System.out.println("Duplicate ticket id " + id);
			else {
				System.out.println("Enter the subject");
				String subject = scanner.next();

				System.out.println("Enter the agent name : \t");
				String ag_name = scanner.next();

				HashSet set = tagsInfo();

				long createdTime = System.currentTimeMillis() / 1000L;

				long modifiedTime = createdTime;

//			ArrayList arrayList = new ArrayList();
//			arrayList.add(subject);
//			arrayList.add(ag_name);
//			arrayList.add(set);
//			arrayList.add(createdTime);
//			arrayList.add(modifiedTime);
//			ticketDetails.put(id, arrayList);

				Ticket ticketData = new Ticket();
				ticketData.setSubject(subject);
				ticketData.setAgent(ag_name);
				ticketData.setTags(set);
				ticketData.setCreated(createdTime);
				ticketData.setModified(modifiedTime);

				this.ticketDetails.put(id, ticketData);
				System.out.println("Ticket " + id + " has been added successfully \n");
			}
		}
		catch (InputMismatchException e) {
			System.out.println("Please enter a valid ticket Id");
		}
		finally {
			scanner.nextLine(); // cjm - Is this line necessary?
		}

	}

	//update ticket
	public void updateTicketOps() {
		System.out.println("Ticket id : " + ticketDetails);
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to update.");
		}
		else {
			System.out.println("Enter the ticket id");
			int id = scanner.nextInt();
			Ticket ticketObj = ticketDetails.get(id); // cjm - You don't validate that there really is a ticket as entered!
			System.out.println("Change subject?? y/n"); // cjm - note that changing t
			String selection = scanner.next();
			updateInfo(selection, ticketObj, "sub"); // cjm - Exception will propagate to the caller here if the ticket wasn't found

			System.out.println("Change agent assigned?? y/n");
			selection = scanner.next();
			updateInfo(selection, ticketObj, "ag");

			System.out.println("Change tags?? y/n");
			selection = scanner.next();
			updateInfo(selection, ticketObj, "tags");
		}
	}

	// update info of the ticket
	public void updateInfo(String selection, com.caseStudy.Ticket ticketObj, String field) {
		if (selection.equals("y") || selection.equals("Y")) {
			// cjm - I would break this up into three methods and not use the switch/case.
			switch (field) {
				case "sub": // cjm - Defining strings like this can be error prone and hard to maintain
					System.out.println("Enter the data");
					String data = scanner.next();
					ticketObj.setSubject(data);
					ticketObj.setModified(System.currentTimeMillis() / 1000L); // cjm - I would let the ticket class manage its modified date
					break;
				case "ag":
					System.out.println("Enter the data");
					data = scanner.next();
					ticketObj.setAgent(data);
					ticketObj.setModified(System.currentTimeMillis() / 1000L);
					break;
				case "tags":
					ticketObj.setTags(tagsInfo());
					ticketObj.setModified(System.currentTimeMillis() / 1000L);
					break;
				default:
					System.out.println("Nothing to update");
			}
		}
	}

	// get tags info to update
	public HashSet tagsInfo() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the tags (comma separated)");
		String tags = scanner.nextLine();
		HashSet set = new HashSet(Arrays.asList(tags.split("\\s*,\\s*"))); // cjm - Just using "," here is enough.
		return set;
	}

	// delete, show individual ticket and show list of ticket
	public void ticketOperation(String operation) {
		if (ticketDetails.isEmpty())
			System.out.println("No tickets available.");
		else {
			switch (operation) {
				case "delete": // cjm - same advice as before, break this into several methods
					int id = getTicketId();
					ticketDetails.remove(id);
					System.out.println("Ticket " + id + " has been deleted");
					break;

				case "show":
					this.printData(getTicketId());
					break;

				case "showList":
					Set hmKeySet = ticketDetails.keySet();
					for (Object key : hmKeySet)
						this.printData(key); // cjm - remember this was supposed to be sorted by modified date
					break;

				default:
					System.out.println("No operation to perform on ticket");
					break;
			}
		}
	}

	// get ticket id
	private int getTicketId() {
		System.out.println("Enter the ticket id");
		int id = scanner.nextInt();
		return id;
	}

	// fetch and print dat of the ticket
	public void printData(Object key) {
		Ticket secData = ticketDetails.get(key);
		System.out.println("---------------------------------------------");
		System.out.println("Id       : " + key);
		System.out.println("Subject  : " + secData.getSubject());
		System.out.println("Agent    : " + secData.getAgent());
		System.out.println("tags     : " + secData.getTags());
		System.out.println("Created  : " + secData.getCreated());
		System.out.println("Modified : " + secData.getModified());
	}

	// agent specific ticket search
	public void agentSearchTicketOps() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to display.");
		}
		else {
			System.out.println("Enter the agent name");
			String ag = scanner.next().trim();
			Set hmKeySet = ticketDetails.keySet();
			for (Object key : hmKeySet) {
				Ticket eachTicket = ticketDetails.get(key);
				String agent = eachTicket.getAgent();
				if (agent.equals(ag))
					this.printData(key); // cjm - also should be sorted by modified date
				else
					System.out.println("Tickets for agent " + ag + " not present");
			}
		}
	}

	// count of tickets assigned to each agent
	public void ticketCountOps() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to display.");
		}
		else {
			HashMap agTicketCount = new HashMap();
			Set hmKeySet = ticketDetails.keySet();
			for (Object key : hmKeySet) {
				Ticket eachTicket = ticketDetails.get(key);
				String ag = eachTicket.getAgent();
				if (agTicketCount.containsKey(ag)) {
					int value = (int) agTicketCount.get(ag);
					value++;
					agTicketCount.replace(ag, value);
				}
				else
					agTicketCount.put(ag, 1);
			}
			Set keys = agTicketCount.keySet();
			System.out.println("Agent -> Count"); // cjm - this is supposed to be sorted by agent name
			for (Object key : keys) {
				System.out.println(key + " -> " + agTicketCount.get(key));
			}
		}
	}

	// tag specific ticket search
	public void tagSearchOps() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to display.");
		}
		else {
			System.out.println("Enter the tag");
			String tag = scanner.next();
			Set hmKeySet = ticketDetails.keySet();
			for (Object key : hmKeySet) {
				Ticket eachTicket = ticketDetails.get(key);
				Set tags = eachTicket.getTags();
				if (tags.contains(tag))
					printData(key); // cjm - modified date sort
				else
					System.out.println("Tickets for tag " + tag + " not present");
			}
		}
	}

	public static void main(String[] args) {

		String proceed;
		caseStudy caseStudy = new caseStudy();
		do {
			caseStudy.menuPopUp();
			caseStudy.selectCase();
			System.out.println("\nContinue with other operations??  y/n");
			proceed = caseStudy.scanner.next();
		}
		while (proceed.equals("y"));
	}

	// cjm - The three functions below are not used
	public void deleteTicketOps() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to delete.");
		}
		else {
			System.out.println("Enter the ticket id");
			int id = scanner.nextInt();
			ticketDetails.remove(id);
			System.out.println("Ticket " + id + " has deleted");
		}
	}

	public void showTicketOps() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to display.");
		}
		else {
			System.out.println("Enter the ticket id");
			int id = scanner.nextInt();
			System.out.println("Ticket Details");
			this.printData(id);
		}
	}

	public void showTicketList() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to display.");
		}
		else {
			System.out.println("Ticket Details");
			Set hmKeySet = ticketDetails.keySet();
			for (Object key : hmKeySet) {
				this.printData(key);
			}
		}
	}
}
*/
