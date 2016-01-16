package service;

import helpers.DateTime;
import helpers.ConsoleReader;
import helpers.Util;
import model.TicketModel;

import java.util.*;

/**
 * Created by root on 5/1/16.
 */
public class TicketService {

    TicketModel tm = null;

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
//        return Util.isNotEmpty(tags) ? new HashSet<String>(Arrays.asList(tags.toLowerCase().split(","))) : new HashSet<String>();
        return Util.isNotEmpty(tags) ? new HashSet<String>(Arrays.asList(tags.toLowerCase().split(","))) : null;
    }

    /**
     * processCreateTicket operation
     *
     * @return
     */
    public void processCreateTicket() {
        try {
            Scanner scanner = ConsoleReader.getInstance();

            int id = processId(scanner, false);

            if (id > 0) {
                System.out.println("Enter subject");
                String subject = scanner.next();

                System.out.println("Enter agent name");
                String agentName = scanner.next();

                System.out.println("Enter tags (Comma separated if multiple)");
                String tags = scanner.next();

                tm = new TicketModel();
                tm.setId(id);
                tm.setAgentName(agentName);
                tm.setSubject(subject);
                HashSet<String> hs = getTags(tags);
                tm.setTags(hs);
                if (tm.save()) {
                    System.out.println("Ticket created successfully");
                } else {
                    System.out.println("Error while creating ticket");
                }
            }
        } catch (InputMismatchException ime) {
//            ime.printStackTrace();
            System.out.println("Invalid input provided!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get id as input, check for valid id
     * @param scanner
     * @param isUpdate
     * @return
     */
    private int processId(Scanner scanner, boolean isUpdate) {
        try {
            System.out.println("Enter id");
            int id = scanner.nextInt();

            if (id > 0) {
                //if update operation then skip checking existing
                if (!isUpdate && TicketModel.getInstance().isExists(id)) {
                    System.out.println("Id : " + id + " already taken, please try with another id");
                } else {
                    return id;
                }
            } else {
                System.out.println("Invalid input provided!!!");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input provided!!!");
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return -1;
    }

    /**
     * processUpdateTicket operation
     *
     * @return
     */
    public void processUpdateTicket() {
        try {
            Scanner scanner = ConsoleReader.getInstance();
            int id = processId(scanner, true);
            if (id > 0) {
                if (TicketModel.getInstance().isExists(id)) {

                    TicketModel tm = TicketModel.getInstance().find(id);
                    System.out.println("Enter agent name");
                    String agentName = scanner.next();

                    System.out.println("Enter tags (Comma separated if multiple)");
                    String tags = scanner.next();

                    tm.isUpdate = true;
                    if (agentName != null) {
                        tm.setAgentName(agentName);
                    }
                    if (tags != null) {
                        HashSet<String> hs = getTags(tags);
                        tm.setTags(hs);
                    }
                    if (tm.save()) {
                        System.out.println("Ticket created successfully");
                    } else {
                        System.out.println("Error while creating ticket");
                    }
                }
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input provided!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get ticket detail
     *
     * @param id
     * @return
     */
    public TicketModel getTicketDetail(int id) {
        return TicketModel.getInstance().find(id);
    }

    /**
     * get ticket detail
     *
     * @return
     */
    public List<TicketModel> getTicketList() {
        return TicketModel.getInstance().findAll();
    }

    /**
     * getTicketDetail all tickets by agent name
     *
     * @param agentName
     * @return
     */
    public List<TicketModel> findAllTicketsByAgentName(String agentName) {
        if (agentName != null && !agentName.isEmpty()) {
            return TicketModel.getInstance().findAllByAgentName(agentName);
        }
        return new ArrayList<TicketModel>();
    }

    /**
     * getTicketDetail all tickets by tags
     *
     * @param tag
     * @return
     */
    public List<TicketModel> getAllTIcketsByTag(String tag) {
        if (tag != null && !tag.isEmpty()) {
            return TicketModel.getInstance().findAllByTag(tag);
        }
        return new ArrayList<TicketModel>();
    }

    /**
     * display agent name with respective ticket count
     */
    public void processAgentWithTicketCount() {
        try {
            TreeMap<String, Integer> tmAgentNameCount = TicketModel.getInstance().findAllAgentWithTicketCount();
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
     * select individual ticket by Id ticket menu
     */
    public void processDeleteTicket() throws InterruptedException {
        try {
            System.out.println("Enter id");
            int id = ConsoleReader.getInstance().nextInt();

            boolean b = TicketModel.getInstance().delete(id);
            if (b) {
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
     * select individual ticket by Id ticket menu
     */
    public void processGetTicketDetail() {
        try {
            Scanner scanner = ConsoleReader.getInstance();
            System.out.println("Enter id");
            int id = scanner.nextInt();

            TicketModel tm = getTicketDetail(id);
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
        System.out.println("----------------------");
        System.out.println("Ticket id  : " + tm.getId());
        System.out.println("Subject    : " + tm.getSubject());
        System.out.println("Agent name : " + tm.getAgentName());
        System.out.println("Tags       : " + Util.convertSetToString(tm.getTags()));
        System.out.println("Created    : " + DateTime.getFormattedDateTime(tm.getCreated()));
        System.out.println("Modified   : " + DateTime.getFormattedDateTime(tm.getModified()));
        System.out.println("----------------------");
    }

    /**
     * display all tickets
     */
    public void processGetAllTicketList() {
        List<TicketModel> ls = getTicketList();
        if (ls != null && ls.size() > 0) {
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
            if (ls != null && ls.size() > 0) {
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

            List<TicketModel> ls = getAllTIcketsByTag(tag);
            if (ls != null && ls.size() > 0) {
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
