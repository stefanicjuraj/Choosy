package tables;

import java.util.ArrayList;

import model.ChoosyDatabase;

public class ArtistSong extends Table {
    private int artist_id;
    private int song_id;
    private ChoosyDatabase db;

    public ArtistSong() {
        this.artist_id = 0;
        this.song_id = 0;
    }

    public ArtistSong(int artist_id, int song_id) {
        this.artist_id = artist_id;
        this.song_id = song_id;
    }

    // fetch
    public boolean fetch() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = String.format("SELECT * FROM artist_song WHERE artist_id = %s AND song_id = %s", this.artist_id,
                this.song_id);
        arrayList = db.getData(query);

        try {
            setArtistID(Integer.parseInt(arrayList.get(0).get(0)));
            setSongID(Integer.parseInt(arrayList.get(0).get(1)));
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }

        return true;
    }

    // put
    public boolean put() {
        String query = String.format("INSERT INTO artist_song VALUES (%d, %d);", this.artist_id, this.song_id);
        return db.setData(query);
    }

    // remove
    public boolean remove() {
        String query = String.format("DELETE FROM artist_song WHERE artist_id = %d AND song_id = %d;", this.artist_id,
                this.song_id);
        return db.setData(query);
    }

    // remove song only
    public boolean removeSongOnly() {
        String query = String.format("DELETE FROM artist_song WHERE song_id = %d;", this.song_id);
        return db.setData(query);
    }

    // get artist id
    public int getArtistID() {
        return artist_id;
    }

    // set artist id
    public void setArtistID(int artist_id) {
        this.artist_id = artist_id;
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

}