package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.dimensions.Dimensions;
import bitmovers.elementaldimensions.dimensions.providers.*;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegister {

    public static DimensionType earthDimensionType;
    public static DimensionType waterDimensionType;
    public static DimensionType airDimensionType;
    public static DimensionType spiritDimensionType;
    public static DimensionType fireDimensionType;

    public static void preInit() {
        registerDimensionTypes();
        registerDimensions();
    }

    private static void registerDimensionTypes() {
        earthDimensionType = DimensionType.register(ElementalDimensions.MODID, "_earth", Dimensions.EARTH.getDimensionID(), EarthWorldProvider.class, false);
        waterDimensionType = DimensionType.register(ElementalDimensions.MODID, "_water", Dimensions.WATER.getDimensionID(), WaterWorldProvider.class, false);
        airDimensionType = DimensionType.register(ElementalDimensions.MODID, "_air", Dimensions.AIR.getDimensionID(), AirWorldProvider.class, false);
        spiritDimensionType = DimensionType.register(ElementalDimensions.MODID, "_spirit", Dimensions.SPIRIT.getDimensionID(), SpiritWorldProvider.class, false);
        fireDimensionType = DimensionType.register(ElementalDimensions.MODID, "_fire", Dimensions.FIRE.getDimensionID(), FireWorldProvider.class, false);
    }

    private static void registerDimensions() {
        DimensionManager.registerDimension(Dimensions.EARTH.getDimensionID(), earthDimensionType);
        DimensionManager.registerDimension(Dimensions.WATER.getDimensionID(), waterDimensionType);
        DimensionManager.registerDimension(Dimensions.AIR.getDimensionID(), airDimensionType);
        DimensionManager.registerDimension(Dimensions.SPIRIT.getDimensionID(), spiritDimensionType);
        DimensionManager.registerDimension(Dimensions.FIRE.getDimensionID(), fireDimensionType);
    }

}
