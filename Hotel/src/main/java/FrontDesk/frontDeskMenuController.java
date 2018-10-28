package FrontDesk;

import Guest.GuestMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class frontDeskMenuController {

    @FXML
    private Button front_desk_guest;

    @FXML
    private Button front_desk_recommend;

    @FXML
    private Button front_desk_room;



    @FXML
    void to_guestmenu(ActionEvent event) {
        loadWindow("/GuestMenu.fxml", "GuestMenu");
    }

    @FXML
    void to_rooms(ActionEvent event) {

    }

    @FXML
    void to_recommend(ActionEvent event) {

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
}



