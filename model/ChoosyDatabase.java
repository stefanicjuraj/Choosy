package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

/**
 * ISTE 330 - Choosy
 * This class represents all functionalities available when it comes
 * to the relationship between the database and the tables inside of
 * it. This includes the establishment and closing of the connection
 * between the jdbc and the MySQL Server, querying the database using
 * DDL and DML statements, and more to come.
 * 
 */

public class ChoosyDatabase {
    // attributes
    public String url;
    public String username;
    public String password;

    public Connection conn = null;
    private ResultSet rs = null;
    private ResultSetMetaData metadata = null;

    private int tries;

    // constructor for connection to the database
    public ChoosyDatabase(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // connect
    public boolean connect() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                this.conn = connection;
                return true;
            } else {
                return false;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            return false;
        }

    }

    // close
    public boolean close() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException sql) {
                sql.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    // get data
    public ArrayList<ArrayList<String>> getData(String query) {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

        try (Statement st = conn.createStatement()) {
            rs = st.executeQuery(query);

            metadata = rs.getMetaData();
            int numCols = metadata.getColumnCount();

            while (rs.next()) {
                ArrayList<String> rows = new ArrayList<String>();
                for (int i = 1; i <= numCols; i++)
                    rows.add(rs.getString(i));
                data.add(rows);
            }
        } catch (SQLException sqle) {
            sqle.getMessage();
        }
        if (data.size() == 0)
            return null;
        else
            return data;
    }

    // set data
    public boolean setData(String query) {
        int rowsUpdated = 0;
        try (Statement st = conn.createStatement()) {
            rowsUpdated = st.executeUpdate(query);
        } catch (SQLException sqle) {
            sqle.getMessage();
        }
        if (rowsUpdated > 0)
            return true;
        else
            return false;
    }

    /**
     * hash a string password and return the hash
     * 
     * @param password
     * @return
     * @throws DLException
     */
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }

    // login
    public boolean login(String username, String password) {
        String pword = hashPassword(password);
        if (!authenticate(username, pword)) {
            tries++;
            if (tries > 3) {
                System.out.println("Too many tries");
                System.exit(1);
            }
            System.out.println("Incorrect username or password");
            return false;
        }
        return true;
    }

    // register
    public boolean register(String username, String password, String fName, String lName) {
        String query = (String.format("SELECT * FROM users WHERE username = '%s'", username));
        if (getData(query) != null) {
            System.out.println("User already exists");
            return false;
        } else {
            String pword = hashPassword(password);
            String query2 = (String.format(
                    "INSERT INTO users (username, password, fName, lName) VALUES ('%s', '%s', '%s', '%s')", username,
                    pword, fName, lName));
            setData(query2);
            return setUserRole(username);
        }
    }

    /**
     * check whether the expected password under
     * the set username matches the input password.
     */
    public boolean authenticate(String username, String password) {
        String query = (String.format("SELECT * FROM users WHERE username = '%s'", username));
        if (getData(query) == null) {
            System.out.println("User does not exist");
            return false;
        }
        String pword = getData(
                String.format("SELECT password FROM users WHERE username = '%s'", username))
                .get(0).get(0);
        return pword.equals(password);
    }

    // set user role
    public boolean setUserRole(String username) {
        int generalUser = 3;
        String query = String.format("INSERT INTO user_role VALUES('%s', %d);", username, generalUser);
        return setData(query);
    }

}