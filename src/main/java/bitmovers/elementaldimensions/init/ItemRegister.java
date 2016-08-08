package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.items.*;
import bitmovers.elementaldimensions.ncLayer.dev.ItemSchematicCreator;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import elec332.core.main.ElecCore;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class ItemRegister {

    public static ItemWaterBossSeed waterBossSeed;
    public static ItemAirBossSeed airBossSeed;

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

    public static void init(){

        if (ElecCore.developmentEnvironment){
            GameRegistry.register(new ItemSchematicCreator(), new EDResourceLocation("schematicCreator_DEV"));
        }

        waterBossSeed = new ItemWaterBossSeed();
        airBossSeed = new ItemAirBossSeed();

        elementalWand = new ItemElementalWand();
        focusDamage = new ItemFocusDamage();
        focusDigging = new ItemFocusDigging();
        focusTeleport = new ItemFocusTeleport();

        runeOfWater = new ItemWaterRune();
        runeOfAir = new ItemAirRune();
        runeOfSpirit = new ItemSpiritRune();
        runeOfFire = new ItemFireRune();
        runeOfEarth = new ItemEarthRune();

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

    public static void initCrafting() {
        GameRegistry.addShapelessRecipe(new ItemStack(runeOfAir), new ItemStack(runeOfAirPart1), new ItemStack(runeOfAirPart2), new ItemStack(runeOfAirPart3));
        GameRegistry.addShapelessRecipe(new ItemStack(runeOfWater), new ItemStack(runeOfWaterPart1), new ItemStack(runeOfWaterPart2), new ItemStack(runeOfWaterPart3));
        GameRegistry.addShapelessRecipe(new ItemStack(runeOfFire), new ItemStack(runeOfFirePart1), new ItemStack(runeOfFirePart2), new ItemStack(runeOfFirePart3));
        GameRegistry.addShapelessRecipe(new ItemStack(runeOfSpirit), new ItemStack(runeOfSpiritPart1), new ItemStack(runeOfSpiritPart2), new ItemStack(runeOfSpiritPart3));

        GameRegistry.addRecipe(new ItemStack(waterBossSeed), " s ", "sSs", " s ", 's', Items.WHEAT_SEEDS, 'S', Items.DIAMOND);
        GameRegistry.addRecipe(new ItemStack(airBossSeed), " s ", "sSs", " s ", 's', Items.WHEAT_SEEDS, 'S', Items.EMERALD);
    }

}
