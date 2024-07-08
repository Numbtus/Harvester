package com.terrasia.harvester.events;

import com.terrasia.harvester.Main;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class TestEvents implements Listener {

    private Main index;
    public TestEvents(Main main) {
        this.index = main;
    }





    @EventHandler
    public void onGrow(BlockGrowEvent event) {

        if(event.getNewState().getType() == Material.PUMPKIN) {
            final File hfile = new File(index.getDataFolder(), "storage.yml");
            final YamlConfiguration harvestFile = YamlConfiguration.loadConfiguration(hfile);
            Chunk chunkCoord = event.getNewState().getLocation().getChunk();
            String key = "location." + chunkCoord  + ".";
            if(harvestFile.getBoolean(key + "harvest.isHere")) {
                int quantity = harvestFile.getInt(key + "harvest.storage.pumpkin");
                if (quantity < index.getConfig().getInt("maxstorage")) {
                    harvestFile.set(key + "harvest.storage.pumpkin", quantity + 1);
                    try {
                        harvestFile.save(hfile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    event.setCancelled(true);
                }
            }
        }

        if(event.getNewState().getType() == Material.MELON_BLOCK) {
            final File hfile = new File(index.getDataFolder(), "storage.yml");
            final YamlConfiguration harvestFile = YamlConfiguration.loadConfiguration(hfile);
            Chunk chunkCoord = event.getNewState().getLocation().getChunk();
            String key = "location." + chunkCoord  + ".";
            if(harvestFile.getBoolean(key + "harvest.isHere")) {
                int quantity = harvestFile.getInt(key + "harvest.storage.melon");
                if (quantity < index.getConfig().getInt("maxstorage")) {


                    harvestFile.set(key + "harvest.storage.melon", quantity + 1);
                    try {
                        harvestFile.save(hfile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    event.setCancelled(true);
                }
            }
        }

        // No lights crops

        Block block = event.getBlock();
        Material type = block.getType();





    }
}
