package ModelServer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Users {
    @XmlElement(name = "user")
    private ArrayList<User> userList = new ArrayList<>();

    public void addUser (User user){
    userList.add(user);
    }

    public ArrayList<User> getUserList() {
        return userList;
    }
}
