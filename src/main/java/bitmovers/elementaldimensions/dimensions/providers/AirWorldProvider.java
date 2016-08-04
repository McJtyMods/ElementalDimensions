package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.AirChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class AirWorldProvider extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return DimensionRegister.airDimensionType;
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
