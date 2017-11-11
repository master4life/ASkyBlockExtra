package de.kiyan.skyblockextra.commands;

import de.kiyan.skyblockextra.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMDhreload implements CommandExecutor
{
    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args )
    {
        if( ( cmd.getName( ).equalsIgnoreCase( "hreload" ) ) )
            if( sender.hasPermission( "harvest.reload" ) || sender.isOp( ) )
            {
                Main.instance.reloadConfig( );
                Main.instance.saveConfig( );
                sender.sendMessage( "§a§oHarvest configuration reloaded!" );
            } else
            {
                sender.sendMessage( "§c§oYou do not have permission to use this command!" );
                {
                }
            }
        return false;
    }

}
