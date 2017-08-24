package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.AirChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import mcjty.lib.compat.CompatWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nonnull;

public class AirWorldProvider extends CompatWorldProvider {

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
        return new AirChunkGenerator(getWorld());
    }

    @Override
    public double getHorizon() {
        return 0.0D;
    }
}
