package me.lt_col_bodo.bodo_mystery_box;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Random;

public class mystery_box extends JavaPlugin implements Listener
{
    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    /**
     * On Command give a Mystery box, only allowed when you are OP
     *
     * @param sender
     * @param cmd
     * @param label
     * @param args
     * @return
     */
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("mysterybox") && ((Player)sender).isOp()) {
            ItemStack mysterybox = new ItemStack(Material.ORANGE_GLAZED_TERRACOTTA);
            ItemMeta im = mysterybox.getItemMeta();

            im.setDisplayName(ChatColor.RED + "Bodo's Mystery Box");
            im.setLore(Arrays.asList("A mystery box can contain something valuable,", "but it can also contains something horrible. Good luck!", convertToInvisibleString("#!*&%")));
            mysterybox.setItemMeta(im);

            ((Player) sender).getInventory().addItem(mysterybox);
        } else {
            ((Player)sender).sendMessage(ChatColor.RED + "You are not allowed to use this command!");
        }

        return false;
    }

    public static String convertToInvisibleString(String s) {
        String hidden = "";
        for (char c : s.toCharArray()) hidden += ChatColor.COLOR_CHAR+""+c;
        return hidden;
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        ItemMeta block = e.getItemInHand().getItemMeta();

        Location pos = e.getBlock().getLocation();

        if(block.hasLore()) {
            String lore = block.getLore().toString();

            if(lore.contains("§#§!§*§&§%")) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "A mystery box has been opened by " + player.getDisplayName());
                e.getBlock().setType(Material.AIR);
                giveLoot(pos);
            }
        }
    }


    /**
     * Give loot
     *
     * The loot will given randomly, with a 5% change for a big sword and a 5% change for a big pickaxe
     *
     * @param player
     */
    public void giveLoot(Location player) {
        Random random = new Random();
        Integer number = random.nextInt(20);

        World world = player.getWorld();

        switch (number){
            case 0: case 11:
                world.dropItem(player, new ItemStack(Material.DIAMOND, 15));
                break;
            case 1: case 12:
                world.dropItem(player, new ItemStack(Material.COAL_ORE, 10));
                break;
            case 2:
                ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
                ItemMeta swordMeta = sword.getItemMeta();
                swordMeta.setDisplayName(ChatColor.RED + "Koningszwaard");
                swordMeta.addEnchant(Enchantment.FIRE_ASPECT, 3, true);
                swordMeta.addEnchant(Enchantment.DURABILITY, 5, true);
                swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
                sword.setItemMeta(swordMeta);
                world.dropItem(player, sword);
                break;
            case 3:
                ItemStack axe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
                ItemMeta axeMeta = axe.getItemMeta();
                axeMeta.setDisplayName(ChatColor.RED + "Koningshouweel");
                axeMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
                axeMeta.addEnchant(Enchantment.DURABILITY, 5, true);
                axeMeta.addEnchant(Enchantment.DIG_SPEED, 5, true);
                axeMeta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
                axe.setItemMeta(axeMeta);
                world.dropItem(player, axe);
                break;
            case 4:  case 13:
                world.spawnEntity(player, EntityType.WITCH);
                break;
            case 5: case 14:
                world.spawnEntity(player, EntityType.CREEPER);
                world.spawnEntity(player, EntityType.CREEPER);
                break;
            case 6: case 15:
                world.dropItem(player, new ItemStack(Material.OBSIDIAN, 15));
                break;
            case 7: case 16:
                Entity tnt = world.spawn(player, TNTPrimed.class);
                ((TNTPrimed)tnt).setFuseTicks(0);
                break;
            case 8: case 17:
                world.spawnEntity(player, EntityType.DONKEY);
                break;
            case 9: case 18:
                world.spawnEntity(player, EntityType.CHICKEN);
                break;
            case 10: case 19:
                world.spawnEntity(player, EntityType.WITHER_SKELETON);
                world.spawnEntity(player, EntityType.WITHER_SKELETON);
                world.spawnEntity(player, EntityType.WITHER_SKELETON);
                break;

        }
    }

}

