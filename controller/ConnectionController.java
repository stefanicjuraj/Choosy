package controller;

import model.ConnectionModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ConnectionController {

    private ConnectionModel model;
    private boolean connected;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField dbName;

    @FXML
    private TextField dbms;

    @FXML
    private TextField host;

    @FXML
    private PasswordField password;

    @FXML
    private TextField port;

    @FXML
    private TextField username;

    @FXML
    void handleConnect(ActionEvent event) {
        model = new ConnectionModel();
        connected = model.doConnect(
                String.format("jdbc:%s://%s:%s/%s", dbms.getText(), host.getText(), port.getText(), dbName.getText()),
                username.getText(), password.getText());
        if (connected) {
            System.out.println("Connected!");
            loadLoginView();
        }
        event.consume();
    }

    // load login view
    public void loadLoginView() {
        try {
            URL fxmlLocation = getClass().getResource("/view/LoginView.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Parent root = (Parent) fxmlLoader.load();
            LoginController controller = fxmlLoader.getController();
            controller.addConnectionModel(model);
            controller.loadView(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // initialize
    @FXML
    void initialize() {
        assert dbName != null : "fx:id=\"dbName\" was not injected: check your FXML file 'ConnectionView.fxml'.";
        assert dbms != null : "fx:id=\"dbms\" was not injected: check your FXML file 'ConnectionView.fxml'.";
        assert host != null : "fx:id=\"host\" was not injected: check your FXML file 'ConnectionView.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'ConnectionView.fxml'.";
        assert port != null : "fx:id=\"port\" was not injected: check your FXML file 'ConnectionView.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'ConnectionView.fxml'.";

    }

}