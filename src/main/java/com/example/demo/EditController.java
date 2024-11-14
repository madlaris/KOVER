package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label mainlabel;

    @FXML
    private TextField insertname;

    @FXML
    private TextArea insertdescr;

    @FXML
    private TextArea insertexec;

    @FXML
    private TextField insertprice;

    @FXML
    private Spinner<Integer> inserthours;

    @FXML
    private DatePicker insertdate;

    @FXML
    private Spinner<Integer> insertmin;

    @FXML
    private Button backbutt;
    DatabaseHandler db;
    @FXML
    private Button acceptbutt;
    private Training training;

    @FXML
    void accept(ActionEvent event) throws SQLException {
        if (training != null) {
            if (insertname.getText()!=null && !insertname.getText().trim().isEmpty() &&
                    insertdescr.getText()!=null && !insertdescr.getText().trim().isEmpty() &&
                    insertexec.getText()!=null && !insertexec.getText().trim().isEmpty() &&
                    insertprice.getText()!=null && !insertprice.getText().trim().isEmpty() &&
                    insertdate.getValue()!=null)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setHeaderText("Вы уверены?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==ButtonType.OK){
                    Date date = Date.valueOf(insertdate.getValue());
                    Time time = Time.valueOf(inserthours.getValue() + ":" + insertmin.getValue() + ":00");
                    db.updateTable(Integer.valueOf(training.getId()), insertname.getText(), insertdescr.getText(), insertexec.getText(), Double.valueOf(insertprice.getText()), date, time);
                    acceptbutt.getScene().getWindow().hide();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Заполните поля");
                alert.show();
            }
        }
        else {
            if (insertname.getText()!=null && !insertname.getText().trim().isEmpty() &&
                    insertdescr.getText()!=null && !insertdescr.getText().trim().isEmpty() &&
                    insertexec.getText()!=null && !insertexec.getText().trim().isEmpty() &&
                    insertprice.getText()!=null && !insertprice.getText().trim().isEmpty() &&
                    insertdate.getValue()!=null)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setHeaderText("Вы уверены?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==ButtonType.OK){
                    Date date = Date.valueOf(insertdate.getValue());
                    Time time = Time.valueOf(inserthours.getValue() + ":" + insertmin.getValue() + ":00");
                    db.addTable(insertname.getText(), insertdescr.getText(), insertexec.getText(), Double.valueOf(insertprice.getText()), date, time);
                    acceptbutt.getScene().getWindow().hide();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Заполните поля");
                alert.show();
            }

        }

    }

    @FXML
    void back(ActionEvent event) {
        backbutt.getScene().getWindow().hide();
    }

    public void setLabel(String text) {
        mainlabel.setText(text);
    }

    public void setTraining(Training train) {
        this.training = train;
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DatabaseHandler();
        db.getDbconnection();
        inserthours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 22, 8));
        insertmin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        insertprice.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty()) {
                    return;
                }

                Double.parseDouble(newValue);
            } catch (NumberFormatException e) {
                insertprice.setText(oldValue);
            }
        });

        mainlabel.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (training != null) {
                    insertname.setText(training.getName());
                    insertdescr.setText(training.getDescription());
                    insertexec.setText(training.getExer());
                    insertprice.setText(String.valueOf(training.getPrice()));
                    insertdate.setValue(Date.valueOf(training.getDate().toString()).toLocalDate());
                    inserthours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 22, training.getTime().getHours()));
                    insertmin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, training.getTime().getMinutes()));

                }
            }
        });

    }
}
