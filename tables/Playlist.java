package tables;

import java.util.ArrayList;

import model.ChoosyDatabase;

public class Playlist extends Table {
    private int playlist_id;
    private String playlist_name;
    private String playlist_description;
    private int num_of_songs;
    private double length_of_playlist;
    private String username;
    private ChoosyDatabase db;

    public Playlist() {
        this.playlist_id = 0;
        this.playlist_name = "";
        this.playlist_description = "";
        this.num_of_songs = 0;
        this.length_of_playlist = 0;
        this.username = "";
    }

    public Playlist(int playlist_id, String playlist_name, String username) {
        this.playlist_id = playlist_id;
        this.playlist_name = playlist_name;
        this.playlist_description = "";
        this.num_of_songs = 0;
        this.length_of_playlist = 0;
        this.username = "";
    }

    public Playlist(int playlist_id, String playlist_name, String playlist_description, int num_of_songs,
            int length_of_playlist, String username) {
        this.playlist_id = playlist_id;
        this.playlist_name = playlist_name;
        this.playlist_description = playlist_description;
        this.num_of_songs = num_of_songs;
        this.length_of_playlist = length_of_playlist;
        this.username = username;
    }

    // fetch
    public boolean fetch() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = "SELECT * FROM playlist WHERE playlist_id = " + playlist_id + ";";
        arrayList = db.getData(query);

        try {
            setPlaylistID(Integer.parseInt(arrayList.get(0).get(0)));
            setPlaylistName(arrayList.get(0).get(1));
            setPlaylistDescription(arrayList.get(0).get(2));
            setNumOfSongs(Integer.parseInt(arrayList.get(0).get(3)));
            setLengthOfPlaylist(Integer.parseInt(arrayList.get(0).get(4)));
            setUsername(arrayList.get(0).get(5));
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }

        return true;
    }

    /**
     * Fetch data for the playlist table
     * based on the username and playlist name.
     */
    public boolean fetchByName() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = "SELECT * FROM playlist WHERE username = '" + username + "' AND playlist_name = '"
                + playlist_name + "';";
        arrayList = db.getData(query);

        try {
            setPlaylistID(Integer.parseInt(arrayList.get(0).get(0)));
            setPlaylistName(arrayList.get(0).get(1));
            setPlaylistDescription(arrayList.get(0).get(2));
            setNumOfSongs(Integer.parseInt(arrayList.get(0).get(3)));
            setLengthOfPlaylist(Double.parseDouble(arrayList.get(0).get(4)));
            setUsername(arrayList.get(0).get(5));
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }

        return true;
    }

    // put
    public boolean put() {
        String query = String.format("INSERT INTO playlist VALUES (%d, '%s', '%s', %d, %f, '%s');", playlist_id,
                playlist_name, playlist_description, num_of_songs, length_of_playlist, username);
        return db.setData(query);
    }

    // post
    public boolean post() {
        String query = String.format(
                "UPDATE playlist SET playlist_id = %d, playlist_name = '%s', playlist_description = '%s', num_of_songs = %d, length_of_playlist = %d, username = '%s' WHERE playlist_id = %d;",
                playlist_id, playlist_name, playlist_description, num_of_songs, length_of_playlist, username,
                playlist_id);
        return db.setData(query);
    }

    // remove
    public boolean remove() {
        String query = String.format("DELETE FROM playlist WHERE playlist_id = %d;", playlist_id);
        return db.setData(query);
    }

    /**
     * Return all playlists inside of the playlist
     * table. If there are no playlists, return empty
     * list.
     */
    public ArrayList<Playlist> getTable() {
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();
        ArrayList<ArrayList<String>> arrayList = null;
        String query = "SELECT * FROM playlist;";
        arrayList = db.getData(query);
        if (arrayList == null) {
            return playlists;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            Playlist playlist = new Playlist();
            playlist.setPlaylistID(Integer.parseInt(arrayList.get(i).get(0)));
            playlist.setPlaylistName(arrayList.get(i).get(1));
            playlist.setPlaylistDescription(arrayList.get(i).get(2));
            playlist.setNumOfSongs(Integer.parseInt(arrayList.get(i).get(3)));
            playlist.setLengthOfPlaylist(Double.parseDouble(arrayList.get(i).get(4)));
            playlist.setUsername(arrayList.get(i).get(5));
            playlists.add(playlist);
        }

        return playlists;
    }

    // generate id
    public boolean generateID() {
        String query = "SELECT MAX(playlist_id) FROM playlist;";
        ArrayList<ArrayList<String>> arrayList = db.getData(query);
        if (arrayList == null) {
            return false;
        }
        if (arrayList.get(0).get(0) == null) {
            playlist_id = 1;
        } else {
            playlist_id = Integer.parseInt(arrayList.get(0).get(0)) + 1;
        }
        return true;
    }

    // get playlist id
    public int getPlaylistID() {
        return playlist_id;
    }

    // set playlist id
    public void setPlaylistID(int playlist_id) {
        this.playlist_id = playlist_id;
    }

    // get playlist name
    public String getPlaylistName() {
        return playlist_name;
    }

    // set playlist name
    public void setPlaylistName(String playlist_name) {
        this.playlist_name = playlist_name;
    }

    // get playlist description
    public String getPlaylistDescription() {
        return playlist_description;
    }

    // set playlist description
    public void setPlaylistDescription(String playlist_description) {
        this.playlist_description = playlist_description;
    }

    // get number of songs
    public int getNumOfSongs() {
        return num_of_songs;
    }

    // set number of songs
    public void setNumOfSongs(int num_of_songs) {
        this.num_of_songs = num_of_songs;
    }

    // get length of playlist
    public double getLengthOfPlaylist() {
        return length_of_playlist;
    }

    // set length of playlist
    public void setLengthOfPlaylist(double length_of_playlist) {
        this.length_of_playlist = length_of_playlist;
    }

    // get username
    public String getUsername() {
        return username;
    }

    // set username
    public void setUsername(String username) {
        this.username = username;
    }

    // set database
    public void setDatabase(ChoosyDatabase db) {
        this.db = db;
    }

}