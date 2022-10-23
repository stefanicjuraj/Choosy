package tables;

import java.util.ArrayList;

import model.ChoosyDatabase;

public class Genre extends Table {
    private int genre_id;
    private String genre_name;

    private ChoosyDatabase db;

    public Genre() {
        this.genre_id = 0;
        this.genre_name = "undefined";
    }

    public Genre(int genre_id, String genre_name) {
        this.genre_id = genre_id;
        this.genre_name = genre_name;
    }

    // fetch
    public boolean fetch() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = "SELECT * FROM genres WHERE genre_id = " + genre_id + ";";
        arrayList = db.getData(query);

        try {
            setGenreID(Integer.parseInt(arrayList.get(0).get(0)));
            setGenreName(arrayList.get(0).get(1));
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }

        return true;

    }

    // post
    public boolean post() {
        String query = "UPDATE genres SET genre_id = " + genre_id + ", genre_name = " + genre_name + ";";
        return db.setData(query);
    }

    // put
    public boolean put() {
        if (db.getData("SELECT * FROM genres WHERE genre_name = " + genre_name + ";") != null) {
            return false;
        }
        String query = String.format("INSERT INTO genres VALUES (%d, '%s');", genre_id, genre_name);
        return db.setData(query);
    }

    // remove
    public boolean remove() {
        String query = "DELETE FROM genres WHERE genre_id = " + genre_id + ";";
        return (db.setData(query));
    }

    // generate id
    public boolean generateID() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = "SELECT * FROM genres;";
        arrayList = db.getData(query);
        int id = arrayList.size() + 1;
        setGenreID(id);
        return true;
    }

    // find id
    public boolean findID() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = String.format("SELECT * FROM genres WHERE genre_name = '%s';", genre_name);
        arrayList = db.getData(query);

        try {
            setGenreID(Integer.parseInt(arrayList.get(0).get(0)));
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }

        return true;
    }

    // exists
    public boolean exists() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = String.format("SELECT * FROM genres WHERE genre_name = '%s';", genre_name);
        arrayList = db.getData(query);
        if (arrayList == null) {
            return false;
        }

        return true;
    }

    // get genre id
    public int getGenreID() {
        return genre_id;
    }

    // set genre id
    public void setGenreID(int genre_id) {
        this.genre_id = genre_id;
    }

    // get genre name
    public String getGenreName() {
        return genre_name;
    }

    // set genre name
    public void setGenreName(String genre_name) {
        this.genre_name = genre_name;
    }

    // set database
    public void setDatabase(ChoosyDatabase db) {
        this.db = db;
    }

    // to string
    public String toString() {
        return "Genre ID: " + genre_id + "\nGenre Name: " + genre_name;
    }

}