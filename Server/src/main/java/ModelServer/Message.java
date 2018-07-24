package ModelServer;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@XmlRootElement(name = "XMLMess")
@XmlType(propOrder = {"commandMess","user" ,"from", "bodyMess", "userOnline"})
public class Message {
    private int commandMess;
    private String from;
    private String bodyMess;
    private User user;
    private ArrayList<String> userOnline;
    @XmlElement
    public int getCommandMess() {
        return commandMess;
    }

    public void setCommandMess(int commandMess) {
        this.commandMess = commandMess;
    }

    @XmlElement
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    @XmlElement
    public String getBodyMess() {
        return bodyMess;
    }

    public void setBodyMess(String bodyMess) {
        this.bodyMess = bodyMess;
    }
    @XmlElement
    public User getUser() {
        return user;
    }

    public User setUser(User user) {
        this.user = user;
        return user;
    }
    @XmlElement
    public ArrayList<String> getUserOnline() {
        return userOnline;
    }

    public void setUserOnline(ArrayList<String> userOnline) {
        this.userOnline = userOnline;
    }
}