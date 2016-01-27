package com.Tickets;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by root on 14/1/16.
 */
public class TicketServiceComponent{
    public boolean print = true;
    public HashMap<Integer, Ticket> thm = new HashMap<>();
    public boolean createTicket(String sub, String agent, String tags){
        if(!sub.isEmpty() && !agent.isEmpty() && !tags.isEmpty()){
            String[] parsedtags = tags.split(",");
            Set<String> set = new HashSet<>(Arrays.asList(parsedtags));

            Ticket ticket = TicketFactoryClass.getInstance(sub,set,agent);
            try{

                FileOutputStream fileOutputStream = new FileOutputStream("tickets.ser");
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
                outputStream.writeObject(ticket);
                fileOutputStream.close();

                FileInputStream fileInputStream = new FileInputStream("tickets.ser");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Ticket ticket1 = (Ticket) objectInputStream.readObject();
                System.out.println(ticket1);
                fileInputStream.close();
            }catch (FileNotFoundException F){
                System.out.println(Sout.ACT_NOT_FOUND+" "+F);
            }catch (IOException Io){
                System.out.println(Sout.ACT_NOT_FOUND);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            thm.put(ticket.getId(),ticket);
            if (checkIfExists(ticket.getId())) {
                return true;
            }
        }

        return false;
    }

    public boolean updateTicket(int id, String type, String val){
        try{
            boolean updated = false;
            Ticket ticket = thm.get(id);
            if (type.equals("agent") && !val.isEmpty()) {
                ticket.setAgent_name(val);
                thm.put(id, ticket);
                updated = true;
            } else if (type.equals("tags") && !val.isEmpty()) {
                String [] parsedtags = val.split(",");
                Set set1 = new HashSet<>(Arrays.asList(parsedtags));

                ticket.setTags(set1);

                thm.put(id,ticket);
                updated = true;
            }
            if (updated){
                return true;
            }
        }catch(InputMismatchException Im){
            System.out.println(Sout.ACT_INVALID+Im);
            return false;
        }catch (NullPointerException Null){
            return false;
        }
        return false;
    }

    public boolean removeTicketById(int id){
        if (id > 0){
            if (checkIfExists(id)){
                thm.remove(id);
                return true;
            }
        }
        return false;
    }


    public Ticket getTicketById(int id){

        boolean check = checkIfExists(id);
        if (check){
            Ticket t3 = thm.get(id);
            return t3;
        }
        return null;
    }

    public List<Ticket> getTicketsByAgentName(String name){
        List<Ticket> l = new ArrayList<>();
        if (!name.isEmpty()){
            l = thm.values().stream()
                    .filter(l2 -> l2.getAgent_name().equals(name))
                    .collect(Collectors.toList());
        }
        return l;// sorted by modified date
    }

    public List getAllTickets(){
        List<Ticket> t3 = new ArrayList<>();
        if (!thm.isEmpty()){
            t3 = new ArrayList<>(thm.values());
            if (print){
                this.display(t3);
            }
            return t3;
        }else {
            System.out.println(Sout.ACT_NOT_FOUND);
        }
        return t3;
    }

    /**
     * function to fetch count of ticket with respect to its agent.
     * Data is grouped by Agent name in ascending order.
     */
    public Map<String,Integer> getTicketsGroupByAgent(){
        Map<String, Integer> agentsTickets = new TreeMap<>();
        if (!thm.isEmpty()){

            List<Ticket> l = new ArrayList<>(thm.values());
            Collections.sort(l,Ticket.ByAgentNameComparator);
            for (int index = 0; index < l.size(); index++){
                int countTicket = agentsTickets.containsKey(l.get(index).getAgent_name()) ? agentsTickets.get(l.get(index).getAgent_name()) : 0;
                agentsTickets.put(l.get(index).getAgent_name(), countTicket+ 1);
            }
            if (print){
                for (Map.Entry<String, Integer> entry : agentsTickets.entrySet())
                    System.out.println(entry.getKey()+" : "+entry.getValue());
            }
        }else {
            System.out.println(Sout.ACT_NOT_FOUND);
        }
        return agentsTickets;
    }

    /**
     * Function to fetch all ticket with respect to Tag.
     */
    public Set<Ticket> getAllTicketsByTag(String tag){
        Set<Ticket> set2 = new HashSet<>();
        if (!thm.isEmpty()){

            List<Ticket> l = new ArrayList<>(thm.values());
            set2 = l.stream()
                    .filter(list -> list.getTags().contains(tag))
                    .collect(Collectors.toSet());
            if(!set2.isEmpty()){
                List<Ticket> t3 = new ArrayList<>(set2);
                Collections.sort(t3);
                if (print){
                    System.out.println(Sout.ACT_TABLE_HEADER);
                    for (Ticket str: t3) {
                        System.out.println(str);
                    }
                }
            }else {
                System.out.println(Sout.ACT_NOT_FOUND);
            }
        }
        return set2;
    }

    public void display(List<Ticket> tickets){
        Collections.sort(tickets);
        System.out.println(Sout.ACT_TABLE_HEADER);

        for (Ticket str: tickets) {
            System.out.println(str);
        }
    }

    public boolean checkIfExists(int id){
        return (thm.containsKey(id)) ? true : false;
    }
}
