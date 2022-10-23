package tables;

import java.util.ArrayList;

import model.ChoosyDatabase;

public class Favorites extends Table {
    public String username;
    public int song_id;
    private ChoosyDatabase db;

    public Favorites() {
        this.username = "";
        this.song_id = 0;
    }

    public Favorites(String username, int song_id) {
        this.username = username;
        this.song_id = song_id;
    }

    // fetch
    public boolean fetch() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = "SELECT * FROM favorites WHERE username = " + username + " AND song_id = " + song_id + ";";
        if (db.getData(query) == null)
            return false;
        arrayList = db.getData(query);

        try {
            setUsername(arrayList.get(0).get(1));
            setSongID(Integer.parseInt(arrayList.get(0).get(2)));
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }
        return true;
    }

    // put
    public boolean put() {
        String query = String.format("INSERT INTO favorites VALUES ('%s', %d);", username, song_id);
        return db.setData(query);
    }

    // post
    public boolean post() {
        String query = "UPDATE favorites SET username = " + username + ", song_id = " + song_id + " WHERE username = "
                + username + " AND song_id = " + song_id + ";";
        return db.setData(query);
    }

    // remove
    public boolean remove() {
        String query = String.format("DELETE FROM favorites WHERE username = '%s' AND song_id = %d;", username,
                song_id);
        return db.setData(query);
    }

    // song exists
    public boolean songExists() {
        String query = String.format("SELECT * FROM favorites WHERE song_id = %d;",
                song_id);
        if (db.getData(query) == null)
            return false;
        return true;
    }

    // remove from all tables
    public boolean removeFromAllTables() {
        String query = String.format("DELETE FROM favorites WHERE song_id = %d;",
                song_id);
        return db.setData(query);
    }

    // get table
    public ArrayList<Favorites> getTable() {
        ArrayList<Favorites> favorites = new ArrayList<Favorites>();
        ArrayList<ArrayList<String>> arrayList = db.getData("SELECT * FROM favorites;");
        if (arrayList != null) {
            for (ArrayList<String> row : arrayList) {
                Favorites favorite = new Favorites();
                favorite.setUsername(row.get(0));
                favorite.setSongID(Integer.parseInt(row.get(1)));
                favorites.add(favorite);
            }
        }
        return favorites;
    }

    // get username
    public String getUsername() {
        return username;
    }

    // set username
    public void setUsername(String username) {
        this.username = username;
    }

    // get song id
    public int getSongID() {
        return song_id;
    }

    // set song id
    public void setSongID(int song_id) {
        this.song_id = song_id;
    }

    // set database
    public void setDatabase(ChoosyDatabase db) {
        this.db = db;
    }

    // to string
    public String toString() {
        return "Username: " + username + " Song ID: " + song_id;
    }
}