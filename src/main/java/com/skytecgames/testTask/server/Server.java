package com.skytecgames.testTask.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Getter
@Slf4j
public class Server{
    private final DataBaseConnection dataBase;
    private List<Clan> clans;

    public Server() {
        clans=new ArrayList<>();
        dataBase = new DataBaseConnection();
        try (ServerSocket serverSocket = new ServerSocket(8400);) {
            log.debug("Server start");
            getAllclansFromDataBase();
            while (true) {
                Socket socket = serverSocket.accept();
                new Handler(this, socket);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllclansFromDataBase() throws SQLException {
        ResultSet rs = dataBase.getStatement().executeQuery("select * from clans");
        while (rs.next()){
            clans.add(new Clan(rs.getInt(1), rs.getString(2), rs.getInt(3)));
        }
    }
}
