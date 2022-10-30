package com.skytecgames.testTask.server;

import lombok.Data;
import lombok.Getter;

// Упрощенный объект клана
@Getter
public class Clan {
    private long id;     // id клана
    private String name; // имя клана
    private int gold;// текущее количество золота в казне клана
    private Object monitor = new Object();

    public Clan(long id, String name, int gold) {
        this.id = id;
        this.name = name;
        this.gold = gold;
    }

    public void addGold(int addGold) {
        synchronized (monitor) {
            gold += addGold;
        }
    }

}
