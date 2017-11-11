package de.kiyan.skyblockextra.Listener;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import com.wasteofplastic.askyblock.events.IslandNewEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class Create implements Listener
{
    @EventHandler
    public void newIsland( IslandNewEvent event )
    {
        Player player = event.getPlayer( );
        ASkyBlockAPI Askyblock = ASkyBlockAPI.getInstance( );

        if( Askyblock == null )
        {
            return;
        }

        Island is = Askyblock.getIslandOwnedBy( player.getUniqueId( ) );

        if( is == null )
            return;

        is.setIgsFlag( Island.SettingsFlag.FLY, false );
        is.setIgsFlag( Island.SettingsFlag.ENCHANTING, false );
        is.setIgsFlag( Island.SettingsFlag.MOB_SPAWN, false );
        is.setIgsFlag( Island.SettingsFlag.MONSTER_SPAWN, false );
        is.setIgsFlag( Island.SettingsFlag.FIRE, true );
        is.setIgsFlag( Island.SettingsFlag.FIRE_SPREAD, true );
        is.setIgsFlag( Island.SettingsFlag.PVP, false );
        is.setIgsFlag( Island.SettingsFlag.BREEDING, true );
        is.setIgsFlag( Island.SettingsFlag.PORTAL, false );
        is.setIgsFlag( Island.SettingsFlag.SHEARING, false );
        is.setIgsFlag( Island.SettingsFlag.VILLAGER_TRADING, false );
    }
}
