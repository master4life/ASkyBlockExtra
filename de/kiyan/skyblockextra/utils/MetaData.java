package de.kiyan.skyblockextra.utils;

import de.kiyan.skyblockextra.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class MetaData
{
    public static void spawnGrass( Player player, Location loc )
    {
        Item z = ( Item ) loc.getWorld( ).spawnEntity( loc, EntityType.DROPPED_ITEM );
        z.setItemStack( new ItemStack( Material.GRASS ) );


    }


    public static void setOwnerless( Item item, Player owner )
    {

    }

}
