package de.kiyan.skyblockextra.Listener;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionEnteredEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;
import com.mewin.WGRegionEvents.events.RegionLeftEvent;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import de.kiyan.skyblockextra.Main;
import de.kiyan.skyblockextra.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.List;

public class RegionEvent implements Listener
{
    public static Integer taskid = 0;
    public static Integer playerCount = 0;

    @EventHandler
    public void deathPlayer( PlayerDeathEvent event )
    {
        Player player = event.getEntity();
        if( !( player instanceof Player ) )
            return;

        List< ItemStack > is = event.getDrops();
        for( ItemStack drops : is )
        {
            if( drops.getType() == Material.FLINT && drops.getItemMeta().getDisplayName().equalsIgnoreCase( "§cSpectral" ) )
            {
                drops.setType( Material.AIR );
            }
        }
    }

    @EventHandler
    public void enterRegion( RegionEnteredEvent event )
    {
        Player player = event.getPlayer( );
        if( !( player instanceof Player ) )
            return;

        ProtectedRegion rg = event.getRegion( );

        if( rg == null )
            return;

        if( rg.getId( ).equals( "pvp_farm" ) )
        {
            playerCount++;

            if( playerCount == 1 )
            {
                if( !Bukkit.getServer( ).getScheduler( ).isCurrentlyRunning( taskid ) )
                {
                    start( );
                }
            } else if( playerCount > 1 )
            {
                if( taskid > 0 )
                {
                    Bukkit.getServer( ).getScheduler( ).cancelTask( taskid );

                    World world = Bukkit.getWorld( "world" );
                    Block stainedblock = world.getBlockAt( new Location( world, -21.0, 62.0, 267.0 ) );

                    stainedblock.setType( Material.STAINED_GLASS );
                    stainedblock.setData( ( byte ) 1 );
                }
            }
        }
    }

    @EventHandler
    public void leaveRegion( RegionLeftEvent event )
    {
        Player player = event.getPlayer( );
        if( !( player instanceof Player ) )
            return;

        ProtectedRegion rg = event.getRegion( );

        if( rg == null )
            return;

        if( rg.getId( ).equals( "pvp_farm" ) )
        {
            playerCount--;

            if( playerCount == 0 )
            {
                World world = Bukkit.getWorld( "world" );
                Block diablock = world.getBlockAt( new Location( world, -21.0, 60.0, 267 ) );
                Block stainedblock = world.getBlockAt( new Location( world, -21.0, 62.0, 267.0 ) );

                if( diablock.getType( ) == Material.DIAMOND_BLOCK )
                    diablock.setType( Material.AIR );

                stainedblock.setType( Material.STAINED_GLASS );
                stainedblock.setData( ( byte ) 0 );

                if( taskid > 0 )
                {
                    Bukkit.getServer( ).getScheduler( ).cancelTask( taskid );
                }
            } else if( playerCount == 1 )
            {
                start( );
            }
        }
    }

    public void start( )
    {
        taskid = Bukkit.getServer( ).getScheduler( ).scheduleSyncRepeatingTask( Main.instance, new Runnable( )
        {
            @Override
            public void run( )
            {
                World world = Bukkit.getWorld( "world" );
                Block diablock = world.getBlockAt( new Location( world, -21.0, 60.0, 267 ) );
                Block stainedblock = world.getBlockAt( new Location( world, -21.0, 62.0, 267.0 ) );
                if( diablock.getType( ) != Material.DIAMOND_BLOCK )
                    diablock.setType( Material.DIAMOND_BLOCK );

                stainedblock.setType( Material.STAINED_GLASS );
                stainedblock.setData( ( byte ) 13 );

                ItemStack is = new ItemBuilder( Material.FLINT ).setName( "§cSpectral" ).setLore( "§r", "§cWhat is this strange", "§cMineral?" ).toItemStack( );

                world.dropItemNaturally( new Location( world, -21.0, 69.0, 267.0 ), is );
            }
        }, 20, 20 );
    }

}
