package de.kiyan.skyblockextra.utils;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.*;

public class Utils
{
    static Random rand = new Random( );

    public static String FString( String Message )
    {
        return ChatColor.translateAlternateColorCodes( '&', Message );
    }

    public static int randInt( int min, int max )
    {
        int randomNum = rand.nextInt( ( max - min ) + 1 ) + min;
        return randomNum;
    }

    public static double randDouble( double min, double max )
    {
        double result = Math.random( ) * ( max - min ) + min;
        return result;
    }

    public static boolean isInt( String s )
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

    public static int GetFreeSlot( Player player )
    {
        for( int i = 0; i < 36; i++ )
        {
            if( player.getInventory( ).getItem( i ) == null || player.getInventory( ).getItem( i ).getType( ) == Material.AIR )
            {
                return i;
            }
        }
        return -1;
    }

    public static int removeItem( Inventory inventory, Material Mat, short meta, int quantity, String Name, List< String > Lore )
    {
        int rest = quantity;
        for( int i = 0; i < inventory.getSize( ); i++ )
        {
            ItemStack stack = inventory.getItem( i );
            if( stack == null || stack.getType( ) != Mat )
                continue;
            if( stack.getDurability( ) != meta )
            {
                continue;
            }

            if( stack.hasItemMeta( ) == false || stack.getItemMeta( ).hasDisplayName( ) == false || stack.getItemMeta( ).hasLore( ) == false )
            {
                continue;
            }

            if( stack.getItemMeta( ).getLore( ).equals( Lore ) == false )
            {
                continue;
            }

            if( ChatColor.stripColor( stack.getItemMeta( ).getDisplayName( ) ).equals( ChatColor.stripColor( Name ) ) == false )
            {
                continue;
            }
            if( rest >= stack.getAmount( ) )
            {
                rest -= stack.getAmount( );
                inventory.clear( i );
            } else if( rest > 0 )
            {
                stack.setAmount( stack.getAmount( ) - rest );
                rest = 0;
            } else
            {
                break;
            }
        }
        return quantity - rest;
    }

    public static int removeItem( Inventory inventory, Material Mat, short meta, int quantity, String Name )
    {
        int rest = quantity;
        for( int i = 0; i < inventory.getSize( ); i++ )
        {
            ItemStack stack = inventory.getItem( i );
            if( stack == null || stack.getType( ) != Mat )
                continue;
            if( stack.getDurability( ) != meta )
            {
                continue;
            }
            if( stack.hasItemMeta( ) == false || stack.getItemMeta( ).hasDisplayName( ) == false )
            {
                continue;

            }
            if( ChatColor.stripColor( ChatColor.stripColor( stack.getItemMeta( ).getDisplayName( ) ) ).equals( Name ) == false )
            {
                continue;
            }
            if( rest >= stack.getAmount( ) )
            {
                rest -= stack.getAmount( );
                inventory.clear( i );
            } else if( rest > 0 )
            {
                stack.setAmount( stack.getAmount( ) - rest );
                rest = 0;
            } else
            {
                break;
            }
        }
        return quantity - rest;
    }

    public static int removeItem( Inventory inventory, Material Mat, short meta, int quantity )
    {
        int rest = quantity;
        for( int i = 0; i < inventory.getSize( ); i++ )
        {
            ItemStack stack = inventory.getItem( i );
            if( stack == null || stack.getType( ) != Mat )
                continue;
            if( stack.getDurability( ) != meta )
            {
                continue;
            }
            if( rest >= stack.getAmount( ) )
            {
                rest -= stack.getAmount( );
                inventory.clear( i );
            } else if( rest > 0 )
            {
                stack.setAmount( stack.getAmount( ) - rest );
                rest = 0;
            } else
            {
                break;
            }
        }
        return quantity - rest;
    }

    public static int removeItem( Inventory inventory, ItemStack ItemR, int quantity )
    {
        int rest = quantity;
        for( int i = 0; i < inventory.getSize( ); i++ )
        {
            ItemStack stack = inventory.getItem( i );
            if( stack == null || ItemR == null )
            {
                continue;
            }

            if( stack.getType( ).equals( ItemR.getType( ) ) == false )
            {
                continue;
            }

            if( ( stack.getDurability( ) == ItemR.getDurability( ) ) == false )
            {
                continue;
            }

            if( ( stack.getItemMeta( ).equals( ItemR.getItemMeta( ) ) == false ) )
            {
                continue;
            }

            if( rest >= stack.getAmount( ) )
            {
                rest -= stack.getAmount( );
                inventory.clear( i );
            } else if( rest > 0 )
            {
                stack.setAmount( stack.getAmount( ) - rest );
                rest = 0;
            } else
            {
                break;
            }
        }
        return quantity - rest;
    }

    public static ItemStack CIFI( Material ItemType, String ItemName, int Amount, String[] ItemLore, Byte Data, String[] Enchatments )
    {
        List< String > Lore = new ArrayList< String >( );
        ItemStack is = new ItemStack( ItemType, Amount );
        is.setDurability( Data );
        ItemMeta im = is.getItemMeta( );
        im.setDisplayName( ChatColor.translateAlternateColorCodes( '&', ItemName ) );
        for( String s : ItemLore )
        {
            Lore.add( ChatColor.translateAlternateColorCodes( '&', s ) );
        }
        im.setLore( Lore );
        is.setItemMeta( im );
        if( Enchatments != null && Enchatments.length > 0 )
        {
            for( int i = 0; i < Enchatments.length; i++ )
            {
                if( Enchatments[ i ] != null )
                {
                    String[] DataString = Enchatments[ i ].split( "#" );
                    String[] args = Arrays.copyOfRange( DataString, 0, DataString.length );

                    String Type = args[ 0 ];
                    int Power = Integer.parseInt( args[ 1 ] );
                    is.addUnsafeEnchantment( Enchantment.getByName( Type ), Power );
                } else
                {
                    break;
                }
            }
            is.getItemMeta( ).addItemFlags( ItemFlag.HIDE_ENCHANTS );
        }
        return is;
    }

