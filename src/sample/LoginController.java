package sample;

import BCrypt.BCrypt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private Label error_field;
    @FXML
    private Button fb_log_in_btn, log_in_btn, sign_up_btn;
    @FXML
    private TextField username_field;
    @FXML
    private PasswordField password_field;

    private String password, ziskaneHeslo;
    private static String username;
    private int cislo = 2;

    @FXML
    private void logInWithFb(){
        error_field.setText("No co by si este nechcel. Pekne si tu ucet vytvor.");
        error_field.setVisible(true);
    }

    @FXML
    private void logIn(ActionEvent event) throws IOException {
        username = username_field.getText();
        password = password_field.getText();
        try {
            DBConnection cn = DBConnection.getInstance();
            Connection conn = cn.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM accounts WHERE username LIKE '" + username + "';");
            while(rs.next()){
                cislo = rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(cislo == 1) {
            try {
                DBConnection cn = DBConnection.getInstance();
                Connection conn = cn.getConnection();
                ResultSet rs = conn.createStatement().executeQuery("SELECT password FROM accounts WHERE username LIKE '" + username + "';");
                while(rs.next()){
                    ziskaneHeslo = rs.getString("password");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(BCrypt.checkpw(password, ziskaneHeslo)) {
                try {
                    DBConnection cn = DBConnection.getInstance();
                    Connection conn = cn.getConnection();
                    PreparedStatement post = conn.prepareStatement("INSERT INTO users_activity(username, state) VALUES('" + username + "', 'Log in');");
                    post.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Parent changeScene = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Scene newScene = new Scene(changeScene);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
                stage.show();
            }else{
                error_field.setText("Username or password is incorrect");
                error_field.setVisible(true);
                password_field.setText("");
            }
        }else if (cislo == 0){
            error_field.setText("Username or password is incorrect");
            error_field.setVisible(true);
            password_field.setText("");
        }else{
            error_field.setText("A hopa. Daco je velmi zle.");
            error_field.setVisible(true);
            password_field.setText("");
        }
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            password_field.requestFocus();
        }
    }

    @FXML
    private void otherKeyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            log_in_btn.fire();
        }
    }

    @FXML
    private void signUp(ActionEvent event) throws IOException{
        Parent changeScene = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene newScene = new Scene(changeScene);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    private void logFbOn(){
        fb_log_in_btn.setStyle("-fx-background-color: #305090; -fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20;");
        fb_log_in_btn.setEffect(new Glow(0.2));
    }

    @FXML
    private void logFbOff(){
        fb_log_in_btn.setStyle("-fx-background-color: #3A5898; -fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20;");
        fb_log_in_btn.setEffect(new Glow(0));
    }

    @FXML
    private void logInOn(){
        log_in_btn.setStyle("-fx-background-color: #18a048; -fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20;");
        log_in_btn.setEffect(new Glow(0.45));
    }

    @FXML
    private void logInOff(){
        log_in_btn.setStyle("-fx-background-color: #1cb953; -fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20;");
        log_in_btn.setEffect(new Glow(0));
    }

    @FXML
    private void signUpOn(){
        sign_up_btn.setEffect(new Glow(0.6));
    }

    @FXML
    private void signUpOff(){
        sign_up_btn.setEffect(new Glow(0));
    }

    @FXML
    private void usernameOn(){
        username_field.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: white;");
    }

    @FXML
    private void usernameOff(){
        username_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
    }

    @FXML
    private void passwordOn(){
        password_field.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: white;");
    }

    @FXML
    private void passwordOff(){
        password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
    }

    public static String getUsername(){
        return username;
    }

    public static void setUsername(String newUsername){
        username = newUsername;
    }

}
