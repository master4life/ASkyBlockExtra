package de.kiyan.skyblockextra.Listener;

import de.kiyan.skyblockextra.utils.VoidItem;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;

public class ItemDropInVoid implements Listener
{
    @EventHandler
    public void itemDrop( ItemDespawnEvent event )
    {
        if( !( event.getEntity() instanceof Item ) || event.getEntity() == null )
            return;

        Item item = ( Item ) event.getEntity();

        if( event.getEntity().getLocation().getY() <= 0.0 )
        {
//            new VoidItem().addItem( item );
            System.out.println( "test" );
        }
    }
}
