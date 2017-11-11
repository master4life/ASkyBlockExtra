package de.kiyan.skyblockextra.Listener;

import de.kiyan.skyblockextra.commands.CMDEnchantme;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Random;

public class EventEnchantMe implements Listener
{
    private HashMap< Player, Material > materialHash = new HashMap< Player, Material >( );
    private HashMap< Player, ItemMeta > metaHash = new HashMap< Player, ItemMeta >( );

    @EventHandler
    public void invclose( InventoryCloseEvent event )
    {
        if( ChatColor.stripColor( event.getInventory( ).getName( ) ).equalsIgnoreCase( "EnchantMe - SubZero" ) )
        {
            Player player = ( Player ) event.getPlayer( );
            Integer level = Integer.parseInt( ChatColor.stripColor( event.getInventory( ).getItem( 21 ).getItemMeta( ).getDisplayName( ).replace( " Levels", "" ) ) );
            ItemStack item = event.getInventory( ).getItem( 22 );

            if( item == null )
                return;

            if( item.getType( ) != Material.STAINED_GLASS_PANE )
                player.getInventory( ).addItem( item );

            if( level == 5 )
            {
                player.getInventory( ).addItem( new ItemStack( Material.GRASS, 5 ) );
            } else if( level == 15 )
            {
                player.getInventory( ).addItem( new ItemStack( Material.GRASS, 10 ) );
            } else if( level == 30 )
            {
                player.getInventory( ).addItem( new ItemStack( Material.GRASS, 20 ) );
            }
        }
    }

    @EventHandler
    public void invitemdrag( InventoryDragEvent event )
    {
        if( ChatColor.stripColor( event.getInventory( ).getName( ) ).equalsIgnoreCase( "EnchantMe - SubZero" ) && event.getInventorySlots( ).equals( event.getRawSlots( ) ) )
        {
            event.setResult( Event.Result.DENY );
            event.setCancelled( true );
        }
    }


    public double randommultiplier( )
    {
        Random rmultiplier = new Random( );
        double multiplier = 0.8 + 0.3999999999999999 * rmultiplier.nextDouble( );
        return multiplier;
    }

    public Integer randomenchantid( )
    {
        Random renchantid = new Random( );
        Integer enchantid = renchantid.nextInt( 100 );
        return enchantid;
    }

    public Integer enchantmentlvl( Player player, Double divider, Double multiplier, Double enchantoffset )
    {
        Integer enchantmentlvl = ( int ) Math.ceil( CMDEnchantme.xphash.get( player ) * ( 1.0 / divider ) * multiplier * enchantoffset );
        return enchantmentlvl;
    }

