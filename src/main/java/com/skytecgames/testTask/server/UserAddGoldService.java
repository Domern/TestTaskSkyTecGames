package com.skytecgames.testTask.server;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class UserAddGoldService implements ClanService{ // пользователь добавляет золото из собственного кармана

    private final Server server;
    private final long userId;
    private final long clanId;
    private final int gold;

    public UserAddGoldService(Server server, long userId, long clanId, int gold) {
        this.server=server;
        this.userId = userId;
        this.clanId = clanId;
        this.gold = gold;
        Clan clan = getClan(clanId);
        clan.addGold(gold);
        log.debug("user " + userId + " внес в клан " + clan.getId() + " " + clan.getName() + " " + gold + "золота. Теперь золота в клане: "+clan.getGold());
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
