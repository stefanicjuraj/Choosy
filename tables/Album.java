package tables;

import java.util.ArrayList;

import model.ChoosyDatabase;

public class Album extends Table {
    private int album_id;
    private String album_name;
    private String release_year;
    private double album_length;
    private int num_of_songs;
    private int artist_id;
    private ChoosyDatabase db;

    public Album() {
        this.album_id = 0;
        this.album_name = "";
        this.release_year = "";
        this.album_length = 0;
        this.num_of_songs = 0;
        this.artist_id = 0;
    }

    public Album(int album_id, String album_name, String release_year, int artist_id) {
        this.album_id = album_id;
        this.album_name = album_name;
        this.release_year = release_year;
        this.artist_id = artist_id;
    }

    public Album(int album_id, String album_name, String release_year, double album_length, int num_of_songs,
            int artist_id) {
        this.album_id = album_id;
        this.album_name = album_name;
        this.release_year = release_year;
        this.album_length = album_length;
        this.num_of_songs = num_of_songs;
        this.artist_id = artist_id;
    }

    // fetch
    public boolean fetch() {
        String query = String.format("SELECT * FROM albums WHERE album_name = %s AND artist_id = %d", album_name,
                artist_id);
        ArrayList<ArrayList<String>> result = db.getData(query);
        if (result != null) {
            album_id = Integer.parseInt(result.get(0).get(0));
            album_name = result.get(0).get(1);
            release_year = result.get(0).get(2);
            album_length = Double.parseDouble(result.get(0).get(3));
            num_of_songs = Integer.parseInt(result.get(0).get(4));
            artist_id = Integer.parseInt(result.get(0).get(5));
            return true;
        } else {
            return false;
        }
    }

    // put
    public boolean put() {
        if (db.getData("SELECT * FROM albums WHERE album_name = " + album_name + " AND artist_id = " + artist_id
                + ";") != null) {
            return false;
        }
        String query = String.format("INSERT INTO albums VALUES(%d,'%s','%s',%f,%d,%d);", album_id, album_name,
                release_year,
                album_length, num_of_songs, artist_id);
        return db.setData(query);
    }

    // generate id
    public boolean generateID() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = "SELECT * FROM albums;";
        arrayList = db.getData(query);
        int id = arrayList.size() + 1;
        album_id = id;
        return true;
    }

    // find id
    public boolean findID() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = String.format("SELECT * FROM albums WHERE album_name = '%s' AND artist_id = %d;", album_name,
                artist_id);
        arrayList = db.getData(query);
        if (arrayList == null) {
            return false;
        }
        album_id = Integer.parseInt(arrayList.get(0).get(0));
        return true;
    }

    // exists
    public boolean exists() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = String.format("SELECT * FROM albums WHERE album_name = '%s' AND artist_id = %d;", album_name,
                artist_id);
        arrayList = db.getData(query);
        if (arrayList == null) {
            return false;
        }
        return true;
    }

    // add to length
    public void addToLength(double songDuration) {
        num_of_songs++;
        album_length += songDuration;
    }

    // set album id
    public void setAlbumID(int album_id) {
        this.album_id = album_id;
    }

    // get album id
    public int getAlbumID() {
        return album_id;
    }

    // get album name
    public String getAlbumName() {
        return album_name;
    }

    // get release year
    public String getReleaseYear() {
        return release_year;
    }

    // get album length
    public double getAlbumLength() {
        return album_length;
    }

    // get number of songs
    public int getNumOfSongs() {
        return num_of_songs;
    }

    // get artist id
    public int getArtistID() {
        return artist_id;
    }

    // set database
    public void setDatabase(ChoosyDatabase db) {
        this.db = db;
    }

    // to string
    public String toString() {
        return String.format("%d,'%s','%s',%f,%d,%d", album_id, album_name, release_year, album_length, num_of_songs,
                artist_id);
    }

}