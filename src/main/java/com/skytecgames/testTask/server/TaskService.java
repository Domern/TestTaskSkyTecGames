package com.skytecgames.testTask.server;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class TaskService implements ClanService{ // какой-то сервис с заданиями

   private Server server;
    private boolean complite;
    private int winGold;
    private final long clanId;
    private final long taskId;

    public TaskService(Server server,long clanId, long taskId) {
        this.server=server;
        this.clanId = clanId;
        this.taskId = taskId;
        Clan clan = getClan(clanId);

        // выполняется какое-то задание
        // в случае успешного прохождения
        //winGold = выигранному золоту
        //complite=true

        if(complite){
            clan.addGold(winGold);
            log.debug("выполнив задание "+taskId+" внесено в клан " + clan.getId() + " " + clan.getName() + " " + winGold + "золота");
        }
    }

        @Override
        public Clan getClan(long clanId) {
            boolean beClan = false;
            for (Clan clan : server.getClans()) {
                if (clan.getId() == clanId) {
                    beClan = true;
                    return clan;
                }
            }
            if (beClan) {
                try {
                    server.getAllclansFromDataBase();
                    for (Clan clan : server.getClans()) {
                        if (clan.getId() == clanId) {
                            return clan;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

}
