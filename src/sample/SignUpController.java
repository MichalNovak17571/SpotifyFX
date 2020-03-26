package sample;

import BCrypt.BCrypt;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpController {
    @FXML
    private Label error_field;
    @FXML
    private Button fb_sign_up_btn, sign_up_btn;
    @FXML
    private ImageView back_btn;
    @FXML
    private TextField username_field;
    @FXML
    private PasswordField password_field, repeat_password_field;
    @FXML
    private DatePicker date_picker;
    @FXML
    private AnchorPane pane;

    private String username, password, repeatedPassword, date;
    private int cislo;
    private boolean badUsername = false, badPassword = false, badDate = false;

    @FXML
    private void signUp(){
        username = username_field.getText();
        password = password_field.getText();
        repeatedPassword = repeat_password_field.getText();
        date = date_picker.getEditor().getText();
        if(username.length() <= 30) {
            if(username.length() >= 3) {
                    try {
                        DBConnection cn = DBConnection.getInstance();
                        Connection conn = cn.getConnection();
                        ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM accounts WHERE username LIKE '" + username + "';");
                        while (rs.next()) {
                            cislo = rs.getInt("count(*)");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (cislo > 0) {
                        error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-text-fill: white");
                        error_field.setText("Username is already taken");
                        error_field.setVisible(true);
                        badUsername = true;
                        badPassword = false;
                        badDate = false;
                        username_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                        password_field.setText("");
                        repeat_password_field.setText("");
                    } else {
                        if (password.length() <= 20) {
                            if (password.length() >= 6) {
                                if (password.equals(repeatedPassword)) {
                                    if (date_picker.getValue() != null) {
                                        try {
                                            DBConnection cn = DBConnection.getInstance();
                                            Connection conn = cn.getConnection();
                                            PreparedStatement post = conn.prepareStatement("INSERT INTO accounts(username, password, birthdate) VALUES('" + username + "', '" + BCrypt.hashpw(password, BCrypt.gensalt(12)) + "', '" + date + "');");
                                            post.executeUpdate();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        error_field.setStyle("-fx-background-color: #00ff00; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-text-fill: black");
                                        error_field.setVisible(true);
                                        badUsername = false;
                                        badPassword = false;
                                        badDate = false;
                                        username_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                                        password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                                        repeat_password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                                        date_picker.setStyle("-fx-background-color: #ffffff;");
                                        error_field.setText("You were successfully registered");
                                    } else {
                                        error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-text-fill: white");
                                        error_field.setText("Select birthdate first");
                                        error_field.setVisible(true);
                                        badDate = true;
                                        badUsername = false;
                                        badPassword = false;
                                        date_picker.setStyle("-fx-background-color: #ff4444;");
                                        username_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                                        password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                                        repeat_password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                                        password_field.setText("");
                                        repeat_password_field.setText("");
                                    }
                                } else {
                                    error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-text-fill: white");
                                    error_field.setText("Passwords do not match");
                                    error_field.setVisible(true);
                                    badPassword = true;
                                    badUsername = false;
                                    badDate = false;
                                    password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                                    repeat_password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                                    username_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                                    date_picker.setStyle("-fx-background-color: #ffffff;");
                                    password_field.setText("");
                                    repeat_password_field.setText("");
                                }
                            } else {
                                error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-text-fill: white");
                                error_field.setText("Password is too short (min 6 characters)");
                                error_field.setVisible(true);
                                badPassword = true;
                                badUsername = false;
                                badDate = false;
                                password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                                repeat_password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                                username_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                                date_picker.setStyle("-fx-background-color: #ffffff;");
                                password_field.setText("");
                                repeat_password_field.setText("");
                            }
                        } else {
                            error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-text-fill: white");
                            error_field.setText("Password is too long (max 20 characters)");
                            error_field.setVisible(true);
                            badPassword = true;
                            badUsername = false;
                            badDate = false;
                            password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                            repeat_password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                            username_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                            date_picker.setStyle("-fx-background-color: #ffffff;");
                            password_field.setText("");
                            repeat_password_field.setText("");
                        }
                    }
            }else{
                error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-text-fill: white");
                error_field.setText("Username is too short (min 3 characters)");
                error_field.setVisible(true);
                badUsername = true;
                badPassword = false;
                badDate = false;
                username_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                repeat_password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                date_picker.setStyle("-fx-background-color: #ffffff;");
                password_field.setText("");
                repeat_password_field.setText("");
            }
        }else{
            error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-text-fill: white");
            error_field.setText("Username is too long (max 30 characters)");
            error_field.setVisible(true);
            badUsername = true;
            badPassword = false;
            badDate = false;
            username_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
            password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
            repeat_password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
            date_picker.setStyle("-fx-background-color: #ffffff;");
            password_field.setText("");
            repeat_password_field.setText("");
        }
    }

    @FXML
    private void goBack() throws IOException {
        Parent changeScene = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene newScene = new Scene(changeScene);
        Stage stage = (Stage) back_btn.getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
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
            repeat_password_field.requestFocus();
        }
    }

    @FXML
    private void nextKeyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            date_picker.requestFocus();
        }
    }

    @FXML
    private void otherNextKeyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            sign_up_btn.fire();
        }
    }

    @FXML
    private void goMaybeBack(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ESCAPE){
            try {
                Parent changeScene = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene newScene = new Scene(changeScene);
                Stage stage = (Stage) pane.getScene().getWindow();
                stage.setScene(newScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void signUpWithFb(){
        error_field.setText("No co by si este nechcel. Pekne si tu ucet vytvor.");
        error_field.setVisible(true);
    }

    @FXML
    private void signFbOn(){
        fb_sign_up_btn.setStyle("-fx-background-color: #305090; -fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20;");
        fb_sign_up_btn.setEffect(new Glow(0.2));
    }

    @FXML
    private void signFbOff(){
        fb_sign_up_btn.setStyle("-fx-background-color: #3A5898; -fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20;");
        fb_sign_up_btn.setEffect(new Glow(0));
    }
    @FXML
    private void signUpOn(){
        sign_up_btn.setStyle("-fx-background-color: #18a048; -fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20;");
        sign_up_btn.setEffect(new Glow(0.45));
    }

    @FXML
    private void signUpOff(){
        sign_up_btn.setStyle("-fx-background-color: #1cb953; -fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20;");
        sign_up_btn.setEffect(new Glow(0));
    }

    @FXML
    private void usernameOn(){
        if(badUsername){
            username_field.setStyle("-fx-background-color: #763b3b; -fx-text-fill: white;");
        }else{
            username_field.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: white;");
        }
    }

    @FXML
    private void usernameOff(){
        if(badUsername){
            username_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white;");
        }else{
            username_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
        }
    }

    @FXML
    private void passwordOn(){
        if(badPassword){
            password_field.setStyle("-fx-background-color: #763b3b; -fx-text-fill: white;");
        }else{
            password_field.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: white;");
        }
    }

    @FXML
    private void passwordOff(){
        if(badPassword){
            password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white;");
        }else{
            password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
        }
    }

    @FXML
    private void repeatPasswordOn(){
        if(badPassword){
            repeat_password_field.setStyle("-fx-background-color: #763b3b; -fx-text-fill: white;");
        }else{
            repeat_password_field.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: white;");
        }
    }

    @FXML
    private void repeatPasswordOff(){
        if(badPassword){
            repeat_password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white;");
        }else{
            repeat_password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
        }
    }

    @FXML
    private void backBtnOn(){
        back_btn.setEffect(new Glow(0.6));
    }

    @FXML
    private void backBtnOff(){
        back_btn.setEffect(new Glow(0));
    }

}
