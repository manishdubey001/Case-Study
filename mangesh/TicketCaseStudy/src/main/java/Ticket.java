import java.util.Date;
import java.util.List;

/**
 * Created by root on 15/1/16.
 */
public class Ticket implements Comparable<Ticket>{
    int id;
    String subject;
    String agent_name;
    List<String> tags;
    String tempTags;
    long timeStamp;
    Date created;
    Date modified;

    public Ticket(int id, String subject, String agentName, List<String> tagsList) {
        this.setId(id);
        this.setSubject(subject);
        this.setAgent_name(agentName);
        this.setTags(tagsList);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTempTags() {
        return tempTags;
    }

    public void setTempTags(String tempTags) {
        this.tempTags = tempTags;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public int compareTo(Ticket obj) {
        return obj.getModified().compareTo(getModified());
    }
}
