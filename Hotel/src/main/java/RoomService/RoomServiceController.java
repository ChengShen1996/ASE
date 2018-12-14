package RoomService;

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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomServiceController implements Initializable {
    DatabaseHandler databaseHandler;
    public void initialize(URL url, ResourceBundle rb){
        databaseHandler = new DatabaseHandler();

    }

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton room_service_save;

    @FXML
    private JFXTextField room_service_name;

    @FXML
    private JFXTextField room_service_num;

    @FXML
    private JFXButton room_service_cancel;

    @FXML
    private JFXTextField room_service_req;

    @FXML
    void addRoomService(ActionEvent event) {
        Boolean gone = false;
        String name = room_service_name.getText();
        int roomId = Integer.parseInt(room_service_num.getText());
        String requirement = room_service_req.getText();
        String currentRequirement = "";
        if(name.isEmpty() || requirement.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }
        String qu = "SELECT c.requirement, c.isGone " +
                "FROM CUSTOMER c " +
                "WHERE c.name = '" + name + "' AND c.roomId = " + roomId + " ";
        System.out.println("Cheng's part");
        System.out.println(qu);
        ResultSet rs = databaseHandler.execQuery(qu);
        try{
            if(!rs.next()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("No Such guest");
                alert.showAndWait();
                return;
            }
            while(rs.next()){
                gone = rs.getBoolean("isGone");
                if(gone){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("The guest has already checked out");
                    alert.showAndWait();
                    return;
                }
                currentRequirement = rs.getString("requirement");
            }
        }catch (SQLException ex){
            Logger.getLogger(RoomServiceController.class.getName()).log(Level.SEVERE,null,ex);
        }
        if( requirement.length()+currentRequirement.length()>200){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The requirement length is longer than 200");
            alert.showAndWait();
            return;
        }
        System.out.println(currentRequirement);

        requirement = currentRequirement + " ; " + requirement;
        String newqu = "UPDATE Customer " +
                "SET requirement = '" + requirement +
                "' WHERE name = '" + name + "' AND roomId = " + roomId + " ";
        System.out.println(newqu);

        if (databaseHandler.execAction(newqu)) {
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
    void cancelRoomService(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

}

