package com.caseStudy.Service;

import com.caseStudy.Model.Ticket;
import com.caseStudy.Util.Util;

import java.util.*;

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
