package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseHandler {

    Connection connection = null;
    InfoUser infoUser;
    public Connection getDbconnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://10.207.144.159:3306/user087_db1?useUnicode=true&serverTimezone=UTC", "user087_user1", "m_aeSh9y");
        return connection;
    }
    public int checkUser(String login, String password) throws SQLException, ClassNotFoundException{
        int checked = -1;
        String query = "Select UserID From users WHERE UserLogin = ? AND UserPassword = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,login);
        preparedStatement.setString(2,password);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            checked = rs.getInt("UserID");
        }
        return checked;
    }
    public void loadUser(int id) throws SQLException, ClassNotFoundException{
        String sql = "Select * FROM users WHERE UserID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            infoUser.getUserInfo(id,rs.getString("UserName"),
                    rs.getString("UserSurname"),
                    rs.getString("UserPassport"),
                    rs.getDate("UserBorn"),
                    rs.getInt("RoleID"),
                    rs.getString("UserPassword"),
                    rs.getString("UserLogin"));

        }
    }
    public ObservableList<String> getNumbers() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM Training_sections";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            list.add("SectionID");
        }
        return list;
    }
    public ObservableList<Training> getInfo() throws SQLException{
        ObservableList<Training> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM training_sections as trainings, schedule as sch where sch.ScheduleID = trainings.ScheduleID";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            int id = rs.getInt("SectionID");
            String name = rs.getString("SectionName");
            String description = rs.getString("SectionDescription");
            String exercises = rs.getString("SectionExercises");
            double price = rs.getDouble("SectionPrice");
            Date date = rs.getDate("Date");
            Time time = rs.getTime("Time");
            Training training = new Training(id,name,description,exercises,price,date,time);
            list.add(training);
        }
        rs.close();
        return list;
    }
    public ObservableList<String> getExercises()throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM training_sections ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            list.add("SectionExercises");
        }
        return list;
    }
    public ObservableList<String> getCategories() throws SQLException{
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM training_sections";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            list.add(rs.getString("SectionName"));
        }
        return list;
    }
    public int scheduleCount()throws  SQLException{
        int count = 0;
        String query = "SELECT COUNT(*) count FROM schedule";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs  = preparedStatement.executeQuery();
        while (rs.next()){
            count = rs.getInt("count");
        }
        return count;
    }
    public int scheduleCheck(Date date, Time time) throws SQLException{
        int id = 0;
        String query = "SELECT ScheduleID FROM schedule WHERE Date = ? AND Time = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1,date);
        preparedStatement.setTime(2,time);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            id = rs.getInt("ScheduleID");
        }
        return id;
    }
    public int sheduleUpdate(Date date, Time time) throws SQLException{
        int id = 0;
        int scheduleID = scheduleCheck(date,time);
        if(scheduleID==0){
            String query = "INSERT INTO schedule VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            id = scheduleCount()+1;
            preparedStatement.setInt(1,id);
            preparedStatement.setDate(2,date);
            preparedStatement.setTime(3,time);
            preparedStatement.executeUpdate();
        }
        else {
            return scheduleID;
        }
        return id;
    }
    public void updateTable(int sectionID, String name,String description, String exer, double price,Date date, Time time) throws SQLException{
        String query = "UPDATE training_sections SET SectionName = ?,SectionDescription = ?, SectionExercises = ?, SectionPrice = ?, ScheduleID=? WHERE SectionID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,description);
        preparedStatement.setString(3,exer);
        preparedStatement.setDouble(4,price);
        preparedStatement.setInt(5,sheduleUpdate(date,time));
        preparedStatement.setInt(6,sectionID);
        preparedStatement.executeUpdate();
    }
    public int sectionsCount()throws  SQLException{
        int count = 0;
        String query = "SELECT COUNT(*) count FROM training_sections";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs  = preparedStatement.executeQuery();
        while (rs.next()){
            count = rs.getInt("count");
        }
        return count;
    }
    public void addTable(String name,String description, String exer, double price,Date date, Time time) throws SQLException{
        String query = "INSERT INTO training_sections VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,sectionsCount()+1);
        preparedStatement.setString(2,name);
        preparedStatement.setString(3,description);
        preparedStatement.setString(4,exer);
        preparedStatement.setDouble(5,price);
        preparedStatement.setInt(6,sheduleUpdate(date,time));
        preparedStatement.executeUpdate();
    }
    public void delete(int sectionID) throws SQLException{
        String query = "DELETE FROM training_sections WHERE SectionID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,sectionID);
        preparedStatement.executeUpdate();
    }
    public int countSubs()throws SQLException{
        int count = 0;
        String query = "SELECT COUNT(*) count from subscription";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs  = preparedStatement.executeQuery();
        while (rs.next()){
            count = rs.getInt("count");
        }
        return count;
    }
    public void addSectionSubs(int subscriptionID, int sectionID,int quantity) throws SQLException{
        String query = "INSERT INTO sections_subs VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,subscriptionID);
        preparedStatement.setInt(2,sectionID);
        preparedStatement.setInt(3,quantity);
        preparedStatement.executeUpdate();
    }
    public int userID(String login) throws SQLException{
        int id = 0;
        String query = "SELECT UserID from users WHERE UserLogin = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,login);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            id = rs.getInt("UserID");
        }
        return id;
    }
    public int addSubscription(int userID, double price) throws SQLException{
        int id = countSubs()+1;
        String query = "INSERT INTO subscription VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,id);
        preparedStatement.setDouble(2,price);
        preparedStatement.setInt(3,userID);
        preparedStatement.executeUpdate();
        return id;
    }

}
