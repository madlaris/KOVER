package com.example.demo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Training {


    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleDoubleProperty price;
    private Date date;
    private String exer;
    private String id;
    private Time time;
    public SimpleObjectProperty<TextFlow> exercises;
    public SimpleObjectProperty<TextFlow> infoProperty;
    public SimpleIntegerProperty numbers;
    public SimpleStringProperty quantity;



    public Training(int sectionID, String name, String description, String exercises, double price, Date date, Time time) {
        this.numbers = new SimpleIntegerProperty(Integer.valueOf(sectionID));
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
        this.date = date;
        this.time = time;
        Text exercisesText = new Text(exercises);
        exercisesText.setFont(Font.font("FreeSerif", 14));
        this.exercises = new SimpleObjectProperty<TextFlow>(new TextFlow(exercisesText));
        this.exer = exercises;
        this.id = String.valueOf(sectionID);

        Text nametext = new Text("Наименование:" + name + "\n");
        nametext.setFont(Font.font("FreeSerif", 16));
        Text descriptiontext = new Text("Описание:" + description + "\n");
        descriptiontext.setFont(Font.font("FreeSerif", 14));
        Text pricetext = new Text("Цена:" + price + "\n");
        pricetext.setFont(Font.font("FreeSerif", 13));
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT,new Locale("ru","RU"));
        Text datetimetext = new Text("Время тренировки: " + dateFormat.format(date)+" " + time + "\n");
        TextFlow textFlow = new TextFlow(nametext, descriptiontext, pricetext,datetimetext);
        this.infoProperty = new SimpleObjectProperty<>(textFlow);

    }
    public Training(Training training) {
        this.numbers = new SimpleIntegerProperty(Integer.valueOf(training.getId()));
        this.name = new SimpleStringProperty(training.getName());
        this.description = new SimpleStringProperty(training.getDescription());
        this.price = new SimpleDoubleProperty(training.getPrice());
        this.date = training.getDate();
        this.time = training.getTime();
        Text exercisesText = new Text(training.getExer());
        exercisesText.setFont(Font.font("FreeSerif", 14));
        this.exercises = new SimpleObjectProperty<TextFlow>(new TextFlow(exercisesText));
        this.exer = training.getExer();
        this.id = String.valueOf(training.getId());
        this.quantity =  new SimpleStringProperty("1");

        Text nametext = new Text("Наименование:" + training.getName() + "\n");
        nametext.setFont(Font.font("FreeSerif", 16));
        Text descriptiontext = new Text("Описание:" + training.getDescription() + "\n");
        descriptiontext.setFont(Font.font("FreeSerif", 14));
        Text pricetext = new Text("Цена:" + training.getPrice() + "\n");
        pricetext.setFont(Font.font("FreeSerif", 13));
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT,new Locale("ru","RU"));
        Text datetimetext = new Text("Время тренировки: " + dateFormat.format(training.getDate())+" " + training.getTime() + "\n");
        TextFlow textFlow = new TextFlow(nametext, descriptiontext, pricetext,datetimetext);
        this.infoProperty = new SimpleObjectProperty<>(textFlow);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExer() {
        return exer;
    }

    public void setExer(String exer) {
        this.exer = exer;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public TextFlow getExercises() {
        return exercises.get();
    }

    public SimpleObjectProperty<TextFlow> exercisesProperty() {
        return exercises;
    }

    public void setExercises(TextFlow exercises) {
        this.exercises.set(exercises);
    }

    public TextFlow getInfoProperty() {
        return infoProperty.get();
    }

    public SimpleObjectProperty<TextFlow> infoPropertyProperty() {
        return infoProperty;
    }

    public void setInfoProperty(TextFlow infoProperty) {
        this.infoProperty.set(infoProperty);
    }

    public int getNumbers() {
        return numbers.get();
    }

    public SimpleIntegerProperty numbersProperty() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers.set(numbers);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }
}