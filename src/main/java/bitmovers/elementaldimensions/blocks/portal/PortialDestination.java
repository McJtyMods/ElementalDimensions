package bitmovers.elementaldimensions.blocks.portal;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import net.minecraft.util.IStringSerializable;

public enum PortialDestination implements IStringSerializable {
    EARTH("earth", Dimensions.EARTH),
    WATER("water", Dimensions.WATER),
    AIR("air", Dimensions.AIR),
    SPIRIT("spirit", Dimensions.SPIRIT),
    FIRE("fire", Dimensions.FIRE);

    private final String name;
    private final Dimensions dimension;

    PortialDestination(String name, Dimensions dimension) {
        this.name = name;
        this.dimension = dimension;
    }

    @Override
    public String getName() {
        return name;
    }

    public Dimensions getDimension() {
        return dimension;
    }
}
