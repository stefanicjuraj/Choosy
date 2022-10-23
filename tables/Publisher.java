package tables;

import java.util.ArrayList;

import model.ChoosyDatabase;

public class Publisher extends Table {
    private int publisher_id;
    private String publisher_name;
    private String publisher_state;
    private ChoosyDatabase db;

    public Publisher() {
        this.publisher_id = 0;
        this.publisher_name = "undefined";
        this.publisher_state = "undefined";
    }

    public Publisher(int publisher_id, String publisher_name) {
        this.publisher_id = publisher_id;
        this.publisher_name = publisher_name;
    }

    public Publisher(int publisher_id, String publisher_name, String publisher_state) {
        this.publisher_id = publisher_id;
        this.publisher_name = publisher_name;
        this.publisher_state = publisher_state;
    }

    // fetch
    public boolean fetch() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = String.format("SELECT * FROM publisher WHERE publisher_id = %s", this.publisher_id);
        arrayList = db.getData(query);

        try {
            setPublisherID(Integer.parseInt(arrayList.get(0).get(0)));
            setPublisherName(arrayList.get(0).get(1));
            setPublisherState(arrayList.get(0).get(2));
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }

        return true;

    }

    // post
    public boolean post() {
        String query = "UPDATE publisher SET publisher_id = " + publisher_id + ", publisher_name = " + publisher_name
                + ", publisher_state = " + publisher_state + ";";
        return db.setData(query);
    }

    // put
    public boolean put() {
        if (db.getData("SELECT * FROM publisher WHERE publisher_name = " + publisher_name + ";") != null) {
            return false;
        }
        String query = String.format("INSERT INTO publisher VALUES (%d, '%s', '%s');", publisher_id, publisher_name,
                publisher_state);
        return db.setData(query);
    }

    // remove
    public boolean remove() {
        String query = "DELETE FROM publisher WHERE publisher_id = " + publisher_id + ";";
        return (db.setData(query));
    }

    // generate id
    public boolean generateID() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = "SELECT * FROM publisher;";
        arrayList = db.getData(query);
        int id = arrayList.size() + 1;
        setPublisherID(id);
        return true;
    }

    // find id
    public boolean findID() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = String.format("SELECT * FROM publisher WHERE publisher_name = '%s';", publisher_name);
        arrayList = db.getData(query);
        if (arrayList == null) {
            return false;
        }
        setPublisherID(Integer.parseInt(arrayList.get(0).get(0)));
        return true;
    }

    // exists
    public boolean exists() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = String.format("SELECT * FROM publisher WHERE publisher_name = '%s';", publisher_name);
        arrayList = db.getData(query);
        if (arrayList == null) {
            return false;
        }
        return true;
    }

    // get table
    public ArrayList<Publisher> getTable() {
        ArrayList<Publisher> publishers = new ArrayList<Publisher>();
        ArrayList<ArrayList<String>> arrayList = db.getData("SELECT * FROM publisher;");
        for (ArrayList<String> row : arrayList) {
            Publisher publisher = new Publisher();
            publisher.setPublisherID(Integer.parseInt(row.get(0)));
            publisher.setPublisherName(row.get(1));
            publisher.setPublisherState(row.get(2));
            publishers.add(publisher);
        }
        return publishers;
    }

    // get publisher id
    public int getPublisherID() {
        return publisher_id;
    }

    // set publisher id
    public void setPublisherID(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    // get publisher name
    public String getPublisherName() {
        return publisher_name;
    }

    // set publisher name
    public void setPublisherName(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    // get publisher state
    public String getPublisherState() {
        return publisher_state;
    }

    // set publisher state
    public void setPublisherState(String publisher_state) {
        this.publisher_state = publisher_state;
    }

    // set database
    public void setDatabase(ChoosyDatabase db) {
        this.db = db;
    }

    // to string
    public String toString() {
        return String.format("|  %20s  |  |  %20s  |  |  %20s  |", publisher_id, publisher_name, publisher_state);
    }

}