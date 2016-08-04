package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.ModDimensions;
import bitmovers.elementaldimensions.dimensions.generators.SpiritChunkGenerator;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class SpiritWorldProvider extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.spiritDimensionType;
    }

    @Override
    public String getSaveFolder() {
        return "ELDIM_SPIRIT";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new SpiritChunkGenerator(worldObj);
    }
}
