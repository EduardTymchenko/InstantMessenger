package ModelServer;

public class User {
    private int idUser;
    private String nameUser;
    private String passwordUser;
    private boolean adminUser;
    private boolean online;

    public int getIdUser() {
        return idUser;
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

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
