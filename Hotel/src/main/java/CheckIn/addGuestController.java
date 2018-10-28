package CheckIn;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.DatabaseHandler;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class addGuestController implements Initializable{

    DatabaseHandler databaseHandler;
    public void initialize(URL url, ResourceBundle rb){
        databaseHandler = new DatabaseHandler();

    }
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton save_button;

    @FXML
    private JFXButton cancel_button;

    @FXML
    private JFXTextField requirements;

    @FXML
    private JFXTextField check_out_date;

    @FXML
    private JFXTextField guest_name;

    @FXML
    private JFXTextField total_price;

    @FXML
    private JFXTextField room_number;

    @FXML
    private JFXTextField check_in_date;

    @FXML
    void addGuest(ActionEvent event) {
        String name = guest_name.getText();
        String roomId = room_number.getText();
        String checkInDate = check_in_date.getText();
        String checkOutDate = check_out_date.getText();
        String requirement = requirements.getText();


        if (name.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty() || roomId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }

        String qu = "INSERT INTO CUSTOMER VALUES("
                + "'" + name + "',"
                + roomId + ","
                + "'" + checkInDate + "',"
                + "'" + checkOutDate + "',"
                + "'" + requirement + "',"
                + "'" + "false" + "'"
                + ")";
        System.out.println(qu);

        if (databaseHandler.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();

        }
        else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();

        }
        checkData();
    }


    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void checkData() {
        String qu = "SELECT * FROM CUSTOMER";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {

            while (rs.next()) {
                String name = rs.getString("name");
                int roomId = rs.getInt("roomId");
                String checkInDate = rs.getString("checkInDate");
                String checkOutDate = rs.getString("checkOutDate");;
                boolean isGone = rs.getBoolean("isGone");
                System.out.println(name + " " + roomId + " " + checkInDate + " " + checkOutDate + " " +  isGone);


            }

        } catch (SQLException ex){
            Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE, null, ex);
        }



    }



}

