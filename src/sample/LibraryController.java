package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LibraryController implements Initializable {
    @FXML
    private ImageView log_out_btn, close_btn, home_button, search_button, library_button;
    @FXML
    private TableView<CreateTable> table;
    @FXML
    private TableColumn<CreateTable, Integer> col_band;
    @FXML
    private TableColumn<CreateTable, String> col_song;
    ObservableList<CreateTable> obList = FXCollections.observableArrayList();
    @FXML
    private Label username;
    @FXML
    private Button add_btn;
    @FXML
    private TextField band_field, song_field;

    private String bandName, songName, usernameName;

    private static int selectedId;

    @FXML
    private void HomeButtonPressed () throws IOException {
        Parent changeScene = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene newScene = new Scene(changeScene);
        Stage stage = (Stage) home_button.getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    private void SearchButtonPressed () throws IOException {
        Parent changeScene = FXMLLoader.load(getClass().getResource("Search.fxml"));
        Scene newScene = new Scene(changeScene);
        Stage stage = (Stage) search_button.getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameName = LoginController.getUsername();
        username.setText(usernameName);
        TableView.TableViewSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<CreateTable> selectedItems = selectionModel.getSelectedItems();
        selectedItems = selectionModel.getSelectedItems();

        table.setItems(loadTable());


        selectedItems.addListener(new ListChangeListener<CreateTable>() {
            @Override
            public void onChanged(Change<? extends CreateTable> change) {
                selectedId = change.getList().get(0).getId();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("BandInfo.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 380, 200));
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void addToDb(){
        bandName = band_field.getText();
        songName = song_field.getText();
        try {
            DBConnection cn = DBConnection.getInstance();
            Connection conn = cn.getConnection();
            PreparedStatement post = conn.prepareStatement("INSERT INTO songs(name, song, id_user) VALUES('" + bandName + "', '" + songName + "', (SELECT id FROM accounts WHERE username LIKE '" + usernameName + "'));");
            post.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.getItems().clear();
        table.setItems(loadTable());
        band_field.setText("");
        song_field.setText("");
    }

    @FXML
    private void logOut () throws IOException {
        Parent changeScene = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene newScene = new Scene(changeScene);
        Stage stage = (Stage) log_out_btn.getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    private void close(){
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
        search_button.setEffect(new Glow(0));
    }

    @FXML
    private void OnLibrary(){
        library_button.setEffect(new Glow(1));
    }

    @FXML
    private void OffLibrary(){
        library_button.setEffect(new Glow(0.3));
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

    @FXML
    private void onBand(){
        band_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
    }

    @FXML
    private void offBand(){
        band_field.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: white;");
    }

    @FXML
    private void onSong(){
        song_field.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
    }

    @FXML
    private void offSong(){
        song_field.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: white;");
    }

    @FXML
    private void onAdd(){
        add_btn.setStyle("-fx-background-color: #18a048; -fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20;");
        add_btn.setEffect(new Glow(0.45));
    }

    @FXML
    private void offAdd(){
        add_btn.setStyle("-fx-background-color: #1cb953; -fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20;");
        add_btn.setEffect(new Glow(0));
    }

    private ObservableList loadTable (){
        try {
            DBConnection cn = DBConnection.getInstance();
            Connection conn = cn.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM songs WHERE id_user = (SELECT id FROM accounts WHERE username like '" + LoginController.getUsername() + "');");
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

    public static int getId(){
        return selectedId;
    }

}
