package com.terrasia.harvester.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HarvestSellEvent extends Event {

    private static final HandlerList handlers = new HandlerList();


    Player seller;
    int moneyEarn;
    int quantitySell;
    Material type;


    public HarvestSellEvent(Player seller, int moneyEarn, int quantitySell, Material type){
        this.seller = seller;
        this.moneyEarn = moneyEarn;
        this.quantitySell = quantitySell;
        this.type = type;
    }

    public Player getSeller() {
        return seller;
    }



    public int getMoneyEarn() {
        return moneyEarn;
    }

    public int getQuantitySell() {
        return quantitySell;
    }

    public Material getType() {
        return type;
    }



    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
