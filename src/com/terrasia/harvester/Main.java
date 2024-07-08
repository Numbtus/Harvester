package com.terrasia.harvester;


import com.terrasia.harvester.commands.giveItem;
import com.terrasia.harvester.events.TestEvents;
import com.terrasia.harvester.events.onPlaceBlock;
import com.terrasia.harvester.manager.ItemManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class Main extends JavaPlugin implements Listener{
    public Economy economy = null;
    private static Main instance;

    FileConfiguration config;
    File cfile;
    private File langFile = null;
    private FileConfiguration lang = null;


    @Override
    public void onEnable() {
        config = getConfig();
        saveDefaultConfig();
        cfile = new File(getDataFolder(), "config.yml");

        createLang();


        instance = this;

        ItemManager.init();


        getServer().getPluginManager().registerEvents(new TestEvents(this), this);
        getServer().getPluginManager().registerEvents(new onPlaceBlock(this), this);
        getCommand("harvest").setExecutor(new giveItem(this));
        System.out.println("[Harvester] Plugin is enable!");

    }

    public FileConfiguration getLang() {
        return this.lang;
    }

    private void createLang() {
        langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            langFile.getParentFile().mkdirs();
            saveResource("lang.yml", false);
        }

        lang = new YamlConfiguration();
        try {
            lang.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }



    public void reloadLang() throws UnsupportedEncodingException {
        if (langFile == null) {
            langFile = new File(getDataFolder(), "lang.yml");
        }
        lang = YamlConfiguration.loadConfiguration(langFile);

        // Look for defaults in the jar
        Reader defConfigStream = new InputStreamReader(this.getResource("lang.yml"), "UTF8");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            lang.setDefaults(defConfig);
        }
    }


    public boolean setupEconomy() {
        RegisteredServiceProvider<Economy> eco = getServer().getServicesManager().getRegistration(Economy.class);
        if (eco != null) {
            economy = eco.getProvider();
        }

        return economy != null;
    }

    @Override
    public void onDisable() {
        System.out.println("[Harvester] Plugin is disable!");
    }

    public static Main getInstance() {
        return instance;
    }
}
