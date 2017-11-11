package de.kiyan.skyblockextra.commands;

import de.kiyan.skyblockextra.Config;
import de.kiyan.skyblockextra.utils.ItemBuilder;
import de.kiyan.skyblockextra.utils.VoidItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class CMDdropparty implements CommandExecutor
{
    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args )
    {
        if( !( sender instanceof Player ) )
        {
            sender.sendMessage( "§cYou must be a player" );

            return false;
        }

        if( !label.equalsIgnoreCase( "dropparty" ) )
        {
            return false;
        }

        Player player = ( Player ) sender;

        if( !player.hasPermission( "subzero.dropparty" ) )
        {
            player.sendMessage( Config.DropPartyPrefix + " §cYou dont have enough permissions!" );

            return false;
        }
        if( args.length == 0 )
        {
            player.openInventory( getInventory() );
            return true;
        }

        return false;
    }

    public Inventory getInventory( )
    {
        List< Item > iItems = new VoidItem( ).getItems( );
        int i = 0;
        boolean bNext = true;
        int iPage = 1;
        int entriesPerPage = 44;
        int startIndex = ( iPage - 1 ) * entriesPerPage;
        int endIndex = startIndex + entriesPerPage;
        if( endIndex > iItems.size( ) )
        {
            endIndex = iItems.size( );
            bNext = false;
        }

        Inventory inv = Bukkit.getServer( ).createInventory( null, 54, "§e§lDrop Party§f - §c§l" + iPage );

        for( Item item : iItems.subList( startIndex, endIndex ) )
        {
            if( i < 46 )
            {
                inv.setItem( i, item.getItemStack( ) );

                i++;
            }
        }
        if( bNext )
        {
            inv.setItem( 53, new ItemBuilder( Material.STAINED_GLASS_PANE, 1, ( byte ) 5 ).setName( "&aNext" ).toItemStack( ) );
        } else if( iPage != 1 )
        {
            inv.setItem( 45, new ItemBuilder( Material.STAINED_GLASS_PANE, 1, ( byte ) 14 ).setName( "&cBack" ).toItemStack( ) );
        }

        return inv;
    }
}
