package com.caseStudy.Service;

import com.caseStudy.Model.Ticket;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by root on 19/1/16.
 */
public class TicketService {

	public Ticket updateAgent(Ticket ticketObj, String data) {
		ticketObj.setAgent(data);
		return ticketObj;
	}

	public Ticket updatetags(Ticket ticketObj, String tagString) {
		HashSet tags = getTagsInfo(tagString);
		ticketObj.setTags(tags);
		return ticketObj;
	}

	public HashSet getTagsInfo(String tagString) {
		HashSet set = new HashSet(Arrays.asList(tagString.split("\\s*,\\s*")));
		return set;
	}

}
