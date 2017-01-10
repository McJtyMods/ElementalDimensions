package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.SpiritChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import elec332.core.world.AbstractWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraft.world.chunk.IChunkGenerator;

import javax.annotation.Nonnull;

public class SpiritWorldProvider extends AbstractWorldProvider {

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
    protected void setup() {
        super.setup();
        this.hasSkyLight = false;
        this.hasNoSky = true;
    }

    @Override
    public int getActualHeight() {
        return 256;
    }
    
}
