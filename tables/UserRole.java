package tables;

public class UserRole {
    private String username;
    private int role_id;

    public UserRole() {
        this.username = "";
        this.role_id = 0;
    }

    public UserRole(String username, int role_id) {
        this.username = username;
        this.role_id = role_id;
    }

    // get username
    public String getUsername() {
        return username;
    }

    // set username
    public void setUsername(String username) {
        this.username = username;
    }

    // get role id
    public int getRoleID() {
        return role_id;
    }

    // set role id
    public void setRoleID(int role_id) {
        this.role_id = role_id;
    }

}