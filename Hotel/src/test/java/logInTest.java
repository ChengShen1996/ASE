import LogIn.logIn;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.assertions.api.Assertions.assertThat;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.jfoenix.controls.JFXButton;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class logInTest extends ApplicationTest {
     logIn login = new logIn();
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/logInMenu.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void button() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXButton bt1 = lookup("#main_front_desk").query();
                assertThat(bt1).hasText("Front Desk");
                JFXButton bt2 = lookup("#main_manager").query();
                assertThat(bt2).hasText("Manager");
                bt1.fire();
                bt2.fire();
            }
        });
    }

}
