import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.assertions.api.Assertions.assertThat;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import static org.junit.Assert.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class frontDeskUITest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/frontDeskMenu.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void nametest() {
        Button bt1 = lookup("#front_desk_guest").query();
        Button bt2 = lookup("#map").query();
        assertThat(bt1).hasText("Guest");
        assertThat(bt2).hasText("Map");
    }


}