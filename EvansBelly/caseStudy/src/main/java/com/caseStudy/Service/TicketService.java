package com.caseStudy.Service;

import com.caseStudy.Factory.TicketFactory;
import com.caseStudy.Factory.TicketOperations;
import com.caseStudy.Model.Ticket;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 19/1/16.
 */
public class TicketService {

	public Ticket getTicketData(int id, String subject, String agent, Set tags){

		return TicketFactory.ticketInstance(id,subject,agent,tags);
	}

	public void updateAgent(Ticket ticketObj, String data) {
		ticketObj.setAgent(data);
	}

	public void updatetags(Ticket ticketObj, String tagString) {
		HashSet tags = getTagsInfo(tagString);
		ticketObj.setTags(tags);
	}

	public HashSet getTagsInfo(String tagString) {
		HashSet set = new HashSet(Arrays.asList(tagString.split("\\s*,\\s*")));
		return set;
	}

}
