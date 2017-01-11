package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.SpiritChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import elec332.core.world.AbstractWorldProvider;
import mcjty.lib.compat.CompatWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraft.world.chunk.IChunkGenerator;

import javax.annotation.Nonnull;

public class SpiritWorldProvider extends CompatWorldProvider {

    @Override
    @Nonnull
    public DimensionType getDimensionType() {
        return DimensionRegister.spiritDimensionType;
    }

    @Override
    @Nonnull
    public String getSaveFolder() {
        return "ELDIM_SPIRIT";
    }

    @Override
    @Nonnull
    public IChunkGenerator createChunkGenerator() {
        return new SpiritChunkGenerator(getWorld());
    }

    public SpiritWorldProvider() {
        hasNoSky = true;
    }

    @Override
    protected void initialize() {
        super.initialize();
        disableSkyLight();
    }

    @Override
    public int getActualHeight() {
        return 256;
    }
    
}
