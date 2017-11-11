package de.kiyan.skyblockextra;

import com.boydti.fawe.Fawe;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.patterns.BlockChance;
import com.sk89q.worldedit.patterns.RandomFillPattern;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import de.kiyan.skyblockextra.Listener.*;
import de.kiyan.skyblockextra.commands.*;
import de.kiyan.skyblockextra.utils.PotionSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin
{
    public static Main instance;
    public Hologram holo;

    private static WorldGuardPlugin worldguard;
    private Plugin holograph;

    public int time = 60;

    public Main getInstance( )
    {
        return instance;
    }

    @Override
    public void onEnable( )
    {
        instance = this;

        saveDefaultConfig( );
        reloadConfig( );

        new Config( ).AssignVar( );

        PluginManager pm = Bukkit.getServer( ).getPluginManager( );
        pm.registerEvents( new Create( ), this );
        pm.registerEvents( new Inventory( ), this );
        pm.registerEvents( new Fly( ), this );
        pm.registerEvents( new stopGrowth( ), this );
        pm.registerEvents( new BlockEnchant( ), this );
        pm.registerEvents( new PlayerMove( ), this );
        pm.registerEvents( new PlayerPickup( ), this );
        pm.registerEvents( new PlayerInteract( ), this );
        pm.registerEvents( new DisableCraft( ), this );
        pm.registerEvents( new RegionEvent( ), this );
        pm.registerEvents( new EventEnchantMe( ), this );
        pm.registerEvents( new Harvest( ), this );
        pm.registerEvents( new MonsterItemDrop(), this );
//        pm.registerEvents( new ItemDropInVoid(), this );

        this.getCommand( "isconfig" ).setExecutor( new CMDISConfig( ) );
        this.getCommand( "setpotion" ).setExecutor( new CMDSetpotion( ) );
        this.getCommand( "disablecraft" ).setExecutor( new CMDDisableCraft( ) );
        this.getCommand( "enchantme" ).setExecutor( new CMDEnchantme() );
//        this.getCommand( "dropparty" ).setExecutor( new CMDdropparty() );

        PotionSpawner.PotionRespawner( );
        PotionSpawner.PotionEffects( );

        worldguard = ( WorldGuardPlugin ) Bukkit.getServer( ).getPluginManager( ).getPlugin( "WorldGuard" );
        if( worldguard != null )
        {
            Bukkit.getServer( ).getScheduler( ).scheduleSyncRepeatingTask( this, new Runnable( )
            {
                @Override
                public void run( )
                {
                    Mine( );
                }
            }, 20 * 5, ( 20 * 60 ) * 60 );
        }
        holograph = pm.getPlugin( "HolographicDisplays" );

        if( holograph != null )
        {
            Bukkit.getServer( ).getScheduler( ).scheduleSyncRepeatingTask( this, new Runnable( )
            {
                @Override
                public void run( )
                {
                    if( holo != null )
                        holo.delete( );

                    holo = HologramsAPI.createHologram( Main.instance, new Location( Bukkit.getWorld( "world" ), -37, 78, 104 ) );

                    TextLine textLine1 = holo.appendTextLine( "§7[§b§lPublic Mine§7]" );
                    ItemLine itemLine2 = holo.appendItemLine( new ItemStack( Material.STONE ) );
                    TextLine testLine3 = holo.appendTextLine( "" );
                    TextLine textLine4 = holo.appendTextLine( "§cBe careful! PVP is enabled here!" );
                    TextLine textLine5 = holo.insertTextLine( 4, "§7Mine reset will be in: §4§l" + time + " §7Minutes" );

                    if( time == 0 )
                    {
                        TextLine textLine6 = holo.insertTextLine( 4, "§7Mine reset will be in: §4§l" + time + " §7Minutes" );

                        time = 60;
                        Mine( );

                        for( Player target : Bukkit.getOnlinePlayers( ) )
                            if( target.getWorld( ).getName( ).equalsIgnoreCase( "world" ) )
                                target.sendMessage( "§7[§3Public Mine§7] §eMine has been reseted." );

                        holo.delete( );
                    } else
                    {
                        time = time - 1;
                    }
                }
            }, 0, ( 20 * 60 ) );
        }

    }

    public void Mine( )
    {
        ProtectedRegion reg = Main.getWorldguard( ).getRegionManager( Bukkit.getWorld( "world" ) ).getRegion( "world_miner" );

        // 94%stone ,2%coalore ,1%goldore, 0.5%ironore, 0.05%diamondore
        EditSession editSession = new EditSessionBuilder( "world" ).fastmode( true ).build( );

        List< BlockChance > blocks = new ArrayList< BlockChance >( );
        BaseBlock stone = new BaseBlock( 1 );
        BaseBlock goldore = new BaseBlock( 14 );
        BaseBlock coalore = new BaseBlock( 16 );
        BaseBlock ironore = new BaseBlock( 15 );
        BaseBlock diamondore = new BaseBlock( 56 );

        blocks.add( new BlockChance( stone, 94 ) );
        blocks.add( new BlockChance( goldore, 1 ) );
        blocks.add( new BlockChance( coalore, 2 ) );
        blocks.add( new BlockChance( ironore, 0.5 ) );
        blocks.add( new BlockChance( diamondore, 0.05 ) );

        RandomFillPattern random = new RandomFillPattern( blocks );

        BlockVector v = reg.getMaximumPoint( );
        BlockVector v2 = reg.getMinimumPoint( );
        org.bukkit.util.Vector vv = new org.bukkit.util.Vector( v.getBlockX( ), v.getBlockY( ), v.getBlockZ( ) );
        org.bukkit.util.Vector vv2 = new org.bukkit.util.Vector( v2.getBlockX( ), v2.getBlockY( ), v2.getBlockZ( ) );
        CuboidSelection sel = new CuboidSelection( Bukkit.getWorld( "world" ), v, v2 );

        for( Player target : Bukkit.getOnlinePlayers( ) )
        {
            Location loc = target.getLocation( );
            if( loc.toVector( ).isInAABB( vv2, vv ) )
            {
                loc.setY( ( double ) 73 );
                target.teleport( loc );
            }
        }

        try
        {
            Region r = sel.getRegionSelector( ).getRegion( );
            editSession.setBlocks( r, random );
        } catch( IncompleteRegionException e )
        {
            e.printStackTrace( );
        }

        editSession.flushQueue( );
    }

    public static WorldGuardPlugin getWorldguard( )
    {
        return worldguard;
    }

    @Override
    public void onDisable( )
    {
        new stopGrowth( ).breakWarn.clear( );
        new stopGrowth( ).silkWarn.clear( );

        holo.delete( );
    }

    public static boolean PlayerWithinRegion( Player player, Location loc )
    {
        return Main.getWorldguard( ).canBuild( player, loc );
    }
}
