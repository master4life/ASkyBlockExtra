package de.kiyan.skyblockextra.utils;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class EnchantmentTable
{
    private EntityPlayer entityPlayer;
    private String title = "";
    private EnchantingTableContainer container;

    public EnchantmentTable( Player player )
    {
        EntityPlayer entityPlayer = ( ( CraftPlayer ) player ).getHandle( );
        container = new EnchantingTableContainer( entityPlayer );
    }

    public void open( )
    {
        int c = entityPlayer.nextContainerCounter( );

        entityPlayer.playerConnection.sendPacket( new PacketPlayOutOpenWindow( c, "minecraft:enchanting_table", IChatBaseComponent.ChatSerializer.a( "{'text':'" + title + "'}" ) ) );
        entityPlayer.activeContainer = container;
        entityPlayer.activeContainer.windowId = c;
        entityPlayer.activeContainer.addSlotListener( entityPlayer );
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public void addItem( EnchantingSlot enchantingSlot, ItemStack stack )
    {
        container.setItem( enchantingSlot.getSlot( ), CraftItemStack.asNMSCopy( stack ) );
    }

    public enum EnchantingSlot
    {
        TO_ENCHANT( 0 ), LAPIS( 1 );

        private int slot;

        private EnchantingSlot( int slot )
        {
            this.slot = slot;
        }

        public int getSlot( )
        {
            return slot;
        }
    }

    private class EnchantingTableContainer extends ContainerEnchantTable
    {
        public EnchantingTableContainer( EntityHuman entity )
        {
            super( entity.inventory, entity.world, new BlockPosition( 0, 0, 0 ) );
        }

        public boolean a( EntityHuman entityhuman )
        {
            return true;
        }
    }
}