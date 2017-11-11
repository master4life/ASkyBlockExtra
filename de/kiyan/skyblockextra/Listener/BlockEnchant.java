package de.kiyan.skyblockextra.Listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockEnchant implements Listener
{
    @EventHandler
    public void disableEnchant( PlayerInteractEvent event )
    {
        Block block = event.getClickedBlock();

        if( block == null )
            return;

        Material mat = block.getType();

        if( !( mat == Material.ENCHANTMENT_TABLE ) )
            return;

        if( event.getAction() == Action.RIGHT_CLICK_BLOCK  )
        {
            event.setCancelled( true );
            Player player = event.getPlayer();

            player.sendMessage( "§7[§bSky§9Block§7]§7 You cannot use enchantment tables on this skyblock" );
        }
    }
}
