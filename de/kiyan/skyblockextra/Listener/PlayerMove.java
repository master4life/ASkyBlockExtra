package de.kiyan.skyblockextra.Listener;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import de.kiyan.skyblockextra.Main;
import de.kiyan.skyblockextra.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerMove implements Listener
{
    @EventHandler
    public void playerMove( PlayerMoveEvent event )
    {
        Player player = event.getPlayer( );

        if( !( player instanceof Player ) || player.getGameMode() == GameMode.CREATIVE || !player.getWorld().getName().equalsIgnoreCase( "world" ) )
            return;

        Location loc = player.getLocation( );
        if( loc.getY( ) <= 23.0D )
        {
            player.teleport( Bukkit.getServer( ).getWorlds( ).get( 0 ).getSpawnLocation( ) );
            player.sendMessage( "§7[§bSky§9Block§7] Please be careful, next time!" );
        }
    }

}
