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
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/logInMenu.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void button() {
        JFXButton bt = lookup("#main_front_desk").query();
        assertThat(bt).hasText("Front Desk");
    }

}