    @EventHandler
    public void invclick( InventoryClickEvent event )
    {
        Player player = ( Player ) event.getWhoClicked( );
        if( ChatColor.stripColor( event.getInventory( ).getName( ) ).equalsIgnoreCase( "EnchantMe - SubZero" ) )
        {
            if( event.getSlot( ) == 22 && ChatColor.stripColor( event.getInventory( ).getName( ) ).equalsIgnoreCase( "EnchantMe - SubZero" ) && event.getRawSlot( ) == event.getSlot( ) )
            {
                if( event.getCursor( ).getAmount( ) > 1 )
                {
                    event.setCancelled( true );
                    event.setResult( Event.Result.DENY );
                    return;
                }
                if( event.getCursor( ).getType( ) != Material.AIR && event.getCurrentItem( ).getType( ) == Material.STAINED_GLASS_PANE )
                {
                    event.setCancelled( true );
                    event.setResult( Event.Result.DENY );
                    if( !event.getCursor( ).getEnchantments( ).isEmpty( ) )
                    {
                        return;
                    }
                    this.materialHash.put( player, event.getCursor( ).getType( ) );
                    this.metaHash.put( player, event.getCursor( ).getItemMeta( ) );
                    event.getClickedInventory( ).setItem( 22, event.getCursor( ) );
                    event.getCursor( ).setAmount( 0 );
                    ItemStack emblock = new ItemStack( Material.EMERALD_BLOCK, 1 );
                    ItemMeta metaS = emblock.getItemMeta( );
                    metaS.setDisplayName( ChatColor.GREEN + "Click to Enchant" );
                    metaS.addEnchant( Enchantment.KNOCKBACK, 1, true );
                    metaS.addItemFlags( new ItemFlag[]{ ItemFlag.HIDE_ENCHANTS } );
                    emblock.setItemMeta( metaS );
                    event.getClickedInventory( ).setItem( 13, emblock );
                    return;
                } else
                {
                    if( event.getCursor( ).getType( ) == Material.AIR && event.getCurrentItem( ).getType( ) != Material.STAINED_GLASS_PANE )
                    {
                        event.setCancelled( true );
                        event.setResult( Event.Result.DENY );
                        ItemStack redpane = new ItemStack( Material.STAINED_GLASS_PANE, 1, ( short ) 14 );
                        ItemMeta redpanemeta = redpane.getItemMeta( );
                        redpanemeta.setDisplayName( ChatColor.GRAY + "Place Item Here" );
                        redpane.setItemMeta( redpanemeta );
                        event.getClickedInventory( ).setItem( 22, redpane );
                        ItemStack returned = new ItemStack( ( Material ) this.materialHash.get( player ) );
                        ItemMeta returnedmeta = this.metaHash.get( player );
                        returned.setItemMeta( returnedmeta );
                        player.getInventory( ).addItem( new ItemStack[]{ returned } );
                        ItemStack block = new ItemStack( Material.BARRIER, 1 );
                        ItemMeta blockmeta = block.getItemMeta( );
                        blockmeta.setDisplayName( ChatColor.DARK_RED + "Item Missing" );
                        block.setItemMeta( blockmeta );
                        event.getClickedInventory( ).setItem( 13, block );
                        this.metaHash.remove( player );
                        this.materialHash.remove( player );
                        return;
                    }
                    if( event.getCurrentItem( ).getType( ) != Material.AIR && event.getCursor( ).getType( ) != Material.AIR )
                    {
                        event.setResult( Event.Result.DENY );
                        event.setCancelled( true );
                        return;
                    }
                }
            }
            if( event.getCurrentItem( ).getType( ) == Material.EMERALD_BLOCK && ChatColor.stripColor( event.getInventory( ).getItem( 13 ).getItemMeta( ).getDisplayName( ) ).equalsIgnoreCase( "Click to Enchant" ) )
            {
                event.setResult( Event.Result.DENY );
                event.setCancelled( true );
                ItemStack item = event.getInventory( ).getItem( 22 );
                ItemStack type = new ItemStack( item.getType( ) );
                Integer enchantments = 1;
                Double enchantoffset = 1.0;
                if( CMDEnchantme.xphash.get( player ) < 10 )
                {
                    Random rmoreenchants = new Random( );
                    Integer moreechants = rmoreenchants.nextInt( 100 );
                    if( moreechants >= 70 )
                    {
                        ++enchantments;
                        enchantoffset = 0.6;
                        Random rmoreenchants2 = new Random( );
                        Integer moreechants2 = rmoreenchants2.nextInt( 100 );
                        if( moreechants2 >= 80 )
                        {
                            ++enchantments;
                        }
                    }
                }
                if( CMDEnchantme.xphash.get( player ) < 20 && CMDEnchantme.xphash.get( player ) >= 10 )
                {
                    Random rmoreenchants = new Random( );
                    Integer moreechants = rmoreenchants.nextInt( 100 );
                    if( moreechants >= 60 )
                    {
                        ++enchantments;
                        enchantoffset = 0.7;
                        Random rmoreenchants2 = new Random( );
                        Integer moreechants2 = rmoreenchants2.nextInt( 100 );
                        if( moreechants2 >= 50 )
                        {
                            ++enchantments;
                        }
                    }
                }
                if( CMDEnchantme.xphash.get( player ) > 20 )
                {
                    Random rmoreenchants = new Random( );
                    Integer moreechants = rmoreenchants.nextInt( 100 );
                    if( moreechants >= 40 )
                    {
                        ++enchantments;
                        enchantoffset = 0.8;
                        Random rmoreenchants2 = new Random( );
                        Integer moreechants2 = rmoreenchants2.nextInt( 100 );
                        if( moreechants2 >= 25 )
                        {
                            ++enchantments;
                        }
                    }
                }
                if( type.getType( ) == Material.WOOD_AXE || type.getType( ) == Material.STONE_AXE || type.getType( ) == Material.IRON_AXE || type.getType( ) == Material.GOLD_AXE || type.getType( ) == Material.DIAMOND_AXE )
                {
                    for( Integer x = 1; x <= enchantments; ++x )
                    {
                        Double multiplier = randommultiplier( );
                        Integer enchantid = randomenchantid( );
                        if( enchantid >= 0 && enchantid <= 33 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DURABILITY, ( int ) enchantmentlvl );
                        } else if( enchantid >= 34 && enchantid <= 49 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.LOOT_BONUS_BLOCKS, ( int ) enchantmentlvl );
                        } else if( enchantid >= 50 && enchantid <= 100 )
                        {
                            Double divider = 6.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 5 )
                            {
                                enchantmentlvl = 5;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DIG_SPEED, ( int ) enchantmentlvl );
                        }
                    }
                } else if( type.getType( ) == Material.WOOD_SWORD || type.getType( ) == Material.STONE_SWORD || type.getType( ) == Material.IRON_SWORD || type.getType( ) == Material.GOLD_SWORD || type.getType( ) == Material.DIAMOND_SWORD )
                {
                    for( Integer x = 1; x <= enchantments; ++x )
                    {
                        Double multiplier = this.randommultiplier( );
                        Integer enchantid = this.randomenchantid( );
                        if( enchantid >= 0 && enchantid <= 49 && ( item.getEnchantments( ).keySet( ).contains( Enchantment.DAMAGE_ALL ) || item.getEnchantments( ).keySet( ).contains( Enchantment.DAMAGE_UNDEAD ) || item.getEnchantments( ).keySet( ).contains( Enchantment.DAMAGE_ARTHROPODS ) ) )
                        {
                            enchantid = 100 - ( 50 - enchantid );
                        }
                        if( enchantid >= 0 && enchantid <= 24 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 5 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DAMAGE_ALL, ( int ) enchantmentlvl );
                        } else if( enchantid >= 25 && enchantid <= 37 )
                        {
                            Double divider = 6.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 5 )
                            {
                                enchantmentlvl = 5;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DAMAGE_UNDEAD, ( int ) enchantmentlvl );
                        } else if( enchantid >= 38 && enchantid <= 49 )
                        {
                            Double divider = 6.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 5 )
                            {
                                enchantmentlvl = 5;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DAMAGE_ARTHROPODS, ( int ) enchantmentlvl );
                        } else if( enchantid >= 50 && enchantid <= 58 )
                        {
                            Double divider = 15.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 2 )
                            {
                                enchantmentlvl = 2;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.FIRE_ASPECT, ( int ) enchantmentlvl );
                        } else if( enchantid >= 59 && enchantid <= 73 )
                        {
                            Double divider = 15.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 2 )
                            {
                                enchantmentlvl = 2;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.KNOCKBACK, ( int ) enchantmentlvl );
                        } else if( enchantid >= 74 && enchantid <= 91 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DURABILITY, ( int ) enchantmentlvl );
                        } else if( enchantid >= 92 && enchantid <= 100 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.LOOT_BONUS_MOBS, ( int ) enchantmentlvl );
                        }
                    }
                } else if( type.getType( ) == Material.WOOD_HOE || type.getType( ) == Material.STONE_HOE || type.getType( ) == Material.IRON_HOE || type.getType( ) == Material.GOLD_HOE || type.getType( ) == Material.DIAMOND_HOE )
                {
                    for( Integer x = 1; x <= enchantments; ++x )
                    {
                        Double multiplier = this.randommultiplier( );
                        Integer enchantid = this.randomenchantid( );
                        if( enchantid >= 0 && enchantid <= 100 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DURABILITY, ( int ) enchantmentlvl );
                        }
                    }
                } else if( type.getType( ) == Material.WOOD_PICKAXE || type.getType( ) == Material.STONE_PICKAXE || type.getType( ) == Material.IRON_PICKAXE || type.getType( ) == Material.GOLD_PICKAXE || type.getType( ) == Material.DIAMOND_PICKAXE || type.getType( ) == Material.WOOD_SPADE || type.getType( ) == Material.STONE_SPADE || type.getType( ) == Material.IRON_SPADE || type.getType( ) == Material.GOLD_SPADE || type.getType( ) == Material.DIAMOND_SPADE )
                {
                    for( Integer x = 1; x <= enchantments; ++x )
                    {
                        Double multiplier = this.randommultiplier( );
                        Integer enchantid = this.randomenchantid( );
                        if( enchantid >= 0 && enchantid <= 29 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DURABILITY, ( int ) enchantmentlvl );
                        } else if( enchantid >= 30 && enchantid <= 59 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.LOOT_BONUS_BLOCKS, ( int ) enchantmentlvl );
                        } else if( enchantid >= 60 && enchantid <= 85 )
                        {
                            Double divider = 6.00;
                            Integer enchantmentlvl = enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 5 )
                                enchantmentlvl = 5;
                            if( enchantmentlvl < 1 )
                                enchantmentlvl = 1;
                            item.addEnchantment( Enchantment.DIG_SPEED, enchantmentlvl );
                        } else if( enchantid >= 86 && enchantid <= 100 )
                        {
                            Double divider = 6.00;
                            Integer enchantmentlvl = enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 1 )
                                enchantmentlvl = 1;
                            if( enchantmentlvl < 1 )
                                enchantmentlvl = 1;
                            item.addEnchantment( Enchantment.SILK_TOUCH, enchantmentlvl );
                        }
                    }
                } else if( type.getType( ) == Material.LEATHER_HELMET || type.getType( ) == Material.IRON_HELMET || type.getType( ) == Material.GOLD_HELMET || type.getType( ) == Material.DIAMOND_HELMET )
                {
                    for( Integer x = 1; x <= enchantments; ++x )
                    {
                        Double multiplier = this.randommultiplier( );
                        Integer enchantid = this.randomenchantid( );
                        if( enchantid >= 0 && enchantid <= 53 && ( item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_ENVIRONMENTAL ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_EXPLOSIONS ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_FIRE ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_PROJECTILE ) ) )
                        {
                            enchantid = 100 - ( 54 - enchantid );
                        }
                        if( enchantid >= 0 && enchantid <= 24 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_ENVIRONMENTAL, ( int ) enchantmentlvl );
                        } else if( enchantid >= 25 && enchantid <= 36 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_PROJECTILE, ( int ) enchantmentlvl );
                        } else if( enchantid >= 37 && enchantid <= 41 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_EXPLOSIONS, ( int ) enchantmentlvl );
                        } else if( enchantid >= 42 && enchantid <= 53 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_FIRE, ( int ) enchantmentlvl );
                        } else if( enchantid >= 54 && enchantid <= 63 )
                        {
                            Double divider = 30.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.WATER_WORKER, ( int ) enchantmentlvl );
                        } else if( enchantid >= 64 && enchantid <= 73 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.OXYGEN, ( int ) enchantmentlvl );
                        } else if( enchantid >= 74 && enchantid <= 95 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DURABILITY, ( int ) enchantmentlvl );
                        } else if( enchantid >= 96 && enchantid <= 100 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.THORNS, ( int ) enchantmentlvl );
                        }
                    }
                } else if( type.getType( ) == Material.LEATHER_BOOTS || type.getType( ) == Material.IRON_BOOTS || type.getType( ) == Material.GOLD_BOOTS || type.getType( ) == Material.DIAMOND_BOOTS )
                {
                    for( Integer x = 1; x <= enchantments; ++x )
                    {
                        Double multiplier = this.randommultiplier( );
                        Integer enchantid = this.randomenchantid( );
                        if( enchantid >= 0 && enchantid <= 56 && ( item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_ENVIRONMENTAL ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_EXPLOSIONS ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_FIRE ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_PROJECTILE ) ) )
                        {
                            enchantid = 100 - ( 57 - enchantid );
                        }
                        if( enchantid >= 0 && enchantid <= 25 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_ENVIRONMENTAL, ( int ) enchantmentlvl );
                        } else if( enchantid >= 26 && enchantid <= 37 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_PROJECTILE, ( int ) enchantmentlvl );
                        } else if( enchantid >= 38 && enchantid <= 43 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_EXPLOSIONS, ( int ) enchantmentlvl );
                        } else if( enchantid >= 44 && enchantid <= 56 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_FIRE, ( int ) enchantmentlvl );
                        } else if( enchantid >= 57 && enchantid <= 67 )
                        {
                            Double divider = 15.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 2 )
                            {
                                enchantmentlvl = 2;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DEPTH_STRIDER, ( int ) enchantmentlvl );
                        } else if( enchantid >= 68 && enchantid <= 92 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DURABILITY, ( int ) enchantmentlvl );
                        } else if( enchantid >= 93 && enchantid <= 100 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.THORNS, ( int ) enchantmentlvl );
                        }
                    }
                } else if( type.getType( ) == Material.LEATHER_LEGGINGS || type.getType( ) == Material.IRON_LEGGINGS || type.getType( ) == Material.GOLD_LEGGINGS || type.getType( ) == Material.DIAMOND_LEGGINGS )
                {
                    for( Integer x = 1; x <= enchantments; ++x )
                    {
                        Double multiplier = this.randommultiplier( );
                        Integer enchantid = this.randomenchantid( );
                        if( enchantid >= 0 && enchantid <= 60 && ( item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_ENVIRONMENTAL ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_EXPLOSIONS ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_FIRE ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_PROJECTILE ) ) )
                        {
                            enchantid = 100 - ( 61 - enchantid );
                        }
                        if( enchantid >= 0 && enchantid <= 26 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_ENVIRONMENTAL, ( int ) enchantmentlvl );
                        } else if( enchantid >= 27 && enchantid <= 39 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_PROJECTILE, ( int ) enchantmentlvl );
                        } else if( enchantid >= 40 && enchantid <= 46 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_EXPLOSIONS, ( int ) enchantmentlvl );
                        } else if( enchantid >= 47 && enchantid <= 60 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_FIRE, ( int ) enchantmentlvl );
                        } else if( enchantid >= 61 && enchantid <= 86 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DURABILITY, ( int ) enchantmentlvl );
                        } else if( enchantid >= 87 && enchantid <= 100 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.THORNS, ( int ) enchantmentlvl );
                        }
                    }
                } else if( type.getType( ) == Material.LEATHER_CHESTPLATE || type.getType( ) == Material.IRON_CHESTPLATE || type.getType( ) == Material.GOLD_CHESTPLATE || type.getType( ) == Material.DIAMOND_CHESTPLATE )
                {
                    for( Integer x = 1; x <= enchantments; ++x )
                    {
                        Double multiplier = this.randommultiplier( );
                        Integer enchantid = this.randomenchantid( );
                        if( enchantid >= 0 && enchantid <= 60 && ( item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_ENVIRONMENTAL ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_EXPLOSIONS ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_FIRE ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_PROJECTILE ) ) )
                        {
                            enchantid = 100 - ( 61 - enchantid );
                        }
                        if( enchantid >= 0 && enchantid <= 26 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_ENVIRONMENTAL, ( int ) enchantmentlvl );
                        } else if( enchantid >= 27 && enchantid <= 39 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_PROJECTILE, ( int ) enchantmentlvl );
                        } else if( enchantid >= 40 && enchantid <= 46 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_EXPLOSIONS, ( int ) enchantmentlvl );
                        } else if( enchantid >= 47 && enchantid <= 60 )
                        {
                            Double divider = 7.5;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 4 )
                            {
                                enchantmentlvl = 4;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.PROTECTION_FIRE, ( int ) enchantmentlvl );
                        } else if( enchantid >= 61 && enchantid <= 86 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DURABILITY, ( int ) enchantmentlvl );
                        } else if( enchantid >= 87 && enchantid <= 100 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.THORNS, ( int ) enchantmentlvl );
                        }
                    }
                } else if( type.getType( ) == Material.BOW )
                {
                    for( Integer x = 1; x <= enchantments; ++x )
                    {
                        Double multiplier = this.randommultiplier( );
                        Integer enchantid = this.randomenchantid( );
                        if( enchantid >= 0 && enchantid <= 19 )
                        {
                            Double divider = 6.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 5 )
                            {
                                enchantmentlvl = 5;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.ARROW_DAMAGE, ( int ) enchantmentlvl );
                        } else if( enchantid >= 20 && enchantid <= 39 )
                        {
                            Double divider = 15.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 2 )
                            {
                                enchantmentlvl = 2;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.ARROW_KNOCKBACK, ( int ) enchantmentlvl );
                        } else if( enchantid >= 40 && enchantid <= 59 )
                        {
                            Double divider = 15.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 2 )
                            {
                                enchantmentlvl = 2;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.ARROW_FIRE, ( int ) enchantmentlvl );
                        } else if( enchantid >= 60 && enchantid <= 74 )
                        {
                            Double divider = 30.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.ARROW_INFINITE, ( int ) enchantmentlvl );
                        } else if( enchantid >= 75 && enchantid <= 100 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DURABILITY, ( int ) enchantmentlvl );
                        }
                    }
                } else if( type.getType( ) == Material.FISHING_ROD )
                {
                    for( Integer x = 1; x <= enchantments; ++x )
                    {
                        Double multiplier = this.randommultiplier( );
                        Integer enchantid = this.randomenchantid( );
                        if( enchantid >= 0 && enchantid <= 39 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.DURABILITY, ( int ) enchantmentlvl );
                        } else if( enchantid >= 40 && enchantid <= 60 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.LUCK, ( int ) enchantmentlvl );
                        } else if( enchantid >= 61 && enchantid <= 100 )
                        {
                            Double divider = 10.0;
                            Integer enchantmentlvl = this.enchantmentlvl( player, divider, multiplier, enchantoffset );
                            if( enchantmentlvl > 3 )
                            {
                                enchantmentlvl = 3;
                            }
                            if( enchantmentlvl < 1 )
                            {
                                enchantmentlvl = 1;
                            }
                            item.addEnchantment( Enchantment.LURE, ( int ) enchantmentlvl );
                        }
                    }
                } else if( type.getType( ) == Material.BOOK )
                {
                    item.setType( Material.ENCHANTED_BOOK );
                    EnchantmentStorageMeta esm = ( EnchantmentStorageMeta ) item.getItemMeta( );
                    for( Integer x2 = 1; x2 <= enchantments; ++x2 )
                    {
                        Double multiplier2 = this.randommultiplier( );
                        Integer enchantid2 = this.randomenchantid( );
                        if( enchantid2 >= 0 && enchantid2 <= 15 && ( item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_ENVIRONMENTAL ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_EXPLOSIONS ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_FIRE ) || item.getEnchantments( ).keySet( ).contains( Enchantment.PROTECTION_PROJECTILE ) ) )
                        {
                            enchantid2 = 100 - ( 16 - enchantid2 );
                        }
                        if( enchantid2 >= 0 && enchantid2 <= 3 )
                        {
                            Double divider2 = 7.5;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 4 )
                            {
                                enchantmentlvl2 = 4;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.PROTECTION_ENVIRONMENTAL, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 4 && enchantid2 <= 7 )
                        {
                            Double divider2 = 7.5;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 4 )
                            {
                                enchantmentlvl2 = 4;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.PROTECTION_PROJECTILE, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 8 && enchantid2 <= 11 )
                        {
                            Double divider2 = 7.5;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 4 )
                            {
                                enchantmentlvl2 = 4;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.PROTECTION_EXPLOSIONS, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 12 && enchantid2 <= 15 )
                        {
                            Double divider2 = 7.5;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 4 )
                            {
                                enchantmentlvl2 = 4;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.PROTECTION_FIRE, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 16 && enchantid2 <= 19 )
                        {
                            Double divider2 = 10.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 3 )
                            {
                                enchantmentlvl2 = 3;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.DURABILITY, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 20 && enchantid2 <= 23 )
                        {
                            Double divider2 = 10.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 3 )
                            {
                                enchantmentlvl2 = 3;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.THORNS, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 24 && enchantid2 <= 27 )
                        {
                            Double divider2 = 10.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 3 )
                            {
                                enchantmentlvl2 = 3;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.LUCK, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 28 && enchantid2 <= 31 )
                        {
                            Double divider2 = 10.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 3 )
                            {
                                enchantmentlvl2 = 3;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.LURE, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 32 && enchantid2 <= 35 )
                        {
                            Double divider2 = 6.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 5 )
                            {
                                enchantmentlvl2 = 5;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.ARROW_DAMAGE, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 36 && enchantid2 <= 39 )
                        {
                            Double divider2 = 15.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 2 )
                            {
                                enchantmentlvl2 = 2;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.ARROW_KNOCKBACK, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 40 && enchantid2 <= 543 )
                        {
                            Double divider2 = 15.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 2 )
                            {
                                enchantmentlvl2 = 2;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.ARROW_FIRE, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 44 && enchantid2 <= 47 )
                        {
                            Double divider2 = 30.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.ARROW_INFINITE, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 48 && enchantid2 <= 51 )
                        {
                            Double divider2 = 15.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 2 )
                            {
                                enchantmentlvl2 = 2;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.DEPTH_STRIDER, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 52 && enchantid2 <= 55 )
                        {
                            Double divider2 = 30.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.WATER_WORKER, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 56 && enchantid2 <= 59 )
                        {
                            Double divider2 = 10.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 3 )
                            {
                                enchantmentlvl2 = 3;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.OXYGEN, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 60 && enchantid2 <= 63 )
                        {
                            Double divider2 = 10.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 3 )
                            {
                                enchantmentlvl2 = 3;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.LOOT_BONUS_BLOCKS, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 64 && enchantid2 <= 67 )
                        {
                            Double divider2 = 6.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 5 )
                            {
                                enchantmentlvl2 = 5;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.DIG_SPEED, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 68 && enchantid2 <= 71 )
                        {
                            Double divider2 = 6.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 5 )
                            {
                                enchantmentlvl2 = 5;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.DAMAGE_ALL, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 72 && enchantid2 <= 75 )
                        {
                            Double divider2 = 6.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 5 )
                            {
                                enchantmentlvl2 = 5;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.DAMAGE_UNDEAD, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 76 && enchantid2 <= 79 )
                        {
                            Double divider2 = 6.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 5 )
                            {
                                enchantmentlvl2 = 5;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.DAMAGE_ARTHROPODS, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 80 && enchantid2 <= 83 )
                        {
                            Double divider2 = 15.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 2 )
                            {
                                enchantmentlvl2 = 2;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.FIRE_ASPECT, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 84 && enchantid2 <= 87 )
                        {
                            Double divider2 = 15.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 2 )
                            {
                                enchantmentlvl2 = 2;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.KNOCKBACK, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 88 && enchantid2 <= 91 )
                        {
                            Double divider2 = 6.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 5 )
                            {
                                enchantmentlvl2 = 5;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.DAMAGE_UNDEAD, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 92 && enchantid2 <= 96 )
                        {
                            Double divider2 = 10.0;
                            Integer enchantmentlvl2 = this.enchantmentlvl( player, divider2, multiplier2, enchantoffset );
                            if( enchantmentlvl2 > 3 )
                            {
                                enchantmentlvl2 = 3;
                            }
                            if( enchantmentlvl2 < 1 )
                            {
                                enchantmentlvl2 = 1;
                            }
                            esm.addStoredEnchant( Enchantment.DURABILITY, ( int ) enchantmentlvl2, true );
                        } else if( enchantid2 >= 97 && enchantid2 <= 100 )
                        {
                            Double divider = 10.00;
                            Integer enchantmentlvl = enchantmentlvl( player, divider, multiplier2, enchantoffset );
                            if( enchantmentlvl > 1 )
                                enchantmentlvl = 1;
                            if( enchantmentlvl < 1 )
                                enchantmentlvl = 1;
                            esm.addStoredEnchant( Enchantment.SILK_TOUCH, enchantmentlvl, true );
                        }
                    }
                    item.setItemMeta( ( ItemMeta ) esm );
                }

                player.getInventory( ).addItem( new ItemStack[]{ item } );
                event.getClickedInventory( ).setItem( 22, new ItemStack( Material.AIR ) );
                this.metaHash.remove( player );
                this.materialHash.remove( player );
                player.closeInventory( );
                return;
            }
            if( event.getRawSlot( ) == event.getSlot( ) )
            {
                event.setResult( Event.Result.DENY );
                event.setCancelled( true );
            }
            if( event.getCurrentItem( ).getType( ) == ( Material.STONE_HOE ) || event.getCurrentItem( ).getType( ) == Material.STONE_AXE || event.getCurrentItem( ).getType( ) == Material.STONE_PICKAXE || event.getCurrentItem( ).getType( ) == Material.STONE_SPADE || event.getCurrentItem( ).getType( ) == Material.STONE_SWORD || event.getCurrentItem( ).getType( ) == Material.IRON_HELMET || event.getCurrentItem( ).getType( ) == Material.IRON_CHESTPLATE || event.getCurrentItem( ).getType( ) == Material.IRON_LEGGINGS || event.getCurrentItem( ).getType( ) == Material.IRON_BOOTS || event.getCurrentItem( ).getType( ) == Material.IRON_HOE || event.getCurrentItem( ).getType( ) == Material.IRON_AXE || event.getCurrentItem( ).getType( ) == Material.IRON_SWORD || event.getCurrentItem( ).getType( ) == Material.IRON_SPADE || event.getCurrentItem( ).getType( ) == Material.IRON_PICKAXE || event.getCurrentItem( ).getType( ) == Material.GOLD_HELMET || event.getCurrentItem( ).getType( ) == Material.GOLD_CHESTPLATE || event.getCurrentItem( ).getType( ) == Material.GOLD_LEGGINGS || event.getCurrentItem( ).getType( ) == Material.GOLD_BOOTS || event.getCurrentItem( ).getType( ) == Material.GOLD_HOE || event.getCurrentItem( ).getType( ) == Material.GOLD_AXE || event.getCurrentItem( ).getType( ) == Material.GOLD_SWORD || event.getCurrentItem( ).getType( ) == Material.GOLD_SPADE || event.getCurrentItem( ).getType( ) == Material.GOLD_PICKAXE || event.getCurrentItem( ).getType( ) == Material.DIAMOND_HELMET || event.getCurrentItem( ).getType( ) == Material.DIAMOND_CHESTPLATE || event.getCurrentItem( ).getType( ) == Material.DIAMOND_LEGGINGS || event.getCurrentItem( ).getType( ) == Material.DIAMOND_BOOTS || event.getCurrentItem( ).getType( ) == Material.DIAMOND_HOE || event.getCurrentItem( ).getType( ) == Material.DIAMOND_AXE || event.getCurrentItem( ).getType( ) == Material.DIAMOND_SWORD || event.getCurrentItem( ).getType( ) == Material.DIAMOND_SPADE || event.getCurrentItem( ).getType( ) == Material.DIAMOND_PICKAXE || event.getCurrentItem( ).getType( ) == Material.BOW || event.getCurrentItem( ).getType( ) == Material.AIR || event.getCurrentItem( ).getType( ) == Material.LEATHER_LEGGINGS || event.getCurrentItem( ).getType( ) == Material.LEATHER_HELMET || event.getCurrentItem( ).getType( ) == Material.LEATHER_CHESTPLATE || event.getCurrentItem( ).getType( ) == Material.LEATHER_BOOTS || event.getCurrentItem( ).getType( ) == ( Material.WOOD_HOE ) || event.getCurrentItem( ).getType( ) == Material.WOOD_AXE || event.getCurrentItem( ).getType( ) == Material.WOOD_PICKAXE || event.getCurrentItem( ).getType( ) == Material.WOOD_SPADE || event.getCurrentItem( ).getType( ) == Material.WOOD_SWORD || event.getCurrentItem( ).getType( ) == Material.BOOK || event.getCurrentItem( ).getType( ) == Material.FISHING_ROD )
            {
            } else
            {
                event.setResult( Event.Result.DENY );
                event.setCancelled( true );
            }
        }
    }
}
