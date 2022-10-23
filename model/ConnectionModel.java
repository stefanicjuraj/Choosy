package model;

import java.util.ArrayList;
import java.util.List;

import tables.Album;
import tables.Artist;
import tables.ArtistSong;
import tables.Favorites;
import tables.Genre;
import tables.Playlist;
import tables.PlaylistSong;
import tables.Publisher;
import tables.Song;
import tables.Table;
import tables.User;

/**
 * This class is designed to enable the controller to
 * provide the view with data from the ChoosyDatabse
 * by offering adapter methods for the establishment
 * and closing of the connection with the database.
 */

public class ConnectionModel {
    private ChoosyDatabase db;
    private User user;
    private Artist artist;
    private Album album;
    private Publisher publisher;
    private Song song;
    private Genre genre;
    private Favorites favorites;
    private ArtistSong artistSong;
    private Playlist playlist;
    private PlaylistSong playlistSong;

    List<Table> tables = new ArrayList<Table>();

    // connect
    public boolean doConnect(String url, String username, String password) {
        System.out.println("Connecting to database...");
        db = new ChoosyDatabase(url, username, password);
        if (db.connect()) {
            return true;
        } else {
            return false;
        }
    }

    // disconnect
    public boolean doDisconnect() {
        return db.close();
    }

    // login
    public boolean doLogin(String username, String password) {
        if (db.login(username, password)) {
            user = new User(username, "", "", password);
            return true;
        } else {
            return false;
        }
    }

    // register
    public boolean doRegister(String username, String password, String fName, String lName) {
        return db.register(username, password, fName, lName);
    }

    public boolean initializeTable(Table table) {
        if (!table.exists()) { // if doesn't exist
            table.generateID(); // create new ID
            return table.put(); // insert into database
        } else {
            return table.findID(); // find ID
        }
    }

    /**
     * Add song to database by first creating the
     * artist that will be stored inside of the album
     * table as a foreign key. After that create the
     * album table, genre table, and publisher table.
     * Finally create the song table. All tables need to
     * have the database of this class to be initialized
     * inside of them.
     */
    public boolean addSong(String songName, String songArtist, String songAlbum, String songGenre, String songBPM,
            String songKey, String songDuration, String songReleaseYear, String songPublisher) {
        // initialize artist
        artist = new Artist(0, songArtist);
        artist.setDatabase(db);
        initializeTable(artist);
        artist.fetch();
        // initialize album
        album = new Album(0, songAlbum, songReleaseYear, artist.getArtistID());
        album.setDatabase(db);
        album.addToLength(Double.parseDouble(songDuration));
        initializeTable(album);
        album.fetch();
        // initialize publisher
        publisher = new Publisher(0, songPublisher);
        publisher.setDatabase(db);
        initializeTable(publisher);
        publisher.fetch();
        // initialize genre
        genre = new Genre(0, songGenre);
        genre.setDatabase(db);
        initializeTable(genre);
        genre.fetch();
        // initialize song
        song = new Song(0, songName, Integer.parseInt(songBPM), songKey, Double.parseDouble(songDuration),
                songReleaseYear, genre.getGenreID(), publisher.getPublisherID(), album.getAlbumID());
        song.setDatabase(db);
        if (song.exists() == false) {
            song.generateID();
            if (song.put()) {
                System.out.println("Song added");
                artistSong = new ArtistSong(artist.getArtistID(), song.getSongID());
                artistSong.setDatabase(db);
                return artistSong.put();
            } else {
                System.out.println("Song not added");
                return false;
            }
        } else {
            System.out.println("Song already exists");
            return false;
        }
    }

    // remove song
    public boolean removeSong(int songID) {
        favorites = new Favorites(getUsername(), songID);
        favorites.setDatabase(db);
        if (favorites.songExists() == true) {
            favorites.removeFromAllTables();
        }
        artistSong = new ArtistSong(0, songID);
        artistSong.setDatabase(db);
        artistSong.findID();
        artistSong.removeSongOnly();
        song = new Song(songID);
        song.setDatabase(db);
        return song.remove();
    }

    /**
     * Gives the Favorites table the ID of the song
     * and posts it to the database. If it already
     * exists, it will not be added again.
     * 
     * @param song_id
     * @return true if the song was added to the favorites
     */
    public boolean addToFavorites(int song_id) {
        System.out.println("Adding to favorites...");
        favorites.setUsername(getUsername());
        favorites.setSongID(song_id);
        return favorites.put();
    }

    /**
     * Create a row in the playlist_song table that
     * will be used to link the song to the playlist.
     */
    public boolean addToPlaylist(String song_name, String playlist_name) {
        System.out.println("Adding to playlist...");
        return true;
    }

    /**
     * Removes the song from the Favorites table
     * 
     * @param song_id
     * @return true if the song was removed from the favorites
     */
    public boolean removeFromFavorites(int song_id) {
        System.out.println("Removing from favorites...");
        favorites.setUsername(getUsername());
        favorites.setSongID(song_id);
        return favorites.remove();
    }

    // get current user role
    public String getCurrentUserRole() {
        String query = String.format(
                "SELECT role_name FROM roles JOIN user_role ON user_role.role_id = roles.role_id WHERE user_role.username = '%s'",
                getUsername());
        String role = db.getData(query).get(0).get(0);
        System.out.println(role);
        return role;
    }

    // get username
    public String getUsername() {
        return user.getUserID();
    }

    // get artists
    public ArrayList<ArrayList<Artist>> getArtists() {
        return artist.getTable();
    }

    // get songs
    public ArrayList<Song> getSongs() {
        return song.getTable();
    }

