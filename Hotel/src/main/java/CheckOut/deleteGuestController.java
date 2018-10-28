package CheckOut;

import CheckIn.addGuestController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class deleteGuestController implements Initializable {

    DatabaseHandler databaseHandler;
    public void initialize(URL url, ResourceBundle rb){
        databaseHandler = new DatabaseHandler();

    }
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton check_out;

    @FXML
    private JFXButton check_out_cancel;

    @FXML
    private JFXTextField check_out_guest_name;

    @FXML
    private JFXTextField check_out_room_number;

    @FXML
    void CheckOutGuest(ActionEvent event) {
        System.out.println("here");
        String name = check_out_guest_name.getText();
        String roomId = check_out_room_number.getText();



        if (name.isEmpty() ||  roomId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }

        String qu = "UPDATE Customer "
                + "SET isGone = true "
                + "WHERE roomId = " + roomId
                + " AND name = '" + name + "'";
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


    }

    @FXML
    void CheckOutCancel(ActionEvent event) {
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

