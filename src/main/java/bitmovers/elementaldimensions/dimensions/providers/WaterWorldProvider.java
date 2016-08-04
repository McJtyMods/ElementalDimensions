package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.WaterChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class WaterWorldProvider extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return DimensionRegister.waterDimensionType;
    }

    @Override
    public String getSaveFolder() {
        return "ELDIM_WATER";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new WaterChunkGenerator(worldObj);
    }
}
