package de.kiyan.skyblockextra.Listener;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteract implements Listener
{
    @EventHandler
    public void playerInteract( PlayerInteractEntityEvent event )
    {
        Player player = event.getPlayer( );
        Entity entity = event.getRightClicked( );

        if( player == null || !( player instanceof Player ) || entity == null )
        {
            return;
        }

        Island is = ASkyBlockAPI.getInstance( ).getIslandAt( player.getLocation( ) );

        if( is == null )
        {
            return;
        }

        if( entity.getType( ) == EntityType.VILLAGER )
        {
            if( !player.hasPermission( "IslandConfig.VILLAGER_TRADING" ) )
            {
                event.setCancelled( true );
                player.sendMessage( "§7[§bSky§9Block§7] §fYou can't trade with villager, please check the §ashop-§9tier 3§f out." );

                return;
            }

            if( !is.getIgsFlag( Island.SettingsFlag.VILLAGER_TRADING ) )
            {
                event.setCancelled( true );
                player.sendMessage( "§7[§bSky§9Block§7] §fYou need to enable this. §eUse /isconfig." );

                return;
            }
        }

        if( entity instanceof Sheep && player.getItemInHand( ).getType( ) == Material.SHEARS )
        {
            if( !player.hasPermission( "IslandConfig.SHEARING" ) )
            {
                event.setCancelled( true );
                player.sendMessage( "§7[§bSky§9Block§7] §fYou can't shear those animals, please check the §ashop-§2tier 2§f out." );

                return;
            }

            if( !is.getIgsFlag( Island.SettingsFlag.SHEARING ) )
            {
                event.setCancelled( true );
                player.sendMessage( "§7[§bSky§9Block§7] §fYou need to enable this. §eUse /isconfig." );

                return;
            }
        }

        if( entity instanceof Animals && ( player.getItemInHand( ).getType( ) == Material.SEEDS || player.getItemInHand( ).getType( ) == Material.CARROT_ITEM || player.getItemInHand( ).getType( ) == Material.WHEAT ) )
        {
            if( !player.hasPermission( "IslandConfig.BREEDING" ) )
            {
                event.setCancelled( true );
                player.sendMessage( "§7[§bSky§9Block§7] §fYou can't breed those animals, please check the §ashop-§2tier 2§f out." );

                return;
            }

            if( !is.getIgsFlag( Island.SettingsFlag.BREEDING ) )
            {
                event.setCancelled( true );
                player.sendMessage( "§7[§bSky§9Block§7] §fYou need to enable this. §eUse /isconfig." );

                return;
            }
        }
    }
}
