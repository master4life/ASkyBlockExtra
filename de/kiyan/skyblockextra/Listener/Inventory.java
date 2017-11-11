package de.kiyan.skyblockextra.Listener;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import de.kiyan.skyblockextra.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class Inventory implements Listener
{
    @EventHandler
    public void onInventory( InventoryClickEvent event )
    {
        org.bukkit.inventory.Inventory inv = event.getInventory( );

        if( inv.getName( ).equalsIgnoreCase( Config.Display ) )
        {
            Player player = ( Player ) event.getWhoClicked( );
            ItemStack clicked = event.getCurrentItem( );

            if( !( player instanceof Player ) )
            {
                return;
            }
            ASkyBlockAPI Askyblock = ASkyBlockAPI.getInstance( );

            if( Askyblock == null )
                return;

            Location loc = player.getLocation( );

            if( !Askyblock.getIslandAt( player.getLocation( ) ).getMembers().contains( player.getUniqueId() ) )
            {
                player.sendMessage( Config.IslandConfigPrefix + " §cYou are not the owner of this island.." );

                return;
            }

            Island is = Askyblock.getIslandAt( loc );

            if( is == null )
                return;

            if( clicked.getType( ) == Material.STAINED_GLASS_PANE )
            {
                event.setCancelled( true );
                player.sendMessage( Config.IslandConfigPrefix + " §7You needs to §apurchases§7 this feature, before you can change this value." );
            }

            if( clicked.getType( ) == Material.GOLD_SWORD )
            {
                event.setCancelled( true );

                is.setIgsFlag( Island.SettingsFlag.PVP, is.getIgsFlag( Island.SettingsFlag.PVP ) ? false : true );

                player.sendMessage( Config.IslandConfigPrefix + " " + ( is.getIgsFlag( Island.SettingsFlag.PVP ) ? "§eYou have §a§benabled§e PVP on your island!" : "§eYou have §cdisabled§e PVP on your island!" ) );
                player.closeInventory( );
            }
            if( clicked.getType( ) == Material.MONSTER_EGG )
            {
                event.setCancelled( true );

                is.setIgsFlag( Island.SettingsFlag.MOB_SPAWN, is.getIgsFlag( Island.SettingsFlag.MOB_SPAWN ) ? false : true );

                player.sendMessage( Config.IslandConfigPrefix + " " + ( is.getIgsFlag( Island.SettingsFlag.MOB_SPAWN ) ? "§eYou have §a§benabled§e Mob spawning on your island!" : "§eYou have §cdisabled§e Mob spawningon your island!" ) );
                player.closeInventory( );

            }
            if( clicked.getType( ) == Material.SKULL_ITEM )
            {
                event.setCancelled( true );

                is.setIgsFlag( Island.SettingsFlag.MONSTER_SPAWN, is.getIgsFlag( Island.SettingsFlag.MONSTER_SPAWN ) ? false : true );

                player.sendMessage( Config.IslandConfigPrefix + " " + ( is.getIgsFlag( Island.SettingsFlag.MONSTER_SPAWN ) ? "§eYou have §a§benabled§e Monster spawning on your island!" : "§eYou have §cdisabled§e Monster spawning on your island!" ) );
                player.closeInventory( );
            }
            if( clicked.getType( ) == Material.CARROT_ITEM )
            {
                event.setCancelled( true );

                is.setIgsFlag( Island.SettingsFlag.BREEDING, is.getIgsFlag( Island.SettingsFlag.BREEDING ) ? false : true );

                player.sendMessage( Config.IslandConfigPrefix + " " + ( is.getIgsFlag( Island.SettingsFlag.BREEDING ) ? "§eYou have §a§benabled§e Monster spawning on your island!" : "§eYou have §cdisabled§e Monster spawning on your island!" ) );
                player.closeInventory( );
            }
            if( clicked.getType( ) == Material.SHEARS )
            {
                event.setCancelled( true );

                is.setIgsFlag( Island.SettingsFlag.SHEARING, is.getIgsFlag( Island.SettingsFlag.SHEARING ) ? false : true );

                player.sendMessage( Config.IslandConfigPrefix + " " + ( is.getIgsFlag( Island.SettingsFlag.SHEARING ) ? "§eYou have §a§benabled§e Shearing your sheep on your island!" : "§eYou have §cdisabled§e Shearing your sheep on your island!" ) );
                player.closeInventory( );
            }
            if( clicked.getType( ) == Material.FLINT_AND_STEEL )
            {
                event.setCancelled( true );

                is.setIgsFlag( Island.SettingsFlag.FIRE, is.getIgsFlag( Island.SettingsFlag.FIRE ) ? false : true );
                is.setIgsFlag( Island.SettingsFlag.FIRE_SPREAD, is.getIgsFlag( Island.SettingsFlag.FIRE_SPREAD ) ? false : true );

                player.sendMessage( Config.IslandConfigPrefix + " " + ( is.getIgsFlag( Island.SettingsFlag.FIRE ) ? "§eYou have §a§benabled§e Fire spread on your island!" : "§eYou have §cdisabled§e Fire spread on your island!" ) );
                player.closeInventory( );
            }
            if( clicked.getType( ) == Material.EMERALD )
            {
                event.setCancelled( true );

                is.setIgsFlag( Island.SettingsFlag.VILLAGER_TRADING, is.getIgsFlag( Island.SettingsFlag.VILLAGER_TRADING ) ? false : true );

                player.sendMessage( Config.IslandConfigPrefix + " " + ( is.getIgsFlag( Island.SettingsFlag.VILLAGER_TRADING ) ? "§eYou have §a§benabled§e Villager trading on your island!" : "§eYou have §cdisabled§e Villager trading on your island!" ) );
                player.closeInventory( );
            }
            if( clicked.getType( ) == Material.FEATHER )
            {
                event.setCancelled( true );

                is.setIgsFlag( Island.SettingsFlag.FLY, is.getIgsFlag( Island.SettingsFlag.FLY ) ? false : true );
                player.sendMessage( Config.IslandConfigPrefix + " " + ( is.getIgsFlag( Island.SettingsFlag.FLY ) ? "§eYou have §a§benabled§e Fly on your island!" : "§eYou have §cdisabled§e Fly on your island!" ) );

                if( is.getIgsFlag( Island.SettingsFlag.FLY ) )
                {
                    for( Player target : Bukkit.getOnlinePlayers( ) )
                    {
                        if( is.onIsland( target.getLocation( ) ) )
                        {
                            target.setAllowFlight( true );
                            target.sendMessage( "§6You can fly now." );
                        }
                    }
                }

                if( !is.getIgsFlag( Island.SettingsFlag.FLY ) )
                {
                    for( Player target : Bukkit.getOnlinePlayers( ) )
                    {
                        if( is.onIsland( target.getLocation( ) ) )
                        {
                            target.setAllowFlight( false );
                            target.setFlying( false );
                            target.sendMessage( "§6You cannot fly now." );
                        }
                    }
                }
                player.closeInventory( );
            }
        }
    }
}
