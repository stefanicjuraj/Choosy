package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ConnectionModel;

public class LoginController {
    private Stage mainStage;
    private Scene mainScene;
    private ConnectionModel model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfFname;

    @FXML
    private TextField tfLname;

    @FXML
    private TextField tfRegPassword;

    @FXML
    private TextField tfRegUsername;

    @FXML
    private TextField tfUsername;

    // handle login
    @FXML
    void handleLogin(ActionEvent event) {
        try {
            if (model.doLogin(tfUsername.getText(), pfPassword.getText())) {
                System.out.println("Login successful!");
                URL fxmlLocation = getClass().getResource("/view/BrowseView.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                Parent root = (Parent) fxmlLoader.load();
                BrowseController controller = fxmlLoader.getController();
                controller.addConnectionModel(model);
                controller.addStage(mainStage);
                controller.loadView(root);
            } else {
                System.out.println("Login failed!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // handle register
    @FXML
    void handleRegister(ActionEvent event) {
        if (model.doRegister(tfRegUsername.getText(), tfRegPassword.getText(), tfFname.getText(), tfLname.getText())) {
            System.out.println("Registered!");
        } else {
            System.out.println("Failed registration!");
        }
    }

    // add connection model
    public void addConnectionModel(ConnectionModel connectionModel) {
        this.model = connectionModel;
    }

    // load view
    public void loadView(Parent root) {
        mainScene = new Scene(root);
        mainStage = new Stage();

        mainStage.setTitle("Choosy");
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    // initialize
    @FXML
    void initialize() {
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert btnRegister != null : "fx:id=\"btnRegister\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert pfPassword != null : "fx:id=\"pfPassword\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert tfFname != null : "fx:id=\"tfFname\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert tfLname != null : "fx:id=\"tfLname\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert tfRegPassword != null
                : "fx:id=\"tfRegPassword\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert tfRegUsername != null
                : "fx:id=\"tfRegUsername\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert tfUsername != null : "fx:id=\"tfUsername\" was not injected: check your FXML file 'LoginView.fxml'.";

    }

}