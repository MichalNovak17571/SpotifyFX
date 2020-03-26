package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BandInfoController implements Initializable {
    @FXML
    private Label band_label, song_label, year_label, colour_label, state_label;
    @FXML
    private ImageView close_btn;

    private String bandName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DBConnection cn = DBConnection.getInstance();
            Connection conn = cn.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM songs WHERE id = " + LibraryController.getId() + ";");
            while(rs.next()){
                bandName = rs.getString("name");
                band_label.setText(bandName);
                song_label.setText(rs.getString("song"));
            }
            rs = conn.createStatement().executeQuery("SELECT * FROM band_detail WHERE band_name LIKE '" + bandName + "';");
            if(rs.next()){
                year_label.setText(rs.getString("year"));
                colour_label.setText(rs.getString("colour"));
                state_label.setText(rs.getString("state"));
            }else{
                year_label.setText("No info for this band");
                colour_label.setText("No info for this band");
                state_label.setText("No info for this band");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void close(){
        Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCloseBtn(){
        close_btn.setEffect(new Glow(1));
    }

    @FXML
    private void offCloseBtn(){
        close_btn.setEffect(new Glow(0));
    }
}
