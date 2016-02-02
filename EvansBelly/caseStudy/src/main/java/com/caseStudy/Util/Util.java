package com.caseStudy.Util;

import com.caseStudy.Model.Ticket;

/**
 * Created by root on 15/1/16.
 */
public class Util {

	// menu section for displaying operations
	public static void menuPopUp() {
		System.out.println(
				"1. Create Ticket\n" +
						"2. Update Ticket by id\n" +
						"3. Delete/Remove Ticket by id\n" +
						"4. Select single Ticket by id\n" +
						"5. Select all Tickets\n" +
						"6. Select tickets assigned to specific agent.\n" +
						"7. Ticket count grouped by agent name(order by agent name).\n" +
						"8. Search all tickets by specific tag. \n" +
						"9. Exit. \n" +
						"Select the operation you want to perform:");
	}

	// fetch and print the data of the ticket
	public static void printData(Ticket ticketData) {

		System.out.println("---------------------------------------------");
		System.out.println("Id       : " + ticketData.getId());
		System.out.println("Subject  : " + ticketData.getSubject());
		System.out.println("Agent    : " + ticketData.getAgent());
		System.out.println("tags     : " + ticketData.getTags());
		System.out.println("Created  : " + ticketData.getCreated());
		System.out.println("Modified : " + ticketData.getModified());
	}

	// sort according to modified timestamp
//	public List<Ticket> sortedList(Collection<Ticket> valueSet) {
//		List<Ticket> sortedList = new ArrayList<>(valueSet);
//		Collections.sort(sortedList);
//		return sortedList;
//	}

	// ticket data insertion
//	public void dataInsertion() {
//		System.out.println("Enter the ticket id");
//		try {
//			int id = scanner.nextInt();
//			if (ticketDetails.containsKey(id))
//				System.out.println("Duplicate ticket id " + id);
//			else {
//				System.out.println("Enter the subject");
//				String subject = scanner.next();
//
//				System.out.println("Enter the agent name : \t");
//				String ag_name = scanner.next();
//
//				HashSet set = tagsInfo();
//
//				Ticket ticketData = new Ticket();
//				ticketData.setId(id);
//				ticketData.setSubject(subject);
//				ticketData.setAgent(ag_name);
//				ticketData.setTags(set);
//
//				this.ticketDetails.put(id, ticketData);
//				System.out.println("Ticket " + id + " has been added successfully \n");
//			}
//		}
//		catch (InputMismatchException e) {
//			System.out.println("Please enter a valid ticket Id");
//		}
//		finally {
//			scanner.nextLine();
//		}
//	}
}
