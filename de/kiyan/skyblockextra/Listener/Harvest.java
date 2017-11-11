package de.kiyan.skyblockextra.Listener;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import de.kiyan.skyblockextra.Config;
import de.kiyan.skyblockextra.Main;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutAnimation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Harvest implements Listener
{
    @SuppressWarnings( "deprecation" )
    @EventHandler
    public void RightClickBeetroot( PlayerInteractEvent event )
    {
        if( event.getAction( ) == Action.RIGHT_CLICK_BLOCK )
        {
            if( event.getPlayer( ).hasPermission( "harvest.use" ) || event.getPlayer( ).isOp( ) )
            {
                if( event.getClickedBlock( ).getType( ) == Material.BEETROOT_BLOCK && event.getClickedBlock( ).getData( ) == 3 )
                {
                    ItemStack bonemeal = new ItemStack( Material.INK_SACK, 1, ( short ) 15 );
                    if( Config.bBeetroot == true )
                    {
                        if( Config.bWorldGuard == true && Main.instance.getServer( ).getPluginManager( ).getPlugin( "WorldGuard" ) != null )
                        {
                            Player player = ( Player ) event.getPlayer( );
                            if( !isOnIsland( player ) )
                            {
                                return;
                            }

                            if( Main.PlayerWithinRegion( event.getPlayer( ), event.getClickedBlock( ).getLocation( ) ) )
                            {
                                if( Config.bAllowBoneMeal == false && event.getPlayer( ).getItemInHand( ).isSimilar( bonemeal ) )
                                {
                                    return;
                                } else
                                {
                                    event.getClickedBlock( ).setType( Material.AIR );
                                    Player p = event.getPlayer( );
                                    EntityPlayer ep = ( ( CraftPlayer ) p ).getHandle( );
                                    PacketPlayOutAnimation packet = new PacketPlayOutAnimation( ep, 0 );
                                    ( ( CraftPlayer ) p ).getHandle( ).playerConnection.sendPacket( packet );
                                    if( Config.bAutoReplant == true )
                                    {
                                        int upper = 2;
                                        Random random = new Random( );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.BEETROOT_SEEDS, random.nextInt( upper ) ) );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.BEETROOT, 1 ) );
                                        event.getClickedBlock( ).setType( Material.BEETROOT_BLOCK );
                                        if( Config.bplaySound == true )
                                        {
                                            event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                        }
                                    } else
                                    {
                                        int upper = 3;
                                        Random random = new Random( );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.BEETROOT_SEEDS, random.nextInt( upper ) ) );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.BEETROOT, 1 ) );
                                        if( Config.bplaySound == true )
                                        {
                                            event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                        }
                                    }
                                }
                            }
                        } else
                        {
                            if( Config.bAllowBoneMeal == false && event.getPlayer( ).getItemInHand( ).isSimilar( bonemeal ) )
                            {
                                return;
                            } else
                            {
                                event.getClickedBlock( ).setType( Material.AIR );
                                Player p = event.getPlayer( );
                                EntityPlayer ep = ( ( CraftPlayer ) p ).getHandle( );
                                PacketPlayOutAnimation packet = new PacketPlayOutAnimation( ep, 0 );
                                ( ( CraftPlayer ) p ).getHandle( ).playerConnection.sendPacket( packet );
                                if( Config.bAutoReplant == true )
                                {
                                    int upper = 2;
                                    Random random = new Random( );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.BEETROOT_SEEDS, random.nextInt( upper ) ) );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.BEETROOT, 1 ) );
                                    event.getClickedBlock( ).setType( Material.BEETROOT_BLOCK );
                                    if( Config.bplaySound == true )
                                    {
                                        event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                    }
                                } else
                                {
                                    int upper = 3;
                                    Random random = new Random( );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.BEETROOT_SEEDS, random.nextInt( upper ) ) );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.BEETROOT, 1 ) );
                                    if( Config.bplaySound == true )
                                    {
                                        event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings( "deprecation" )
    @EventHandler
    public void RightClickWheat( PlayerInteractEvent event )
    {
        if( event.getAction( ) == Action.RIGHT_CLICK_BLOCK )
        {
            if( event.getPlayer( ).hasPermission( "harvest.use" ) || event.getPlayer( ).isOp( ) )
            {
                if( event.getClickedBlock( ).getType( ) == Material.CROPS && event.getClickedBlock( ).getData( ) == 7 )
                {
                    ItemStack bonemeal = new ItemStack( Material.INK_SACK, 1, ( short ) 15 );
                    if( Config.bWheat == true )
                    {
                        if( Config.bWorldGuard == true && Main.instance.getServer( ).getPluginManager( ).getPlugin( "WorldGuard" ) != null )
                        {
                            Player player = ( Player ) event.getPlayer( );
                            if( !isOnIsland( player ) )
                            {
                                return;
                            }

                            if( WorldGuardPlugin.inst( ).canBuild( event.getPlayer( ), event.getClickedBlock( ).getLocation( ).getBlock( ) ) )
                            {
                                if( Config.bAllowBoneMeal == false && event.getPlayer( ).getItemInHand( ).isSimilar( bonemeal ) )
                                {
                                    return;
                                } else
                                {
                                    event.getClickedBlock( ).setType( Material.AIR );
                                    Player p = event.getPlayer( );
                                    EntityPlayer ep = ( ( CraftPlayer ) p ).getHandle( );
                                    PacketPlayOutAnimation packet = new PacketPlayOutAnimation( ep, 0 );
                                    ( ( CraftPlayer ) p ).getHandle( ).playerConnection.sendPacket( packet );
                                    if( Config.bAutoReplant == true )
                                    {
                                        int upper = 2;
                                        Random random = new Random( );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.SEEDS, random.nextInt( upper ) ) );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.WHEAT, 1 ) );
                                        event.getClickedBlock( ).setTypeId( 59 );
                                        if( Config.bplaySound == true )
                                        {
                                            event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, 1, 1 );
                                        }
                                    } else
                                    {
                                        int upper = 4;
                                        Random random = new Random( );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.SEEDS, random.nextInt( upper ) ) );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.WHEAT, 1 ) );
                                        if( Config.bplaySound == true )
                                        {
                                            event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, 1, 1 );
                                        }
                                    }
                                }
                            }
                        } else
                        {
                            if( Config.bAllowBoneMeal == false && event.getPlayer( ).getItemInHand( ).isSimilar( bonemeal ) )
                            {
                                return;
                            } else
                            {
                                event.getClickedBlock( ).setType( Material.AIR );
                                Player p = event.getPlayer( );
                                EntityPlayer ep = ( ( CraftPlayer ) p ).getHandle( );
                                PacketPlayOutAnimation packet = new PacketPlayOutAnimation( ep, 0 );
                                ( ( CraftPlayer ) p ).getHandle( ).playerConnection.sendPacket( packet );
                                if( Config.bAutoReplant == true )
                                {
                                    int upper = 2;
                                    Random random = new Random( );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.SEEDS, random.nextInt( upper ) ) );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.WHEAT, 1 ) );
                                    event.getClickedBlock( ).setTypeId( 59 );
                                    if( Config.bplaySound == true )
                                    {
                                        event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                    }
                                } else
                                {
                                    int upper = 4;
                                    Random random = new Random( );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.SEEDS, random.nextInt( upper ) ) );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.WHEAT, 1 ) );
                                    if( Config.bplaySound == true )
                                    {
                                        event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings( "deprecation" )
    @EventHandler
    public void RightClickPotato( PlayerInteractEvent event )
    {
        if( event.getAction( ) == Action.RIGHT_CLICK_BLOCK )
        {
            if( event.getPlayer( ).hasPermission( "harvest.use" ) || event.getPlayer( ).isOp( ) )
            {
                if( event.getClickedBlock( ).getType( ) == Material.POTATO && event.getClickedBlock( ).getData( ) == 7 )
                {
                    ItemStack bonemeal = new ItemStack( Material.INK_SACK, 1, ( short ) 15 );
                    if( Config.bPotato == true )
                    {
                        if( Config.bWorldGuard == true && Main.instance.getServer( ).getPluginManager( ).getPlugin( "WorldGuard" ) != null )
                        {
                            Player player = ( Player ) event.getPlayer( );
                            if( !isOnIsland( player ) )
                            {
                                return;
                            }

                            if( WorldGuardPlugin.inst( ).canBuild( event.getPlayer( ), event.getClickedBlock( ).getLocation( ).getBlock( ) ) )
                            {
                                if( Config.bAllowBoneMeal == false && event.getPlayer( ).getItemInHand( ).isSimilar( bonemeal ) )
                                {
                                    return;
                                } else
                                {
                                    event.getClickedBlock( ).setType( Material.AIR );
                                    Player p = event.getPlayer( );
                                    EntityPlayer ep = ( ( CraftPlayer ) p ).getHandle( );
                                    PacketPlayOutAnimation packet = new PacketPlayOutAnimation( ep, 0 );
                                    ( ( CraftPlayer ) p ).getHandle( ).playerConnection.sendPacket( packet );
                                    if( Config.bAutoReplant == true )
                                    {
                                        int upper = 3;
                                        Random random = new Random( );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.POTATO_ITEM, random.nextInt( upper ) ) );
                                        event.getClickedBlock( ).setType( Material.POTATO );
                                        if( Config.bplaySound == true )
                                        {
                                            event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                        }
                                    } else
                                    {
                                        int upper = 4;
                                        Random random = new Random( );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.POTATO_ITEM, random.nextInt( upper ) ) );
                                        if( Config.bplaySound == true )
                                        {
                                            event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                        }
                                    }
                                }
                            }
                        } else
                        {
                            if( Config.bAllowBoneMeal == false && event.getPlayer( ).getItemInHand( ).isSimilar( bonemeal ) )
                            {
                                return;
                            } else
                            {
                                event.getClickedBlock( ).setType( Material.AIR );
                                Player p = event.getPlayer( );
                                EntityPlayer ep = ( ( CraftPlayer ) p ).getHandle( );
                                PacketPlayOutAnimation packet = new PacketPlayOutAnimation( ep, 0 );
                                ( ( CraftPlayer ) p ).getHandle( ).playerConnection.sendPacket( packet );
                                if( Config.bAutoReplant == true )
                                {
                                    int upper = 3;
                                    Random random = new Random( );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.POTATO_ITEM, random.nextInt( upper ) ) );
                                    event.getClickedBlock( ).setType( Material.POTATO );
                                    if( Config.bplaySound == true )
                                    {
                                        event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                    }
                                } else
                                {
                                    int upper = 4;
                                    Random random = new Random( );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.POTATO_ITEM, random.nextInt( upper ) ) );
                                    if( Config.bplaySound == true )
                                    {
                                        event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings( "deprecation" )
    @EventHandler
    public void RightClickCarrot( PlayerInteractEvent event )
    {
        if( event.getAction( ) == Action.RIGHT_CLICK_BLOCK )
        {
            if( event.getPlayer( ).hasPermission( "harvest.use" ) || event.getPlayer( ).isOp( ) )
            {
                if( event.getClickedBlock( ).getType( ) == Material.CARROT && event.getClickedBlock( ).getData( ) == 7 )
                {
                    ItemStack bonemeal = new ItemStack( Material.INK_SACK, 1, ( short ) 15 );
                    if( Config.bCarrot == true )
                    {
                        if( Config.bWorldGuard == true && Main.instance.getServer( ).getPluginManager( ).getPlugin( "WorldGuard" ) != null )
                        {
                            Player player = ( Player ) event.getPlayer( );
                            if( !isOnIsland( player ) )
                            {
                                return;
                            }
                            if( WorldGuardPlugin.inst( ).canBuild( event.getPlayer( ), event.getClickedBlock( ).getLocation( ).getBlock( ) ) )
                            {
                                if( Config.bAllowBoneMeal == false && event.getPlayer( ).getItemInHand( ).isSimilar( bonemeal ) )
                                {
                                    return;
                                } else
                                {
                                    event.getClickedBlock( ).setType( Material.AIR );
                                    Player p = event.getPlayer( );
                                    EntityPlayer ep = ( ( CraftPlayer ) p ).getHandle( );
                                    PacketPlayOutAnimation packet = new PacketPlayOutAnimation( ep, 0 );
                                    ( ( CraftPlayer ) p ).getHandle( ).playerConnection.sendPacket( packet );
                                    if( Config.bAutoReplant == true )
                                    {
                                        int upper = 3;
                                        Random random = new Random( );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.CARROT_ITEM, random.nextInt( upper ) ) );
                                        event.getClickedBlock( ).setType( Material.CARROT );
                                        if( Config.bplaySound == true )
                                        {
                                            event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                        }
                                    } else
                                    {
                                        int upper = 4;
                                        Random random = new Random( );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.CARROT_ITEM, random.nextInt( upper ) ) );
                                        if( Config.bplaySound == true )
                                        {
                                            event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                        }
                                    }
                                }
                            }
                        } else
                        {
                            if( Config.bAllowBoneMeal == false && event.getPlayer( ).getItemInHand( ).isSimilar( bonemeal ) )
                            {
                                return;
                            } else
                            {
                                event.getClickedBlock( ).setType( Material.AIR );
                                Player p = event.getPlayer( );
                                EntityPlayer ep = ( ( CraftPlayer ) p ).getHandle( );
                                PacketPlayOutAnimation packet = new PacketPlayOutAnimation( ep, 0 );
                                ( ( CraftPlayer ) p ).getHandle( ).playerConnection.sendPacket( packet );
                                if( Config.bAutoReplant == true )
                                {
                                    int upper = 3;
                                    Random random = new Random( );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.CARROT_ITEM, random.nextInt( upper ) ) );
                                    event.getClickedBlock( ).setType( Material.CARROT );
                                    if( Config.bplaySound == true )
                                    {
                                        event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                    }
                                } else
                                {
                                    int upper = 4;
                                    Random random = new Random( );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.CARROT_ITEM, random.nextInt( upper ) ) );
                                    if( Config.bplaySound == true )
                                    {
                                        event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_GRASS_BREAK, Config.iVolume, Config.iPitch );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings( "deprecation" )
    @EventHandler
    public void RightClickNetherWart( PlayerInteractEvent event )
    {
        if( event.getAction( ) == Action.RIGHT_CLICK_BLOCK )
        {
            if( event.getPlayer( ).hasPermission( "harvest.use" ) || event.getPlayer( ).isOp( ) )
            {
                if( event.getClickedBlock( ).getType( ) == Material.NETHER_WARTS && event.getClickedBlock( ).getData( ) == 3 )
                {
                    if( Config.bNetherWart == true )
                    {
                        if( Config.bWorldGuard == true && Main.instance.getServer( ).getPluginManager( ).getPlugin( "WorldGuard" ) != null )
                        {
                            Player player = ( Player ) event.getPlayer( );
                            if( !isOnIsland( player ) )
                            {
                                return;
                            }

                            if( WorldGuardPlugin.inst( ).canBuild( event.getPlayer( ), event.getClickedBlock( ).getLocation( ).getBlock( ) ) )
                            {
                                event.getClickedBlock( ).setType( Material.AIR );
                                Player p = event.getPlayer( );
                                EntityPlayer ep = ( ( CraftPlayer ) p ).getHandle( );
                                PacketPlayOutAnimation packet = new PacketPlayOutAnimation( ep, 0 );
                                ( ( CraftPlayer ) p ).getHandle( ).playerConnection.sendPacket( packet );
                                if( Config.bAutoReplant == true )
                                {
                                    int upper = 3;
                                    Random random = new Random( );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.NETHER_STALK, 1 ) );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.NETHER_STALK, random.nextInt( upper ) ) );
                                    event.getClickedBlock( ).setType( Material.NETHER_WARTS );
                                    if( Config.bplaySound == true )
                                    {
                                        event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_STONE_BREAK, Config.iVolume, Config.iPitch );
                                    }
                                } else
                                {
                                    int upper = 4;
                                    Random random = new Random( );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.NETHER_STALK, 1 ) );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.NETHER_STALK, random.nextInt( upper ) ) );
                                    if( Config.bplaySound == true )
                                    {
                                        event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_STONE_BREAK, Config.iVolume, Config.iPitch );
                                    }
                                }
                            }
                        } else
                        {
                            event.getClickedBlock( ).setType( Material.AIR );
                            Player p = event.getPlayer( );
                            EntityPlayer ep = ( ( CraftPlayer ) p ).getHandle( );
                            PacketPlayOutAnimation packet = new PacketPlayOutAnimation( ep, 0 );
                            ( ( CraftPlayer ) p ).getHandle( ).playerConnection.sendPacket( packet );
                            if( Config.bAutoReplant == true )
                            {
                                int upper = 3;
                                Random random = new Random( );
                                event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.NETHER_STALK, 1 ) );
                                event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.NETHER_STALK, random.nextInt( upper ) ) );
                                event.getClickedBlock( ).setType( Material.NETHER_WARTS );
                                if( Config.bplaySound == true )
                                {
                                    event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_STONE_BREAK, Config.iVolume, Config.iPitch );
                                }
                            } else
                            {
                                int upper = 4;
                                Random random = new Random( );
                                event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.NETHER_STALK, 1 ) );
                                event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.NETHER_STALK, random.nextInt( upper ) ) );
                                if( Config.bplaySound == true )
                                {
                                    event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_STONE_BREAK, Config.iVolume, Config.iPitch );
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings( "deprecation" )
    @EventHandler
    public void RightClickCocoa( PlayerInteractEvent event )
    {
        if( event.getAction( ) == Action.RIGHT_CLICK_BLOCK )
        {
            if( event.getPlayer( ).hasPermission( "harvest.use" ) || event.getPlayer( ).isOp( ) )
            {
                if( event.getClickedBlock( ).getType( ) == Material.COCOA && event.getClickedBlock( ).getData( ) == 8 || event.getClickedBlock( ).getType( ) == Material.COCOA && event.getClickedBlock( ).getData( ) == 9 || event.getClickedBlock( ).getType( ) == Material.COCOA && event.getClickedBlock( ).getData( ) == 10 || event.getClickedBlock( ).getType( ) == Material.COCOA && event.getClickedBlock( ).getData( ) == 11 )
                {
                    ItemStack bonemeal = new ItemStack( Material.INK_SACK, 1, ( short ) 15 );
                    byte blockData = event.getClickedBlock( ).getData( );

                    if( Config.bCocoa == true )
                    {
                        if( Config.bWorldGuard == true && Main.instance.getServer( ).getPluginManager( ).getPlugin( "WorldGuard" ) != null )
                        {
                            Player player = ( Player ) event.getPlayer( );
                            if( !isOnIsland( player ) )
                            {
                                return;
                            }

                            if( WorldGuardPlugin.inst( ).canBuild( player, event.getClickedBlock( ).getLocation( ).getBlock( ) ) )
                            {
                                if( Config.bAllowBoneMeal == false && player.getItemInHand( ).isSimilar( bonemeal ) )
                                {
                                    return;
                                } else
                                {
                                    event.getClickedBlock( ).setType( Material.AIR );
                                    Player p = event.getPlayer( );
                                    EntityPlayer ep = ( ( CraftPlayer ) p ).getHandle( );
                                    PacketPlayOutAnimation packet = new PacketPlayOutAnimation( ep, 0 );
                                    ( ( CraftPlayer ) p ).getHandle( ).playerConnection.sendPacket( packet );
                                    if( Config.bAutoReplant == true )
                                    {
                                        int upper = 2;
                                        Random random = new Random( );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.INK_SACK, 1, ( short ) 3 ) );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.INK_SACK, random.nextInt( upper ), ( short ) 3 ) );
                                        event.getClickedBlock( ).setTypeIdAndData( 127, ( byte ) ( Integer.valueOf( blockData ) - 8 ), true );
                                        if( Config.bplaySound == true )
                                        {
                                            event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_STONE_BREAK, Config.iVolume, Config.iPitch );
                                        }
                                    } else
                                    {
                                        int upper = 2;
                                        Random random = new Random( );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.INK_SACK, 1, ( short ) 3 ) );
                                        event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.INK_SACK, random.nextInt( upper ), ( short ) 3 ) );
                                        if( Config.bplaySound == true )
                                        {
                                            event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_STONE_BREAK, Config.iVolume, Config.iPitch );
                                        }
                                    }
                                }
                            }
                        } else
                        {
                            if( Config.bAllowBoneMeal == false && event.getPlayer( ).getItemInHand( ).isSimilar( bonemeal ) )
                            {
                                return;
                            } else
                            {
                                event.getClickedBlock( ).setType( Material.AIR );
                                Player p = event.getPlayer( );
                                EntityPlayer ep = ( ( CraftPlayer ) p ).getHandle( );
                                PacketPlayOutAnimation packet = new PacketPlayOutAnimation( ep, 0 );
                                ( ( CraftPlayer ) p ).getHandle( ).playerConnection.sendPacket( packet );
                                if( Config.bAutoReplant == true )
                                {
                                    int upper = 2;
                                    Random random = new Random( );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.INK_SACK, 1, ( short ) 3 ) );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.INK_SACK, random.nextInt( upper ), ( short ) 3 ) );
                                    event.getClickedBlock( ).setTypeIdAndData( 127, ( byte ) ( Integer.valueOf( blockData ) - 8 ), true );
                                    if( Config.bplaySound == true )
                                    {
                                        event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_STONE_BREAK, Config.iVolume, Config.iPitch );
                                    }
                                } else
                                {
                                    int upper = 2;
                                    Random random = new Random( );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.INK_SACK, 1, ( short ) 3 ) );
                                    event.getClickedBlock( ).getLocation( ).getWorld( ).dropItemNaturally( event.getClickedBlock( ).getLocation( ), new ItemStack( Material.INK_SACK, random.nextInt( upper ), ( short ) 3 ) );
                                    if( Config.bplaySound == true )
                                    {
                                        event.getPlayer( ).getWorld( ).playSound( event.getPlayer( ).getLocation( ), Sound.BLOCK_STONE_BREAK, Config.iVolume, Config.iPitch );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isOnIsland( Player player )
    {
        ASkyBlockAPI Askyblock = ASkyBlockAPI.getInstance();
        Location loc = player.getLocation();

        if( Askyblock == null )
            return false;

        if( Askyblock.getOwner( loc ) == null )
        {
            return false;
        }
        if( Askyblock.getIslandAt( loc ).getMembers().contains( player.getUniqueId() ) )
        {
                        return true;
        }

        return false;
    }
}
