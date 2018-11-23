package ManagerRevenue;

import database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import java.sql.SQLException;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

public class ShowRevenueController implements Initializable{

    @FXML
    private TableColumn<?, ?> revenue;

    @FXML
    private TextField total_revenue;

    @FXML
    private TableColumn<?, ?> revenue_date;

    @FXML
    private ComboBox<String> month;

    @FXML
    private ComboBox<String> year;

    DatabaseHandler databaseHandler;

    public void initialize(URL url, ResourceBundle rb){
        databaseHandler = new DatabaseHandler();
        year.getItems().addAll("2012", "2013", "2014", "2015", "2016", "2017", "2018");
        month.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
    }

    @FXML
    void show_revenue(ActionEvent event) {

    }
}
