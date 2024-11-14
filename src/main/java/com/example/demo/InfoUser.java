package com.example.demo;

import java.sql.Date;

public class InfoUser {
    private static int USER_ID;
    private static String USER_NAME;
    private static String USER_SURNAME;
    private static String USER_PASSPORT;
    private static Date USER_BORN;
    private static int USER_ROLE;
    private static String USER_PASSWORD;
    private static String USER_LOGIN;

    public static void getUserInfo(int id, String name, String surname, String passport, Date born, int role, String password, String login) {
        USER_ID = id;
        USER_NAME = name;
        USER_SURNAME = surname;
        USER_PASSPORT = passport;
        USER_BORN = born;
        USER_ROLE = role;
        USER_PASSWORD = password;
        USER_LOGIN = login;
    }

    public static int getUserId() {
        return USER_ID;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public static String getUserSurname() {
        return USER_SURNAME;
    }

    public static String getUserPassport() {
        return USER_PASSPORT;
    }

    public static Date getUserBorn() {
        return USER_BORN;
    }

    public static int getUserRole() {
        return USER_ROLE;
    }

    public static String getUserPassword() {
        return USER_PASSWORD;
    }

    public static String getUserLogin() {
        return USER_LOGIN;
    }
}
