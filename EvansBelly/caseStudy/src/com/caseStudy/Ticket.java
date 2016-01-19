package com.caseStudy;

import java.util.Set;

/**
 * Created by root on 8/1/16.
 */
public class Ticket {

	private int id;
	private String agent;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public Set getTags() {
		return tags;
	}

	public void setTags(Set tags) {
		this.tags = tags;
	}

	public long getModified() {
		return modified;
	}

	// cjm - I would remove this accessor and have the Ticket manage setting this.
	public void setModified(long modified) {
		this.modified = modified;
	}

	private String subject;
	private Set tags;
	private long created;
	private long modified;
}
