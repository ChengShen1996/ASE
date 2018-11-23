package FrontDesk;

import CheckIn.addGuestController;
import Guest.GuestMenuController;
import database.DatabaseHandler;
import entity.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTextArea;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class frontDeskMenuController implements Initializable {

    DatabaseHandler databaseHandler;
    ObservableList<Room> list = FXCollections.observableArrayList();
    public void initialize(URL url, ResourceBundle rb){
        databaseHandler = new DatabaseHandler();
        initCol();

    }

    @FXML
    private Button front_desk_guest;

    @FXML
    private Button resetBtn;

    @FXML
    private Button okBtn;

    @FXML
    private JFXButton guest_info_Reset;

    @FXML
    private JFXButton guest_info_OK;

    @FXML
    private TextField startDateText;

    @FXML
    private TextField endDateText;

    @FXML
    private TableView<Room>  tableView;

    @FXML
    private TableColumn<Room, Integer> roomIdCol;

    @FXML
    private TableColumn<Room, String> typeCol;

    @FXML
    private JFXTextField Guest_info_name;

    @FXML
    private JFXTextField Guest_info_room;

    @FXML
    private JFXTextField guest_name;

    @FXML
    private JFXTextArea guest_requirement;

    @FXML
    private JFXTextField guest_room;

    @FXML
    private JFXTextField guest_check_in;

    @FXML
    private JFXTextField guest_check_out;

    @FXML
    private JFXTextField guest_total_price;

    @FXML
    void to_guestmenu(ActionEvent event) {
        loadWindow("/GuestMenu.fxml", "GuestMenu");
    }

    @FXML
    void show_map(ActionEvent event) {
        loadWindow("/showMap.fxml", "Map");
    }

    void loadWindow(String loc, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex){
            Logger.getLogger(frontDeskMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initCol() {
        roomIdCol.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("roomTypeName"));
        tableView.getItems().clear();
    }

    @FXML
    void show_availability(ActionEvent event) {
        tableView.getItems().clear();
        String fromDate = startDateText.getText();
        String toDate = endDateText.getText();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = formatter1.parse(fromDate);
            Date date2 = formatter1.parse(toDate);
        }
        catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Invalid Date Format");
            alert.showAndWait();
            return;

        }
        load_room();

    }

    void load_room() {
        list.clear();
        String fromDate = startDateText.getText();
        String toDate = endDateText.getText();
        String qu = "SELECT r.roomId, t.roomTypeName, t.price, t.roomTypeId FROM ROOM r, ROOMTYPE t " +
                "WHERE r.roomTypeId = t.roomTypeId " +
                "EXCEPT " +
                "SELECT r.roomId, t.roomTypeName, t.price, t.roomTypeId FROM CUSTOMER c, ROOM r, ROOMTYPE t " +
                "WHERE c.isGone = false AND " +
                "c.roomId = r.roomId AND " +
                "r.roomTypeId = t.roomTypeId AND (('"
                + fromDate + "' BETWEEN c.checkInDate AND c.checkOutDate) OR ('"
                + toDate + "' BETWEEN c.checkInDate AND c.checkOutDate))";

        System.out.println(qu);
        ResultSet rs = databaseHandler.execQuery(qu);

        try {
            while (rs.next()) {

                int roomId = rs.getInt("roomId");
                int price = rs.getInt("price");
                int roomTypeId = rs.getInt("roomTypeId");
                String roomTypeName = rs.getString("roomTypeName");

                list.add(new Room(roomId, roomTypeId, price, roomTypeName));

                System.out.println(roomId + ","
                        + roomTypeName );
            }

        } catch (SQLException ex){
            Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.getItems().addAll(list);
    }



    @FXML
    void Guest_info_initCol(ActionEvent event) {
        guest_name.clear();
        guest_room.clear();
        guest_check_in.clear();
        guest_check_out.clear();
        guest_total_price.clear();
        guest_requirement.clear();
    }

    @FXML
    void show_guest(ActionEvent event) {
        String name = Guest_info_name.getText();
        int room = Integer.parseInt(Guest_info_room.getText());
        String qu = "SELECT c.name, c.roomId, c.CheckInDate, c.CheckOutDate, c.requirement, c.totalPrice " +
                "FROM CUSTOMER c " +
                "WHERE c.name = '" + name + "' AND c.roomId = " + room + " ";
        System.out.println(qu);
        ResultSet rs = databaseHandler.execQuery(qu);

        try {
            while (rs.next()){
                String g_name = rs.getString("name");
                String g_room = rs.getString("roomId");
                String g_checkin = rs.getString("CheckInDate");
                String g_checkout = rs.getString("CheckOutDate");
                String g_req = rs.getString("requirement");
                int g_price = rs.getInt("totalPrice");
                System.out.println(g_name + " " + g_room + " " + g_checkin);
                guest_name.setText(g_name);
                guest_room.setText(g_room);
                guest_check_in.setText(g_checkin);
                guest_check_out.setText(g_checkout);
                guest_total_price.setText(String.valueOf(g_price));
                guest_requirement.setText(g_req);
            }
        } catch (SQLException ex){
            Logger.getLogger(addGuestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}



