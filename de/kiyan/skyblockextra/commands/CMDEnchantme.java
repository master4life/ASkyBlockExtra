package de.kiyan.skyblockextra.commands;

import de.kiyan.skyblockextra.Config;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

import java.io.File;
import java.util.HashMap;

public class CMDEnchantme implements CommandExecutor
{
    private static Command instance;
    File fconfig;
    YamlConfiguration yamlFile;

    public static HashMap< Player, Integer > xphash;

    public CMDEnchantme( )
    {
        this.fconfig = new File( "plugins/EnchantmentGUI/config.yml" );
        this.yamlFile = YamlConfiguration.loadConfiguration( this.fconfig );
        this.xphash = new HashMap< Player, Integer >( );
    }

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args )
    {
        if( !( sender instanceof Player ) )
        {
            sender.sendMessage( "§cYou must be a player" );

            return false;
        }

        if( !label.equalsIgnoreCase( "enchantme" ) )
        {
            return false;
        }

        Player player = ( Player ) sender;

        if( !player.hasPermission( "subzero.enchantme" ) )
        {
            player.sendMessage( Config.EnchantMePrefix + " §cYou dont have enough permissions!" );

            return false;
        }

        if( args.length == 0 )
        {
            player.sendMessage( Config.EnchantMePrefix + " §c Use /enchantme <number>" );

            return false;
        }

        if( args.length > 0 )
        {
            Integer level = player.getLevel( );
            openGUI( player );
            ItemStack xpbot = new ItemStack( Material.EXP_BOTTLE );
            ItemMeta xpmeta = xpbot.getItemMeta( );
            Integer xp = Integer.parseInt( args[ 0 ] );
            xpmeta.setDisplayName( "§a" + args[ 0 ] + " Levels" );
            xpbot.setItemMeta( xpmeta );
            player.getOpenInventory( ).setItem( 21, xpbot );
            xphash.put( player, xp );

            return true;
        }

        return false;
    }

    private void openGUI( final Player player )
    {
        Inventory inv = Bukkit.getServer( ).createInventory( ( InventoryHolder ) null, 45, "§2EnchantMe§7 - SubZero" );
        ItemStack filler = new ItemStack( Material.STAINED_GLASS_PANE, 1, ( short ) 15 );
        ItemStack corner = new ItemStack( Material.STAINED_GLASS_PANE, 1, ( short ) 5 );
        ItemStack nether = new ItemStack( Material.NETHER_STAR, 1 );
        ItemStack table = new ItemStack( Material.ENCHANTMENT_TABLE, 1 );
        ItemStack barrier = new ItemStack( Material.BARRIER, 1 );
        ItemMeta barriermeta = barrier.getItemMeta( );
        barriermeta.setDisplayName( "§bItem Missing" );
        barrier.setItemMeta( barriermeta );
        final ItemStack book = new ItemStack( Material.ENCHANTED_BOOK, 1 );
        final ItemMeta bookmeta = book.getItemMeta( );
        bookmeta.setDisplayName( "§bDivine's Blessings" );
        book.setItemMeta( bookmeta );
        final ItemStack redpane = new ItemStack( Material.STAINED_GLASS_PANE, 1, ( short ) 14 );
        final ItemMeta redpanemeta = redpane.getItemMeta( );
        redpanemeta.setDisplayName( "§7Place Item Here" );
        redpane.setItemMeta( redpanemeta );
        inv.setItem( 0, corner );
        inv.setItem( 1, corner );
        inv.setItem( 7, corner );
        inv.setItem( 8, corner );
        inv.setItem( 9, corner );
        inv.setItem( 12, filler );
        inv.setItem( 13, barrier );
        inv.setItem( 14, filler );
        inv.setItem( 17, corner );
        inv.setItem( 18, nether );
        inv.setItem( 20, filler );
        inv.setItem( 22, redpane );
        inv.setItem( 23, book );
        inv.setItem( 24, filler );
        inv.setItem( 26, nether );
        inv.setItem( 27, corner );
        inv.setItem( 30, filler );
        inv.setItem( 31, table );
        inv.setItem( 32, filler );
        inv.setItem( 35, corner );
        inv.setItem( 36, corner );
        inv.setItem( 37, corner );
        inv.setItem( 43, corner );
        inv.setItem( 44, corner );
        player.openInventory( inv );
    }

}
