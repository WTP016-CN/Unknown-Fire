package com.wtp016.unknownfire;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class Unknownfire extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Hello! This is WTP016's Plugin, named \"UnknownFire\".");
        getLogger().info("This Plugin is open source on Github.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Say again, thanks for using this plugin!");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location loc = event.getPlayer().getLocation();
        loc.setY(loc.getY() + 5);
        Block b = loc.getBlock();
        b.setType(Material.BEDROCK);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        Player player = evt.getPlayer();
        PlayerInventory inventory = player.getInventory();
        ItemStack itemstack = new ItemStack(Material.DIAMOND_BLOCK, 64);

        if (inventory.contains(itemstack)) {
            inventory.addItem(itemstack);
            player.sendMessage("§3§l注意!检测到你从系统中偷取了一组钻石块，请在5分钟内使用/spawn指令回到主城并放回!");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ignite")) {
            if (args.length != 1) {
                return false;
            }

            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can set other players on fire.");
                sender.sendMessage("This is an arbitrary requirement for demonstration purposes only.");
                return false;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(args[0] + "is not currently online.");
                return true;
            }

            target.setFireTicks(2000);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
            player.getWorld().strikeLightning(player.getTargetBlock(null,200).getLocation());
        } // getItemInHand废弃，待修复。
    }
}
