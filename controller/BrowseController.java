package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.ConnectionModel;

public class BrowseController {
    private Stage mainStage;
    private Scene mainScene;
    private ConnectionModel model;

    private String current;
    private Dialog<ButtonType> addSongDialog;
    private Dialog<ButtonType> promoteDialog;
    private Dialog<ButtonType> addToPlaylistDialog;
    private Dialog<ButtonType> createPlaylist;

    // dialog textfields
    private TextField songName;
    private TextField songArtist;
    private TextField songAlbum;
    private TextField songGenre;
    private TextField songBPM;
    private TextField songKey;
    private TextField songDuration;
    private TextField songReleaseYear;
    private TextField songPublisher;

    // dialog comboboxes
    private ComboBox<String> users;
    private ComboBox<String> roles;
    private ComboBox<String> playlists;

    /* FXML view attributes */

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblBrowse;

    @FXML
    private Label lblFavorites;

    @FXML
    private Label lblPlaylists;

    @FXML
    private ListView<String> lvSongs;

    @FXML
    private Button btnFavorites;

    @FXML
    private Button btnRemoveFavorite;

    @FXML
    private Button btnPlaylist;

    @FXML
    private Label lblCurrentArtist;

    @FXML
    private Label lblCurrentSong;

    @FXML
    private Label lblPromote;

    @FXML
    private Button btnAddSong;

    @FXML
    private Button btnRemoveSong;

    @FXML
    private Button btnNewPlaylist;

    @FXML
    private Button btnRemovePlaylist;

    /* handlers */

    @FXML
    void handleBrowse(MouseEvent event) {
        utilityVisibility(true);
        btnNewPlaylist.setVisible(false);
        btnRemovePlaylist.setVisible(false);
        lvSongs.getItems().clear();
        lblCurrentSong.setText("Browse");

        ObservableList<String> songs = FXCollections.observableArrayList();
        for (String song : model.getFormattedSongs()) {
            songs.add(song);
        }
        lvSongs.getItems().addAll(songs);
        event.consume();
    }

    @FXML
    void handleFavorites(MouseEvent event) {
        utilityVisibility(false);
        lblCurrentSong.setText("Favorites");
        btnNewPlaylist.setVisible(false);
        btnRemovePlaylist.setVisible(false);
        lvSongs.getItems().clear();

        ObservableList<String> favorites = FXCollections.observableArrayList();
        for (String favorite : model.getCurrentUserSongs()) {
            favorites.add(favorite);
        }
        lvSongs.getItems().addAll(favorites);
        event.consume();
    }

    @FXML
    void handlePlaylists(MouseEvent event) {
        utilityVisibility(false);
        btnRemoveFavorite.setVisible(false);
        btnNewPlaylist.setVisible(true);
        btnRemovePlaylist.setVisible(true);
        lblCurrentSong.setText("Playlists");
        lvSongs.getItems().clear();

        ObservableList<String> playlists = FXCollections.observableArrayList();
        for (String playlist : model.getCurrentUserPlaylists()) {
            playlists.add(playlist);
        }
        lvSongs.getItems().addAll(playlists);
        event.consume();
    }

