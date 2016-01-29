package com.caseStudy;

import com.caseStudy.Factory.TicketFactory;
import com.caseStudy.Model.Ticket;
import com.caseStudy.Util.Util;

import java.io.*;
import java.util.*;

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
		System.out.println("Single ticket");
		util.printData(ticketData);
	}

	public void multipleTickets() throws IOException, ClassNotFoundException {
		Set tags = new HashSet<>(Arrays.asList("info", "latest", "edited"));
		Ticket ticket1 = TicketFactory.ticketInstance(1, "Test", "Evans", tags);
		Ticket ticket2 = TicketFactory.ticketInstance(2, "Result", "Leroy", tags);

		ArrayList<Ticket> listOfTickets = new ArrayList<>();
		listOfTickets.add(ticket1);
		listOfTickets.add(ticket2);

		// Serialize
		multiSerialize(listOfTickets);

		// De-Serialize
		System.out.println("_____________________________________________");
		System.out.println("Multiple tickets");
		ArrayList<Ticket> deSer = multiDeSerialize();
		deSer.forEach(util::printData);

	}

	public void multiSerialize(ArrayList list) throws IOException {
		ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file));
		objOut.writeObject(list);
	}

	public ArrayList multiDeSerialize() throws IOException, ClassNotFoundException {
		ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(file));
		return (ArrayList) objIn.readObject();
	}
}