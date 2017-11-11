package de.kiyan.skyblockextra.Listener;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import com.wasteofplastic.askyblock.events.IslandEnterEvent;
import com.wasteofplastic.askyblock.events.IslandExitEvent;
import com.wasteofplastic.askyblock.events.IslandLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Fly implements Listener
{
    @EventHandler
    public void enterIsland( IslandEnterEvent event )
    {
        Player player = Bukkit.getPlayer( event.getPlayer( ) );

        if( !( player instanceof Player ) )
        {
            return;
        }

        Island is = ASkyBlockAPI.getInstance( ).getIslandAt( player.getLocation( ) );

        if( is == null )
            return;

        if( is.getIgsFlag( Island.SettingsFlag.FLY ) )
        {
            player.setAllowFlight( true );
            player.sendMessage( "§6You can fly now." );
        }
    }

    @EventHandler
    public void leaveIsland( IslandLeaveEvent event )
    {
        Player player = Bukkit.getPlayer( event.getPlayer( ) );

        if( !( player instanceof Player ) )
        {
            return;
        }

        if( player.getAllowFlight( ) )
        {
            player.setAllowFlight( false );
            player.setFlying( false );
            player.sendMessage( "§6You cannot fly now." );
        }
    }

    @EventHandler
    public void teleport( PlayerTeleportEvent event )
    {
        Player player = event.getPlayer( );

        if( !( player instanceof Player ) || player == null )
        {
            return;
        }

        Island is = ASkyBlockAPI.getInstance( ).getIslandAt( event.getTo() );

        if( is == null )
        {
            if( player.getAllowFlight( ) )
            {
                player.setAllowFlight( false );
                player.setFlying( false );
                player.sendMessage( "§6You cannot fly now." );
            }

            return;
        }

        if( is.getIgsFlag( Island.SettingsFlag.FLY ) )
        {
            player.setAllowFlight( true );
            player.sendMessage( "§6You can fly now." );
        }
        if( player.getAllowFlight( ) )
        {
            if( !is.getIgsFlag( Island.SettingsFlag.FLY ) )
            {
                player.setAllowFlight( false );
                player.setFlying( false );
                player.sendMessage( "§6You cannot fly now." );
            }
        }
    }
}
