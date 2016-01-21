package model;


import data.Repository;
import helpers.DateTime;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by root on 31/12/15.
 */
public class TicketModel {
    private int id;
    private String subject;
    private String agent_name;
    private HashSet tags;
    private long created;
    private long modified;
    public boolean isUpdate = false;

    // You don't use this.
    public static TicketModel getInstance() {
        return new TicketModel();
    }

    //
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    // I would not expose setters for all properties; only the ones absolutely necessary.
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAgentName() {
        return agent_name;
    }

    public void setAgentName(String agent_name) {
        this.agent_name = agent_name;
    }

    // Here you are allowing the caller to modify the tags collection directly;
    // instead either make a defensive copy, or use a wrapper like Collections.unmodifiableList()
    public HashSet getTags() {
        return tags;
    }

    // allowing a caller to give you a collection directly can be a problem; what if they
    // change it after they give it to you? Sometimes the right thing to do is to create a
    // defensive copy. Also, there is no reason to require the caller to pass a HashSet
    // (instead of just a Set).
    public void setTags(HashSet tags) {
        this.tags = tags;
    }

    public long getCreated() {
        return this.created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getModified() {
        return this.modified;
    }

    public void setModified(long modified) {

        this.modified = modified;
    }

    /**
     * before modification of any item
     */

    // I like that the TicketModel class is responsible for managing the timestamps;
    // however I would not have clients be required to set the isUpdate flag directly.
    // Not everyone will agree with the following recommendations but I think it is
    // usually the way to go
    // 1. Have the accessors like setAgentName() update the modified timestamp; that
    // way when a caller changes the agent, the modification update is automatic.
    // 2. Have a constructor (or use the builder pattern) that takes all the required
    // parameters and sets the create timestamp. This way you know that any call
    // after the constructor is a modify (does away with the need for isUpdate).
    // 3. Do NOT use accessors internally in the class's implementation. You do need
    // to consider carefully if there is any impact on sbuclasses when you do this.
    private void beforeSave() {
        //pending logic for date time function
        long currentTimeStamp = DateTime.getCurrentTimeStampInSeconds();
        if (!this.isUpdate) {
            this.setCreated(currentTimeStamp);
        }
        this.setModified(currentTimeStamp);
    }

    /**
     * get ticket detail
     *
     * @param id
     * @return
     */
    public TicketModel find(int id) {
        if (isExists(id)) {
            return (TicketModel) Repository.getInstance().ticketData.get(id);
        }
        return null;
    }

    /**
     * used for getTicketDetail all
     *
     * @return
     */
    public List<TicketModel> findAll() {
        Function<TicketModel, Long> byModified = ticketModel -> ticketModel.modified;
        return Repository.getInstance().ticketData.values().stream().sorted(Comparator.comparing(byModified).reversed()).collect(Collectors.toList());
    }

    /**
     * if record exists
     *
     * @param id
     * @return
     */
    // I would not have the TicketModel class implement this kind of logic. I would recommend
    // 1. Move these kind of methods to the Repository class (or another class responsible for
    // managing all tickets), and
    // 2. Don't expose the hashMap directly. Let Repository worry about where the tickets are
    // stored. This about changing the storage from HashMap to a database for example; you should
    // not have to change TicketModel to do that.
    // Same recommendation for the FIND methods as well as delete, etc.
    public boolean isExists(int id) {
        System.out.println("checking id = " + id);
        System.out.println("contains= " + Repository.getInstance().ticketData.containsKey(id));
        return Repository.getInstance().ticketData.containsKey(id);
    }

    /**
     * delete ticket
     *
     * @param id
     * @return
     */
    public boolean delete(int id) {
        TicketModel tm = null;
        if (isExists(id)) {
            tm = Repository.getInstance().ticketData.remove(id);
        }
        return tm != null ? true : false;
    }

    /**
     * insert or processUpdateTicket
     *
     * @return boolean
     */
    public boolean save() {
        beforeSave();
        Repository.getInstance().ticketData.put(id, this);
        return true;
    }

    /**
     * getTicketDetail all by agent name
     *
     * @param agentName
     * @return
     */
    public List<TicketModel> findAllByAgentName(String agentName) {
        Function<TicketModel, String> byAgentName = tm -> tm.agent_name;
        // good use of streams!
        return Repository.getInstance().ticketData.values().stream().filter(ticketModel -> ticketModel.agent_name.equalsIgnoreCase(agentName)).sorted(Comparator.comparing(byAgentName)).collect(Collectors.toList());
    }

    /**
     * getTicketDetail all by tags
     *
     * @param tag
     * @return
     */
    // Sort by modifiied date, right?
    public List<TicketModel> findAllByTag(String tag) {
        List<TicketModel> ls = new ArrayList<TicketModel>();
        Collection<TicketModel> c = Repository.getInstance().ticketData.values();
        Iterator<TicketModel> itr = c.iterator();
        tag = tag.toLowerCase();
        while (itr.hasNext()) {
            TicketModel tm = itr.next();
            HashSet hsTags = tm.getTags();
            if (hsTags.contains(tag)) {
                ls.add(tm);
            }
        }
        // Two ways to try to simplify this
        // 1. Use the for-each mechanism: for(TicketModel tm : ticketData.values())
        // or
        // 2. Use streams.
        return ls;
    }

    /**
     * getTicketDetail all agent with ticket count
     *
     * @return
     */
    public TreeMap<String, Integer> findAllAgentWithTicketCount() {
        TreeMap<String, Integer> tmAgentNameCount = new TreeMap<String, Integer>();
        Collection<TicketModel> c = Repository.getInstance().ticketData.values();
        Iterator<TicketModel> itr = c.iterator();

        while (itr.hasNext()) {
            TicketModel tm = itr.next();
            String agentName = tm.getAgentName();
            int cnt = 1;

            if (tmAgentNameCount.containsKey(agentName)) {
                cnt = tmAgentNameCount.get(agentName);
                cnt++;
            }
            tmAgentNameCount.put(agentName, cnt);
            Calendar.getInstance(); // why this line?
        }
        return tmAgentNameCount;
    }
}