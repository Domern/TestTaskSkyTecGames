package com.skytecgames.testTask.server;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
@Slf4j
public class Handler {
    private final Server server;
    private final Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ClanService clanService;

    public Handler(Server server, Socket socket) {
        log.debug("Handler start");
        this.server = server;
        this.socket = socket;

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                readMsg();
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readMsg() {
        boolean read=true;
        log.debug("read msg from client");
        //команда от клиента будет формата название сервиса и данные например:
        //#task clanId taskId
        //#userAddGold userId clanId gold
        while (read) {
            try {
                String msg = in.readUTF();
                log.debug(msg);
                if (msg.startsWith("#task")) {
                    String[] split = msg.split("\\s");
                    long clanId = Long.parseLong(split[1]);
                    long taskId = Long.parseLong(split[2]);
                    clanService= new TaskService(server,clanId, taskId);
                } else if (msg.startsWith("#userAddGold")) {
                    String[] split = msg.split("\\s");
                    long userId = Long.parseLong(split[1]);
                    long clanId = Long.parseLong(split[2]);
                    int gold = Integer.parseInt(split[3]);
                    log.debug(userId+" "+clanId+" "+gold);
                    clanService=new UserAddGoldService(server,userId, clanId, gold);

                }
            } catch (IOException e) {
                read=false;
                closeConnection();
                e.printStackTrace();
            }
        }

    }

    private void closeConnection() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
