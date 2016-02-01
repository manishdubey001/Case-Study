package operations;

import data.Repository;
import helpers.Util;
import model.TicketModel;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by root on 29/1/16.
 */
public class TicketOperations {

    public static TicketOperations newInstance() {
        return new TicketOperations();
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
        Function<TicketModel, Long> byModified = ticketModel -> ticketModel.getModified();
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
    //UPDATE : moved into into file for TicketOperations
    public boolean isExists(int id) {
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
     * getTicketDetail all by agent name
     *
     * @param agentName
     * @return
     */
    public List<TicketModel> findAllByAgentName(String agentName) {
        Function<TicketModel, String> byAgentName = tm -> tm.getAgentName();
        // good use of streams!
        return Repository.getInstance().ticketData.values().stream().filter(ticketModel -> ticketModel.getAgentName().equalsIgnoreCase(agentName)).sorted(Comparator.comparing(byAgentName)).collect(Collectors.toList());
    }

    /**
     * getTicketDetail all by tags
     *
     * @param tag
     * @return
     */
    // Sort by modifiied date, right?
    //UPDATE : right, I have fixed it
    public List<TicketModel> findAllByTag(String tag) {
        List<TicketModel> ticketModelList = new ArrayList<TicketModel>();
        final String toCheck = tag.toLowerCase();

        Function<TicketModel, Long> byModified = ticketModel -> ticketModel.getModified();
        ticketModelList.addAll(Repository.getInstance().ticketData.values().stream().filter(ticketModel -> ticketModel.getTags().contains(toCheck)).sorted(Comparator.comparing(byModified)).collect(Collectors.toList()));
        // Two ways to try to simplify this
        // 1. Use the for-each mechanism: for(TicketModel tm : ticketData.values())
        // or
        // 2. Use streams.
        //UPDATE : used stream
        return ticketModelList;
    }

    /**
     * getTicketDetail all agent with ticket count
     *
     * @return
     */
    public TreeMap<String, Integer> findAllAgentWithTicketCount() {
        TreeMap<String, Integer> tmAgentNameCount = new TreeMap<String, Integer>();
        Collection<TicketModel> ticketModelCollection = Repository.getInstance().ticketData.values();

        if (Util.isCollectionValid(ticketModelCollection)) {
            for (TicketModel ticketModel : ticketModelCollection) {

                String agentName = ticketModel.getAgentName();

                int cnt = 1;

                if (tmAgentNameCount.containsKey(agentName)) {
                    cnt = tmAgentNameCount.get(agentName);
                    cnt++;
                }
                tmAgentNameCount.put(agentName, cnt);
            }
        }
        return tmAgentNameCount;
        //
    }

    /**
     * get total number of tickets
     *
     * @return int
     */
    public int getTotalNumberOfTickets() {
        return Repository.getInstance().ticketData.size();
    }

    /**
     * get oldest ticket
     *
     * @return TicketModel
     */
    public TicketModel getOldestTicket() {
        Map<Integer, TicketModel> ticketData = Repository.getInstance().ticketData;
        if (Util.isMapValid(ticketData)) {
            return ticketData.values().stream().max((ticketModel1, ticketModel2) -> Long.compare(ticketModel1.getModified(), ticketModel2.getModified())).get();
        }
        return new TicketModel();
    }

    /**
     * get all tickets within specified timestamp
     *
     * @param startTimestamp
     * @param endTimestampe
     * @return
     */
    public List<TicketModel> findAll(long startTimestamp, long endTimestampe) {
        Map<Integer, TicketModel> ticketData = Repository.getInstance().ticketData;
        if (Util.isMapValid(ticketData)) {
            Function<TicketModel, Long> byModified = ticketModel -> ticketModel.getModified();
            return ticketData.values().stream().filter(ticketModel -> ticketModel.getCreated() > startTimestamp && ticketModel.getCreated() <= endTimestampe).sorted(Comparator.comparing(byModified).reversed()).collect(Collectors.toList());
        }
        return new ArrayList<TicketModel>();
    }

    /**
     * tag in used with ticket count
     *
     * @return Map<String, Integer>
     */
    public Map<String, Integer> tagsInUsedWithCount() {
        Map<String, Integer> tagNameWithTicketCount = new HashMap<String, Integer>();
        Collection<TicketModel> ticketModelCollection = Repository.getInstance().ticketData.values();
        if (Util.isCollectionValid(ticketModelCollection)) {
            for (TicketModel ticketModel : ticketModelCollection) {

                Set<String> tagsSet = ticketModel.getTags();
                if (Util.isCollectionValid(tagsSet)) {

                    for (String tag : tagsSet) {

                        int cnt = 1;
                        if (tagNameWithTicketCount.containsKey(tag)) {
                            cnt = tagNameWithTicketCount.get(tag);
                            cnt++;
                        }
                        tagNameWithTicketCount.put(tag, cnt);
                    }
                }
            }
        }
        return tagNameWithTicketCount;
    }
}
