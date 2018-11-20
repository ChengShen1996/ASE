package FrontDesk;

import API.API;
import CheckIn.addGuestController;
import Guest.GuestMenuController;
import database.DatabaseHandler;
import entity.Restaurant;
import entity.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class frontDeskMenuController implements Initializable {

    DatabaseHandler databaseHandler;
    ObservableList<Room> list = FXCollections.observableArrayList();
    ObservableList<Restaurant> yelplist = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle rb){
        databaseHandler = new DatabaseHandler();
        initCol();

    }
    @FXML
    private Tab restaurant_tab;

    @FXML
    private Button front_desk_guest;

    @FXML
    private Button resetBtn;

    @FXML
    private Button okBtn;

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
    private TableView<Restaurant> yelptable;

    @FXML
    private TableColumn<Restaurant, ImageView> thumbnail_col;

    @FXML
    private TableColumn<Restaurant,String> name_col;

    @FXML
    private TableColumn<Restaurant,String> rating_col;

    @FXML
    private TableColumn<Restaurant,String> location_col;

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
        thumbnail_col.setCellValueFactory(new PropertyValueFactory<>("img_url"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        location_col.setCellValueFactory(new PropertyValueFactory<>("location"));
        rating_col.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tableView.getItems().clear();
        yelptable.getItems().clear();
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

    public void loadRestaurant() {
        yelplist.clear();
        API yelpApi = new API();
        String term = "restaurants", location = "NYC";
        int limit = 2;
        try{
            List<String[]> restaurantList = yelpApi.Get_yelp(term,location,limit);
            String[] img_urls = restaurantList.get(0);
            String[] names = restaurantList.get(1);
            String[] address = restaurantList.get(2);
            String[] rating = restaurantList.get(3);

            for (int i = 0; i < limit; i++) {
                Image img = new Image(img_urls[i]);
//                ImageView imgView = new ImageView(img);
                yelplist.add(new Restaurant(new ImageView(img), names[i], address[i], rating[i]));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        yelptable.getItems().addAll(yelplist);

    }


}



