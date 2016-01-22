package com.caseStudy.Model;

import java.util.Set;

/**
 * Created by root on 8/1/16.
 */
public class Ticket {

	public Ticket(int id, String subject, String agent, Set tags) {

		this.id = id;
		this.subject = subject;
		this.agent = agent;
		this.tags = tags;
		this.created = System.currentTimeMillis() / 1000L;
		this.modified = System.currentTimeMillis() / 1000L;
	}

	public String getSubject() {
		return subject;
	}

	public int getId() {
		return id;
	}

	public String getAgent() {
		return this.agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
		this.modified = System.currentTimeMillis() / 1000L;
	}

	public long getCreated() {
		return created;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set tags) {
		this.tags = tags;
		this.modified = System.currentTimeMillis() / 1000L;
	}

	public Long getModified() {
		return modified;
	}

	private int id;
	private long created;
	private long modified;
	private String agent;
	private String subject;
	private Set<String> tags;
}
