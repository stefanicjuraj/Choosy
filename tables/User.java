package tables;

import java.util.ArrayList;

import model.ChoosyDatabase;

public class User extends Table {
    private String username;
    private String fName;
    private String lName;
    private String password;
    protected ChoosyDatabase database;

    // user
    public User() {
        this.username = "";
        this.fName = "";
        this.lName = "";
        this.password = "";
    }

    public User(String id, String fName, String lName, String password) {
        this.username = id;
        this.fName = fName;
        this.lName = lName;
        this.password = password;
    }

    // fetch
    public boolean fetch() {
        ArrayList<String> data = database
                .getData(String.format("SELECT * FROM users WHERE username = '%s'", this.username)).get(0);
        if (data.size() == 0) {
            return false;
        }
        this.fName = data.get(1);
        this.lName = data.get(2);
        this.password = data.get(3);
        return true;
    }

    // put
    public boolean put() {
        return database.setData(
                String.format("INSERT INTO users (username, fName, lName, password) VALUES ('%s', '%s', '%s', '%s')",
                        getUserID(), getfName(), getfName(), getPassword()));
    }

    // post
    public boolean post() {
        return database.setData(String.format(
                "UPDATE users SET username = '%s', fName = '%s', lName = '%s', password = '%s' WHERE username = '%s'",
                getUserID(), getfName(), getfName(), getPassword(), getUserID()));
    }

    // remove
    public boolean remove() {
        return database.setData(String.format("DELETE FROM users WHERE username = '%s'", getUserID()));
    }

    // get user id
    public String getUserID() {
        return username;
    }

    // set user id
    public void setUserID(String userID) {
        this.username = userID;
    }

    // get fname
    public String getfName() {
        return fName;
    }

    // set fname
    public void setfName(String fName) {
        this.fName = fName;
    }

    // get lname
    public String getlName() {
        return lName;
    }

    // set lname
    public void setlName(String lName) {
        this.lName = lName;
    }

    // get password
    public String getPassword() {
        return password;
    }

    // set password
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @param database
     */
    public void setDatabase(ChoosyDatabase database) {
        this.database = database;
    }

    // to string
    @Override
    public String toString() {
        return "User{" +
                "Id=" + getUserID() +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}