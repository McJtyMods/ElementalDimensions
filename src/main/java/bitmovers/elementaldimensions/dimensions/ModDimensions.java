package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.dimensions.providers.*;
import bitmovers.elementaldimensions.util.Config;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {

    public static DimensionType earthDimensionType;
    public static DimensionType waterDimensionType;
    public static DimensionType airDimensionType;
    public static DimensionType spiritDimensionType;
    public static DimensionType fireDimensionType;

    public static void preInit() {
        registerDimensionTypes();
        registerDimensions();
    }

    private static void registerDimensions() {
        DimensionManager.registerDimension(Config.Dimensions.Earth.dimensionID, earthDimensionType);
        DimensionManager.registerDimension(Config.Dimensions.Water.dimensionID, waterDimensionType);
        DimensionManager.registerDimension(Config.Dimensions.Air.dimensionID, airDimensionType);
        DimensionManager.registerDimension(Config.Dimensions.Spirit.dimensionID, spiritDimensionType);
        DimensionManager.registerDimension(Config.Dimensions.Fire.dimensionID, fireDimensionType);
    }

    private static void registerDimensionTypes() {

        /* AFAIK, the ID should be the dimension ID, example: http://prntscr.com/c1lmfb , Example 2: http://prntscr.com/c1lne0
            This ID also gets saved to the save files, so it shouldn't be dynamic
            Uncomment to revert
        earthDimensionType = DimensionType.register(ElementalDimensions.MODID, "_earth", fetchDimensionTypeId(), EarthWorldProvider.class, false);
        waterDimensionType = DimensionType.register(ElementalDimensions.MODID, "_water", fetchDimensionTypeId(), WaterWorldProvider.class, false);
        airDimensionType = DimensionType.register(ElementalDimensions.MODID, "_air", fetchDimensionTypeId(), AirWorldProvider.class, false);
        spiritDimensionType = DimensionType.register(ElementalDimensions.MODID, "_spirit", fetchDimensionTypeId(), SpiritWorldProvider.class, false);
        fireDimensionType = DimensionType.register(ElementalDimensions.MODID, "_fire", fetchDimensionTypeId(), FireWorldProvider.class, false);
        */

        earthDimensionType = DimensionType.register(ElementalDimensions.MODID, "_earth", Config.Dimensions.Earth.dimensionID, EarthWorldProvider.class, false);
        waterDimensionType = DimensionType.register(ElementalDimensions.MODID, "_water", Config.Dimensions.Water.dimensionID, WaterWorldProvider.class, false);
        airDimensionType = DimensionType.register(ElementalDimensions.MODID, "_air", Config.Dimensions.Air.dimensionID, AirWorldProvider.class, false);
        spiritDimensionType = DimensionType.register(ElementalDimensions.MODID, "_spirit", Config.Dimensions.Spirit.dimensionID, SpiritWorldProvider.class, false);
        fireDimensionType = DimensionType.register(ElementalDimensions.MODID, "_fire", Config.Dimensions.Fire.dimensionID, FireWorldProvider.class, false);

    }

    private static int fetchDimensionTypeId() {
        int id = -1;

        for (DimensionType type : DimensionType.values()) {
            if (type.getId() > id) {
                id = type.getId();
            }
        }
        id++;
        return id;
    }

}
