package de.kiyan.skyblockextra.commands;

import de.kiyan.skyblockextra.utils.PotionSpawner;
import de.kiyan.skyblockextra.Config;
import de.kiyan.skyblockextra.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;

public class CMDSetpotion implements CommandExecutor
{
    private final List< String > potionList = Arrays.asList( "STRENGTH_1", "STRENGTH_2", "REGEN_1", "REGEN_2", "SPEED_1", "SPEED_2", "NIGHT_VISION", "SLOWNESS", "WEAKNESS", "LEAPING_1", "LEAPING_2", "INVISIBLE", "FIRE_RESISTANCE" );

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args )
    {
        if( !( sender instanceof Player ) )
        {
            sender.sendMessage( "§cYou must be a player" );

            return false;
        }

        if( !label.equalsIgnoreCase( "setpotion" ) )
        {
            return false;
        }

        Player player = ( Player ) sender;

        if( !player.hasPermission( "skyblock.potion" ) )
        {
            player.sendMessage( Config.PotionPrefix + " §cYou dont have enough permissions!" );

            return false;
        }

        if( args.length == 0 )
        {
            player.sendMessage( Config.PotionPrefix + " §7Please use those following PotionTypes:" );
            player.sendMessage( Config.PotionPrefix + " §e/setpotion add <PotionType> <Timspan>" );
            player.sendMessage( Config.PotionPrefix + " §d" + potionList );

            return true;
        }
        if( args[ 0 ].equalsIgnoreCase( "load" ) )
        {
            player.sendMessage( Config.PotionPrefix + " §aPotions are loaded." );
            PotionSpawner.SpawnPotions( );
        }

        if( args[ 0 ].equalsIgnoreCase( "clear" ) )
        {
            player.sendMessage( Config.PotionPrefix + " §aYou cleared all potions on worlds." );
            PotionSpawner.ClearAllPotions( );
        }

        if( args[ 0 ].equalsIgnoreCase( "add" ) )
        {
            if( args.length == 1 )
            {
                player.sendMessage( Config.PotionPrefix + " §ePlease provide a Type." );

                return true;
            }

            if( args.length > 1 )
            {
                if( args[ 1 ].equalsIgnoreCase( "STRENGTH_1" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.STRENGTH, "1", Integer.parseInt( args[ 2 ] ) );
                    PotionSpawner.SpawnPotions( );

                    return true;
                } else if( args[ 1 ].equalsIgnoreCase( "STRENGTH_2" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.STRENGTH, "2", Integer.parseInt( args[ 2 ] ) );
                    PotionSpawner.SpawnPotions( );

                    return true;
                } else if( args[ 1 ].equalsIgnoreCase( "REGEN_1" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.REGEN, "1", Integer.parseInt( args[ 2 ] ) );
                    PotionSpawner.SpawnPotions( );

                    return true;
                } else if( args[ 1 ].equalsIgnoreCase( "REGEN_2" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.REGEN, "2", Integer.parseInt( args[ 2 ] ) );
                    PotionSpawner.SpawnPotions( );

                    return true;
                } else if( args[ 1 ].equalsIgnoreCase( "SPEED_1" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.SPEED, "1", Integer.parseInt( args[ 2 ] ) );
                    PotionSpawner.SpawnPotions( );

                    return true;
                } else if( args[ 1 ].equalsIgnoreCase( "SPEED_2" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.SPEED, "2", Integer.parseInt( args[ 2 ] ) );
                    PotionSpawner.SpawnPotions( );

                    return true;
                } else if( args[ 1 ].equalsIgnoreCase( "NIGHT_VISION" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.NIGHT_VISION, "1", Integer.parseInt( args[ 2 ] ) );
                    PotionSpawner.SpawnPotions( );

                    return true;
                } else if( args[ 1 ].equalsIgnoreCase( "SLOWNESS" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.SLOWNESS, "1", Integer.parseInt( args[ 2 ] ) );
                    PotionSpawner.SpawnPotions( );

                    return true;
                } else if( args[ 1 ].equalsIgnoreCase( "WEAKNESS" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.WEAKNESS, "1", Integer.parseInt( args[ 2 ] ) );
                    PotionSpawner.SpawnPotions( );

                    return true;
                } else if( args[ 1 ].equalsIgnoreCase( "LEAPING_1" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.JUMP, "1", Integer.parseInt( args[ 2 ] ) );
                    PotionSpawner.SpawnPotions( );

                    return true;
                } else if( args[ 1 ].equalsIgnoreCase( "LEAPING_2" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.JUMP, "2", Integer.parseInt( args[ 2 ] ) );

                    return true;
                } else if( args[ 1 ].equalsIgnoreCase( "INVISIBLE" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.INVISIBILITY, "1", Integer.parseInt( args[ 2 ] ) );
                    PotionSpawner.SpawnPotions( );

                    return true;
                } else if( args[ 1 ].equalsIgnoreCase( "FIRE_RESISTANCE" ) && Utils.isInt( args[ 2 ] ) )
                {
                    PotionSpawner.writePotionPosition( player, PotionType.FIRE_RESISTANCE, "1", Integer.parseInt( args[ 2 ] ) );
                    PotionSpawner.SpawnPotions( );

                    return true;
                } else
                {
                    player.sendMessage( Config.PotionPrefix + " §cYour type is invalid. please do /setpotion to see all." );
                    PotionSpawner.SpawnPotions( );

                    return false;
                }
            }
            else
            {
                player.sendMessage( Config.PotionPrefix + " §cPlease provide a timespan." );

                return false;
            }
        }

        return false;
    }
}
