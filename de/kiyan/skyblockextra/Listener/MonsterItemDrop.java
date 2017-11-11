package de.kiyan.skyblockextra.Listener;

import de.kiyan.skyblockextra.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class MonsterItemDrop implements Listener
{
    @EventHandler
    public void mobDrop( EntityDeathEvent event )
    {
        LivingEntity ent = event.getEntity( );

        if( ent == null ) return;

        if( !ent.getWorld( ).getName( ).equalsIgnoreCase( "world" ) ) return;

        if( !( ent instanceof Zombie ) ) return;

        event.setDroppedExp( 0 );
        List< ItemStack > drops = event.getDrops( );
        if( drops.size( ) > 0 )
        {
            for( ItemStack is : drops )
            {
                is.setType( Material.AIR );
            }
        }

        if( randInt( 1, 100 ) >= 50 )
        {
            if( randInt( 1, 100 ) >= 50 )
            {
                ent.getLocation( ).getWorld( ).dropItemNaturally( ent.getLocation( ), new ItemBuilder( Material.FLINT ).setName( "§cSpectral" ).setLore( "§r", "§cWhat is this strange", "§cMineral?" ).toItemStack( ) );
            } else
            {
                ent.getLocation( ).getWorld( ).dropItemNaturally( ent.getLocation( ), new ItemBuilder( Material.FLINT ).setName( "§cSpectral" ).setLore( "§r", "§cWhat is this strange", "§cMineral?" ).toItemStack( ) );
                ent.getLocation( ).getWorld( ).dropItemNaturally( ent.getLocation( ), new ItemBuilder( Material.FLINT ).setName( "§cSpectral" ).setLore( "§r", "§cWhat is this strange", "§cMineral?" ).toItemStack( ) );
            }
        }
    }

    public int randInt( int min, int max )
    {
        int randomNum = new Random( ).nextInt( ( max - min ) + 1 ) + min;
        return randomNum;
    }

}
