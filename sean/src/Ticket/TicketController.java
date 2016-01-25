package Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TicketController {
	private HashMap<Integer, Ticket> tickets = new HashMap<Integer, Ticket>();

	public HashMap<Integer, Ticket> getTickets() {
		return tickets;
	}

	public Ticket createTicket(HashMap<String, String> attributes) {
		Logger.info("Create ticket operation started!");
		TicketFactory ticketFactory = new TicketFactory();
		Ticket ticket = ticketFactory.createTicket(attributes);
		getTickets().put(ticket.getId(), ticket);
		return ticket;
	}

	public Ticket updateTicket(HashMap<String, String> attributes, int id) {
		Logger.info("Update ticket operation started!");
		try {
			Ticket ticket = getTickets().get(id);

			if (ticket != null) {
				TicketFactory ticketFactory = new TicketFactory();
				return ticketFactory.updateTicket(attributes, ticket);
			} else
				Logger.info("Ticket with id " + id + " not found!");
		} catch (Exception e) {
			Logger.info("Exception occurred while updating ticket!");
			return null;
		}
		return null;
	}

	public Ticket deleteTicket(int id) {
		Logger.info("Delete ticket operation started!");
		if (getTickets().get(id) != null) {
			return getTickets().remove(id);
		} else Logger.info("Delete ticket operation failed! Ticket not found!");
		return null;
	}

	public Ticket getTicket(int id) {
		Logger.info("Get ticket detail operation started!");
		return getTickets().get(id);
	}
	
	public List<Ticket> getList(String mode, String filter) {
		if(mode.equals("agent"))
			return getListByAgentName(filter);
		else if(mode.equals("tag"))
			return getListByTagName(filter);
		
		return getListDescendingByModified(getTickets().values().stream().collect(Collectors.toList()));
	}
	
	public List<Ticket> getListDescendingByModified(List<Ticket> ticketList) {
		return ticketList
                .stream()
                .sorted((Ticket o1, Ticket o2) -> -o1.getModified().compareTo(o2.getModified()))
                .collect(Collectors.toList());
	}
	
	public List<Ticket> getListByAgentName(String agentName) {
		List<Ticket> ticketList = getTickets().values().stream().filter(
				ticket -> ticket.getAgentName().toLowerCase().equals(agentName.toLowerCase())
			).collect(Collectors.toList());
		return getListDescendingByModified(ticketList);
	}
	
	public List<Ticket> getListByTagName(String tagName) {
		List<Ticket> ticketList = getTickets().values().stream().filter(
				ticket -> ticket.getTags().contains(tagName.toLowerCase())
			).collect(Collectors.toList());
		return getListDescendingByModified(ticketList);
	}

	public void printTicket(Ticket ticket) {
		System.out.println("Ticket data id: " + ticket.getId() + " \n subject: " + ticket.getSubject() + " \n agent Name: "
				+ ticket.getAgentName() + " \n Tags : " + ticket.getTags().toString() + " \n Created " + ticket.getCreated().toString()
				+ " \n Modified " + ticket.getModified().toString()
				);
	}
}