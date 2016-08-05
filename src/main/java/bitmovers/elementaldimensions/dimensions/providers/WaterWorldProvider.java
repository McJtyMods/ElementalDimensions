package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.WaterChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.IChunkGenerator;

import javax.annotation.Nonnull;

public class WaterWorldProvider extends WorldProvider {

    @Override
    @Nonnull
    public DimensionType getDimensionType() {
        return DimensionRegister.waterDimensionType;
    }

    @Override
    @Nonnull
    public String getSaveFolder() {
        return "ELDIM_WATER";
    }

    @Override
    @Nonnull
    public IChunkGenerator createChunkGenerator() {
        return new WaterChunkGenerator(worldObj);
    }

    @Override
    protected void createBiomeProvider() {
        this.biomeProvider = new BiomeProvider(worldObj.getWorldInfo()) {

            @Override
            @Nonnull
            public Biome getBiome(@Nonnull BlockPos pos) {
                return Biomes.DEEP_OCEAN;
            }

            @Override
            @Nonnull
            public Biome getBiome(BlockPos pos, @Nonnull Biome defaultBiome) {
                return Biomes.DEEP_OCEAN;
            }

        };
    }

}
