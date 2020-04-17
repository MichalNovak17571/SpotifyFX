package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private ImageView log_out_btn, close_btn, home_button, search_button, library_button;
    @FXML
    private Label username;
    private String usernameText = LoginController.getUsername();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(usernameText);
    }

    @FXML
    private void SearchButtonPressed () throws IOException {
        Parent changeScene = FXMLLoader.load(getClass().getResource("Search.fxml"));
        Scene newScene = new Scene(changeScene);
        Stage stage = (Stage) search_button.getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    private void LibraryButtonPressed () throws IOException {
        Parent changeScene = FXMLLoader.load(getClass().getResource("Library.fxml"));
        Scene newScene = new Scene(changeScene);
        Stage stage = (Stage) library_button.getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    private void userData() throws IOException{
        Parent changeScene = FXMLLoader.load(getClass().getResource("UserData.fxml"));
        Scene newScene = new Scene(changeScene);
        Stage stage = (Stage) username.getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    private void logOut () throws IOException {
        try {
            DBConnection cn = DBConnection.getInstance();
            Connection conn = cn.getConnection();
            PreparedStatement post = conn.prepareStatement("INSERT INTO users_activity(username, state) VALUES('" + usernameText + "', 'Log out');");
            post.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Parent changeScene = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene newScene = new Scene(changeScene);
        Stage stage = (Stage) log_out_btn.getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    private void close(){
        try {
            DBConnection cn = DBConnection.getInstance();
            Connection conn = cn.getConnection();
            PreparedStatement post = conn.prepareStatement("INSERT INTO users_activity(username, state) VALUES('" + usernameText + "', 'Log out');");
            post.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void OnHome(){
        home_button.setEffect(new Glow(1));
    }

    @FXML
    private void OffHome(){
        home_button.setEffect(new Glow(0.3));
    }

    @FXML
    private void OnSearch(){
        search_button.setEffect(new Glow(1));
    }

    @FXML
    private void OffSearch(){
        search_button.setEffect(new Glow(0));
    }

    @FXML
    private void OnLibrary(){
        library_button.setEffect(new Glow(1));
    }

    @FXML
    private void OffLibrary(){
        library_button.setEffect(new Glow(0));
    }

    @FXML
    private void backBtnOn(){
        log_out_btn.setEffect(new Glow(1));
    }

    @FXML
    private void backBtnOff(){
        log_out_btn.setEffect(new Glow(0));
    }

    @FXML
    private void onCloseBtn(){
        close_btn.setEffect(new Glow(1));
    }

    @FXML
    private void offCloseBtn(){
        close_btn.setEffect(new Glow(0));
    }

    @FXML
    private void onName(){
        username.setEffect(new Glow(0.6));
    }

    @FXML
    private void offName(){
        username.setEffect(new Glow(0));
    }
}
