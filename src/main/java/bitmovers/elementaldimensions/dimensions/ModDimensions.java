package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.dimensions.providers.*;
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
        // Ids should be configurable! @todo
        DimensionManager.registerDimension(100, earthDimensionType);
        DimensionManager.registerDimension(101, waterDimensionType);
        DimensionManager.registerDimension(102, airDimensionType);
        DimensionManager.registerDimension(103, spiritDimensionType);
        DimensionManager.registerDimension(104, fireDimensionType);
    }

    private static void registerDimensionTypes() {
        earthDimensionType = DimensionType.register(ElementalDimensions.MODID, "_earth", fetchDimensionTypeId(), EarthWorldProvider.class, false);
        waterDimensionType = DimensionType.register(ElementalDimensions.MODID, "_water", fetchDimensionTypeId(), WaterWorldProvider.class, false);
        airDimensionType = DimensionType.register(ElementalDimensions.MODID, "_air", fetchDimensionTypeId(), AirWorldProvider.class, false);
        spiritDimensionType = DimensionType.register(ElementalDimensions.MODID, "_spirit", fetchDimensionTypeId(), SpiritWorldProvider.class, false);
        fireDimensionType = DimensionType.register(ElementalDimensions.MODID, "_fire", fetchDimensionTypeId(), FireWorldProvider.class, false);
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
