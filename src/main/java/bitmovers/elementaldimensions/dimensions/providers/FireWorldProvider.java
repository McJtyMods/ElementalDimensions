package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.init.DimensionRegister;
import bitmovers.elementaldimensions.dimensions.generators.FireChunkGenerator;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class FireWorldProvider extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return DimensionRegister.fireDimensionType;
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
