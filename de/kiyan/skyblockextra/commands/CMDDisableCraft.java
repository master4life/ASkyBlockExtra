package de.kiyan.skyblockextra.commands;

import de.kiyan.skyblockextra.Config;
import de.kiyan.skyblockextra.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CMDDisableCraft implements CommandExecutor {
    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args ) {
        if( !( sender instanceof Player ) )
        {
            sender.sendMessage( "§cYou must be a player" );

            return false;
        }

        if( !label.equalsIgnoreCase( "disablecraft" ) )
        {
            return false;
        }

        Player player = ( Player ) sender;

        if( !player.hasPermission( "subzero.disablecraft" ) )
        {
            player.sendMessage(Config.DisablePrefix + " §cYou dont have enough permissions!" );

            return false;
        }
        List<String> disabledItems = Main.instance.getConfig().getStringList( "DisableCrafting.Items" );

        if( args.length == 0 )
        {
            player.sendMessage( "     §e§l" + Config.DisablePrefix );
            player.sendMessage( " §e§lDisable Item to being crafted :");
            player.sendMessage( "§d"+ disabledItems.toString() );

            return true;
        }

        if( args[ 0 ].equalsIgnoreCase( "add" ))
        {
            if( args.length == 1 )
            {
                player.sendMessage( Config.DisablePrefix + " §ePlease provide a ID to being excluded." );

                return true;
            }

            if( ! disabledItems.contains( args[ 1 ] ) && isInt(args[ 1 ] ) )
            {
                player.sendMessage( Config.DisablePrefix + " §aID: §d" + args[ 1 ] +"§a added to your list.");
                disabledItems.add( args[ 1 ] );
                Main.instance.getConfig().set( "DisableCrafting.Items", disabledItems );
                Main.instance.saveConfig();

                return true;
            }
            else if( disabledItems.contains( args[ 1 ] ) )
            {
                player.sendMessage(Config.DisablePrefix + " §cThis ID is already in the list." );

                return true;
            }
            else
            {
                player.sendMessage(Config.DisablePrefix + " §cPlease provide only numbers." );
                player.sendMessage(Config.DisablePrefix + " §7like Diamond Helmet = 310." );
                player.sendMessage(Config.DisablePrefix + " §7http://minecraft-ids.grahamedgecombe.com" );

                return false;
            }
        }

        if( args[ 0 ].equalsIgnoreCase( "remove" ))
        {
            if( args.length == 1 )
            {
                player.sendMessage( Config.DisablePrefix + " §ePlease provide a ID to being excluded." );

                return true;
            }

            if( disabledItems.contains( args[ 1 ] ) && isInt( args[ 1 ] ) )
            {
                player.sendMessage( Config.DisablePrefix + " §aID: §d" + args[ 1 ] +"§a removed from your list.");
                disabledItems.remove( args[ 1 ] );
                Main.instance.getConfig().set( "DisableCrafting.Items", disabledItems );
                Main. instance.saveConfig();

                return true;
            }
            else if( !disabledItems.contains( args[ 1 ] ) && isInt( args[ 1 ] ) )
            {
                player.sendMessage(Config.DisablePrefix + " §cThis ID does not exist in the list." );

                return true;
            }
            else
            {
                player.sendMessage(Config.DisablePrefix + " §cPlease provide only numbers." );
                player.sendMessage(Config.DisablePrefix + " §7like Diamond Helmet = 310." );
                player.sendMessage(Config.DisablePrefix + " §7http://minecraft-ids.grahamedgecombe.com" );

                return false;
            }
        }

        if( args[ 0 ].equalsIgnoreCase( "reload" ) )
        {
            player.sendMessage( Config.DisablePrefix + " §aConfig has reloaded." );
            new Config().AssignVar();

            return true;
        }

        return false;
    }

    public boolean isInt( String s )
    {
        try
        {
            Integer.parseInt( s );
        } catch( NumberFormatException nfe )
        {
            return false;
        }

        return true;
    }
}
