package com.example.demo;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class InfoController {
    DatabaseHandler db;
    InfoUser iu;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> category;
    private ObservableList<Training> list = null;
    @FXML
    private TableView<Training> tableView;
    @FXML
    private TableColumn<Training, TextFlow> exescisescolumn;

    @FXML
    private TableColumn<Training, TextFlow> infocolumn;

    @FXML
    private Label namelabel;

    @FXML
    private TableColumn<Training, Integer> numbercolumn;

    @FXML
    private TextField search;

    @FXML
    void initialize() {
        db = new DatabaseHandler();
        try {
            db.getDbconnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        namelabel.setText(iu.getUserName() + " " + iu.getUserSurname());
        exescisescolumn.setCellValueFactory(new PropertyValueFactory<Training, TextFlow>("exercises"));
        infocolumn.setCellValueFactory(new PropertyValueFactory<Training, TextFlow>("infoProperty"));
        numbercolumn.setCellValueFactory(new PropertyValueFactory<Training, Integer>("numbers"));
        tableView.setRowFactory(tv -> new TableRow<Training>() {
            @Override
            protected void updateItem(Training item, boolean empty) {
                setStyle("-fx-background-color:#10B1A1;");
                super.updateItem(item, empty);
            }
        });

        try {
            list = db.getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FilteredList<Training> data = new FilteredList<>(list, b -> true);
        category.setOnAction(actionEvent -> {
            data.setPredicate(Training -> {
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("ru", "RU"));
                String date = dateFormat.format(Training.getDate()) + " " + Training.getTime();
                String lowerCaseFilter = search.getText().toLowerCase();

                if (category.getValue() == null || Training.getName().equals(category.getValue()) || category.getValue().equals("Выбрать категорию")) {

                    if (Training.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (Training.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (date.toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (String.valueOf(Training.getPrice()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (Training.getExercises().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else return false;
                }
                return false;
            });

        });
        search.textProperty().addListener((observableValue, oldValue, newValue) -> {
            data.setPredicate(Training -> {
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("ru", "RU"));
                String date = dateFormat.format(Training.getDate()) + " " + Training.getTime();
                String lowerCaseFilter = newValue.toLowerCase();

                if (category.getValue() == null || Training.getName().equals(category.getValue()) || category.getValue().equals("Выбрать категорию")) {

                    if (Training.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (Training.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (date.toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (String.valueOf(Training.getPrice()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (Training.getExer().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (Training.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else return false;
                }
                return false;
            });
        });
        tableView.setItems(data);
        try{
            category.setItems(db.getCategories());
        }catch (Exception e){
            e.printStackTrace();
        }
        category.getItems().add(0, "Выбрать категорию");


    }

}


