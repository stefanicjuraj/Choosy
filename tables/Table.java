package tables;

import model.ChoosyDatabase;

public abstract class Table {

    // fetch
    public boolean fetch() {
        return true;
    };

    // put
    public boolean put() {
        return true;
    };

    // post
    public boolean post() {
        return true;
    };

    // remove
    public boolean remove() {
        return true;
    };

    // print table
    public void printTable() {
    }

    // find id
    public boolean findID() {
        return true;
    }

    // generate id
    public boolean generateID() {
        return true;
    }

    // exists
    public boolean exists() {
        return true;
    }

    // set database
    public void setDatabase(ChoosyDatabase db) {
    }

}