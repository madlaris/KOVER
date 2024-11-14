package com.example.demo;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.TextFlow;

import java.sql.SQLException;

public class CreateContoller {

    @FXML
    private TableView<Training> tableView;

    @FXML
    private TableColumn<Training, Integer> numbercolumn;
    @FXML
    private Label pricelabel;
    @FXML
    private TableColumn<Training, TextFlow> infocolumn;
    @FXML
    private MenuItem addCon;
    @FXML
    private TextField loginfield;
    @FXML
    private TableView<Training> selectedView;
    @FXML
    private TableColumn<Training, Integer> numcolumn;

    @FXML
    private TableColumn<Training, TextFlow> informacolumn;

    @FXML
    private TableColumn<Training, String> quantitycolumn;

    @FXML
    private MenuItem deleteCon;
    DatabaseHandler db;

    @FXML
    private Button backbutt;

    @FXML
    private Button concreate;

    @FXML
    void back(ActionEvent event) {
        backbutt.getScene().getWindow().hide();
    }

    @FXML
    void concreate(ActionEvent event) throws SQLException {
        String login = loginfield.getText();
        double price = 0;
        for (Training train : selectedView.getItems()) {
            price += train.getPrice() * Integer.valueOf(train.getQuantity());
        }
        if (db.userID(login) == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not exist");
            alert.setHeaderText("Пользователь не существует");
            alert.show();
        } else {
            int id = db.addSubscription(db.userID(login),price);
            for (Training train:selectedView.getItems()){
                db.addSectionSubs(id,Integer.valueOf(train.getId()),Integer.valueOf(train.getQuantity()));
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Данные успешно добавлены");
            alert.setTitle("Successfully");
            alert.showAndWait();
            concreate.getScene().getWindow().hide();
        }
    }

    void updatePrice() {
        double price = 0;
        for (Training train : selectedView.getItems()) {
            price += train.getPrice() * Integer.valueOf(train.getQuantity());
        }

        pricelabel.setText("Цена: " + String.format("%.2f", price));

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
        concreate.setDisable(true);
        loginfield.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty() || selectedView.getItems().isEmpty()) {
                concreate.setDisable(true);
            } else {
                concreate.setDisable(false);
            }
        });
        selectedView.getItems().addListener(new ListChangeListener<Training>() {
            @Override
            public void onChanged(Change<? extends Training> change) {
                if (loginfield.getText() == null || loginfield.getText().trim().isEmpty() || selectedView.getItems().isEmpty()) {
                    concreate.setDisable(true);
                } else {
                    concreate.setDisable(false);
                }
            }
        });
        infocolumn.setCellValueFactory(new PropertyValueFactory<Training, TextFlow>("infoProperty"));
        numbercolumn.setCellValueFactory(new PropertyValueFactory<Training, Integer>("numbers"));
        tableView.setRowFactory(tv -> new TableRow<Training>() {
            @Override
            protected void updateItem(Training item, boolean empty) {
                setStyle("-fx-background-color:#10B1A1;");
                super.updateItem(item, empty);
            }
        });
        informacolumn.setCellValueFactory(new PropertyValueFactory<Training, TextFlow>("infoProperty"));
        numcolumn.setCellValueFactory(new PropertyValueFactory<Training, Integer>("numbers"));
        quantitycolumn.setCellValueFactory(new PropertyValueFactory<Training, String>("quantity"));
        quantitycolumn.setCellFactory(TextFieldTableCell.<Training>forTableColumn());
        quantitycolumn.setOnEditCommit(event -> {
            try {
                Integer.parseInt(event.getNewValue());
                if (Integer.parseInt(event.getNewValue()) == 0) {
                    event.getTableView().getItems().remove(event.getTablePosition().getRow());
                } else {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setQuantity(event.getNewValue());
                }
            } catch (NumberFormatException nfe) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setQuantity(event.getOldValue());
            }
            event.getTableView().refresh();
        });

        selectedView.setRowFactory(tv -> new TableRow<Training>() {
            @Override
            protected void updateItem(Training item, boolean empty) {
                setStyle("-fx-background-color:#10B1A1;");
                super.updateItem(item, empty);
            }
        });
        try {
            tableView.setItems(db.getInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        addCon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean inlist = false;
                for (Training train : selectedView.getItems()) {
                    if (tableView.getSelectionModel().getSelectedItem().getId().equals(train.getId())) {
                        inlist = true;
                        train.setQuantity(String.valueOf(Integer.parseInt(train.getQuantity()) + 1));

                    }

                }

                if (!inlist) {
                    Training train = new Training(tableView.getSelectionModel().getSelectedItem());
                    selectedView.getItems().add(train);
                }
                selectedView.refresh();
                updatePrice();
            }
        });
        deleteCon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedView.getItems().remove(selectedView.getSelectionModel().getSelectedItem());
                updatePrice();
            }
        });
    }

}
