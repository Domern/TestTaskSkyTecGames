package com.skytecgames.testTask.server;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;
@Getter
public class DataBaseConnection {
    private Connection connection;
    private Statement statement;

    public DataBaseConnection() {
        try {
            connection=DriverManager.getConnection("jdbc:sqlite:clansdb.db");
            statement= connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        }
    }

    private void disconnect(){
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
