package com.caseStudy.Model;

import java.util.Set;

/**
 * Created by root on 8/1/16.
 */
public class Ticket implements Comparable<Ticket> {

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
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
		this.setModified(System.currentTimeMillis() / 1000L);
	}

	public long getCreated() {
		return created;
	}

	private void setCreated(long created) {
		this.created = System.currentTimeMillis() / 1000L;;
	}

	public Set getTags() {
		return tags;
	}

	public void setTags(Set tags) {
		this.tags = tags;
		this.setModified(System.currentTimeMillis() / 1000L);
	}

	public Long getModified() {
		return modified;
	}

	private void setModified(long modified) {
		this.modified = System.currentTimeMillis() / 1000L;
	}

	private int id;
	private String agent;
	private String subject;
	private Set tags;
	private long created;
	private long modified;

	@Override
	public int compareTo(Ticket o) {
		return o.getModified().compareTo(this.getModified());
	}
}
