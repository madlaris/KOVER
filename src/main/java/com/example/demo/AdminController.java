package com.example.demo;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController {
    DatabaseHandler db;
    InfoUser iu;

    @FXML
    private ResourceBundle resources;
    @FXML
    private Button createbutt;

    @FXML
    private URL location;

    @FXML
    private MenuItem addMenu;

    @FXML
    private ComboBox<String> category;

    @FXML
    private MenuItem deleteMenu;

    @FXML
    private MenuItem editMenu;
    @FXML
    private TableView<Training> tableView;
    private ObservableList<Training> list = null;

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
    void create(ActionEvent event){
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("create-view.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(createbutt.getScene().getWindow());
        primaryStage.setResizable(false);
        primaryStage.setMinWidth(747);
        primaryStage.setMinHeight(557);
        primaryStage.setTitle("Create");
        primaryStage.setScene(new Scene(root, 747,557));
        primaryStage.show();
    }
    void updateCateg(){
        try {
            category.setItems(db.getCategories());
        } catch (Exception e) {
            e.printStackTrace();
        }
        category.getItems().add(0, "Выбрать категорию");
        category.setValue("Выбрать категорию");
    }
    void updateTable(){
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
    }

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
        updateTable();
        updateCateg();
        addMenu.setOnAction(actionEvent -> {
            Parent root = null;
            FXMLLoader loader = null;
            try {
                loader = new FXMLLoader(Main.class.getResource("edit-view.fxml"));
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage primaryStage = new Stage();
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.initOwner(createbutt.getScene().getWindow());
            primaryStage.setResizable(false);
            primaryStage.setMinWidth(747);
            primaryStage.setMinHeight(557);
            EditController ed = loader.getController();
            ed.setLabel("Добавление");
            primaryStage.setTitle("Add");
            primaryStage.setScene(new Scene(root, 747,557));
            primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    updateTable();
                    updateCateg();
                }
            });
            primaryStage.show();
        });
        editMenu.setOnAction(actionEvent -> {
            Parent root = null;
            FXMLLoader loader = null;
            try {
                loader = new FXMLLoader(Main.class.getResource("edit-view.fxml"));
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage primaryStage = new Stage();
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.initOwner(createbutt.getScene().getWindow());
            primaryStage.setResizable(false);
            primaryStage.setMinWidth(747);
            primaryStage.setMinHeight(557);
            EditController ed = loader.getController();
            ed.setTraining(tableView.getSelectionModel().getSelectedItem());
            ed.setLabel("Изменение");
            primaryStage.setTitle("Edit");
            primaryStage.setScene(new Scene(root, 747,557));
            primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    updateTable();
                    updateCateg();
                }
            });
            primaryStage.show();
        });
        deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Вы уверены?");
                alert.setTitle("Delete");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==ButtonType.OK){
                    try {
                        db.delete(Integer.valueOf(tableView.getSelectionModel().getSelectedItem().getId()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    updateTable();
                    updateCateg();
                }
            }
        });

    }

}
