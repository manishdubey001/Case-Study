package com.caseStudy;

import com.caseStudy.Factory.TicketFactory;
import com.caseStudy.Model.Ticket;
import com.caseStudy.Util.Util;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 22/1/16.
 */
public class Serialization {
	String file = "ticket.txt";
	Util util = new Util();

	public static void main(String[] args) throws Exception {

		Serialization ser = new Serialization();
		ser.singleTicket();
		ser.multipleTickets();
	}

	public void singleTicket() throws Exception {
		Set tags = new HashSet<>(Arrays.asList("info", "latest", "edited"));
		Ticket ticket = TicketFactory.ticketInstance(1, "Test", "Evans", tags);

		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		ticket.writeObject(objectOutputStream);
		objectOutputStream.close();

		FileInputStream fileInputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		Ticket ticketData = ticket.readObject(objectInputStream);
		objectInputStream.close();
		System.out.println("Single ticket");
		util.printData(ticketData);
	}

	public void multipleTickets() throws IOException, ClassNotFoundException, InterruptedException {
		Set tags1 = new HashSet<>(Arrays.asList("info", "latest", "edited"));
		Ticket ticket1 = TicketFactory.ticketInstance(1, "Test", "Evans", tags1);

		Set tags2 = new HashSet<>(Arrays.asList("info", "new", "dummy"));
		Ticket ticket2 = TicketFactory.ticketInstance(2, "Result", "Leroy", tags2);

		Set tags3 = new HashSet<>(Arrays.asList("info", "deleted", "new"));
		Ticket ticket3 = TicketFactory.ticketInstance(3, "Complain", "Sweta", tags3);

		ArrayList<Ticket> listOfTickets = new ArrayList<>();
		listOfTickets.add(ticket1);
		listOfTickets.add(ticket2);
		listOfTickets.add(ticket3);

		// Serialize
		this.multiSerialize(listOfTickets);
		//virendra: make a practice that once resources work is finished,
		// you are closing the resource.
		//if I close a connection and after again open connection to write new tickets.
		// What would be effect of that ? please check that as well.

		// De-Serialize
		System.out.println("_____________________________________________");
		System.out.println("Multiple tickets");
		ArrayList<Ticket> deSer = multiDeSerialize();
		deSer.forEach(util::printData);
		System.out.println("_____________________________________________");
		this.reports(deSer);

	}

	public void multiSerialize(ArrayList list) throws IOException {
		ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file));
		objOut.writeObject(list);
		objOut.close();
	}

	public ArrayList multiDeSerialize() throws IOException, ClassNotFoundException {
		ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(file));
		ArrayList readList = (ArrayList) objIn.readObject();
		objIn.close();
		return readList;
	}

	public void reports(ArrayList<Ticket> deSer) throws InterruptedException, IOException, ClassNotFoundException {

		// total ticket count
		int ticketsCount = deSer.size();
		System.out.println("Total Tickets count : " + ticketsCount);

		// oldest ticket
		Ticket ticketData = deSer.stream().min((Ticket obj1, Ticket obj2) -> obj1.getModified().compareTo(obj2.getModified())).get();
		System.out.println("_____________________________________________");
		System.out.println("Oldest ticket in the system is below");
		util.printData(ticketData);

		// tag wise ticket count
		List<Set> tagList = deSer.stream().map(Ticket::getTags).collect(Collectors.toList());
		HashMap tagCountMap = new HashMap();
		int i = 1;
		for (Set<String> s : tagList) {
			for (String st : s) {
				if (tagCountMap.containsKey(st)) {
					tagCountMap.put(st, i++);
				}
				else
					tagCountMap.put(st, 1);
			}
		}
		System.out.println("-------------------------------------");
		tagCountMap.keySet().forEach(key -> System.out.println(key + " -> " + tagCountMap.get(key)));

		// Tickets older than a certain number of days
		Scanner scanner = new Scanner(System.in);
		//Virendra: Scanner resource is not closed. After printing reports
		System.out.println("Tickets for last 'n' days. Enter value of n");
		try {
			int days = scanner.nextInt();
			List<Ticket> tkList = deSer.stream().filter(t -> t.getModified().compareTo(LocalDateTime.now().minusDays(days)) < 0).collect(Collectors.toList());

			if (!tkList.isEmpty())
				tkList.forEach(util::printData);
			else System.out.println("No tickets found in the system");
		}
		catch (Exception e) {
			System.out.println("Invalid data");
			this.multipleTickets();
		}
	}
}