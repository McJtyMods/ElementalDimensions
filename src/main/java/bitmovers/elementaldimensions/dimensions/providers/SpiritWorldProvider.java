package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.init.DimensionRegister;
import bitmovers.elementaldimensions.dimensions.generators.SpiritChunkGenerator;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class SpiritWorldProvider extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return DimensionRegister.spiritDimensionType;
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
