package de.kiyan.skyblockextra.Listener;

import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.command.composition.FlagParser;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.*;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import de.kiyan.skyblockextra.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class stopGrowth implements Listener
{
    public ArrayList< Player > breakWarn = new ArrayList<>( );
    public ArrayList< Player > silkWarn = new ArrayList<>( );

    @EventHandler
    public void stopGrassGrow( BlockBreakEvent event ) throws Exception
    {
        Block block = event.getBlock( );
        Player player = event.getPlayer( );

        if( block == null || player.getGameMode( ) == GameMode.CREATIVE )
            return;

        Material mat = block.getType( );

        if( Main.PlayerWithinRegion( player, block.getLocation( ) ) && ( mat == Material.DIAMOND_ORE || mat == Material.COAL_ORE || mat == Material.REDSTONE_ORE || mat == Material.LAPIS_ORE || mat == Material.EMERALD_ORE ) )
        {
            event.setDropItems( false );
            event.setExpToDrop( 0 );
            Location loc = block.getLocation( );

            loc.getWorld( ).dropItemNaturally( loc, new ItemStack( mat ) );

            return;
        }

        if( !Main.PlayerWithinRegion( player, block.getLocation( ) ) )
        {
            return;
        }

        if( mat == Material.GRASS )
        {
            if( ( breakWarn == null || !silkWarn.contains( player ) ) && player.getItemInHand( ).getItemMeta( ).hasEnchant( Enchantment.SILK_TOUCH ) )
            {
                silkWarn.add( player );
                player.sendMessage( "§7[§eSubBlock§7] §cPlease be careful!" );
                player.sendMessage( "§cYou cannot turn Grass Blocks back to item form" );
            }

            event.setDropItems( false );

            block.setType( Material.AIR );
            Location loc = block.getLocation( );
            loc.getWorld( ).dropItemNaturally( loc, new ItemStack( Material.DIRT ) );

            return;
        }
    }

    @EventHandler
    public void protectionGrass( BlockPlaceEvent event )
    {
        Block block = event.getBlock( );
        Player player = event.getPlayer( );

        if( block == null || !Main.PlayerWithinRegion( player, block.getLocation( ) ) )
            return;

        Material mat = block.getType( );

        if( mat == Material.GRASS )
        {
            if( breakWarn == null || !breakWarn.contains( player ) )
            {
                player.sendMessage( "§7[§eSubBlock§7] §cPlease be careful!" );
                player.sendMessage( "§aGrass §7blocks§f are also your§6 currency§f on this server!" );
                Location loc = block.getLocation( );
                block.setType( Material.AIR );
                loc.getWorld( ).dropItemNaturally( loc, new ItemStack( Material.GRASS ) );
                block.getWorld( ).spawnParticle( Particle.BLOCK_CRACK, block.getLocation( ).add( 0D, 0.5D, 0D ), 100, new MaterialData( Material.GRASS ) );
                breakWarn.add( player );
            }
        }
    }

    @EventHandler
    public void leaveUser( PlayerQuitEvent event )
    {
        Player player = event.getPlayer( );

        if( breakWarn.contains( player ) )
        {
            breakWarn.remove( player );
        }
    }
}
