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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Date;
import java.text.SimpleDateFormat;

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
    private Text priceText;

    @FXML
    private JFXButton Total_price_button;

    @FXML
    private JFXTextField room_number;

    @FXML
    private JFXTextField check_in_date;


    @FXML
    void Show_total_price(ActionEvent event) {
        int totalPrice = calculateTotalPrice();
        System.out.println(totalPrice);
        if (totalPrice == 0) {
            priceText.setText("$?");
            return;
        }
        priceText.setText("$" + totalPrice);

    }

    int calculateDateInterval(String d1, String d2) throws Exception {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = formatter1.parse(d1);
        Date date2 = formatter1.parse(d2);
        long difference =  (date2.getTime()-date1.getTime())/86400000;
        if (difference <= 0) {
            throw new Exception();
        }
        return (int) Math.abs(difference);

    }

    int calculateTotalPrice() {
        String roomId = room_number.getText();
        String checkInDate = check_in_date.getText();
        String checkOutDate = check_out_date.getText();
        int oneDayPrice = 0;
        int dayInterval = 0;

        if (checkInDate.isEmpty() || checkOutDate.isEmpty() || roomId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Room Number, Check-in Date, and Check-out Date");
            alert.showAndWait();
            return 0;
        }


        String qu = "SELECT price FROM ROOMTYPE, ROOM WHERE " +
                "ROOM.roomTypeId = ROOMTYPE.roomTypeId " +
                "AND ROOM.roomId = " + roomId;
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()) {
                oneDayPrice = rs.getInt("price");
            }
        } catch (SQLException ex){
            Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE, null, ex);

        }
        try {
            dayInterval = calculateDateInterval(checkInDate, checkOutDate);
        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Invalid Date Format");
            alert.showAndWait();
        }

        return oneDayPrice * dayInterval;

    }

    @FXML
    void addGuest(ActionEvent event) {
        String name = guest_name.getText();
        String roomId = room_number.getText();
        String checkInDate = check_in_date.getText();
        String checkOutDate = check_out_date.getText();
        String requirement = requirements.getText();
        int totalPrice = calculateTotalPrice();
        priceText.setText("$" + totalPrice);

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
                + "'" + "false" + "',"
                + totalPrice
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
        databaseHandler.checkData();
    }


    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

}

