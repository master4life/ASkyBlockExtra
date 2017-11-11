package de.kiyan.skyblockextra.Listener;

import de.kiyan.skyblockextra.Config;
import de.kiyan.skyblockextra.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.List;

public class DisableCraft implements Listener
{
    @EventHandler( priority = EventPriority.HIGHEST )
    public void onCraft( CraftItemEvent e )
    {
        List< Integer > disabled = Main.instance.getConfig( ).getIntegerList( "DisableCrafting.Items" );
        Player p = ( Player ) e.getWhoClicked( );

        if( disabled.contains( Integer.valueOf( e.getCurrentItem( ).getTypeId( ) ) ) )
        {
            e.setCancelled( true );
            p.sendMessage( Config.WarnMessage );
        }
    }

}
