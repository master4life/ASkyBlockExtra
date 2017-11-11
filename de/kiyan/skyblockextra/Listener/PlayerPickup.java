package de.kiyan.skyblockextra.Listener;


import de.kiyan.skyblockextra.Main;
import de.kiyan.skyblockextra.utils.PotionSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class PlayerPickup implements Listener
{
    @EventHandler
    public void onDrop( PlayerDropItemEvent event )
    {
        Item item = event.getItemDrop( );
        List< String > Lore = new ArrayList( );
        if( event.getPlayer( ) == null || !( item.getItemStack( ).getType( ) == Material.GRASS ) )
        {
            return;
        }

        ItemMeta meta = item.getItemStack( ).getItemMeta( );
        if( meta.hasLore( ) )
        {
            Lore = meta.getLore( );
        }
        Lore.add( event.getPlayer( ).getName( ) );
        meta.setLore( Lore );

        item.getItemStack( ).setItemMeta( meta );
    }

    @EventHandler
    public void onPickUp( PlayerPickupItemEvent event )
    {
        ItemMeta meta = event.getItem( ).getItemStack( ).getItemMeta( );
        Player player = event.getPlayer( );

        List< String > Lore = new ArrayList( );

        if( event.getItem( ).getItemStack( ).hasItemMeta( ) )
        {
            if( meta.hasDisplayName( ) && meta.getDisplayName( ).contains( "dn[aOKFN-3JF[OIJ34=-F" ) )
            {
                String[] stringArray = meta.getDisplayName( ).replace( " ", "" ).split( ":" );
                List< String > lore = meta.getLore( );
                double x = Double.parseDouble( lore.get( 0 ) );
                double y = Double.parseDouble( lore.get( 1 ) );
                double z = Double.parseDouble( lore.get( 2 ) );
                String w = lore.get( 3 );

                Location DropLoc = new Location( Bukkit.getWorld( w ), x, y, z );

                if( stringArray[ 1 ].equalsIgnoreCase( "STRENGTH" ) && stringArray[ 2 ].equalsIgnoreCase( "1" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.INCREASE_DAMAGE, 20 * Integer.parseInt( stringArray[ 3 ] ), 0, true ) );
                }
                if( stringArray[ 1 ].equalsIgnoreCase( "STRENGTH" ) && stringArray[ 2 ].equalsIgnoreCase( "2" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.INCREASE_DAMAGE, 20 * Integer.parseInt( stringArray[ 3 ] ), 1, true ) );
                }
                if( stringArray[ 1 ].equalsIgnoreCase( "REGEN" ) && stringArray[ 2 ].equalsIgnoreCase( "1" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.REGENERATION, 20 * Integer.parseInt( stringArray[ 3 ] ), 0, true ) );
                }
                if( stringArray[ 1 ].equalsIgnoreCase( "REGEN" ) && stringArray[ 2 ].equalsIgnoreCase( "2" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.REGENERATION, 20 * Integer.parseInt( stringArray[ 3 ] ), 1, true ) );
                }
                if( stringArray[ 1 ].equalsIgnoreCase( "SPEED" ) && stringArray[ 2 ].equalsIgnoreCase( "1" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.SPEED, 20 * Integer.parseInt( stringArray[ 3 ] ), 0, true ) );
                }
                if( stringArray[ 1 ].equalsIgnoreCase( "SPEED" ) && stringArray[ 2 ].equalsIgnoreCase( "2" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.SPEED, 20 * Integer.parseInt( stringArray[ 3 ] ), 2, true ) );
                }
                if( stringArray[ 1 ].equalsIgnoreCase( "NIGHT_VISION" ) && stringArray[ 2 ].equalsIgnoreCase( "1" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.NIGHT_VISION, 20 * Integer.parseInt( stringArray[ 3 ] ), 1, true ) );
                }
                if( stringArray[ 1 ].equalsIgnoreCase( "SLOWNESS" ) && stringArray[ 2 ].equalsIgnoreCase( "1" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.SLOW, 20 * Integer.parseInt( stringArray[ 3 ] ), 1, true ) );
                }
                if( stringArray[ 1 ].equalsIgnoreCase( "WEAKNESS" ) && stringArray[ 2 ].equalsIgnoreCase( "1" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.WEAKNESS, 20 * Integer.parseInt( stringArray[ 3 ] ), 1, true ) );
                }
                if( stringArray[ 1 ].equalsIgnoreCase( "JUMP" ) && stringArray[ 2 ].equalsIgnoreCase( "1" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.LEVITATION, 20 * Integer.parseInt( stringArray[ 3 ] ), 1, true ) );
                }
                if( stringArray[ 1 ].equalsIgnoreCase( "JUMP" ) && stringArray[ 2 ].equalsIgnoreCase( "2" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.LEVITATION, 20 * Integer.parseInt( stringArray[ 3 ] ), 2, true ) );
                }
                if( stringArray[ 1 ].equalsIgnoreCase( "INVISIBILITY" ) && stringArray[ 2 ].equalsIgnoreCase( "1" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.INVISIBILITY, 20 * Integer.parseInt( stringArray[ 3 ] ), 1, true ) );
                }
                if( stringArray[ 1 ].equalsIgnoreCase( "FIRE_RESISTANCE" ) && stringArray[ 2 ].equalsIgnoreCase( "1" ) )
                {
                    player.addPotionEffect( new PotionEffect( PotionEffectType.FIRE_RESISTANCE, 20 * Integer.parseInt( stringArray[ 3 ] ), 1, true ) );
                }

                event.getItem( ).remove( );
                event.setCancelled( true );

                Bukkit.getScheduler( ).scheduleSyncDelayedTask( Main.instance, new Runnable( )
                {
                    @Override
                    public void run( )
                    {
                        PotionSpawner.SpawnPotion( DropLoc );
                    }
                }, 30 );
            }
        }

        if( event.getPlayer( ) == null || !( event.getItem( ).getItemStack( ).getType( ) == Material.GRASS ) )
        {
            return;
        }
        if( meta.getLore( ) == null )
        {
            return;
        }
        if( meta.hasLore( ) )
        {
            Lore = meta.getLore( );
        }
        if( meta.getLore( ).get( 0 ).equalsIgnoreCase( player.getName( ) ) )
        {
            Lore.remove( Lore.size( ) - 1 );
            meta.setLore( Lore );
            event.getItem( ).getItemStack( ).setItemMeta( meta );

            return;
        }

        event.setCancelled( true );
    }
}