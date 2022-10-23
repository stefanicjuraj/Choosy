package tables;

import java.util.ArrayList;

import model.ChoosyDatabase;

public class Song extends Table {
    private int song_id;
    private String song_title;
    private int song_bpm;
    private String song_key;
    private double song_duration;
    private String release_year;
    private int genre_id;
    private int publisher_id;
    private int album_id;
    private ChoosyDatabase db;

    public Song() {
        this.song_id = 0;
        this.song_title = "";
        this.song_bpm = 0;
        this.song_key = "";
        this.song_duration = 0.0;
        this.release_year = "";
        this.genre_id = 0;
        this.publisher_id = 0;
        this.album_id = 0;
    }

    public Song(int id) {
        this.song_id = id;
        this.song_title = "";
        this.song_bpm = 0;
        this.song_key = "";
        this.song_duration = 0.0;
        this.release_year = "";
        this.genre_id = 0;
        this.publisher_id = 0;
        this.album_id = 0;
    }

    public Song(int song_id, String song_title, int song_bpm, String song_key, double song_duration,
            String release_year,
            int genre_id, int publisher_id, int album_id) {
        this.song_id = song_id;
        this.song_title = song_title;
        this.song_bpm = song_bpm;
        this.song_key = song_key;
        this.song_duration = song_duration;
        this.release_year = release_year;
        this.genre_id = genre_id;
        this.publisher_id = publisher_id;
        this.album_id = album_id;
    }

    // fetch
    public boolean fetch() {
        ArrayList<ArrayList<String>> arrayList = null;
        String query = String.format("SELECT * FROM songs WHERE song_id = %d", song_id);
        arrayList = db.getData(query);

        try {
            setSongID(Integer.parseInt(arrayList.get(0).get(0)));
            setSongTitle(arrayList.get(0).get(1));
            setSongBPM(Integer.parseInt(arrayList.get(0).get(2)));
            setSongKey(arrayList.get(0).get(3));
            setSongDuration(Double.parseDouble(arrayList.get(0).get(4)));
            setReleaseYear(arrayList.get(0).get(5));
            setGenreID(Integer.parseInt(arrayList.get(0).get(6)));
            setPublisherID(Integer.parseInt(arrayList.get(0).get(7)));
            setAlbumID(Integer.parseInt(arrayList.get(0).get(8)));
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }

        return true;

    }

    // put
    public boolean put() {
        String query = String.format("INSERT INTO songs VALUES(%d,'%s',%d,'%s',%.2f,'%s',%d,%d,%d);", song_id,
                song_title,
                song_bpm, song_key, song_duration, release_year, genre_id, publisher_id, album_id);
        return db.setData(query);
    }

    // post
    public boolean post() {
        String query = "UPDATE songs SET song_title = " + song_title + ", song_bpm = " + song_bpm + ", song_key = "
                + song_key + ", song_duration = " + song_duration + ", release_year = " + release_year + ", genre_id = "
                + genre_id + ", publisher_id = " + publisher_id + ", album_id = " + album_id + " WHERE song_id = "
                + song_id + ";";
        return db.setData(query);
    }

    // remove
    public boolean remove() {
        String query = String.format("DELETE FROM songs WHERE song_id = %d", song_id);
        return db.setData(query);
    }

    // exists
    public boolean exists() {
        String query = String.format(
                "SELECT * FROM songs JOIN albums ON albums.album_id = songs.album_id WHERE songs.song_title = '%s' AND albums.album_id = %d;",
                song_title, album_id);
        if (db.getData(query) == null) {
            return false;
        } else {
            return true;
        }
    }

    // generate id
    public boolean generateID() {
        String query = "SELECT MAX(song_id) FROM songs;";
        ArrayList<ArrayList<String>> arrayList = db.getData(query);
        try {
            setSongID(Integer.parseInt(arrayList.get(0).get(0)) + 1);
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }
        return true;
    }

    // get table
    public ArrayList<Song> getTable() {
        ArrayList<Song> songs = new ArrayList<Song>();
        ArrayList<ArrayList<String>> arrayList = null;
        String query = "SELECT * FROM songs;";
        arrayList = db.getData(query);

        for (ArrayList<String> row : arrayList) {
            Song song = new Song();
            song.setSongID(Integer.parseInt(row.get(0)));
            song.setSongTitle(row.get(1));
            song.setSongBPM(Integer.parseInt(row.get(2)));
            song.setSongKey(row.get(3));
            song.setSongDuration(Double.parseDouble(row.get(4)));
            song.setReleaseYear(row.get(5));
            song.setGenreID(Integer.parseInt(row.get(6)));
            song.setPublisherID(Integer.parseInt(row.get(7)));
            song.setAlbumID(Integer.parseInt(row.get(8)));
            songs.add(song);
        }

        return songs;
    }

    // get song id
    public int getSongID() {
        return song_id;
    }

    // set song id
    public void setSongID(int song_id) {
        this.song_id = song_id;
    }

    // get song title
    public String getSongTitle() {
        return song_title;
    }

    // set song title
    public void setSongTitle(String song_title) {
        this.song_title = song_title;
    }

    // get song bpm
    public int getSongBPM() {
        return song_bpm;
    }

    // set song bpm
    public void setSongBPM(int song_bpm) {
        this.song_bpm = song_bpm;
    }

    // get song duration
    public double getSongDuration() {
        return song_duration;
    }

    // set song duration
    public void setSongDuration(double song_duration) {
        this.song_duration = song_duration;
    }

    // get release year
    public String getReleaseYear() {
        return release_year;
    }

    // set release year
    public void setReleaseYear(String release_year) {
        this.release_year = release_year;
    }

    // get genre id
    public int getGenreID() {
        return genre_id;
    }

    // set genre id
    public void setGenreID(int genre_id) {
        this.genre_id = genre_id;
    }

    // get publisher id
    public int getPublisherID() {
        return publisher_id;
    }

    // set publisher id
    public void setPublisherID(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    // get album
    public int getAlbumID() {
        return album_id;
    }

    // set album id
    public void setAlbumID(int album_id) {
        this.album_id = album_id;
    }

    // get song key
    public String getSongKey() {
        return song_key;
    }

    // set song key
    public void setSongKey(String song_key) {
        this.song_key = song_key;
    }

    // set database
    @Override
    public void setDatabase(ChoosyDatabase db) {
        this.db = db;
    }

    // to string
    public String toString() {
        return String.format("%3d %-40s %-3d %3s %.2f %s %d %d %d", song_id, song_title, song_bpm, song_key,
                song_duration,
                release_year, genre_id, publisher_id, album_id);
    }

}