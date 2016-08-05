package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.items.RuneOfAir;
import bitmovers.elementaldimensions.items.RuneOfFire;
import bitmovers.elementaldimensions.items.RuneOfSpirit;
import bitmovers.elementaldimensions.items.RuneOfWater;
import bitmovers.elementaldimensions.ncLayer.dev.ItemSchematicCreator;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import elec332.core.main.ElecCore;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class ItemRegister {

    public static RuneOfWater runeOfWater;
    public static RuneOfAir runeOfAir;
    public static RuneOfSpirit runeOfSpirit;
    public static RuneOfFire runeOfFire;

    public static void init(){
        if (ElecCore.developmentEnvironment){
            GameRegistry.register(new ItemSchematicCreator(), new EDResourceLocation("schematicCreator_DEV"));
        }
        runeOfWater = new RuneOfWater();
        runeOfAir = new RuneOfAir();
        runeOfSpirit = new RuneOfSpirit();
        runeOfFire = new RuneOfFire();
    }

}
