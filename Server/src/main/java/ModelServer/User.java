package ModelServer;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    private String nameUser;
    private String passwordUser;
    private boolean adminUser;
    private boolean ban;

    public User(String nameUser, String passwordUser, boolean adminUser,  boolean ban) {
        this.nameUser = nameUser;
        this.passwordUser = passwordUser;
        this.adminUser = adminUser;
        this.ban = ban;
    }

    public User() {
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public boolean isAdminUser() {
        return adminUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public void setAdminUser(boolean adminUser) {
        this.adminUser = adminUser;
    }
}
