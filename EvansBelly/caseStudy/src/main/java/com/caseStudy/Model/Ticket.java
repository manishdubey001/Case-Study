package com.caseStudy.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

/**
 * Created by root on 8/1/16.
 */
public class Ticket implements Serializable {

	public Ticket(int id, String subject, String agent, Set tags) {
		this.id = id;
		this.subject = subject;
		this.agent = agent;
		// see comment below for setTags() for a potential problem here
		this.tags = tags;
		// This can result in a slightly strange situation for a new
		// ticket here created != modified. I would initialize
		// them both to the same value.
//		LocalDateTime localDateTime = LocalDateTime.now();
//		this.created = this.modified = System.currentTimeMillis() / 1000L;
		this.created = this.modified = LocalDateTime.now().minusDays(new Random().nextInt(20));
//		this.modified = System.currentTimeMillis() / 1000L;
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
		this.modified = LocalDateTime.now();
	}

	public LocalDateTime getCreated() {
		return created;
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
		this.modified = LocalDateTime.now();
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void writeObject(ObjectOutputStream objOutStream) {

		try {
			objOutStream.writeInt(id);
			objOutStream.writeObject(created);
			objOutStream.writeObject(modified);
			objOutStream.writeUTF(agent);
			objOutStream.writeUTF(subject);
			objOutStream.writeObject(tags);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Ticket readObject(ObjectInputStream objInStream) {
		try {
			this.id = objInStream.readInt();
			this.created = (LocalDateTime) objInStream.readObject();
			this.modified = (LocalDateTime) objInStream.readObject();
			this.agent = objInStream.readUTF();
			this.subject = objInStream.readUTF();
			this.tags = (Set) objInStream.readObject();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		//virendra: below is same as return this since you have setup all the value
		return new Ticket(id, subject, agent, tags);
	}
	// Remove the 'modified' parameter since you don't use it.

	// Small issue of style: it is most common to list fields first in a class definition.
	private int id;
	private String agent;
	private String subject;
	private Set<String> tags;
	private LocalDateTime created;
	private LocalDateTime modified;

}
