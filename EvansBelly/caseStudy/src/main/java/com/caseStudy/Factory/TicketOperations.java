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
				HashSet tags = ticketService.getTagsInfo(tagString);

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
	public Ticket updateTicket() {
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
					ticketObj = ticketService.updatetags(ticketObj, tagString);
				}
			}
			else {
				System.out.println("No such Ticket Id present to update");
			}
		}
		return ticketObj;
	}

	// update agent
//	public void updateAgent(Ticket ticketObj, String data) {
////		System.out.println("Enter the agent name");
////		String data = scanner.next();
//		ticketObj.setAgent(data);
//	}

	// update tags
//	public void updatetags(Ticket ticketObj) {
//		String tagString = readTags();
//		HashSet tags = ticketService.getTagsInfo(tagString);
//		ticketObj.setTags(tags);
//	}

	// delete ticket
	public void deleteTicket() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to delete.");
		}
		else {
			int id = readTicketId();
			ticketDetails.remove(id);
			System.out.println("Ticket " + id + " has deleted");
		}
	}

	// view selected ticket
	public void showTicket() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to display.");
		}
		else {
			int id = readTicketId();
			Ticket ticketInfo = ticketDetails.get(id);
			utilOps.printData(ticketInfo);
		}
	}

	// view all the ticket list
	public void showTicketList() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to display.");
		}
		else {
			List<Ticket> sortedList = sortedList();
			sortedList.forEach(utilOps::printData);
		}
	}

	// get tags info to update
//	public HashSet getTagsInfo(String tagString) {
////		Scanner scanner = new Scanner(System.in);
////		System.out.println("Enter the tags (comma separated)");
////		String tags = scanner.nextLine();
//		HashSet set = new HashSet(Arrays.asList(tagString.split("\\s*,\\s*")));
//		return set;
//	}

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
	public void agentSearch() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to display.");
		}
		else {
			System.out.println("Enter the agent name");
			String ag = scanner.next().trim();

			List<Ticket> lt = sortedList();
			for (Ticket ticketData : lt) {
				String agent = ticketData.getAgent();
				if (agent.equals(ag)) {
					utilOps.printData(ticketData);
				}
			}
		}
	}

	// sort according to modified timestamp
	public List<Ticket> sortedList() {
		List<Ticket> sortedList = new ArrayList<>(ticketDetails.values());
		Collections.sort(sortedList);
		return sortedList;
	}

	// count of tickets assigned to each agent
	public void ticketCount() {
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
	public void tagSearch() {
		if (ticketDetails.isEmpty()) {
			System.out.println("No tickets available to display.");
		}
		else {
			System.out.println("Enter the tag");
			String tag = scanner.next();

			List<Ticket> sortedList = sortedList();
			for (Ticket ticketData : sortedList) {
				Set tags = ticketData.getTags();
				if (tags.contains(tag))
					utilOps.printData(ticketData);
			}
		}
	}
}
