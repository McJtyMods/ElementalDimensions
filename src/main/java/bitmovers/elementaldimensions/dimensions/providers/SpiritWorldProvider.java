package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.SpiritChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import mcjty.lib.compat.CompatWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.IChunkGenerator;

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
        hasSkyLight = false;
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
