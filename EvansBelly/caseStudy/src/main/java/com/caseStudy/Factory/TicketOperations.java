package com.caseStudy.Factory;

import com.caseStudy.Model.Ticket;
import com.caseStudy.Service.TicketService;
import com.caseStudy.Util.Util;

import java.util.*;

/**
 * Created by root on 15/1/16.
 */
public class TicketOperations {

	public HashMap<Integer, Ticket> ticketDetails = new HashMap();
	public Scanner scanner = new Scanner(System.in);
	public boolean createFlag = true;
	TicketService ticketService = new TicketService();
	Collection<Ticket> valueSet = ticketDetails.values();
	Util utilOps = new Util();

	//create ticket
	public void createTicket() {
		int id = this.dataInsertion();
		if (id != 0) {
			do {
				System.out.println("Do you wish to add more tickets? Y/N");
				String answer = scanner.next();
				if (answer.toLowerCase().equals("y")) {
					id = this.dataInsertion();
				}
				else if (answer.toLowerCase().equals("n"))
					createFlag = false;
			}
			while (createFlag);
			System.out.println("Ticket(s) created successfully");
			createFlag = true;
		}
	}

	// ticket data insertion
	public int dataInsertion() {
		System.out.println("Enter the ticket id");
		int validId = 0;
		int id = scanner.nextInt();
		try {
			if (ticketDetails.containsKey(id))
				System.out.println("Duplicate ticket id " + id);
			else {
				System.out.println("Enter the subject");
				String subject = scanner.next();

				System.out.println("Enter the agent name : \t");
				String ag_name = scanner.next();

				String tagString = readTags();
				TreeSet tags = ticketService.getTagsInfo(tagString);

				validId = setTicketData(id, subject, ag_name, tags);
			}
		}
		catch (InputMismatchException e) {
			System.out.println("Please enter a valid ticket Id");
		}
		finally {
			scanner.nextLine();
		}
		return validId;
	}

	// actual setting of data
	public int setTicketData(int id, String subject, String ag_name, Set tags) {
		if (id <= 0 || (subject.isEmpty() || ag_name.isEmpty() || tags.isEmpty())) {
			return 0;
		}

		Ticket ticketData = TicketFactory.ticketInstance(id, subject, ag_name, tags);

		this.ticketDetails.put(id, ticketData);
		System.out.println("Ticket " + id + " has been added successfully \n");
		return id;
	}

	//update ticket
	public Ticket updateTicketOps() {
		System.out.println("Ticket id : " + ticketDetails);
		Ticket ticketObj = null;
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to update.");
		}
		else {
			System.out.println("Enter the ticket id");
			int id = scanner.nextInt();
			if (ticketDetails.containsKey(id)) {
				ticketObj = ticketDetails.get(id);

				// change agent assigned
				System.out.println("Change agent assigned?? y/n");
				String selection = scanner.next();
				if (selection.toLowerCase().equals("y")) {
					System.out.println("Enter the agent name");
					String data = scanner.next();
					ticketObj = ticketService.updateAgent(ticketObj, data);
				}

				//change tags
				System.out.println("Change tags?? y/n");
				selection = scanner.next();
				if (selection.toLowerCase().equals("y")) {
					String tagString = readTags();
					ticketObj = ticketService.updateTags(ticketObj, tagString);
				}
			}
			else {
				System.out.println("No such Ticket Id present to update");
			}
		}
		return ticketObj;
	}

	// delete ticket
	public void deleteTicketOps() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to delete.");
		}
		else {
			int id = readTicketId();
			boolean deletion = ticketService.deleteTicket(ticketDetails, id);
			if (deletion) {
				System.out.println("Ticket " + id + " has deleted");
			}
			else {
				System.out.println("No such ticket with id: "+id+" available to delete");
			}
		}
	}

	// view selected ticket
	public void showTicketOps() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to display.");
		}
		else {
			int id = readTicketId();
			Ticket ticketInfo = ticketService.showTicket(ticketDetails,id);
			if (ticketInfo != null)
			utilOps.printData(ticketInfo);
			else System.out.println("Invalid ticket Id : "+ id +" given. No Content available.");
		}
	}

	// view all the ticket list
	public void showTicketList() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to display.");
		}
		else {
			List<Ticket> sortedList = utilOps.sortedList(valueSet);
			sortedList.forEach(utilOps::printData);
		}
	}

	public String readTags() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the tags (comma separated)");
		return scanner.nextLine();
	}

	// get ticket id
	private int readTicketId() {
		System.out.println("Enter the ticket id");
		int id = scanner.nextInt();
		return id;
	}

	// agent specific ticket search
	public void agentSearchTicketOps() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to display.");
		}
		else {
			System.out.println("Enter the agent name");
			String agent = scanner.next().trim();

			List<Ticket> ticketObjs = ticketService.agentSearchTicket(agent,ticketDetails);
			if (!ticketObjs.isEmpty())
			ticketObjs.forEach(utilOps ::printData);
			else System.out.println("No Content available for agent : "+agent);
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
			TreeSet keys = new TreeSet(agTicketCount.keySet());
			System.out.println("Agent -> Count");
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

			List<Ticket> ticketObjs = ticketService.tagSearchTicket(tag,ticketDetails);
			if (!ticketObjs.isEmpty())
				ticketObjs.forEach(utilOps::printData);
			else System.out.println("No content available for tag : "+tag);
		}
	}
}
