package bitmovers.elementaldimensions.dimensions;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class ElementalWorldProvider extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.dimensionType;
    }

    @Override
    public String getSaveFolder() {
        return "ELDIM" + getDimension();
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new EarthChunkGenerator(worldObj);
    }
}
