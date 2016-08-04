package bitmovers.elementaldimensions.dimensions;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class EarthWorldProvider extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.earthDimensionType;
    }

    @Override
    public String getSaveFolder() {
        return "ELDIM_EARTH";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new EarthChunkGenerator(worldObj);
    }
}
