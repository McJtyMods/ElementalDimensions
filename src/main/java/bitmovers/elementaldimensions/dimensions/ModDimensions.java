package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.ElementalDimensions;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {

    public static DimensionType dimensionType;

    public static void preInit() {
        registerDimensionType();
        registerDimensions();
    }

    private static void registerDimensions() {
        int id1 = 100;   // @todo Make configurable
        DimensionManager.registerDimension(id1, dimensionType);
    }

    private static void registerDimensionType() {
        int id = -1;

        for (DimensionType type : DimensionType.values()) {
            if (type.getId() > id) {
                id = type.getId();
            }
        }
        id++;
        dimensionType = DimensionType.register(ElementalDimensions.MODID, "_dim", id, ElementalWorldProvider.class, false);
    }

}
