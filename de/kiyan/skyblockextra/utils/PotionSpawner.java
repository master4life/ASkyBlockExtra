package de.kiyan.skyblockextra.utils;

import de.kiyan.skyblockextra.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PotionSpawner
{
    public static Map< Location, Integer > LoadedPotions = new HashMap< Location, Integer >( );
    public static Map< Location, Entity > PotionEffects = new HashMap< Location, Entity >( );
    public static Map< Location, String > PotionTypes = new HashMap< Location, String >( );
    public static Map< Location, String > PotionMeta = new HashMap< Location, String >( );
    public static Map< Location, String > PotionDura = new HashMap< Location, String >( );
    public static boolean UsePotionEffects = true;

    static int JumpCounter = 0;

    public static void PotionEffects( )
    {
        Bukkit.getScheduler( ).scheduleSyncRepeatingTask( Main.instance, new Runnable( )
        {
            @Override
            public void run( )
            {
                if( UsePotionEffects )
                {

                    if( JumpCounter == 20 )
                    {
                        for( Entity entity : PotionEffects.values( ) )
                        {
                            entity.setVelocity( new Vector( 0, Utils.randDouble( 0.30, 0.50 ), 0 ) );
                        }
                        JumpCounter = 0;
                    }

                    for( Entity entity : PotionEffects.values( ) )
                    {
                        if( entity.isEmpty( ) )
                        {
                            entity.getWorld( ).playEffect( entity.getLocation( ), Effect.TILE_BREAK, 174 );
                            entity.getWorld( ).playEffect( entity.getLocation( ), Effect.TILE_BREAK, 22 );
                        }
                    }

                    JumpCounter++;
                }
            }
        }, 20, 2 );

    }

    public static void PotionRespawner( )
    {
        Bukkit.getScheduler( ).scheduleSyncRepeatingTask( Main.instance, new Runnable( )
        {
            @Override
            public void run( )
            {
                SpawnPotions( );
            }
        }, 100l, 5400l );
    }

    public static void SpawnPotions( )
    {
        loadPotions( );

        for( World world : Bukkit.getWorlds( ) )
        {
            for( Entity entity : world.getEntitiesByClass( Item.class ) )
            {
                if( entity instanceof Item )
                {
                    Item item = ( Item ) entity;
                    ItemStack iStack = item.getItemStack( );
                    if( iStack.hasItemMeta( ) )
                    {
                        if( iStack.getItemMeta( ).hasDisplayName( ) )
                        {
                            if( iStack.getItemMeta( ).getDisplayName( ).contains( "dn[aOKFN-3JF[OIJ34=-F" ) )
                            {
                                entity.remove( );
                            }
                        }
                    }
                }
            }
        }

        for( Location loc : LoadedPotions.keySet( ) )
        {
            SpawnPotion( loc );
        }

    }

    public static void SpawnPotion( Location loc )
    {
        List< String > Lore = new ArrayList< String >( );
        ItemStack iStack = new ItemStack( Material.SPLASH_POTION, 1 );

        ItemMeta itemMeta = iStack.getItemMeta( );
        itemMeta.setDisplayName( "dn[aOKFN-3JF[OIJ34=-F:" + PotionTypes.get( loc ) + ":" + PotionMeta.get( loc ) + ":" + PotionDura.get( loc ) );
        Lore.add( "" + loc.getX( ) );
        Lore.add( "" + loc.getY( ) );
        Lore.add( "" + loc.getZ( ) );
        Lore.add( "" + loc.getWorld( ).getName( ) );
        itemMeta.setLore( Lore );
        iStack.setItemMeta( itemMeta );

        Potion pot = new Potion( 1 );
        pot.setHasExtendedDuration( true );
        pot.setType( PotionType.valueOf( PotionTypes.get( loc ) ) );
        pot.setSplash( true );
        pot.apply( iStack );

        Item i = loc.getWorld( ).dropItem( loc, iStack );
        i.setVelocity( new Vector( 0, 0, 0 ) );
        PotionEffects.put( loc, i );
    }

    public static void ClearAllPotions( )
    {
        for( World world : Bukkit.getWorlds( ) )
        {
            for( Entity entity : world.getEntitiesByClass( Item.class ) )
            {
                if( entity instanceof Item )
                {
                    Item item = ( Item ) entity;
                    ItemStack iStack = item.getItemStack( );
                    if( iStack.hasItemMeta( ) )
                    {
                        if( iStack.getItemMeta( ).hasDisplayName( ) )
                        {
                            if( iStack.getItemMeta( ).getDisplayName( ).contains( "dn[aOKFN-3JF[OIJ34=-F" ) )
                            {
                                entity.remove( );
                            }
                        }
                    }
                }
            }
        }

        PotionEffects.clear( );
        PotionTypes.clear();
        PotionMeta.clear();
        PotionDura.clear();
        LoadedPotions.clear();

        File file = new File( Main.instance.getDataFolder( ) + "/potion.yml" );
        if( file.exists( ) )
        {
            file.delete( );
        }
    }

    public static void ReloadPotions( )
    {
        LoadedPotions.clear( );
        PotionEffects.clear( );
        SpawnPotions( );
    }

    public static void loadPotions( )
    {
        File file = new File( Main.instance.getDataFolder( ) + "/potion.yml" );
        if( !file.exists( ) )
        {
            try
            {
                file.createNewFile( );
            } catch( IOException e )
            {
                e.printStackTrace( );
            }
        }
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration( file );
        List< Location > loadPots = new ArrayList<>( );

        if( yaml.contains( "Potions" ) )
        {
            for( String ids : yaml.getConfigurationSection( "Potions" ).getKeys( false ) )
            {
                double x = yaml.getDouble( "Potions." + ids + ".x" );
                double y = yaml.getDouble( "Potions." + ids + ".y" );
                double z = yaml.getDouble( "Potions." + ids + ".z" );
                String w = yaml.getString( "Potions." + ids + ".World" );

                Location location = new Location( Bukkit.getWorld( w ), x, y, z );

                PotionTypes.put( location, yaml.getString( "Potions." + ids + ".Type" ) );
                PotionMeta.put( location, yaml.getString( "Potions." + ids + ".Meta" ) );
                PotionDura.put( location, yaml.getString( "Potions." + ids + ".Dura" ) );
                LoadedPotions.put( location, Integer.parseInt( ids ) );
            }
        }
    }

    public static void writePotionPosition( Player player, PotionType type, String meta, Integer dura )
    {
        File file = new File( Main.instance.getDataFolder( ) + "/potion.yml" );
        if( !file.exists( ) )
        {
            try
            {
                file.createNewFile( );
            } catch( IOException e )
            {
                e.printStackTrace( );
            }
        }
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration( file );

        for( int i = 0; i < 700; i++ )
        {
            if( yaml.contains( "Potions." + i ) == false )
            {
                yaml.set( "Potions." + i + ".Type", type.toString( ) );
                yaml.set( "Potions." + i + ".Meta", meta );
                yaml.set( "Potions." + i + ".Dura", dura );
                yaml.set( "Potions." + i + ".x", player.getLocation( ).getX( ) );
                yaml.set( "Potions." + i + ".y", player.getLocation( ).getY( ) );
                yaml.set( "Potions." + i + ".z", player.getLocation( ).getZ( ) );
                yaml.set( "Potions." + i + ".World", player.getLocation( ).getWorld( ).getName( ) );
                player.sendMessage( ChatColor.translateAlternateColorCodes( '&', "&aNew speed potion added at [" + player.getLocation( ).getWorld( ).getName( ) + "]: &e" + player.getLocation( ).getBlockX( ) + "-" + player.getLocation( ).getBlockY( ) + "-" + player.getLocation( ).getBlockZ( ) + " &c[ID - " + i + "]" ) );
                saveLoc( yaml );
                return;
            }
        }

        loadPotions( );
    }

    public static void saveLoc( YamlConfiguration yaml )
    {
        try
        {
            yaml.save( Main.instance.getDataFolder( ) + "/potion.yml" );
        } catch( IOException ex )
        {
            ex.printStackTrace( );
        }
    }
}
