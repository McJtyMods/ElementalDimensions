package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.AirChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nonnull;

public class AirWorldProvider extends WorldProvider {

    @Override
    @Nonnull
    public DimensionType getDimensionType() {
        return DimensionRegister.airDimensionType;
    }

    @Override
    @Nonnull
    public String getSaveFolder() {
        return "ELDIM_AIR";
    }

    @Override
    @Nonnull
    public IChunkGenerator createChunkGenerator() {
        return new AirChunkGenerator(world);
    }

    @Override
    public double getHorizon() {
        return 0.0D;
    }
}
