package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.EarthChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import mcjty.lib.compat.CompatWorldProvider;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nonnull;

public class EarthWorldProvider extends CompatWorldProvider {

    @Override
    @Nonnull
    public DimensionType getDimensionType() {
        return DimensionRegister.earthDimensionType;
    }

    @Override
    @Nonnull
    public String getSaveFolder() {
        return "ELDIM_EARTH";
    }

    @Override
    @Nonnull
    public IChunkGenerator createChunkGenerator() {
        return new EarthChunkGenerator(getWorld());
    }

    @Override
    protected void initialize() {
        super.initialize();
        this.biomeProvider = new BiomeProvider(getWorld().getWorldInfo()) {

            @Override
            @Nonnull
            public Biome getBiome(@Nonnull BlockPos pos) {
                return Biomes.PLAINS;
            }

            @Override
            @Nonnull
            public Biome getBiome(BlockPos pos, @Nonnull Biome defaultBiome) {
                return Biomes.PLAINS;
            }
        };
    }

}
