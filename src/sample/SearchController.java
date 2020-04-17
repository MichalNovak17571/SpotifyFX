package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class SearchController implements Initializable {

    @FXML
    private ImageView log_out_btn, close_btn, home_button, search_button, library_button, search_database_button;
    @FXML
    private Label username;
    @FXML
    private TableView<CreateTable> table;
    @FXML
    private TableColumn<CreateTable, Integer> col_band;
    @FXML
    private TableColumn<CreateTable, String> col_song;
    ObservableList<CreateTable> obList = FXCollections.observableArrayList();
    @FXML
    private TextField search_field;

    private String key, usernameText = LoginController.getUsername();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(usernameText);
    }

    @FXML
    private void HomeButtonPressed () throws IOException {
        Parent changeScene = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene newScene = new Scene(changeScene);
        Stage stage = (Stage) home_button.getScene().getWindow();
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
    private void search(){
        TableView.TableViewSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        key = search_field.getText();
        table.setItems(loadTable(key));
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            search();
        }
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
    private void userData() throws IOException{
        Parent changeScene = FXMLLoader.load(getClass().getResource("UserData.fxml"));
        Scene newScene = new Scene(changeScene);
        Stage stage = (Stage) username.getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    private void OverButton () {
        search_database_button.setEffect(new Glow(1));
    }

    @FXML
    private void OffTheButton () {
        search_database_button.setEffect(new Glow(0));
    }

    @FXML
    private void OnHome(){
        home_button.setEffect(new Glow(1));
    }

    @FXML
    private void OffHome(){
        home_button.setEffect(new Glow(0));
    }

    @FXML
    private void OnSearch(){
        search_button.setEffect(new Glow(1));
    }

    @FXML
    private void OffSearch(){
        search_button.setEffect(new Glow(0.3));
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

    private ObservableList loadTable (String key){
        table.getItems().clear();
        try {
            DBConnection cn = DBConnection.getInstance();
            Connection conn = cn.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM songs WHERE id_user = (SELECT id FROM accounts WHERE username like '" + LoginController.getUsername() + "') AND (name LIKE '%" + key + "%' OR song LIKE '%" + key + "%');");
            while(rs.next()){
                obList.add(new CreateTable(rs.getInt("id"), rs.getString("name"), rs.getString("song"), rs.getInt("id_user")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        col_band.setCellValueFactory(new PropertyValueFactory<>("band"));
        col_song.setCellValueFactory(new PropertyValueFactory<>("song"));
        return obList;
    }
}
