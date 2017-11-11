package de.kiyan.skyblockextra;

import java.util.List;

public class Config
{
    public static String IslandConfigPrefix, Display;

    // Jump Potion
    public static String PotionPrefix;
    public static Double PotionMin, PotionMax;

    // disable crafting
    public static String DisablePrefix, WarnMessage;

    // Enchant me
    public static String EnchantMePrefix;

    // Harvest
    public static boolean bBeetroot, bCarrot, bPotato, bNetherWart, bWheat, bCocoa;
    public static boolean bWorldGuard;
    public static boolean bplaySound;
    public static Integer iVolume, iPitch;
    public static boolean bAutoReplant, bAllowBoneMeal;

    // Drop party
    public static String DropPartyPrefix;

    public void AssignVar( )
    {
        IslandConfigPrefix = Main.instance.getConfig( ).getString( "IslandConfig.Prefix" ).replaceAll( "&", "§" );
        Display = Main.instance.getConfig( ).getString( "IslandConfig.Display" ).replaceAll( "&", "§" );

        // Potion
        PotionPrefix = Main.instance.getConfig( ).getString( "Potions.PotionPrefix" ).replaceAll( "&", "§" );
        PotionMin = Main.instance.getConfig( ).getDouble( "Potions.JumpMin" );
        PotionMax = Main.instance.getConfig( ).getDouble( "Potions.JumpMax" );

        // Disable Crafting
        DisablePrefix = Main.instance.getConfig( ).getString( "DisableCrafting.DisablePrefix" ).replaceAll( "&", "§" );
        WarnMessage = Main.instance.getConfig( ).getString( "DisableCrafting.WarnMessage" ).replaceAll( "&", "§" );

        // Enchant Me
        EnchantMePrefix = Main.instance.getConfig( ).getString( "EnchantMe.EnchantPrefix" ).replaceAll( "&", "§" );

        //Harvest
        bBeetroot = Main.instance.getConfig( ).getBoolean( "Harvest.enabled-crops.beetroot" );
        bCarrot = Main.instance.getConfig( ).getBoolean( "Harvest.enabled-crops.carrot" );
        bPotato = Main.instance.getConfig( ).getBoolean( "Harvest.enabled-crops.potato" );
        bNetherWart = Main.instance.getConfig( ).getBoolean( "Harvest.enabled-crops.nether_wart" );
        bWheat = Main.instance.getConfig( ).getBoolean( "Harvest.enabled-crops.wheat" );
        bCocoa = Main.instance.getConfig( ).getBoolean( "Harvest.enabled-crops.cocoa" );

        bWorldGuard = Main.instance.getConfig().getBoolean( "Harvest.hooks.WorldGuard" );

        bplaySound = Main.instance.getConfig().getBoolean( "Harvest.play-sound.enabled" );
        iVolume = Main.instance.getConfig().getInt( "Harvest.play-sound.volume" );
        iPitch = Main.instance.getConfig().getInt( "Harvest.play-sound.pitch" );

        bAutoReplant = Main.instance.getConfig().getBoolean( "Harvest.auto-replant" );
        bAllowBoneMeal = Main.instance.getConfig().getBoolean( "Harvest.allow-bonemeal-harvest" );

        // Drop party
        DropPartyPrefix = Main.instance.getConfig().getString( "DropParty.Prefix" ).replaceAll( "&", "§" );
    }

}
