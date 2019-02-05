package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.varia.DelayedRegister;
import bitmovers.elementaldimensions.dimensions.ores.ElementalDustItem;
import bitmovers.elementaldimensions.items.*;
import bitmovers.elementaldimensions.ncLayer.dev.ItemSchematicCreator;
import elec332.core.ElecCore;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class ItemRegister {

    public static ItemWaterBossSeed waterBossSeed;
    public static ItemAirBossSeed airBossSeed;
    public static ItemFireBossSeed fireBossSeed;

    public static ItemElementalWand elementalWand;
    public static ItemFocusDamage focusDamage;
    public static ItemFocusDigging focusDigging;
    public static ItemFocusTeleport focusTeleport;

    public static ItemWaterRune runeOfWater;
    public static ItemAirRune runeOfAir;
    public static ItemSpiritRune runeOfSpirit;
    public static ItemFireRune runeOfFire;
    public static ItemEarthRune runeOfEarth;

    public static GenericItem runeOfWaterPart1;
    public static GenericItem runeOfWaterPart2;
    public static GenericItem runeOfWaterPart3;

    public static GenericItem runeOfAirPart1;
    public static GenericItem runeOfAirPart2;
    public static GenericItem runeOfAirPart3;

    public static GenericItem runeOfSpiritPart1;
    public static GenericItem runeOfSpiritPart2;
    public static GenericItem runeOfSpiritPart3;

    public static GenericItem runeOfFirePart1;
    public static GenericItem runeOfFirePart2;
    public static GenericItem runeOfFirePart3;

    public static ElementalDustItem elementalDustItem;

    public static void init(){

        if (ElecCore.developmentEnvironment){
            DelayedRegister.registerLater(new ItemSchematicCreator());
        }

        waterBossSeed = new ItemWaterBossSeed();
        airBossSeed = new ItemAirBossSeed();
        fireBossSeed = new ItemFireBossSeed();

        elementalWand = new ItemElementalWand();
        focusDamage = new ItemFocusDamage();
        focusDigging = new ItemFocusDigging();
        focusTeleport = new ItemFocusTeleport();

        runeOfWater = new ItemWaterRune();
        runeOfAir = new ItemAirRune();
        runeOfSpirit = new ItemSpiritRune();
        runeOfFire = new ItemFireRune();
        runeOfEarth = new ItemEarthRune();

        elementalDustItem = new ElementalDustItem();

        runeOfWaterPart1 = new GenericItem("waterrune_part1");
        runeOfWaterPart2 = new GenericItem("waterrune_part2");;
        runeOfWaterPart3 = new GenericItem("waterrune_part3");;

        runeOfAirPart1 = new GenericItem("airrune_part1");;
        runeOfAirPart2 = new GenericItem("airrune_part2");;
        runeOfAirPart3 = new GenericItem("airrune_part3");;

        runeOfSpiritPart1 = new GenericItem("spiritrune_part1");;
        runeOfSpiritPart2 = new GenericItem("spiritrune_part2");;
        runeOfSpiritPart3 = new GenericItem("spiritrune_part3");;

        runeOfFirePart1 = new GenericItem("firerune_part1");;
        runeOfFirePart2 = new GenericItem("firerune_part2");;
        runeOfFirePart3 = new GenericItem("firerune_part3");;
    }
}
