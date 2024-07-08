package com.terrasia.harvester.manager;

import com.terrasia.harvester.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager {

    private Main index;
    public ItemManager(Main main) {
        this.index = main;
    }

    public static ItemStack harvester;

    public static void init() {
        createHarvester();
    }

    public static void createHarvester() {
        ItemStack harvesterI = new ItemStack(Material.DIAMOND_BLOCK, 1);
        harvesterI.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        ItemMeta farmHoeMeta = harvesterI.getItemMeta();
        farmHoeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lHarvester"));
        farmHoeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        String line1 = ChatColor.translateAlternateColorCodes('&', "&7-----------------------------------");
        String line2 = ChatColor.translateAlternateColorCodes('&', "&eObtention: &7Craft");
        String line3 = ChatColor.translateAlternateColorCodes('&', "&eUtilisation: &7Permet de recolter la &6canne a sucre");
        String line4 = ChatColor.translateAlternateColorCodes('&', "&7elle dispose de plusieurs &6upgrades&7.");
        String line5 = ChatColor.translateAlternateColorCodes('&', "&eRareté: &9&lRare");

        farmHoeMeta.setLore(Arrays.asList(line1, line2, line3, line4, line5, line1));
        harvesterI.setItemMeta(farmHoeMeta);

        harvester = harvesterI;

    }
}
