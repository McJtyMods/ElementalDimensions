package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.FireChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import mcjty.lib.compat.CompatWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nonnull;

public class FireWorldProvider extends CompatWorldProvider {

    @Override
    @Nonnull
    public DimensionType getDimensionType() {
        return DimensionRegister.fireDimensionType;
    }

    @Override
    @Nonnull
    public String getSaveFolder() {
        return "ELDIM_FIRE";
    }

    @Override
    @Nonnull
    public IChunkGenerator createChunkGenerator() {
        return new FireChunkGenerator(getWorld());
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0.5F;
    }
}
