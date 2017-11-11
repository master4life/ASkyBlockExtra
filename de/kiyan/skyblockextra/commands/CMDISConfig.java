package de.kiyan.skyblockextra.commands;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import de.kiyan.skyblockextra.Config;
import de.kiyan.skyblockextra.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CMDISConfig implements CommandExecutor
{
    public Inventory modInventory;

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args )
    {
        if( !( sender instanceof Player ) )
        {
            sender.sendMessage( "§cYou must be a player" );

            return false;
        }

        if( !label.equalsIgnoreCase( "isconfig" ) )
        {
            return false;
        }

        Player player = ( Player ) sender;
        ASkyBlockAPI Askyblock = ASkyBlockAPI.getInstance();
        Location loc = player.getLocation();

        if( Askyblock == null )
            return false;

        if( Askyblock.getOwner( loc ) == null )
        {
            player.sendMessage( Config.IslandConfigPrefix + " §cYou are not the owner of this island." );

            return false;
        }
        if( !Askyblock.getIslandAt( loc ).getMembers().contains( player.getUniqueId() )  )
        {
            player.sendMessage( Config.IslandConfigPrefix + " §cYou are not the owner of this island." );

            return false;
        }

        if( !Askyblock.getIslandAt( loc ).onIsland( loc ) )
        {
            player.sendMessage( Config.IslandConfigPrefix + " §cYou are not on your island." );

            return false;
        }

        if( args.length == 0 )
        {
            modInventory = Bukkit.createInventory( player, 36, Config.Display );

            ItemBuilder i1, i2, i3, i4, i5, i6, i7, i8;

            i1 = createItem( player, Material.GOLD_SWORD,
                    "IslandConfig.PVP",
                    "§8PVP [§7-§8]",
                    "§ePVP [§aOn§e]",
                    "§ePVP [§cOff§e]",
                    "§aPVP is enabled on your island!",
                    "§cPVP is disabled on your island!",
                    "§7This allows you to combat your",
                    "§7opponent on your island." );

            i2 = createItem( player, Material.MONSTER_EGG,
                    "IslandConfig.MOB_SPAWN",
                    "§8Mob Spawning [§7-§8]",
                    "§cMob Spawning §e[§aOn§e]",
                    "§cMob Spawning §e[§cOff§e]",
                    "§aMob spawning is enabled on your island!",
                    "§cMob spawning is disabled on your island!",
                    "§7This lets you spawn animals and",
                    "§7monsters on your island." );

            i3 = createItem( player, Material.CARROT_ITEM,
                    "IslandConfig.BREEDING",
                    "§8Breeding [§7-§8]",
                    "§6Breeding §e[§aOn§e]",
                    "§6Breeding §e[§cOff§e]",
                    "§aBreeding is enabled on your island!",
                    "§cBreeding is disabled on your island!",
                    "§7This lets you breed your animals",
                    "§7on your island." );

            i4 = createItem( player, Material.SHEARS,
                    "IslandConfig.SHEARING",
                    "§8Shearing [§7-§8]",
                    "§fShearing §e[§aOn§e]",
                    "§fShearing §e[§cOff§e]",
                    "§aShearing is enabled on your island!",
                    "§cShearing is disabled on your island!",
                    "§7This lets you shear your animals",
                    "§7on your island." );

            i5 = createItem( player, Material.SKULL_ITEM,
                    "IslandConfig.MONSTER_SPAWN",
                    "§8Monster Spawning [§7-§8]",
                    "§7Monster Spawning §e[§aOn§e]",
                    "§7Monster Spawning §e[§cOff§e]",
                    "§aMonster spawning is enabled on your island!",
                    "§cMonster spawning is disabled on your island!",
                    "§7This lets you spawn monsters",
                    "§7on your island." );

            i6 = createItem( player, Material.FLINT_AND_STEEL,
                    "IslandConfig.FIRE",
                    "§8Fire [§7-§8]",
                    "§4Fire §e[§aOn§e]",
                    "§4Fire §e[§cOff§e]",
                    "§aFire is enabled on your island!",
                    "§cFire is disabled on your island!",
                    "§7This prevent fire spreading or fire generally",
                    "§7on your island." );

            i7 = createItem( player, Material.FEATHER,
                    "IslandConfig.FLY",
                    "§8Fly [§7-§8]",
                    "§dFly §e[§aOn§e]",
                    "§dFly §e[§cOff§e]",
                    "§aFly is enabled on your island!",
                    "§cFly is disabled on your island!",
                    "§7This allows you to fly",
                    "§7on your island." );

            i8 = createItem( player, Material.EMERALD,
                    "IslandConfig.VILLAGER_TRADING",
                    "§8Trading [§7-§8]",
                    "§3Trading §e[§aOn§e]",
                    "§3Trading §e[§cOff§e]",
                    "§aTrading is enabled on your island!",
                    "§cTrading is disabled on your island!",
                    "§7This allows you to trade with your",
                    "§7Villagers on your island." );

            modInventory.setItem( 10, i1.toItemStack( ) );
            modInventory.setItem( 12, i2.toItemStack( ) );
            modInventory.setItem( 14, i3.toItemStack( ) );
            modInventory.setItem( 16, i4.toItemStack( ) );
            modInventory.setItem( 19, i5.toItemStack( ) );
            modInventory.setItem( 21, i6.toItemStack( ) );
            modInventory.setItem( 23, i7.toItemStack( ) );
            modInventory.setItem( 25, i8.toItemStack( ) );

            player.openInventory( modInventory );
            return true;
        }

        if( args.length == 1 )
        {
            if( args[ 0 ].equalsIgnoreCase( "reload" ) )
            {
                new Config().AssignVar();

                player.sendMessage( Config.IslandConfigPrefix + " §aYou successfully reloaded the config!" );

                return true;
            }
        }

        return false;
    }

    private ItemBuilder createItem( Player player, Material material, String permission, String name, String On_1, String Off_1, String On_2, String Off_2, String lore1, String lore2 )
    {
        Island island = ASkyBlockAPI.getInstance( ).getIslandOwnedBy( player.getUniqueId( ) );
        ItemBuilder i1;

        if( player.hasPermission( permission ) )
        {
            String state = permission.substring( 13 );

            i1 = new ItemBuilder( material );
            i1.setName( island.getIgsFlag( Island.SettingsFlag.valueOf( state ) ) ? On_1 : Off_1 );
            i1.setLore( "",  island.getIgsFlag( Island.SettingsFlag.valueOf( state ) ) ? On_2 : Off_2, lore1, lore2 );
        } else
        {
            i1 = new ItemBuilder( Material.STAINED_GLASS_PANE, 1, ( short ) 15 );
            i1.setName( name );
            i1.setLore( "", "§f§nThis feature can be purchased at the §a§nshop§f§n.", lore1, lore2 );
        }

        return i1;
    }
}
