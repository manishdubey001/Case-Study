package com.caseStudy.Service;

import com.caseStudy.Model.Ticket;
import com.caseStudy.Util.Util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 19/1/16.
 */
public class TicketService {

	Util utilOps = new Util();

	public Ticket updateAgent(Ticket ticketObj, String data) {
		if (!data.isEmpty()) {
			ticketObj.setAgent(data);
			return ticketObj;
		}
		return null;
	}

	public Ticket updateTags(Ticket ticketObj, String tagString) {
		if (!tagString.isEmpty()) {
			TreeSet tags = getTagsInfo(tagString);
			ticketObj.setTags(tags);
			return ticketObj;
		}
		return null;
	}

	public TreeSet getTagsInfo(String tagString) {
		TreeSet set = new TreeSet(Arrays.asList(tagString.split("\\s*,\\s*")));
		return set;
	}

	public boolean deleteTicket(HashMap<Integer, Ticket> ticketDetails, int id) {

		if (ticketDetails.containsKey(id)) {
			ticketDetails.remove(id);
			return true;
		}
		return false;
	}


	public Ticket showTicket(HashMap<Integer, Ticket> ticketDetails, int id) {
		if (ticketDetails.containsKey(id)) {
			Ticket ticketInfo = ticketDetails.get(id);
			return ticketInfo;
		}
		return null;
	}

	public List<Ticket> agentSearchTicket(String searchAgent, HashMap<Integer, Ticket> ticketDetails) {
		List<Ticket> ticketObjs = new ArrayList<>();
		List<Ticket> lt = utilOps.sortedList(ticketDetails.values());
		for (Ticket ticketData : lt) {
			String agent = ticketData.getAgent();
			if (agent.equals(searchAgent)) {
				ticketObjs.add(ticketData);
			}
		}
		// Your code here is fine. It is a bit less efficient to sort first and then to find the agent
		// (consider, for example, if the agent has not tickets or just one ticket--nothing to sort).
		
		// Can you see how to rewrite these methods with streams? This one would be
		// ticketDetails.values().stream().filter(t->t.getAgent().equals(searchAgent)).sorted().collect(Collectors.toList());
		return ticketObjs;
	}

	public List<Ticket> tagSearchTicket(String tag, HashMap<Integer, Ticket> ticketDetails) {
		List<Ticket> ticketObjs = new ArrayList<>();
		List<Ticket> lt = utilOps.sortedList(ticketDetails.values());
		for (Ticket ticketData : lt) {
			Set tags = ticketData.getTags();
			if (tags.contains(tag)) {
				ticketObjs.add(ticketData);
			}
		}
		return ticketObjs;
	}

}
