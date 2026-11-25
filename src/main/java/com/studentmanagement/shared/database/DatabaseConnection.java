package com.studentmanagement.shared.database;

import com.studentmanagement.shared.env.EnvConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection     {
    private DatabaseConnection(){};
    private  static  String URL = EnvConfig.get("URL");
    private   static String USER = EnvConfig.get("USER");
    private  static  String PASSWORD = EnvConfig.get("PASSWORD");

    public  static Connection getConnection () throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