    @FXML
    void handleAddToPlaylist(ActionEvent event) {
        addToPlaylistDialog = new Dialog<>();
        addToPlaylistDialog.setTitle("Add to Playlist");
        addToPlaylistDialog.setHeaderText("Select a playlist");

        addToPlaylistDialog.setResizable(false);
        playlists = new ComboBox<>(FXCollections.observableArrayList(model.getCurrentUserPlaylists()));

        GridPane grid = new GridPane();
        grid.add(new Label("Playlists: "), 1, 1);
        grid.add(playlists, 2, 1);
        addToPlaylistDialog.getDialogPane().setContent(grid);

        addToPlaylistDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        addToPlaylistDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String playlist = playlists.getValue().trim();

                if (model.addToPlaylist(current, playlist)) {
                    System.out.println("Song added to playlist");
                } else {
                    System.out.println("Song not added to playlist");
                }
            }
        });
    }

    @FXML
    void handleNewPlaylist(ActionEvent event) {
        createPlaylist = new Dialog<>();
        createPlaylist.setTitle("New Playlist");
        createPlaylist.setHeaderText("Create a new playlist");

        createPlaylist.setResizable(false);

        GridPane grid = new GridPane();

        grid.add(new Label("Playlist Name: "), 1, 1);
        grid.add(new Label("Playlist Description: "), 1, 2);

        TextField playlistName = new TextField();
        TextField playlistDescription = new TextField();

        grid.add(playlistName, 2, 1);
        grid.add(playlistDescription, 2, 2);

        createPlaylist.getDialogPane().setContent(grid);

        createPlaylist.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        createPlaylist.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String name = playlistName.getText().trim();
                String description = playlistDescription.getText().trim();
                if (model.createPlaylist(name, description)) {
                    System.out.println("Playlist created");
                } else {
                    System.out.println("Playlist not created");
                }
            }
        });
    }

    @FXML
    void handleRemovePlaylist(ActionEvent event) {
        if (current != null) {
            if (model.removePlaylist(current)) {
                System.out.println("Removed Playlist");
            } else {
                System.out.println("Not removed Playlist");
            }
        }
    }

    @FXML
    void handleAddToFavorites(ActionEvent event) {
        if (current != null) {
            if (model.addToFavorites(Integer.parseInt(current))) {
                System.out.println("Added to Favorites");
            } else {
                System.out.println("Already in Favorites");
            }
        }
    }

    @FXML
    void handleRemoveFromFavorites(ActionEvent event) {
        if (current != null) {
            if (model.removeFromFavorites(Integer.parseInt(current))) {
                System.out.println("Removed from Favorites");
            } else {
                System.out.println("Not removed from Favorites");
            }
        }
    }

    @FXML
    void handlePromote(MouseEvent event) {
        // promotion Dialog Box
        promoteDialog = new Dialog<>();
        promoteDialog.setTitle("Promote User");
        promoteDialog.setHeaderText("Select a user to promote");

        promoteDialog.setResizable(false);
        users = new ComboBox<>(FXCollections.observableArrayList(model.getUsernames()));
        roles = new ComboBox<>(FXCollections.observableArrayList(model.getRoleNames()));

        GridPane grid = new GridPane();
        grid.add(new Label("Users: "), 1, 1);
        grid.add(users, 2, 1);
        grid.add(new Label("Roles: "), 1, 2);
        grid.add(roles, 2, 2);
        promoteDialog.getDialogPane().setContent(grid);

        promoteDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        promoteDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String user = users.getValue().trim();
                String role = roles.getValue().trim();
                if (model.promoteUser(user, role)) {
                    System.out.println("Promoted user");
                } else {
                    System.out.println("User not promoted");
                }
            }
        });
    }

    @FXML
    void handleAddSong(ActionEvent event) {
        addSongDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String song_name = songName.getText();
                String song_artist = songArtist.getText();
                String song_album = songAlbum.getText();
                String song_genre = songGenre.getText();
                String song_bpm = songBPM.getText();
                String song_key = songKey.getText();
                String song_duration = songDuration.getText();
                String song_release_year = songReleaseYear.getText();
                String song_publisher = songPublisher.getText();

                if (model.addSong(song_name, song_artist, song_album, song_genre, song_bpm, song_key, song_duration,
                        song_release_year, song_publisher)) {
                    System.out.println("ArtistSong dependency created (Song created)");
                } else {
                    System.out.println("ArtistSong dependency not created (Song not created)");
                }
            }
        });
    }

    @FXML
    void handleRemoveSong(ActionEvent event) {
        if (current != null) {
            if (model.removeSong(Integer.parseInt(current))) {
                System.out.println("Removed song from database");
            } else {
                System.out.println("Not removed from database");
            }
        }
    }

    /**
     * Utility methods for creating new views with
     * their own controller that is connected to the
     * same model.
     */

    public void loadView(Parent root) {
        model.createTables();
        String role = model.getCurrentUserRole().trim();
        if (role.equals("Admin")) {
            adminMode();
        } else if (role.equals("Editor")) {
            editorMode();
        } else {
            generalMode();
        }
        mainScene = new Scene(root);

        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    public void addConnectionModel(ConnectionModel connectionModel) {
        this.model = connectionModel;
    }

    public void addStage(Stage stage) {
        this.mainStage = stage;
    }

    /* Visibility methods */

    public void utilityVisibility(boolean visible) {
        btnPlaylist.setVisible(visible);
        btnFavorites.setVisible(visible);
        lblCurrentArtist.setVisible(visible);
        lblCurrentArtist.setText("");
        btnAddSong.setVisible(visible);
        btnRemoveSong.setVisible(visible);
        btnRemoveFavorite.setVisible(!visible);
    }

    private void adminMode() {
        btnAddSong.setVisible(true);
        btnRemoveSong.setVisible(true);
        lblPromote.setVisible(true);
    }

    private void generalMode() {
        btnAddSong.setVisible(false);
        btnRemoveSong.setVisible(false);
        lblPromote.setVisible(false);
    }

    private void editorMode() {
        btnAddSong.setVisible(true);
        btnRemoveSong.setVisible(true);
        lblPromote.setVisible(false);
    }

    @FXML
    void initialize() {
        utilityVisibility(true);
        btnRemovePlaylist.setVisible(false);
        btnNewPlaylist.setVisible(false);
        // Initializing the dialog box
        addSongDialog = new Dialog<>();
        addSongDialog.setTitle("Add Song");
        addSongDialog.setHeaderText("Add a song to the database");
        addSongDialog.setResizable(false);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        songName = new TextField();
        songName.setText("Animals");
        songName.setPromptText("Song name");
        songArtist = new TextField();
        songArtist.setText("Martin Garrix");
        songArtist.setPromptText("Artist");
        songAlbum = new TextField();
        songAlbum.setText("Animals");
        songAlbum.setPromptText("Album");
        songGenre = new TextField();
        songGenre.setText("EDM");
        songGenre.setPromptText("Genre");
        songBPM = new TextField();
        songBPM.setText("128");
        songBPM.setPromptText("BPM");
        songKey = new TextField();
        songKey.setText("3B");
        songKey.setPromptText("Key");
        songDuration = new TextField();
        songDuration.setText("5.08");
        songDuration.setPromptText("Duration");
        songReleaseYear = new TextField();
        songReleaseYear.setText("2013");
        songReleaseYear.setPromptText("Release Year");
        songPublisher = new TextField();
        songPublisher.setText("Spinnin Records");
        songPublisher.setPromptText("Publisher");

        gridPane.add(new Label("Song Name:"), 0, 0);
        gridPane.add(songName, 0, 1);
        gridPane.add(new Label("Artist:"), 0, 2);
        gridPane.add(songArtist, 0, 3);
        gridPane.add(new Label("Album:"), 0, 4);
        gridPane.add(songAlbum, 0, 5);
        gridPane.add(new Label("Publisher:"), 0, 6);
        gridPane.add(songPublisher, 0, 7);
        gridPane.add(new Label("Genre:"), 0, 8);
        gridPane.add(songGenre, 0, 9);
        gridPane.add(new Label("BPM:"), 1, 0);
        gridPane.add(songBPM, 1, 1);
        gridPane.add(new Label("Key:"), 1, 2);
        gridPane.add(songKey, 1, 3);
        gridPane.add(new Label("Duration:"), 1, 4);
        gridPane.add(songDuration, 1, 5);
        gridPane.add(new Label("Release Year:"), 1, 6);
        gridPane.add(songReleaseYear, 1, 7);

        addSongDialog.getDialogPane().setContent(gridPane);

        addSongDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // opening a listener for the listview items
        lvSongs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    String[] song = newValue.split("\\|");
                    current = song[0].trim();
                    if (song.length > 1) {
                        lblCurrentArtist.setText(song[1]);
                        lblCurrentSong.setText(song[2]);
                    }
                }
            }
        });
    }

}