package tables;

import java.util.ArrayList;
import model.ChoosyDatabase;

public class Artist extends Table {

    private int artistID;
    private String artistName;
    private ChoosyDatabase db;

    public Artist() {
        this.artistID = 0;
        this.artistName = "undefined";
    }

    public Artist(int artistID, String artistName) {
        this.artistID = artistID;
        this.artistName = artistName;
    }

    public boolean fetch() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = String.format("SELECT * FROM artist WHERE artist_id = %d;", artistID);
        arrayList = db.getData(query);

        try {
            setArtistID(Integer.parseInt(arrayList.get(0).get(0)));
            setArtistName(arrayList.get(0).get(1));
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }

        return true;

    }

    // post
    public boolean post() {
        String query = "UPDATE artist SET artist_id = " + artistID + ", artist_name = " + artistName + ";";
        return db.setData(query);
    }

    // put
    public boolean put() {
        if (db.getData("SELECT * FROM artist WHERE artist_name = " + artistName + ";") != null) {
            return false;
        }
        String query = String.format("INSERT INTO artist VALUES (%d,'%s');", artistID, artistName);
        return db.setData(query);
    }

    // remove
    public boolean remove() {
        String query = "DELETE FROM artist WHERE artist_id = " + artistID + ";";
        return (db.setData(query));
    }

    // generate id
    public boolean generateID() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = "SELECT * FROM artist;";
        arrayList = db.getData(query);
        int id = arrayList.size() + 1;
        setArtistID(id);
        return true;
    }

    // exists
    public boolean exists() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = String.format("SELECT * FROM artist WHERE artist_name = '%s';", artistName);
        arrayList = db.getData(query);
        if (arrayList == null) {
            return false;
        }
        return true;
    }

    // find id
    public boolean findID() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = String.format("SELECT * FROM artist WHERE artist_name = '%s';", artistName);
        arrayList = db.getData(query);
        if (arrayList == null) {
            return false;
        }
        setArtistID(Integer.parseInt(arrayList.get(0).get(0)));
        return true;
    }

    // print table
    @Override
    public void printTable() {
        ArrayList<ArrayList<String>> select = db
                .getData("SELECT * FROM artist");
        for (int i = 0; i < select.size(); i++) {
            System.out.println(
                    String.format(
                            "|  %20s  |  |  %20s  |",
                            select.get(i).get(0), select.get(i).get(1)));
        }
    }

    // get table
    public ArrayList<ArrayList<Artist>> getTable() {
        ArrayList<ArrayList<Artist>> arrayList = new ArrayList<ArrayList<Artist>>();
        ArrayList<ArrayList<String>> select = db
                .getData("SELECT * FROM artist");
        for (int i = 0; i < select.size(); i++) {
            arrayList.add(new ArrayList<Artist>());
            arrayList.get(i).add(new Artist(Integer.parseInt(select.get(i).get(0)), select.get(i).get(1)));
        }
        return arrayList;
    }

    // set artist id
    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    // get artist id
    public int getArtistID() {
        return this.artistID;
    }

    // set artist name
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    // get artist name
    public String getArtistName() {
        return this.artistName;
    }

    // set database
    @Override
    public void setDatabase(ChoosyDatabase db) {
        this.db = db;
    }
    
}