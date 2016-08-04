package bitmovers.elementaldimensions.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static RuneOfWater runeOfWater;
    public static RuneOfAir runeOfAir;
    public static RuneOfSpirit runeOfSpirit;
    public static RuneOfFire runeOfFire;

    public static void preInit() {
        runeOfWater = new RuneOfWater();
        runeOfAir = new RuneOfAir();
        runeOfSpirit = new RuneOfSpirit();
        runeOfFire = new RuneOfFire();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {
        runeOfWater.initModel();
        runeOfAir.initModel();
        runeOfSpirit.initModel();
        runeOfFire.initModel();
    }

}
