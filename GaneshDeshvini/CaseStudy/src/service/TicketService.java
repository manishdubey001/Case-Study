package service;

import factory.TicketModelFactory;
import helpers.DateTime;
import helpers.ConsoleReader;
import helpers.Util;
import model.TicketModel;

import java.util.*;

/**
 * Created by root on 5/1/16.
 */
public class TicketService {

    /**
     * processCreateTicket ticket service object
     *
     * @return
     */
    public static TicketService getInstance() {
        return new TicketService();
    }

    /**
     * get tags from comma separated string
     *
     * @param tags
     * @return
     */
    private HashSet<String> getTags(String tags) {
        final String pattern = "\\s*,\\s*";
        return Util.isStringValid(tags) ? new HashSet<String>(Arrays.asList(tags.toLowerCase().split(pattern))) : null;
    }


    /**
     * check if duplicate ticket id
     *
     * @param id
     * @return boolean
     */
    boolean isDuplicateTicketId(int id) {
        return TicketModelFactory.getInstance().isExists(id);
    }

    /**
     * create ticket
     *
     * @param id
     * @param subject
     * @param agentName
     * @param tags
     * @return
     */
    public boolean createTicket(int id, String subject, String agentName, String tags) {
        if (id > 0 && Util.isStringValid(subject) && Util.isStringValid(agentName)) {
            if (isDuplicateTicketId(id)) {
                System.out.println("Duplicate ticket id!!!");
                return false;
            }
            TicketModel tm = TicketModelFactory.getInstance();
            // rather than set these here, I would have a constructor that takes these parameters
            // (or use a builder pattern). Otherwise you have an object that is sometimes in an
            // invalid state in between each step. Not a big deal for TicketModel but it can be for
            // other classes.
            tm.setId(id);
            tm.setAgentName(agentName);
            tm.setSubject(subject);
            HashSet<String> hs = getTags(tags);
            tm.setTags(hs);
            return tm.save();
        }
        System.out.println("Invalid input");
        return false;
    }

