package com.terrasia.harvester.commands;

import com.terrasia.harvester.Main;
import com.terrasia.harvester.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class giveItem implements CommandExecutor {
    private Main index;
    public giveItem(Main main) {
        this.index = main;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {



        String prefix = ChatColor.translateAlternateColorCodes('&',index.getLang().getString("prefix"));

        String helpMessage1 = ChatColor.translateAlternateColorCodes('&', "&8&l---------------------------------------------");
        String helpMessage2 = ChatColor.translateAlternateColorCodes('&', "&6&l/harvest &e&lgive &a&lplayer &7- &lGive harvester to player");
        String helpMessage3 = ChatColor.translateAlternateColorCodes('&', "&6&l/harvest &e&lreload &7- &lReload the config.yml and lang.yml files");
        String helpMessage4 = ChatColor.translateAlternateColorCodes('&', "&6&l/harvest &e&lhelp &7- &lView this message");



        if (sender instanceof Player) {
            if (args.length >= 1) {

                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(helpMessage1);
                    sender.sendMessage(helpMessage2);
                    sender.sendMessage(helpMessage3);
                    sender.sendMessage(helpMessage4);
                    sender.sendMessage(helpMessage1);

                } else if (args[0].equalsIgnoreCase("give")) {
                    if (!sender.hasPermission("harvest.give")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + index.getLang().getString("noPermission")));
                    } else {
                        if (args.length >= 2) {
                            Player TargetPlayer = Bukkit.getPlayerExact(args[1]);
                            if (TargetPlayer == null) {

                                sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', index.getLang().getString("playerError")));

                            } else {


                                TargetPlayer.getInventory().addItem(ItemManager.harvester);
                                String message = prefix + ChatColor.translateAlternateColorCodes('&', index.getLang().getString("successfullyGiveMessage"));
                                String newMessage = message.replace("%playerSend%", TargetPlayer.getDisplayName());
                                sender.sendMessage(newMessage);
                                TargetPlayer.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', index.getLang().getString("receiveMessage")));
                            }

                        } else {
                            sender.sendMessage(helpMessage1);
                            sender.sendMessage(helpMessage2);
                            sender.sendMessage(helpMessage3);
                            sender.sendMessage(helpMessage4);
                            sender.sendMessage(helpMessage1);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (!sender.hasPermission("harvest.reload")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("noPermission")));
                    } else {
                        index.reloadConfig();
                        index.saveDefaultConfig();
                        try {
                            index.reloadLang();
                        } catch (UnsupportedEncodingException e) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError with lang file!"));
                        }

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lPlugin reloaded!"));
                    }
                } else {
                    sender.sendMessage(helpMessage1);
                    sender.sendMessage(helpMessage2);
                    sender.sendMessage(helpMessage3);
                    sender.sendMessage(helpMessage4);
                    sender.sendMessage(helpMessage1);
                }
            } else {
                sender.sendMessage(helpMessage1);
                sender.sendMessage(helpMessage2);
                sender.sendMessage(helpMessage3);
                sender.sendMessage(helpMessage4);
                sender.sendMessage(helpMessage1);
            }



        }else {
            if (args[0].equalsIgnoreCase("give")) {


                if (args.length >= 2) {
                    Player TargetPlayer = Bukkit.getPlayerExact(args[1]);
                    if (TargetPlayer == null) {

                        return false;
                    } else {


                        TargetPlayer.getInventory().addItem(ItemManager.harvester);


                        TargetPlayer.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', index.getLang().getString("receiveMessage")));
                    }
                }
            }

        }


        return false;
    }


}
