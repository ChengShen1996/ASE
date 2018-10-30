/**
 * Created by jeremyjiang on 10/29/18.
 */
import database.DatabaseHandler;
import javafx.scene.control.Alert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.util.ResourceBundle;

import static org.testfx.matcher.control.LabeledMatchers.hasText;
public class checkInTest {

    DatabaseHandler databaseHandler;
    @Before
    public void setup(){

        databaseHandler = new DatabaseHandler();

    }


    @Test
    public void testInsert() {
        String name = "name1";
        String roomId = "1";
        String checkInDate = "2012-12-12";
        String checkOutDate = "2012-12-13";
        String requirement = "burger";


        String qu = "INSERT INTO CUSTOMER VALUES("
                + "'" + name + "',"
                + roomId + ","
                + "'" + checkInDate + "',"
                + "'" + checkOutDate + "',"
                + "'" + requirement + "',"
                + "'" + "false" + "'"
                + ")";

        assertEquals(databaseHandler.execAction(qu),true);
    }
}
