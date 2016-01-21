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
		// see comment below for setTags() for a potential problem here
		this.tags = tags;
		// This can result in a slightly strange situation for a new
		// ticket here created != modified. I would initialize
		// them both to the same value.
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

	// not used and no longer needed
	private void setCreated(long created) {
		this.created = System.currentTimeMillis() / 1000L;;
	}

	// for both getTags and setTags notice the caller is allowed to modify these
	// collections after calling these methods. For getters you usually want to
	// either make a copy or use something like Collections.unmodifiableList()
	// to give it an immutable wrapper.
	// For setTags() it is common to make a defensive copy of the caller's input.
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

	// Remove the 'modified' parameter since you don't use it.
	private void setModified(long modified) {
		this.modified = System.currentTimeMillis() / 1000L;
	}

	// Small issue of style: it is most common to list fields first in a class definition.
	private int id;
	private String agent;
	private String subject;
	// Use the parameterized type for safety: Set<String>
	private Set tags;
	private long created;
	private long modified;

	@Override
	public int compareTo(Ticket o) {
		return o.getModified().compareTo(this.getModified());
	}
}
