import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.assertions.api.Assertions.assertThat;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static org.junit.Assert.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class checkInUITest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/addGuest.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void nametest() {
        JFXTextField f1 = lookup("#requirements").query();
        JFXTextField f2 = lookup("#check_out_date").query();
        JFXTextField f3 = lookup("#guest_name").query();
        JFXTextField f4 = lookup("#total_price").query();
        JFXTextField f5 = lookup("#room_number").query();
        JFXTextField f6 = lookup("#check_in_date").query();
        JFXButton bt1 = lookup("#save_button").query();
        JFXButton bt2 = lookup("#cancel_button").query();
        assertEquals("Requirements", f1.getPromptText() );
        assertEquals("Check-out Date", f2.getPromptText() );
        assertEquals("Guest Name", f3.getPromptText() );
        assertEquals("Total Price", f4.getPromptText() );
        assertEquals("Room Number", f5.getPromptText() );
        assertEquals("Check-in Date", f6.getPromptText() );
        assertThat(bt1).hasText("Save");
        assertThat(bt2).hasText("Cancel");
    }


}