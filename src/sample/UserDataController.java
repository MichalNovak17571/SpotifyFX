package sample;

import BCrypt.BCrypt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserDataController implements Initializable {
    @FXML
    private Label username, name_info, birth_info, reg_info ,error_field;
    @FXML
    private ImageView log_out_btn, close_btn;
    @FXML
    private Button delete_btn, username_btn, password_btn, rly_delete;
    @FXML
    private TextField username_field;
    @FXML
    private PasswordField password_field, rep_password_field;
    @FXML
    private RadioButton radio_btn;
    @FXML
    private TableView activity_table;
    @FXML
    private TableColumn column_activity, column_timestamp;

    private String usernameName, birthDate, registrationDate, newUsername, newPassword, repNewPassword;
    private int cislo;
    private boolean badPass = false, badUsername = false;

    ObservableList<CreateActivityTable> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameName = LoginController.getUsername();
        username.setText(usernameName);
        try {
            DBConnection cn = DBConnection.getInstance();
            Connection conn = cn.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT birthdate FROM accounts WHERE username LIKE '" + usernameName + "';");
            while (rs.next()){
                birthDate = rs.getString("birthdate");
            }
            rs = conn.createStatement().executeQuery("SELECT timestamp FROM accounts WHERE username LIKE '" + usernameName + "';");
            while (rs.next()){
                registrationDate = rs.getString("timestamp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        name_info.setText(usernameName);
        birth_info.setText(birthDate);
        reg_info.setText(registrationDate);
        activity_table.setItems(loadTable());
    }

    @FXML
    private void deleteAcc(){
            delete_btn.setVisible(false);
            delete_btn.setDisable(true);
            radio_btn.setVisible(true);
            radio_btn.setDisable(false);
            rly_delete.setVisible(true);
            rly_delete.setDisable(false);
    }

    @FXML
    private void perDelete() throws IOException {
        if(radio_btn.isSelected()){
            delete_btn.setVisible(true);
            delete_btn.setDisable(false);
            radio_btn.setVisible(false);
            radio_btn.setDisable(true);
            rly_delete.setVisible(false);
            rly_delete.setDisable(true);
            try {
                DBConnection cn = DBConnection.getInstance();
                Connection conn = cn.getConnection();
                PreparedStatement post = conn.prepareStatement("DELETE FROM songs WHERE id_user = (SELECT id FROM accounts WHERE username LIKE '" + usernameName + "');");
                post.executeUpdate();
                post = conn.prepareStatement("DELETE FROM accounts WHERE username LIKE '" + usernameName + "';");
                post.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Parent changeScene = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene newScene = new Scene(changeScene);
            Stage stage = (Stage) rly_delete.getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        }
    }

    @FXML
    private void changeUsername(){
        newUsername = username_field.getText();
        if(newUsername.length() <= 30){
            if(newUsername.length() >= 3) {
                try {
                    DBConnection cn = DBConnection.getInstance();
                    Connection conn = cn.getConnection();
                    ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM accounts WHERE username LIKE '" + newUsername + "';");
                    while (rs.next()) {
                        cislo = rs.getInt("count(*)");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (cislo == 0) {
                    try {
                        DBConnection cn = DBConnection.getInstance();
                        Connection conn = cn.getConnection();
                        PreparedStatement post = conn.prepareStatement("UPDATE accounts SET username = '" + newUsername + "' WHERE username LIKE '" + usernameName + "';");
                        post.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    usernameName = newUsername;
                    username.setText(usernameName);
                    name_info.setText(usernameName);
                    LoginController.setUsername(usernameName);
                    error_field.setText("Username was changed");
                    error_field.setStyle("-fx-background-color: #00ff00; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-text-fill: black");
                    error_field.setVisible(true);
                    username_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                    badUsername = false;
                } else {
                    error_field.setText("Username is already taken");
                    error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-text-fill: white");
                    error_field.setVisible(true);
                    username_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                    badUsername = true;
                }
            }else{
                error_field.setText("Username is too short (min 3 characters)");
                error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-text-fill: white");
                error_field.setVisible(true);
                username_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                badUsername = true;
            }
        }else{
            error_field.setText("Username is too long (max 30 characters)");
            error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-text-fill: white");
            error_field.setVisible(true);
            username_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
            badUsername = true;
        }
    }

    @FXML
    private void changePassword(){
        newPassword = password_field.getText();
        repNewPassword = rep_password_field.getText();
        if(newPassword.length() <= 20){
            if(newPassword.length() >= 6){
                if(newPassword.equals(repNewPassword)){
                    try {
                        DBConnection cn = DBConnection.getInstance();
                        Connection conn = cn.getConnection();
                        PreparedStatement post = conn.prepareStatement("UPDATE accounts SET password = '" + BCrypt.hashpw(newPassword, BCrypt.gensalt(12)) + "' WHERE username LIKE '" + usernameName + "';");
                        post.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    error_field.setText("Password was changed");
                    error_field.setStyle("-fx-background-color: #00ff00; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-text-fill: black");
                    error_field.setVisible(true);
                    password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                    rep_password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white");
                    badPass = false;
                }else{
                    error_field.setText("Passwords do not match");
                    error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-text-fill: white");
                    error_field.setVisible(true);
                    password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                    rep_password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                    password_field.setText("");
                    rep_password_field.setText("");
                    badPass = true;
                }
            }else{
                error_field.setText("Password is too short (min 6 characters)");
                error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-text-fill: white");
                error_field.setVisible(true);
                password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                rep_password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
                password_field.setText("");
                rep_password_field.setText("");
                badPass = true;
            }
        }else{
            error_field.setText("Password is too long (max 20 characters)");
            error_field.setStyle("-fx-background-color: #ff0000; -fx-padding: 3; -fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-text-fill: white");
            error_field.setVisible(true);
            password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
            rep_password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white");
            password_field.setText("");
            rep_password_field.setText("");
            badPass = true;
        }
    }

    @FXML
    private void logOut () throws IOException {
        try {
            DBConnection cn = DBConnection.getInstance();
            Connection conn = cn.getConnection();
            PreparedStatement post = conn.prepareStatement("INSERT INTO users_activity(username, state) VALUES('" + usernameName + "', 'Log out');");
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
            PreparedStatement post = conn.prepareStatement("INSERT INTO users_activity(username, state) VALUES('" + usernameName + "', 'Log out');");
            post.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goHome () throws IOException {
        Parent changeScene = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene newScene = new Scene(changeScene);
        Stage stage = (Stage) username.getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    private void onName(){
        username.setEffect(new Glow(0.6));
    }

    @FXML
    private void offName(){
        username.setEffect(new Glow(0));
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
    private void onUsernameBtn(){
        username_btn.setStyle("-fx-background-color: #18a048; -fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15;");
        username_btn.setEffect(new Glow(0.45));
    }

    @FXML
    private void offUsernameBtn(){
        username_btn.setStyle("-fx-background-color: #1cb953; -fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15;");
        username_btn.setEffect(new Glow(0));
    }

    @FXML
    private void onPasswordBtn(){
        password_btn.setStyle("-fx-background-color: #18a048; -fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15;");
        password_btn.setEffect(new Glow(0.45));
    }

    @FXML
    private void offPasswordBtn(){
        password_btn.setStyle("-fx-background-color: #1cb953; -fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15;");
        password_btn.setEffect(new Glow(0));
    }

    @FXML
    private void onDeleteBtn(){
        delete_btn.setStyle("-fx-background-color: #cc0000; -fx-background-radius: 30 30 30 30; -fx-border-radius: 30 30 30 30;");
        delete_btn.setEffect(new Glow(1));
    }

    @FXML
    private void offDeleteBtn(){
        delete_btn.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 30 30 30 30; -fx-border-radius: 30 30 30 30;");
        delete_btn.setEffect(new Glow(0));
    }

    @FXML
    private void onUsername(){
        if(badUsername){
            username_field.setStyle("-fx-background-color: #763b3b; -fx-text-fill: white;");
        }else{
            username_field.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: white;");
        }
    }

    @FXML
    private void offUsername(){
        if(badUsername){
            username_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white;");
        }else{
            username_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
        }
    }

    @FXML
    private void onPassword(){
        if(badPass){
            password_field.setStyle("-fx-background-color: #763b3b; -fx-text-fill: white;");
        }else{
            password_field.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: white;");
        }
    }

    @FXML
    private void offPassword(){
        if(badPass){
            password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white;");
        }else{
            password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
        }
    }

    @FXML
    private void onNewPassword(){
        if(badPass){
            rep_password_field.setStyle("-fx-background-color: #763b3b; -fx-text-fill: white;");
        }else{
            rep_password_field.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: white;");
        }
    }

    @FXML
    private void offNewPassword(){
        if(badPass){
            rep_password_field.setStyle("-fx-background-color: #884444; -fx-text-fill: white;");
        }else{
            rep_password_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
        }
    }

    @FXML
    private void onRlyDelete(){
        rly_delete.setStyle("-fx-background-color: #cc0000; -fx-background-radius: 30 30 30 30; -fx-border-radius: 30 30 30 30;");
        rly_delete.setEffect(new Glow(0.6));
    }

    @FXML
    private void offRlyDelete(){
        rly_delete.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 30 30 30 30; -fx-border-radius: 30 30 30 30;");
        rly_delete.setEffect(new Glow(0));
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            username_btn.fire();
        }
    }

    @FXML
    private void otherKeyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            rep_password_field.requestFocus();
        }
    }

    @FXML
    private void nextKeyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            password_btn.fire();
        }
    }

    @FXML
    private void goMaybeBack(KeyEvent keyEvent) throws IOException {
        if(keyEvent.getCode() == KeyCode.ESCAPE){
            Parent changeScene = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene newScene = new Scene(changeScene);
            Stage stage = (Stage) ((Node)keyEvent.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        }
    }

    private ObservableList loadTable (){
        try {
            DBConnection cn = DBConnection.getInstance();
            Connection conn = cn.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users_activity WHERE username LIKE '" + usernameName + "';");
            while(rs.next()){
                obList.add(new CreateActivityTable(rs.getInt("id"), rs.getString("username"), rs.getString("state"), rs.getString("timestamp")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        column_activity.setCellValueFactory(new PropertyValueFactory<>("state"));
        column_timestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        return obList;
    }

}
