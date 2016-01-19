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

    //
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

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAgentName() {
        return agent_name;
    }

    public void setAgentName(String agent_name) {
        this.agent_name = agent_name;
    }

    public HashSet getTags() {
        return tags;
    }

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
        return Repository.getInstance().ticketData.values().stream().filter(ticketModel -> ticketModel.agent_name.equalsIgnoreCase(agentName)).sorted(Comparator.comparing(byAgentName)).collect(Collectors.toList());
    }

    /**
     * getTicketDetail all by tags
     *
     * @param tag
     * @return
     */
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
            Calendar.getInstance();
        }
        return tmAgentNameCount;
    }
}