    /**
     * processCreateTicket operation
     *
     * @return
     */
    public void processCreateTicket() {
        Scanner scanner = ConsoleReader.getInstance();
        try {
            int id = processId();

            System.out.println("Enter subject");
            String subject = scanner.next();

            System.out.println("Enter agent name");
            String agentName = scanner.next();

            System.out.println("Enter tags (Comma separated if multiple)");
            scanner = ConsoleReader.getInstance();
            String tags = scanner.nextLine();
            System.out.println(tags);
            if (createTicket(id, subject, agentName, tags)) {
                System.out.println("Ticket created successfully");
            } else {
                System.out.println("Error while creating ticket");
            }

        } catch (InputMismatchException ime) {
//            ime.printStackTrace();
            System.out.println("Invalid input provided!!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner = null;
        }
    }


    /**
     * get id as input, check for valid id
     *
     * @return
     */
    private int processId() {
        try {
            Scanner scanner = ConsoleReader.getInstance();
            System.out.println("Enter id");
            int id = scanner.nextInt();
            return id;
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input provided!!!");
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return -1;
    }

    /**
     * update ticket
     *
     * @param id
     * @param agentName
     * @param tags
     * @return
     */
    public boolean updateTicket(int id, String agentName, String tags) {
        if (id > 0 && TicketModelFactory.getInstance().isExists(id)) {
            TicketModel tm = TicketModelFactory.getInstance().find(id);

            tm.isUpdate = true;
            if (Util.isStringValid(agentName)) {
                tm.setAgentName(agentName);
            }
            HashSet<String> hs = getTags(tags);
            tm.setTags(hs);
            return tm.save();
        }
        System.out.println("Invalid input provided!!!");
        return false;
    }

    /**
     * processUpdateTicket operation
     *
     * @return
     */
    public void processUpdateTicket() {
        Scanner scanner = ConsoleReader.getInstance();
        try {
            int id = processId();
            System.out.println("Enter agent name");
            String agentName = scanner.next();

            scanner = ConsoleReader.getInstance();
            System.out.println("Enter tags (Comma separated if multiple)");
            String tags = scanner.next();

            if (updateTicket(id, agentName, tags)) {
                System.out.println("Ticket updated successfully");
            } else {
                System.out.println("Error while updating ticket");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input provided!!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner = null;
        }
    }

    /**
     * get ticket detail
     *
     * @param id
     * @return
     */
    public TicketModel getTicketDetail(int id) {
        return TicketModelFactory.getInstance().find(id);
    }

    /**
     * get ticket detail
     *
     * @return
     */
    public List<TicketModel> getTicketList() {
        return TicketModelFactory.getInstance().findAll();
    }

    /**
     * getTicketDetail all tickets by agent name
     *
     * @param agentName
     * @return
     */
    public List<TicketModel> findAllTicketsByAgentName(String agentName) {
        if (Util.isStringValid(agentName)) {
            return TicketModelFactory.getInstance().findAllByAgentName(agentName);
        }
        return new ArrayList<TicketModel>();
    }

    /**
     * getTicketDetail all tickets by tags
     *
     * @param tag
     * @return
     */
    public List<TicketModel> getAllTicketsByTags(String tag) {
        if (Util.isStringValid(tag)) {
            return TicketModelFactory.getInstance().findAllByTag(tag);
        }
        return new ArrayList<TicketModel>();
    }

    /**
     * display agent name with respective ticket count
     */
    public void processAgentWithTicketCount() {
        try {
            TreeMap<String, Integer> tmAgentNameCount = findAllAgentWithTicketCount();
            if (Util.isMapValid(tmAgentNameCount)) {
                System.out.println("-------------------------------------");
                System.out.println("|Agent Name\t|Count|");
                for (Map.Entry<String, Integer> kv : tmAgentNameCount.entrySet()) {
                    String agentName = kv.getKey();
                    int cnt = kv.getValue();
                    System.out.println("|" + agentName + " | Count : " + cnt + "|");
                }
                System.out.println("-------------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * find all agent with respective ticket count
     * @return
     */
    public TreeMap<String, Integer> findAllAgentWithTicketCount (){
        return TicketModelFactory.getInstance().findAllAgentWithTicketCount();
    }

    /**
     * delete ticket
     *
     * @param id
     * @return
     */
    public boolean deleteTicket(int id) {
        if (id > 0) {
            return TicketModelFactory.getInstance().delete(id);
        }
        System.out.println("Invalid input provided!!!");
        return false;
    }

    /**
     * select individual ticket by Id ticket menu
     */
    public void processDeleteTicket() throws InterruptedException {
        try {
            int id = processId();

            if (deleteTicket(id)) {
                System.out.println("Entry removed successfully");
            } else {
                System.out.println("No data found!!!");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input provided!!!");
        } catch (NumberFormatException nfe) {
            System.out.println("Please Enter valid number..please try again...\n\n");
            Thread.sleep(100);
            processDeleteTicket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get ticket details
     *
     * @param id
     * @return
     */
    TicketModel getTicket(int id) {
        if (id > 0) {
            return getTicketDetail(id);
        }
        System.out.println("Invalid input provided!!!");
        return null;
    }

    /**
     * select individual ticket by Id ticket menu
     */
    public void processGetTicketDetail() {
        try {
            int id = processId();

            TicketModel tm = getTicket(id);
            if (tm != null) {
                printTicketDetails(tm);
            } else {
                System.out.println("No data found!!!");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input provided");
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    /**
     * print ticket details based on ticket model
     *
     * @param tm
     */
    void printTicketDetails(TicketModel tm) {
        if (tm != null) {
            System.out.println("----------------------");
            System.out.println("Ticket id  : " + tm.getId());
            System.out.println("Subject    : " + tm.getSubject());
            System.out.println("Agent name : " + tm.getAgentName());
            System.out.println("Tags       : " + Util.convertSetToString(tm.getTags()));
            System.out.println("Created    : " + DateTime.getFormattedDateTime(tm.getCreated()));
            System.out.println("Modified   : " + DateTime.getFormattedDateTime(tm.getModified()));
            System.out.println("----------------------");
        }
    }

    /**
     * display all tickets
     */
    public void processGetAllTicketList() {
        List<TicketModel> ls = getTicketList();
        if (Util.isCollectionValid(ls)) {
            for (TicketModel tm : ls) {
                printTicketDetails(tm);
            }
        } else {
            System.out.println("No data found!!!");
        }
    }

    /**
     * display tickets by agent name
     */
    public void processGetTicketsByAgentName() {
        try {
            System.out.println("Enter agent name");
            String agentName = ConsoleReader.getInstance().next();

            List<TicketModel> ls = findAllTicketsByAgentName(agentName);
            if (Util.isCollectionValid(ls)) {
                for (TicketModel tm : ls) {
                    printTicketDetails(tm);
                }
            } else {
                System.out.println("No data found!!!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * display tickets by agent name
     */
    public void processGetAllTicketsByTag() {
        try {
            System.out.println("Enter a tag");
            String tag = ConsoleReader.getInstance().next();

            List<TicketModel> ls = getAllTicketsByTags(tag);
            if (Util.isCollectionValid(ls)) {
                for (TicketModel tm : ls) {
                    printTicketDetails(tm);
                }
            } else {
                System.out.println("No data found!!!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
