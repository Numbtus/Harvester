package com.terrasia.harvester.events;

import com.terrasia.harvester.Main;
import com.terrasia.harvester.manager.ItemManager;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class onPlaceBlock implements Listener {

    private Main index;
    public onPlaceBlock(Main main) {
        this.index = main;
    }

    @EventHandler
    public void blockPlaced(BlockPlaceEvent event) {
        final File hfile = new File(index.getDataFolder(), "storage.yml");
        final YamlConfiguration harvestFile = YamlConfiguration.loadConfiguration(hfile);


        if(event.getPlayer().getItemInHand().getItemMeta().equals(ItemManager.harvester.getItemMeta())) {

            if (event.getBlockPlaced().getType() == Material.DIAMOND_BLOCK) {

                Chunk chunkLoc = event.getBlockPlaced().getLocation().getChunk();
                String key = "location." + chunkLoc  + ".";

                if (harvestFile.getBoolean(key + "harvest.isHere")){
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("alreadyHarvester")));
                }else {

                    harvestFile.set(key + "harvest.isHere", true);
                    harvestFile.set(key + "harvest.loc.x", event.getBlockPlaced().getX());
                    harvestFile.set(key + "harvest.loc.y", event.getBlockPlaced().getY());
                    harvestFile.set(key + "harvest.loc.z", event.getBlockPlaced().getZ());
                    harvestFile.set(key + "harvest.storage.pumpkin", 0);
                    harvestFile.set(key + "harvest.storage.melon", 0);

                    for(int y= 0; y<=256; y++ ) {
                        for (int x=0; x<=15; x++) {
                            for (int z=0; z<=15; z++) {
                                if(event.getBlockPlaced().getChunk().getBlock(x, y, z).getType() == Material.MELON_BLOCK || event.getBlockPlaced().getChunk().getBlock(x, y,z).getType() == Material.PUMPKIN) {
                                    event.getBlockPlaced().getChunk().getBlock(x, y, z).setType(Material.AIR);
                                }
                            }
                        }
                    }

                    try {
                        harvestFile.save(hfile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }
    @EventHandler
    public void blockBreak(BlockBreakEvent event) {

        final File hfile = new File(index.getDataFolder(), "storage.yml");
        final YamlConfiguration harvestFile = YamlConfiguration.loadConfiguration(hfile);

        if(event.getBlock().getType() == Material.DIAMOND_BLOCK){
            Chunk chunk = event.getBlock().getLocation().getChunk();
            String key = "location." + chunk  + ".";
            int X = event.getBlock().getX();
            int Y = event.getBlock().getY();
            int Z = event.getBlock().getZ();


            int locX = harvestFile.getInt(key + "harvest.loc.x");
            int locY = harvestFile.getInt(key + "harvest.loc.y");
            int locZ = harvestFile.getInt(key + "harvest.loc.z");

            Player player = event.getPlayer();
            if (harvestFile.getBoolean(key + "harvest.isHere")) {

                if(X == locX && Y == locY && Z == locZ) {

                    harvestFile.set(key + "harvest.isHere", false);

                    harvestFile.set(key + "harvest.storage.pumpkin", 0);
                    harvestFile.set(key + "harvest.storage.melon", 0);
                    try {
                        harvestFile.save(hfile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (ItemStack i : event.getBlock().getDrops()) {
                        event.getBlock().getDrops().remove(i);
                    }
                    player.getInventory().addItem(ItemManager.harvester);
                    event.getBlock().setType(Material.AIR);


                }
            }






            }

    }


    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(event.getClickedBlock().getType() == Material.DIAMOND_BLOCK) {
                    final File hfile = new File(index.getDataFolder(), "storage.yml");
                    final YamlConfiguration harvestFile = YamlConfiguration.loadConfiguration(hfile);
                    Chunk chunk = event.getClickedBlock().getLocation().getChunk();
                    String key = "location." + chunk  + ".";
                    int X = event.getClickedBlock().getX();
                    int Y = event.getClickedBlock().getY();
                    int Z = event.getClickedBlock().getZ();


                    int locX = harvestFile.getInt(key + "harvest.loc.x");
                    int locY = harvestFile.getInt(key + "harvest.loc.y");
                    int locZ = harvestFile.getInt(key + "harvest.loc.z");

                    Player player = event.getPlayer();
                    if (harvestFile.getBoolean(key + "harvest.isHere")) {

                        if (X == locX && Y == locY && Z == locZ) {
                            Inventory harvestInv = Bukkit.createInventory(null, 36, ChatColor.translateAlternateColorCodes('&', index.getLang().getString("name")));




                            ItemStack pumpkin = new ItemStack(Material.PUMPKIN, 1);
                            ItemMeta pumpkinM = pumpkin.getItemMeta();
                            pumpkinM.setDisplayName(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("pumpkinName")));
                            pumpkinM.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("quantity")) + harvestFile.get(key + "harvest.storage.pumpkin")));
                            pumpkin.setItemMeta(pumpkinM);
                            harvestInv.setItem(11, pumpkin);

                            ItemStack melon = new ItemStack(Material.MELON_BLOCK, 1);
                            ItemMeta melonM = melon.getItemMeta();
                            melonM.setDisplayName(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("melonName")));
                            melonM.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("quantity")) + harvestFile.get(key + "harvest.storage.melon")));
                            melon.setItemMeta(melonM);
                            harvestInv.setItem(15, melon);


                            ItemStack sell = new ItemStack(Material.DIAMOND, 1);
                            ItemMeta sellM = sell.getItemMeta();
                            sellM.setDisplayName(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("sellButton")));
                            sell.setItemMeta(sellM);
                            harvestInv.setItem(20, sell);
                            harvestInv.setItem(24, sell);


                            ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
                            ItemMeta glassM = glass.getItemMeta();
                            glassM.setDisplayName(" ");
                            glass.setItemMeta(glassM);
                            int cc = 0;
                            while (cc < 36) {

                                if(harvestInv.getItem(cc) == null){
                                    harvestInv.setItem(cc, glass);
                                }

                                cc = cc + 1;
                            }
                            if(player.getLocation().getChunk() == chunk) {
                                player.openInventory(harvestInv);
                            }else{
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("diffChunk")));
                            }

                        }
                    }
                }
            }

    }



    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        if (current == null) return;
        if(event.getClickedInventory().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("name")))) {
            event.setCancelled(true);

            final File hfile = new File(index.getDataFolder(), "storage.yml");
            final YamlConfiguration harvestFile = YamlConfiguration.loadConfiguration(hfile);
            Chunk chunkLoc = player.getLocation().getChunk();
            String key = "location." + chunkLoc  + ".";
            if(event.getSlot() == 20) {
                if(Main.getInstance().setupEconomy()) {
                    int price = index.getConfig().getInt("sellprice.pumpkin");
                    int quantity = harvestFile.getInt(key + "harvest.storage.pumpkin");
                    int moneyEarn = price * quantity;
                    player.closeInventory();
                    String message =  index.getLang().getString("sellMessage");
                    message = message.replace("%price%", "" + moneyEarn + "");
                    message = message.replace("%quantity%", "" + quantity + "");
                    message = message.replace("%crops%", "pumpkin");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

                    Bukkit.getServer().getPluginManager().callEvent(new HarvestSellEvent(player, moneyEarn, quantity, Material.PUMPKIN));

                    Main.getInstance().economy.depositPlayer(player, moneyEarn);

                    harvestFile.set(key + "harvest.storage.pumpkin", 0);


                    try {
                        harvestFile.save(hfile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            if(event.getSlot() == 24) {
                if(Main.getInstance().setupEconomy()) {
                    int price = index.getConfig().getInt("sellprice.melon");
                    int quantity = harvestFile.getInt(key + "harvest.storage.melon");
                    int moneyEarn = price * quantity;

                    harvestFile.set(key + "harvest.storage.melon", 0);
                    player.closeInventory();
                    String message =  index.getLang().getString("sellMessage");
                    message = message.replace("%price%", "" + moneyEarn + "");
                    message = message.replace("%quantity%", "" + quantity + "");
                    message = message.replace("%crops%", "melon");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    Main.getInstance().economy.depositPlayer(player, moneyEarn);
                    Bukkit.getServer().getPluginManager().callEvent(new HarvestSellEvent(player, moneyEarn, quantity, Material.MELON_BLOCK));



                    try {
                        harvestFile.save(hfile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }


    }
}
