package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.ModDimensions;
import bitmovers.elementaldimensions.dimensions.generators.FireChunkGenerator;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class FireWorldProvider extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.fireDimensionType;
    }

    @Override
    public String getSaveFolder() {
        return "ELDIM_FIRE";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new FireChunkGenerator(worldObj);
    }
}
