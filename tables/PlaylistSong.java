package tables;

import java.util.ArrayList;

import model.ChoosyDatabase;

public class PlaylistSong extends Table {
    private int song_id;
    private int playlist_id;
    private ChoosyDatabase db;

    public PlaylistSong() {
        this.song_id = 0;
        this.playlist_id = 0;
    }

    public PlaylistSong(int playlist_id) {
        this.playlist_id = playlist_id;
        this.song_id = 0;
    }

    public PlaylistSong(int song_id, int playlist_id) {
        this.song_id = song_id;
        this.playlist_id = playlist_id;
    }

    // fetch
    public boolean fetch() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = "SELECT * FROM playlist_song WHERE song_id = " + song_id + " AND playlist_id = " + playlist_id
                + ";";
        arrayList = db.getData(query);

        try {
            setSongID(Integer.parseInt(arrayList.get(0).get(0)));
            setPlaylistID(Integer.parseInt(arrayList.get(0).get(1)));
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }

        return true;
    }

    // put
    public boolean put() {
        String query = String.format("INSERT INTO playlist_song VALUES (%d, %d);", song_id, playlist_id);
        return db.setData(query);
    }

    // post
    public boolean post() {
        String query = String.format("DELETE FROM playlist_song WHERE song_id = %d AND playlist_id = %d;", song_id,
                playlist_id);
        return db.setData(query);
    }

    // remove
    public boolean remove() {
        String query = String.format("DELETE FROM playlist_song WHERE song_id = %d AND playlist_id = %d;", song_id,
                playlist_id);
        return db.setData(query);
    }

    // remove playlist id
    public boolean removeByPlaylistId() {
        String query = String.format("DELETE FROM playlist_song WHERE playlist_id = %d;", playlist_id);
        return db.setData(query);
    }

    // get song id
    public int getSongID() {
        return song_id;
    }

    // set song id
    public void setSongID(int song_id) {
        this.song_id = song_id;
    }

    // get playlist id
    public int getPlaylistID() {
        return playlist_id;
    }

    // set playlist id
    public void setPlaylistID(int playlist_id) {
        this.playlist_id = playlist_id;
    }

    // set database
    public void setDatabase(ChoosyDatabase db) {
        this.db = db;
    }

}