    public ArrayList<Favorites> getFavorites() {
        return favorites.getTable();
    }

    /**
     * Returns all IDs of songs that were favorited
     * by the user.
     */
    public ArrayList<String> getFavoriteSongIDs() {
        ArrayList<String> list = new ArrayList<String>();
        for (Favorites favorite : getFavorites()) {
            if (favorite.getUsername().equals(getUsername())) {
                list.add(favorite.getSongID() + "");
            }
        }
        return list;
    }

    /**
     * Returns all of the songs that the user has
     * favorited.
     * 
     * @return list of favorited songs
     */
    public ArrayList<String> getCurrentUserSongs() {
        ArrayList<String> currentUserSongs = new ArrayList<String>();
        for (String song : getFormattedSongs()) {
            String[] songInfo = song.split("\\|");
            for (String favorite : getFavoriteSongIDs()) {
                if (songInfo[0].trim().equals(favorite.trim())) {
                    currentUserSongs.add(song);
                }
            }
        }
        return currentUserSongs;
    }

    /**
     * Returns all playlists inside of playlist table.
     */
    public ArrayList<Playlist> getPlaylists() {
        playlist.setDatabase(db);
        return playlist.getTable();
    }

    /**
     * Return an arraylist of playlists that have
     * the foreign key username of the current user.
     * 
     * @return
     */
    public ArrayList<String> getCurrentUserPlaylists() {
        ArrayList<String> currentUserPlaylists = new ArrayList<String>();
        for (Playlist playlist : getPlaylists()) {
            if (playlist.getUsername().equals(getUsername())) {
                currentUserPlaylists.add(playlist.getPlaylistName());
            }
        }
        return currentUserPlaylists;
    }

    /**
     * This method formats a song so it could be displayed
     * inside of the ListView.
     * 
     * @return formattedSongs
     */
    public ArrayList<String> getFormattedSongs() {
        ArrayList<String> formattedSongs = new ArrayList<String>();
        for (Song song : song.getTable()) {
            if (song.getSongID() != 0) {
                String artistName = db.getData(String.format(
                        "SELECT artist_name FROM artist JOIN artist_song ON artist_song.artist_id = artist.artist_id WHERE song_id = %d",
                        song.getSongID())).get(0).get(0);

                String genreName = db
                        .getData(String.format("SELECT genre_name FROM genres WHERE genre_id = %d", song.getGenreID()))
                        .get(0).get(0);

                String publisherName = db
                        .getData(String.format("SELECT publisher_name FROM publisher WHERE publisher_id = %d",
                                song.getPublisherID()))
                        .get(0).get(0);

                String albumName = db.getData(String.format("SELECT album_name FROM albums WHERE album_id = %d",
                        song.getAlbumID())).get(0).get(0);

                formattedSongs.add(String.format("%4d | %-20s | %-30s | %3d | %3s | %.2f | %s | %-10s | %-25s | %-25s",
                        song.getSongID(), artistName, song.getSongTitle(),
                        song.getSongBPM(), song.getSongKey(), song.getSongDuration(), song.getReleaseYear(), genreName,
                        publisherName, albumName));
            }
        }
        return formattedSongs;
    }

    // get role names
    public ArrayList<String> getRoleNames() {
        String query = "SELECT role_name FROM roles";
        ArrayList<String> roleNames = new ArrayList<String>();
        for (ArrayList<String> roleRow : db.getData(query)) {
            roleNames.add(roleRow.get(0));
        }
        return roleNames;
    }

    // get usernames
    public ArrayList<String> getUsernames() {
        String query = "SELECT username FROM users";
        ArrayList<String> usernames = new ArrayList<String>();
        for (ArrayList<String> userRow : db.getData(query)) {
            usernames.add(userRow.get(0));
        }
        return usernames;
    }

    // promote user
    public boolean promoteUser(String user, String role) {
        String query = String.format("SELECT role_id FROM roles WHERE role_name = '%s'", role);
        int role_id = Integer.parseInt(db.getData(query).get(0).get(0));
        query = String.format("INSERT INTO user_role (username, role_id) VALUES ('%s', %d)", user, role_id);
        return db.setData(query);
    }

    /**
     * Create a playlist with the given name and description.
     */
    public boolean createPlaylist(String name, String description) {
        playlist.setDatabase(db);
        playlist.generateID();
        playlist.setPlaylistName(name);
        playlist.setPlaylistDescription(description);
        playlist.setUsername(getUsername());
        return playlist.put();
    }

    /**
     * Remove a playlist with the given name.
     */
    public boolean removePlaylist(String name) {
        playlist.setDatabase(db);
        playlist.setPlaylistName(name);
        playlist.setUsername(getUsername());
        playlist.fetchByName();
        playlistSong = new PlaylistSong(playlist.getPlaylistID());
        playlistSong.setDatabase(db);
        playlistSong.removeByPlaylistId();
        return playlist.remove();
    }

    // create tables
    public void createTables() {

        artist = new Artist();
        album = new Album();
        song = new Song();
        publisher = new Publisher();
        favorites = new Favorites();
        genre = new Genre();
        artistSong = new ArtistSong();
        playlist = new Playlist();
        playlistSong = new PlaylistSong();

        tables.add(user);
        tables.add(artist);
        tables.add(album);
        tables.add(song);
        tables.add(publisher);
        tables.add(favorites);
        tables.add(genre);
        tables.add(artistSong);
        tables.add(playlist);
        tables.add(playlistSong);

        for (Table table : tables) {
            table.setDatabase(db);
        }
        user.fetch();
    }

}