    public static int GetItemAmount( Player player, ItemStack ItemSearchingFor, Boolean IgnoreLore, Boolean IgnoreName, Boolean Result_ExcludeNamed, Boolean Result_ExcludeLore )
    {
        int amount = 0;

        for( ItemStack is : player.getInventory( ) )
        {
            if( is != null )
            {

                ItemStack is1 = ItemSearchingFor.clone( );
                ItemMeta is1Meta = is1.getItemMeta( );
                ItemStack is2 = is.clone( );
                ItemMeta is2Meta = is2.getItemMeta( );

                if( Result_ExcludeNamed && is2Meta.hasDisplayName( ) )
                {
                    continue;
                }

                if( Result_ExcludeLore && is2Meta.hasLore( ) )
                {
                    continue;
                }

                if( IgnoreName )
                {
                    is1Meta.setDisplayName( "-" );
                    is2Meta.setDisplayName( "-" );
                }

                if( IgnoreLore )
                {
                    List< String > Wipe = new ArrayList<>( );
                    is1Meta.setLore( Wipe );
                    is2Meta.setLore( Wipe );
                }

                is1.setItemMeta( is1Meta );
                is2.setItemMeta( is2Meta );
                is1.setAmount( 1 );
                is2.setAmount( 1 );

                if( is1.equals( is2 ) )
                {
                    amount = amount + is.getAmount( );
                }
            }
        }
        return amount;
    }

    public static ItemStack CIFI( Material ItemType, String ItemName, int Amount, List< String > ItemLore, Byte Data, String[] Enchatments )
    {
        List< String > Lore = new ArrayList< String >( );
        ItemStack is = new ItemStack( ItemType, Amount );
        is.setDurability( Data );
        ItemMeta im = is.getItemMeta( );
        im.setDisplayName( ChatColor.translateAlternateColorCodes( '&', ItemName ) );
        for( String s : ItemLore )
        {
            Lore.add( ChatColor.translateAlternateColorCodes( '&', s ) );
        }
        im.setLore( Lore );
        is.setItemMeta( im );
        if( Enchatments != null && Enchatments.length > 0 )
        {
            for( int i = 0; i < Enchatments.length; i++ )
            {
                if( Enchatments[ i ] != null )
                {
                    String[] DataString = Enchatments[ i ].split( "#" );
                    String[] args = Arrays.copyOfRange( DataString, 0, DataString.length );

                    String Type = args[ 0 ];
                    int Power = Integer.parseInt( args[ 1 ] );
                    is.addUnsafeEnchantment( Enchantment.getByName( Type ), Power );
                } else
                {
                    break;
                }
            }
            is.getItemMeta( ).addItemFlags( ItemFlag.HIDE_ENCHANTS );
        }
        return is;
    }

    public static boolean FinePercentChance( double Chance )
    {
        double Result = Utils.randDouble( 0.01, 100.00 );
        double factor = 1e2;
        Result = Math.round( Result * factor ) / factor;

        if( Result <= Chance )
        {
            return true;
        }
        return false;
    }

    public static OfflinePlayer GetPlayer( )
    {
        UUID ID = UUID.fromString( "450ea709-19af-4b95-8fb1-89e00ce0fd47" );

        if( Bukkit.getOfflinePlayer( ID ) == null || Bukkit.getOfflinePlayer( ID ).hasPlayedBefore( ) == false )
        {
            return null;
        }
        return Bukkit.getOfflinePlayer( ID );

    }

    public static void SendErrorToOpPlayers( String Message )
    {

        for( Player p : Bukkit.getOnlinePlayers( ) )
        {
            if( p.isOp( ) )
            {
                p.sendMessage( ChatColor.translateAlternateColorCodes( '&', Message ) );
            }
        }

    }

    public static void SendTitles( Player p, String title, String subtitle )
    {
        PlayerConnection connection = ( ( CraftPlayer ) p ).getHandle( ).playerConnection;
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a( "{\"text\":\"" + ChatColor.translateAlternateColorCodes( '&', title ) + "\"}" );
        IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a( "{\"text\":\"" + ChatColor.translateAlternateColorCodes( '&', subtitle ) + "\"}" );
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle( PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON );
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle( PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON );
        connection.sendPacket( titlePacket );
        connection.sendPacket( subtitlePacket );
    }

    public static void PushEntityAwayFromLocation( Location Source, Entity entity, double Power, double VertPower )
    {
        Vector unitVector = entity.getLocation( ).toVector( ).subtract( Source.toVector( ) ).normalize( );
        entity.setVelocity( unitVector.multiply( Power ).setY( VertPower ) );
    }

    public static boolean PercentChance( int Chance )
    {
        if( Math.random( ) * 100 < Chance )
        {
            return true;
        } else
        {
            return false;
        }
    }

}
