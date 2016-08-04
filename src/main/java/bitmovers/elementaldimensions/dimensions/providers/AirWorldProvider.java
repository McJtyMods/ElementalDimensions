package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.ModDimensions;
import bitmovers.elementaldimensions.dimensions.generators.AirChunkGenerator;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class AirWorldProvider extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.airDimensionType;
    }

    @Override
    public String getSaveFolder() {
        return "ELDIM_AIR";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new AirChunkGenerator(worldObj);
    }
}
