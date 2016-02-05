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

        //get method it self return TicketModel if present other wise null. No need to check for existence
        //Is type casting is required?
        //Update : removed typecasting
        return Repository.getInstance().ticketData.get(id);
    }

    /**
     * used for getTicketDetail all
     *
     * @return
     */
    public List<TicketModel> findAll() {
        //No need to store this byModified Function object , pass directly into sort method
        //sort according you to your requirement, no need to reverse after sort
        //Update: Removed function object & changed sorting logic
        return Repository.getInstance().ticketData.values().
                stream().
                sorted((ticketModel1, ticketModel2) -> Long.compare(ticketModel2.getModified(), ticketModel1.getModified())).
                collect(Collectors.toList());
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
        //Remove method itself return the null when key not found, here no need to check existence and creating TicketModel
        //Update : changes done
        return Repository.getInstance().ticketData.remove(id) != null;
    }

    /**
     * getTicketDetail all by agent name
     *
     * @param agentName
     * @return
     */
    public List<TicketModel> findAllByAgentName(String agentName) {
        //Pass this is directly into sort method
        //Update: changes done
        // good use of streams!
        return Repository.getInstance().ticketData.values().
                stream().
                filter(ticketModel -> ticketModel.getAgentName().equalsIgnoreCase(agentName)).
                sorted((ticketModel1, ticketModel2) -> Long.compare(ticketModel2.getModified(), ticketModel1.getModified())).
                collect(Collectors.toList());
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
        String toCheck = tag.toLowerCase();

        //Stream it self return the list, no need to crete new one and add all into manually into it
        //Update: changes done
        return Repository.getInstance().ticketData.values().
                stream().
                filter(ticketModel -> ticketModel.getTags().contains(toCheck)).
                sorted(Comparator.comparing(ticketModel -> ticketModel.getModified())).
                collect(Collectors.toList());
        // Two ways to try to simplify this
        // 1. Use the for-each mechanism: for(TicketModel tm : ticketData.values())
        // or
        // 2. Use streams.
        //UPDATE : used stream
    }

    /**
     * getTicketDetail all agent with ticket count
     *
     * @return
     */
    public TreeMap<String, Integer> findAllAgentWithTicketCount() {
        TreeMap<String, Integer> tmAgentNameCount = new TreeMap();
        //Use stream instead of for each
        //Update : used streams
        Repository.getInstance().ticketData.values().forEach(ticketModel -> {
            int cnt = 1;
            String agentName = ticketModel.getAgentName();
            if (tmAgentNameCount.containsKey(agentName)) {
                cnt = tmAgentNameCount.get(agentName);
                cnt++;
            }
            tmAgentNameCount.put(agentName, cnt);
        });
        return tmAgentNameCount;
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
        //Here you creating new ticket if ticket data is empty.
        //Update: changed to null value
        return null;
    }

    /**
     * get all tickets within specified timestamp
     *
     * @param timestamp
     * @return
     */
    public List<TicketModel> getAllTicketFromTimestamp(long timestamp) {
        Map<Integer, TicketModel> ticketData = Repository.getInstance().ticketData;
        if (Util.isMapValid(ticketData)) {
            //sort accordingly , don't reverse after sort
            //Update : changes done
            return ticketData.values().stream().
                    filter(ticketModel -> ticketModel.getCreated() < timestamp).
                    sorted((ticketModel1, ticketModel2) -> Long.compare(ticketModel2.getModified(), ticketModel1.getModified())).
                    collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * tag in used with ticket count
     *
     * @return Map<String, Integer>
     */
    public Map<String, Integer> tagsInUsedWithCount() {
        Map<String, Integer> tagNameWithTicketCount = new HashMap<>();
        Collection<TicketModel> ticketModelCollection = Repository.getInstance().ticketData.values();
        if (Util.isCollectionValid(ticketModelCollection)) {
            //Used stream or internal forEach
            ticketModelCollection.forEach(ticketModel -> {
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
            );
        }
        return tagNameWithTicketCount;
    }
}
