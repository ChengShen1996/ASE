import CheckIn.HotelAssistant;
import ManagerRoomPrice.SetRoomPriceController;
import database.DatabaseHandler;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.assertions.api.Assertions.assertThat;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

import static org.junit.Assert.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class checkInUITest extends ApplicationTest {
    HotelAssistant assistant = new HotelAssistant();
    DatabaseHandler databaseHandler = new DatabaseHandler();
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/addGuest.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void nametest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String[] q = showGuest();
                JFXTextField f1 = lookup("#requirements").query();
                JFXTextField f2 = lookup("#check_out_date").query();
                JFXTextField f3 = lookup("#guest_name").query();
                JFXTextField f5 = lookup("#room_number").query();
                JFXTextField f6 = lookup("#check_in_date").query();
                JFXButton bt1 = lookup("#save_button").query();
                JFXButton bt2 = lookup("#cancel_button").query();
                assertEquals("Requirements", f1.getPromptText() );
                assertEquals("Check-out Date", f2.getPromptText() );
                assertEquals("Guest Name", f3.getPromptText() );
                assertEquals("Room Number", f5.getPromptText() );
                assertEquals("Check-in Date", f6.getPromptText() );
                assertThat(bt1).hasText("Save");
                assertThat(bt2).hasText("Cancel");
                f3.setText(q[0]);
                f5.setText(q[1]);
                f1.setText("I need hot water");
                f2.setText("2018-12-25");
                f6.setText("2018-12-20");
                bt1.fire();
            }
        });
    }

    public String[] showGuest(){
        Random random = new Random();
        String[] result = new String[2];
        String qu = "SELECT c.name, c.roomId FROM CUSTOMER c";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()) {
                result[0] = rs.getString("name") + String.valueOf(random.nextGaussian());
                result[1] = String.valueOf(rs.getInt("roomId"));
                return result;
            }
        } catch (SQLException ex){
            Logger.getLogger(SetRoomPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}